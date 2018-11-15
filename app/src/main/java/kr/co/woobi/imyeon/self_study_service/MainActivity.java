package kr.co.woobi.imyeon.self_study_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyService myService;
    private  boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartService(View view) {
        Intent intent=new Intent(this, MyService.class);
        startService(intent);

    }

    public void onStopService(View view) {
        Intent intent=new Intent(this, MyService.class);
        stopService(intent);
    }

    public void onStatIntentService(View view) {
        Intent intent=new Intent(this, MyService.class);
        startService(intent);
    }

    public void onStatForegroundService(View view) {
        Intent intent=new Intent(this, MyService.class);
        intent.setAction("startForeground");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            }else{
            startService(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this, MyService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mConnection);
            mBound=false;
        }
    }

    private ServiceConnection mConnection  = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder = (MyService.MyBinder)service;
            myService = binder.getService();
            mBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //예기치못한 상황에서 종료되었을떄 호출되는 함수

        }
    };

    public void getCountValue(View view) {
        if(mBound) {
            Toast.makeText(this, "카운팅 : " + myService.getCount(), Toast.LENGTH_SHORT).show();
        }
    }
}
