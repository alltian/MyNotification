package com.qb.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import com.qb.QBNotification.R;

import java.util.Random;

/**
 * Created by qubian on 15/8/13.
 * 思路有问题
 * 方法和参数 不能设置为静态的
 *
 *
 */
public class QBNotificationManager {

    private static final int SmallIcon=R.drawable.ic_launcher;

    public static final int NotificationNumber = 1;
    public static final int RequestCode = 0;
    private static NotificationManager mManager;
    private static NotificationCompat.Builder mBuilder;
    private static final Random RANDOM = new Random();

    /**
     * 获取Builder
     *
     * @param context
     * @return
     */
    private static NotificationCompat.Builder getBuilder(Context context) {

//        if (mBuilder == null) {
//            synchronized (QBNotificationManager.class) {
//                if (mBuilder == null) {

//                }
//            }
//        }
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setNumber(NotificationNumber)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);
        return mBuilder;
    }

    /**
     * 获取NotificationManager
     *
     * @param context
     * @return
     */
    private static NotificationManager getManager(Context context) {

        if (mManager == null) {
            synchronized (QBNotificationManager.class) {
                if (mManager == null) {
                    mManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                }
            }
        }
        return mManager;
    }


    /**
     * 显示普通的通知
     */
    public static void showOrdinaryNotification(Context context, String title, String text, String ticker, int icon,int channel) {
        mBuilder = getBuilder(context);
        mManager = getManager(context);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setContentIntent(getDefalutIntent(context, Notification.FLAG_AUTO_CANCEL))
                .setNumber(NotificationNumber)//显示数量
                .setTicker(ticker)//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                        //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(SmallIcon)
        ;
        Notification mNotification = mBuilder.build();
        mNotification.icon= icon;
        mManager.notify(dealWithId(channel), mNotification);
    }

    public static PendingIntent getDefalutIntent(Context context, int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(), flags);
        return pendingIntent;
    }

    /**
     * 段落样式的大视图通知栏
     * 细节区域只有256dp高度的内容
     *
     * @param context
     * @param title
     * @param text
     * @param ticker
     * @param bigContentTitle
     * @param summaryText
     * @param channel
     */
    public static void showInboxStyleNotification(Context context, String title, String text, String ticker, String bigContentTitle, String summaryText, Intent resultIntent, int icon,int channel) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[5];
        inboxStyle.setBigContentTitle(bigContentTitle);
        inboxStyle.setSummaryText(summaryText);
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        mBuilder = getBuilder(context);
        mManager = getManager(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, RequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setPriority(Notification.PRIORITY_MAX)
                .setTicker(ticker)
                .setSmallIcon(SmallIcon)
                .setStyle(inboxStyle)
                .setContentIntent(pendingIntent);
        Notification mNotification = mBuilder.build();
        mNotification.icon= icon;
        mManager.notify(dealWithId(channel), mNotification);
    }

    /**
     * 带着图片样式的大视图通知栏
     * 细节区域只有256dp高度的内容
     *
     * @param context
     * @param title
     * @param text
     * @param ticker
     * @param bigContentTitle
     * @param bitmap
     * @param channel
     */
    public static void showBigPictureStyleNotification(Context context, String title, String text, String ticker, String bigContentTitle, Bitmap bitmap, Intent resultIntent, int icon,int channel) {
        NotificationCompat.BigPictureStyle inboxStyle = new NotificationCompat.BigPictureStyle();
        inboxStyle.setBigContentTitle(bigContentTitle);
        inboxStyle.bigPicture(bitmap);
        mBuilder = getBuilder(context);
        mManager = getManager(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, RequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setPriority(Notification.PRIORITY_MAX)
                .setTicker(ticker)
                .setSmallIcon(SmallIcon)
                .setStyle(inboxStyle)
                .setContentIntent(pendingIntent);
        Notification mNotification = mBuilder.build();
        mNotification.icon= icon;
        mManager.notify(dealWithId(channel), mNotification);
    }


    /**
     * 长文字样式的大视图通知栏
     * 细节区域只有256dp高度的内容
     *
     * @param context
     * @param title
     * @param text
     * @param ticker
     * @param bigContentTitle
     * @param bigtext
     * @param channel
     */
    public static void showBigTextStyleNotification(Context context, String title, String text, String ticker, String bigContentTitle, String bigtext, Intent resultIntent,int icon, int channel) {
        NotificationCompat.BigTextStyle inboxStyle = new NotificationCompat.BigTextStyle();
        inboxStyle.setBigContentTitle(bigContentTitle);
        inboxStyle.bigText(bigtext);
        mBuilder = getBuilder(context);
        mManager = getManager(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, RequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setPriority(Notification.PRIORITY_MAX)
                .setTicker(ticker)
                .setSmallIcon(SmallIcon)
                .setStyle(inboxStyle)
                .setContentIntent(pendingIntent);
        Notification mNotification = mBuilder.build();
        mNotification.icon= icon;
        mManager.notify(dealWithId(channel), mNotification);
    }


    /**
     * Intent 中可以包含很多参数、功能
     * 如：  页面启动、跳转、
     * 安装apk
     *
     * @param context
     * @param title
     * @param text
     * @param ticker
     * @param resultIntent
     * @param channel
     */
    public static void showIntentNotification(Context context, String title, String text, String ticker, Intent resultIntent,int icon, int channel) {
        mBuilder = getBuilder(context);
        mManager = getManager(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, RequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setTicker(ticker)
                .setSmallIcon(SmallIcon)
                .setContentIntent(pendingIntent);
        Notification mNotification = mBuilder.build();
        mNotification.icon= icon;
        mManager.notify(dealWithId(channel), mNotification);

    }

    /**
     * 设置 常驻通知
     *
     * @param context
     * @param title
     * @param text
     * @param ticker
     * @param resultIntent
     * @param channel
     */
    public static void showLongTimeNotification(Context context, String title, String text, String ticker, Intent resultIntent, int icon,int channel) {
        mBuilder = getBuilder(context);
        mManager = getManager(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, RequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setTicker(ticker)
                .setSmallIcon(SmallIcon)
                .setContentIntent(pendingIntent);
        Notification mNotification = mBuilder.build();
        mNotification.icon=icon;
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
        mManager.notify(dealWithId(channel), mNotification);


    }

    /**
     *  显示进度
     * @param context
     * @param title
     * @param text
     * @param ticker
     * @param resultIntent
     * @param indeterminate
     * @param progress
     * @param icon
     * @param channel
     */
    public static void showProgressNotification(Context context, String title, String text, String ticker, Intent resultIntent,boolean indeterminate, int progress ,int icon,int channel) {
        mBuilder = getBuilder(context);
        mManager = getManager(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, RequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setTicker(ticker)
                .setSmallIcon(SmallIcon)
                .setContentIntent(pendingIntent);
        if(indeterminate){
            //不确定进度的
            // 这个方法是显示进度条  设置为true就是不确定的那种进度条效果
            mBuilder.setProgress(0, 0, true);
        }else{
            //确定进度的
            mBuilder.setProgress(100, progress, false);
        }
        Notification mNotification = mBuilder.build();
        mNotification.icon=icon;
        mManager.notify(dealWithId(channel), mNotification);


    }

    public static void showDefineProgressNotification(Context context, String title, String text, String ticker, Intent resultIntent, int progress ,int icon,int channel) {
        mBuilder = getBuilder(context);
        mManager = getManager(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, RequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setTicker(ticker)
                .setSmallIcon(SmallIcon)
                .setContentIntent(pendingIntent);
        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.view_custom_progress);
        mRemoteViews.setImageViewResource(R.id.custom_progress_icon, icon);
        mRemoteViews.setTextViewText(R.id.tv_custom_progress_title, title);
        mRemoteViews.setTextViewText(R.id.tv_custom_progress_status, text);
        if(progress >= 100 ){
            mRemoteViews.setProgressBar(R.id.custom_progressbar, 0, 0, false);
            mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.GONE);
        }else{
            mRemoteViews.setProgressBar(R.id.custom_progressbar, 100, progress, false);
            mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.VISIBLE);
        }
        Notification mNotification = mBuilder.build();
        mNotification.icon=icon;
        mNotification.contentView = mRemoteViews;
        mManager.notify(dealWithId(channel), mNotification);


    }

    public static  void showDefineView(Context context, String title, String text, String ticker, Intent resultIntent ,int icon,int channel)
    {
        mBuilder = getBuilder(context);
        mManager = getManager(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, RequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setTicker(ticker)
                .setSmallIcon(SmallIcon)
                .setContentIntent(pendingIntent);
        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.view_custom);
        mRemoteViews.setImageViewResource(R.id.custom_icon, icon);
        mRemoteViews.setTextViewText(R.id.tv_custom_title, title);
        mRemoteViews.setTextViewText(R.id.tv_custom_content, text);
        mRemoteViews.setTextViewText(R.id.tv_custom_time, String.valueOf(System.currentTimeMillis()));
//        mRemoteViews.setLong(R.id.tv_custom_time, "setTime", System.currentTimeMillis());
        Notification mNotification = mBuilder.build();
        mNotification.icon=icon;
        mNotification.contentView = mRemoteViews;
        mManager.notify(dealWithId(channel), mNotification);
    }

    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    /** 上一首 按钮点击 ID */
    public final static int BUTTON_PREV_ID = 1;
    /** 播放/暂停 按钮点击 ID */
    public final static int BUTTON_PALY_ID = 2;
    /** 下一首 按钮点击 ID */
    public final static int BUTTON_NEXT_ID = 3;
    /** 通知栏按钮点击事件对应的ACTION */
    public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";

    public static  void showDefineButtonView(Context context, String title, String text, String ticker, Intent resultIntent ,int icon,int channel)
    {
        mBuilder = getBuilder(context);
        mManager = getManager(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, RequestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setTicker(ticker)
                .setSmallIcon(SmallIcon)
                .setContentIntent(pendingIntent);

        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.view_custom_button);
        mRemoteViews.setImageViewResource(R.id.custom_icon, icon);
        mRemoteViews.setTextViewText(R.id.tv_custom_title, title);
        mRemoteViews.setTextViewText(R.id.tv_custom_content, text);
        mRemoteViews.setTextViewText(R.id.tv_custom_time, String.valueOf(System.currentTimeMillis()));

        //如果版本号低于（3。0），那么不显示按钮
        if(getSystemVersion() <= 9){
            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
        }else{
            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
            //if(isPlay)
            {
                mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_pause);
            }
//            else
//            {
//                mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_play);
//            }
        }

        //点击的事件处理
        Intent buttonIntent = new Intent(ACTION_BUTTON);
		/* 上一首按钮 */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
        //这里加了广播，所及INTENT的必须用getBroadcast方法
        PendingIntent intent_prev = PendingIntent.getBroadcast(context, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_prev, intent_prev);
		/* 播放/暂停  按钮 */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
        PendingIntent intent_paly = PendingIntent.getBroadcast(context, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
		/* 下一首 按钮  */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_NEXT_ID);
        PendingIntent intent_next = PendingIntent.getBroadcast(context, 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_next, intent_next);

        Notification mNotification = mBuilder.build();
        mNotification.icon=icon;
        mNotification.contentView = mRemoteViews;
        mManager.notify(dealWithId(channel), mNotification);
    }
    public static int dealWithId(int channel) {
        return channel >= 1 && channel <= 100 ? channel : RANDOM.nextInt(Integer.MAX_VALUE - 100) + 101;
    }

    public static int getSystemVersion(){
        int version= android.os.Build.VERSION.SDK_INT;
        return version;
    }
}

