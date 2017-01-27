package com.prepup.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.prepup.PrepUpApplication;
import com.prepup.R;


public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 2000;
    private long cacheExpiration = 3600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_splash);


        setUpRemoteConfig();
        HTextView hTextView = (HTextView) findViewById(R.id.text);
        hTextView.setAnimateType(HTextViewType.LINE);
        hTextView.animateText(getString(R.string.app_name));
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

    private void setUpRemoteConfig() {
        FirebaseRemoteConfig.getInstance().setDefaults(R.xml.remote_config_defaults);
        FirebaseRemoteConfig.getInstance().fetch(cacheExpiration).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SplashActivity.this, "Fetch Succeeded",
                            Toast.LENGTH_SHORT).show();
                    FirebaseRemoteConfig.getInstance().activateFetched();
                } else {
                    Toast.makeText(SplashActivity.this, "Fetch Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
