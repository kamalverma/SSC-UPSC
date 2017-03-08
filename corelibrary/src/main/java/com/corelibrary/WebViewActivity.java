package com.corelibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.corelibrary.common.AppConstants;

public class WebViewActivity extends AppCompatActivity {


    private WebView mWebView;


    private String mContent;
    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        int toolbarId = getResources().getIdentifier("toolbar", "id", getPackageName());
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mWebView = (WebView) findViewById(R.id.webView);

        WebSettings webViewSettings = mWebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setSupportZoom(false);
        webViewSettings.setBuiltInZoomControls(false);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);
        webViewSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        if (getIntent().getExtras().containsKey(AppConstants.CONTENT)) {
            mContent = getIntent().getExtras().getString(AppConstants.CONTENT);

            try {
                mWebView.loadDataWithBaseURL("", "<style>img{display: inline;height: auto;max-width: 100%;} " +
                        "table {display: inline;border: 1px solid black; max-width: 100%;}</style>" + mContent, "text/html", "UTF-8", null);
            } catch (OutOfMemoryError error) {

            }
        } else if (getIntent().getExtras().containsKey(AppConstants.URL)) {
            mUrl = getIntent().getExtras().getString(AppConstants.URL);

            try {
                mWebView.loadUrl(mUrl);
            } catch (OutOfMemoryError error) {

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
        }
        return true;
    }
}
