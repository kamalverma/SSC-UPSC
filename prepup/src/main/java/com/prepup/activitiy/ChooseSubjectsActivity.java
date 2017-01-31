package com.prepup.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.corelibrary.common.AppConstants;
import com.corelibrary.fragments.ChooseSubjectFragment;
import com.corelibrary.models.Subject;
import com.prepup.R;
import com.prepup.fragments.HomePageFragment;


/**
 * Created by Kamal on 12/8/16.
 */

public class ChooseSubjectsActivity extends AppCompatActivity implements ChooseSubjectFragment.CallBacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_choose_subject);


        int toolbarId = getResources().getIdentifier("toolbar", "id", getPackageName());
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        ChooseSubjectFragment subjectFragment = ChooseSubjectFragment.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, subjectFragment, ChooseSubjectFragment.TAG).commit();


        HomePageFragment homePageFragment = HomePageFragment.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_app_view, homePageFragment, HomePageFragment.TAG).commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
        }
        return true;
    }

    @Override
    public void onSubjectSelected(Subject subject) {
        Intent intent = new Intent(this, QuestionBySubjectActivity.class);
        intent.putExtra(AppConstants.CATEGORY, subject);
        startActivity(intent);
    }
}
