package com.prepup;

import android.app.Application;

import com.corelibrary.common.database.DatabaseHelper;
import com.google.firebase.FirebaseApp;

/**
 * Created by kamalverma on 24/01/17.
 */

public class PrepUpApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);
        DatabaseHelper.init(this);
    }


}
