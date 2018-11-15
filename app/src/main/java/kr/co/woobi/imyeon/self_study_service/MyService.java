package kr.co.woobi.imyeon.self_study_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();
    private Thread mThread;
    int mCount;
    public MyService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mThread==null){
            mThread=new Thread("My Thread"){
                @Override
                public void run() {
                    for(int i=0; i<5; i++){
                        try{
                        mCount++;
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
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
        if(mThread !=null){
            mThread.interrupt();
            mThread=null;
            mCount=0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
       throw new UnsupportedOperationException("Not yet implemented");
    }
}
