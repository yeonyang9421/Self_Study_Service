package kr.co.woobi.imyeon.self_study_service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
        private  int mCount;
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
}
