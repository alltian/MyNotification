package com.example.mynotification;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends Activity {
	NotificationManager nm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		custom();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void custom(){
		nm= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification();
		notification.icon = android.R.drawable.stat_notify_call_mute;//图标
		notification.tickerText = "拦截到了新的短信";
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.content);
		notification.contentView = contentView;//通知显示的布局
		Intent intent = new Intent(this,OtherActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 100, intent, 0);
		notification.contentIntent = contentIntent;//点击的事件
		notification.flags = Notification.FLAG_AUTO_CANCEL;//点击通知之后自动消失
		nm.notify(100, notification);
	 }
    private void clearNotification(){
    	if (nm !=null) {
    		nm.cancel(0);  
		}
  
    }
}
