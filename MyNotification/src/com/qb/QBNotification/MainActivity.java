package com.qb.QBNotification;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.qb.Utils.QBNotificationManager;

import java.io.File;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private Intent resultIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.listview_item, getResources()
                .getStringArray(R.array.items));
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        resultIntent = new Intent(this, SecondActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        QBNotificationManager.clearAllNotifification(this);
        return super.onOptionsItemSelected(item);
    }

    private int index = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                QBNotificationManager.showOrdinaryNotification(this,
                        "标题", "内容", "显示通知", R.drawable.ic_launcher, 1);
                break;
            case 1:

                int num= 10;
                String [] lins =new String[num];
                for (int i=0 ;i<num;i++)
                {
                    lins[i]="第"+i+"行数据AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
                }
                QBNotificationManager.showInboxStyleNotification(this,
                        "InboxStyle标题", "内容", "InboxStyle显示通知", "段落视图", "小标题",lins,
                        resultIntent, R.drawable.ic_launcher);
                break;
            case 2:
                QBNotificationManager.showBigPictureStyleNotification(this,
                        "BigPictureStyle标题", "内容", "BigPictureStyle显示通知", "图标",
                        BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher),
                        resultIntent, R.drawable.ic_launcher, 3);
                break;
            case 3:
                QBNotificationManager.showBigTextStyleNotification(this,
                        "BigTextStyle标题", "内容", "BigTextStyle显示通知", "文字","sss",
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                        resultIntent, R.drawable.ic_launcher, 4);
                break;
            case 4:
                QBNotificationManager.showIntentNotification(this,
                        "标题", "内容", "跳转通知", resultIntent, R.drawable.ic_launcher, 5);
                break;
            case 5:
                QBNotificationManager.showLongTimeNotification(this,
                        "常驻型标题", "内容。。。。", "常驻通知", resultIntent, R.drawable.ic_launcher, 6);
                break;
            case 6:
                Intent apkIntent = new Intent();
                apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                apkIntent.setAction(android.content.Intent.ACTION_VIEW);
                //注意：这里的这个APK是放在assets文件夹下，获取路径不能直接读取的，
                // 要通过COYP出去在读或者直接读取自己本地的PATH，这边只是做一个跳转APK，实际打不开的
                Uri uri = Uri.fromFile(new File("file:///android_asset/cs.apk"));
                apkIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                QBNotificationManager.showIntentNotification(this,
                        "标题", "内容", "APK 安装通知", resultIntent, R.drawable.ic_launcher, 7);
                break;

            case  7:
                QBNotificationManager.showProgressNotification(this,
                        "标题", "内容。。。。", "通知", resultIntent, true, 0, R.drawable.ic_launcher, 8);
                break;
            case  8:

                QBNotificationManager.showProgressNotification(this,
                        "标题", "内容。。。。", "通知", resultIntent, false, 10+index, R.drawable.ic_launcher, 32);
                index+=10;
                break;
            case  9:
                QBNotificationManager.showDefineProgressNotification(this,
                        "自定义标题", "内容。。。。", "自定义通知", resultIntent, 0, R.drawable.ic_launcher, 9);
                downloadThread = new DownloadThread();
                downloadThread.start();

                break;
            case 10:
                QBNotificationManager.showDefineView(this,
                        "自定义标题", "内容。。。。", "自定义通知", resultIntent, R.drawable.ic_launcher, 10);
                break;
            case 11:
                QBNotificationManager.showDefineButtonView(this,
                        "自定义标题", "内容。。。。", "自定义通知", resultIntent, R.drawable.ic_launcher, 11);
                break;

        }


    }

    private DownloadThread downloadThread;

    class DownloadThread extends Thread {

        @Override
        public void run() {
            int now_progress = 0;
            while (now_progress <= 100) {
                if (downloadThread == null) {
                    break;
                }
                now_progress += 10;
                QBNotificationManager.showDefineProgressNotification(MainActivity.this,
                        "自定义型标题", "内容。。。。", "自定义通知", resultIntent, now_progress, R.drawable.ic_launcher, 32);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }

        }
    }
}
