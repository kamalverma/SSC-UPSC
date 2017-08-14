package com.prepup.activitiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.corelibrary.common.AppConstants;
import com.corelibrary.fragments.ChooseSubjectFragment;
import com.corelibrary.fragments.QuestionFragment;
import com.corelibrary.models.Subject;
import com.prepup.R;


/**
 * Created by Kamal on 12/8/16.
 */

public class QuizBySubjectListActivity extends AppCompatActivity {


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
        }
        return true;
    }

}
