package com.example.FirstRainWidget;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;





import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class widgetclass extends AppWidgetProvider {

	public static String ACTION_WIDGET_CONFIGURE = "ConfigureWidget";
	public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
	public static String ACTION_WIDGET_RECEIVER2 = "ActionReceiverWidget";

	SharedPreferences sharedpreferences;
	Editor toEdit; 
	String Btninfo = "BTNINFO";
	String MyPREFERENCES = "WIDGET";



	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		//super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.d("Widget0520","update");

		// shared prefence history check

		String msg="First_Time";


		sharedpreferences = context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
		
		
		toEdit = sharedpreferences.edit();
		toEdit.putString(Btninfo, "LoggedIn"); 
		toEdit.commit();

		if(sharedpreferences.contains(Btninfo)){
			
			msg= sharedpreferences.getString(Btninfo, null);
		}

		Log.d("Widget0520"," original  msg " + msg);

		if(msg.contains("LoggedIn")){

			CallMonitor(context, appWidgetManager);

		}
		else{

			CallLogin(context, appWidgetManager);	
		}

			
		/// for click event  strt

		// for only intent calling comment all just uncomment  configintent part

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		
		Intent settingIntent = new Intent(context, Hello.class);
		settingIntent.setAction(ACTION_WIDGET_CONFIGURE);

		Intent loginIntent = new Intent(context, FirstRainLogin.class);
		loginIntent.setAction(ACTION_WIDGET_CONFIGURE);

		Intent leftactive = new Intent(context, widgetclass.class);
		leftactive.setAction(ACTION_WIDGET_RECEIVER);
		leftactive.putExtra("btnFlag", "LEFT");

		Intent rightactive = new Intent(context, widgetclass.class);
		rightactive.setAction(ACTION_WIDGET_RECEIVER);
		rightactive.putExtra("btnFlag", "RIGHT");

		Intent refreshactive = new Intent(context, widgetclass.class);
		refreshactive.setAction(ACTION_WIDGET_RECEIVER);
		refreshactive.putExtra("btnFlag", "REFRESH");

		PendingIntent leftactionPendingIntent = PendingIntent.getBroadcast(context, 0, leftactive, 0);
		PendingIntent rightactionPendingIntent = PendingIntent.getBroadcast(context, 1, rightactive, 0);
		PendingIntent refreshactionPendingIntent = PendingIntent.getBroadcast(context, 2, refreshactive, 0);
		PendingIntent settingPendingIntent = PendingIntent.getActivity(context, 0, settingIntent, 0);
		PendingIntent loginPendingIntent = PendingIntent.getActivity(context, 1, loginIntent, 0);

		remoteViews.setOnClickPendingIntent(R.id.btnLeft, leftactionPendingIntent);
		remoteViews.setOnClickPendingIntent(R.id.btnRight, rightactionPendingIntent);
		remoteViews.setOnClickPendingIntent(R.id.btnRefresh, refreshactionPendingIntent);
		remoteViews.setOnClickPendingIntent(R.id.btnSettingCall, settingPendingIntent);
		remoteViews.setOnClickPendingIntent(R.id.tvFirstRainApp, loginPendingIntent);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

		// fro cliick event end


	}





	private void CallLogin(Context context, AppWidgetManager appWidgetManager) {

		RemoteViews remoteViews = null;

		remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);



		remoteViews.setTextViewText(R.id.tvFirstRainApp,	"Sign In to FirstRain");
		remoteViews.setTextViewText(R.id.tvMonitor,	"No Monitor Configure");
		//remoteViews.setViewVisibility(R.id.tvMessage, View.GONE);
		remoteViews.setViewVisibility(R.id.tvTitle, View.GONE);
		remoteViews.setViewVisibility(R.id.tvFirstRainApp, View.VISIBLE);

		ComponentName myWidget = new ComponentName(context,	widgetclass.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(myWidget, remoteViews);
	}





	private void CallMonitor(Context context, AppWidgetManager appWidgetManager) {


	}





	@Override
	public void onReceive(Context context, Intent intent) {

		//super.onReceive(context, intent);

		final String action = intent.getAction();
		if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
			final int appWidgetId = intent.getExtras().getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				this.onDeleted(context, new int[] { appWidgetId });
			}
		} else {  
			// check, if our Action was called

			/// this if is for btn msg .. for inten this is use less
			if (intent.getAction().equals(ACTION_WIDGET_RECEIVER)) {


				String flag = "null";
				try {
					flag = intent.getStringExtra("btnFlag");
				} catch (NullPointerException e) {
					Log.e("Error", "msg = null");
				}

				if(flag.contains("LEFT")){
					callLeft(context);
				}
				else if(flag.contains("RIGHT")){
					callRight(context);
				}
				else if(flag.contains("REFRESH")){
					callRefresh(context);
				}


			}


			super.onReceive(context, intent);
		}

	}





	private void callRefresh(Context context) {


		RemoteViews remoteViews = null;

		remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);


		
		//remoteViews.setTextViewText(R.id.tvMessage, "Refresh");
		remoteViews.setTextViewText(R.id.tvTitle, "Refreshing......");
		



		ComponentName myWidget = new ComponentName(context,	widgetclass.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(myWidget, remoteViews);
	}





	private void callRight(Context context) {

		RemoteViews remoteViews = null;

		remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);



		//remoteViews.setTextViewText(R.id.tvMessage, "Right");
		remoteViews.setTextViewText(R.id.tvTitle, "Right......");
		


		ComponentName myWidget = new ComponentName(context,	widgetclass.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(myWidget, remoteViews);
	}





	private void callLeft(Context context) {

		RemoteViews remoteViews = null;

		remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);



		remoteViews.setTextViewText(R.id.tvFirstRainApp,	"Sign In to FirstRain");
		remoteViews.setTextViewText(R.id.tvMonitor,	"No Monitor Configure");
		//remoteViews.setViewVisibility(R.id.tvMessage, View.GONE);
		remoteViews.setViewVisibility(R.id.tvTitle, View.GONE);
		remoteViews.setViewVisibility(R.id.tvFirstRainApp, View.VISIBLE);





		ComponentName myWidget = new ComponentName(context,	widgetclass.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(myWidget, remoteViews);
	}


	



}
