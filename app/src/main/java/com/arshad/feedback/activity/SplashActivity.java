package com.arshad.feedback.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.arshad.feedback.R;

/**
 * Created by root on 2/9/16.
 */
public class SplashActivity extends Activity {

    private Context mContext;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        handler = new Handler();

        startHandler();
    }

    private void startHandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(mContext, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 500);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
        }
        finish();
    }

}
