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
                        "����", "����", "��ʾ֪ͨ", R.drawable.ic_launcher, 1);
                break;
            case 1:

                int num= 10;
                String [] lins =new String[num];
                for (int i=0 ;i<num;i++)
                {
                    lins[i]="��"+i+"������AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
                }
                QBNotificationManager.showInboxStyleNotification(this,
                        "InboxStyle����", "����", "InboxStyle��ʾ֪ͨ", "������ͼ", "С����",lins,
                        resultIntent, R.drawable.ic_launcher);
                break;
            case 2:
                QBNotificationManager.showBigPictureStyleNotification(this,
                        "BigPictureStyle����", "����", "BigPictureStyle��ʾ֪ͨ", "ͼ��",
                        BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher),
                        resultIntent, R.drawable.ic_launcher, 3);
                break;
            case 3:
                QBNotificationManager.showBigTextStyleNotification(this,
                        "BigTextStyle����", "����", "BigTextStyle��ʾ֪ͨ", "����","sss",
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                        resultIntent, R.drawable.ic_launcher, 4);
                break;
            case 4:
                QBNotificationManager.showIntentNotification(this,
                        "����", "����", "��ת֪ͨ", resultIntent, R.drawable.ic_launcher, 5);
                break;
            case 5:
                QBNotificationManager.showLongTimeNotification(this,
                        "��פ�ͱ���", "���ݡ�������", "��פ֪ͨ", resultIntent, R.drawable.ic_launcher, 6);
                break;
            case 6:
                Intent apkIntent = new Intent();
                apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                apkIntent.setAction(android.content.Intent.ACTION_VIEW);
                //ע�⣺��������APK�Ƿ���assets�ļ����£���ȡ·������ֱ�Ӷ�ȡ�ģ�
                // Ҫͨ��COYP��ȥ�ڶ�����ֱ�Ӷ�ȡ�Լ����ص�PATH�����ֻ����һ����תAPK��ʵ�ʴ򲻿���
                Uri uri = Uri.fromFile(new File("file:///android_asset/cs.apk"));
                apkIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                QBNotificationManager.showIntentNotification(this,
                        "����", "����", "APK ��װ֪ͨ", resultIntent, R.drawable.ic_launcher, 7);
                break;

            case  7:
                QBNotificationManager.showProgressNotification(this,
                        "����", "���ݡ�������", "֪ͨ", resultIntent, true, 0, R.drawable.ic_launcher, 8);
                break;
            case  8:

                QBNotificationManager.showProgressNotification(this,
                        "����", "���ݡ�������", "֪ͨ", resultIntent, false, 10+index, R.drawable.ic_launcher, 32);
                index+=10;
                break;
            case  9:
                QBNotificationManager.showDefineProgressNotification(this,
                        "�Զ������", "���ݡ�������", "�Զ���֪ͨ", resultIntent, 0, R.drawable.ic_launcher, 9);
                downloadThread = new DownloadThread();
                downloadThread.start();

                break;
            case 10:
                QBNotificationManager.showDefineView(this,
                        "�Զ������", "���ݡ�������", "�Զ���֪ͨ", resultIntent, R.drawable.ic_launcher, 10);
                break;
            case 11:
                QBNotificationManager.showDefineButtonView(this,
                        "�Զ������", "���ݡ�������", "�Զ���֪ͨ", resultIntent, R.drawable.ic_launcher, 11);
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
                        "�Զ����ͱ���", "���ݡ�������", "�Զ���֪ͨ", resultIntent, now_progress, R.drawable.ic_launcher, 32);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }

        }
    }
}
