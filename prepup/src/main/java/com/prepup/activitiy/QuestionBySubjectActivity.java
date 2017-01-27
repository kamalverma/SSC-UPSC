package com.prepup.activitiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.corelibrary.common.AppConstants;
import com.corelibrary.fragments.ChooseSubjectFragment;
import com.corelibrary.fragments.QuestionFragment;
import com.corelibrary.models.Subject;
import com.prepup.R;
import com.prepup.fragments.HomePageFragment;


/**
 * Created by Kamal on 12/8/16.
 */

public class QuestionBySubjectActivity extends AppCompatActivity {


    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_questions);


        int toolbarId = getResources().getIdentifier("toolbar", "id", getPackageName());
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
        subject = (Subject) getIntent().getExtras().getSerializable(AppConstants.CATEGORY);
        if (toolbar != null) {
            toolbar.setTitle(subject.getCatName());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        QuestionFragment subjectFragment = QuestionFragment.getInstance(subject);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, subjectFragment, ChooseSubjectFragment.TAG).commit();
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

}
