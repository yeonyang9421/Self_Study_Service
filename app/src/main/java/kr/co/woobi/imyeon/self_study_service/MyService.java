package kr.co.woobi.imyeon.self_study_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();
    private Thread mThread;
    int mCount = 0;

    private IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if ("startForeground".equals(intent.getAction())) {
            startForegroundService();
        } else if (mThread == null) {
            mThread = new Thread("My Thread") {
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        try {
                            mCount++;
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            break;
                        }
                        Log.d("My Service", "서비스 동작 중" + mCount);
                    }
                }
            };
            mThread.start();
        }
        //다른 앱에서 자원이 없어서 강제 종료하여도 죽지 않고 계속 진행 할 수 있다. 음악 플래이 같은 것이나 앱 스토어에서 업그레이드할때
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy : ");
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
            mCount = 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int getCount() {
        return mCount;
    }

    private void startForegroundService() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("포그리운드 서비스");
        builder.setContentText("포그라운드 서비스 실행중");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(new NotificationChannel("default", "기본채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        startForeground(1, builder.build());
    }
}
