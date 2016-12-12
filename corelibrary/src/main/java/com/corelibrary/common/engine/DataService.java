package com.corelibrary.common.engine;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class DataService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_CHECK_DOWNLOAD = "com.corelibrary.common.engine.action.FOO";


    public DataService() {
        super("DataService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DataService.class);
        intent.setAction(ACTION_CHECK_DOWNLOAD);
      /*  intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);*/
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_DOWNLOAD.equals(action)) {
                /*final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);*/
                try {
                    checkAndDownloadData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void checkAndDownloadData() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("URL string here")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        InputStream in = response.body().byteStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String result, line = reader.readLine();
        result = line;
        while ((line = reader.readLine()) != null) {
            result += line;
        }
        System.out.println(result);
        response.body().close();
    }

}
