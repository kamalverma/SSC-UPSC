package com.prepup.activitiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

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

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        subject = (Subject) getIntent().getExtras().getSerializable(AppConstants.CATEGORY);

        toolbar.setTitle(subject.getCatName());

        QuestionFragment subjectFragment = QuestionFragment.getInstance(subject);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, subjectFragment, ChooseSubjectFragment.TAG).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
