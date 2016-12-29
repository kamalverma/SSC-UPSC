package com.prepup.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.prepup.R;


public class SplashActivity extends AppCompatActivity {


    private static final int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_splash);


        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startSubjectActivity();
            }
        }, SPLASH_TIME);

    }


    private void startSubjectActivity() {
        Intent intent = new Intent(this, ChooseSubjectsActivity.class);
        startActivity(intent);
        finish();
    }


    private void startLoginActivity() {
        Intent intent = new Intent(this, SignUpLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
