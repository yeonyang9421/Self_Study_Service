package kr.co.woobi.imyeon.self_study_service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class MyIntentService extends IntentService {

    private int mCount;

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        for(int i=0; i<5; i++){
            try{
                mCount++;
                Thread.sleep(1000);
            }catch(InterruptedException e){
               Thread.currentThread().interrupt();
            }
            Log.d("My IntentService", "인텐트 서비스 동작 중" + mCount);
        }


    }
}