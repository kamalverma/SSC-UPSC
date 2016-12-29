package com.prepup.activitiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.corelibrary.fragments.ChooseSubjectFragment;
import com.prepup.R;


/**
 * Created by Kamal on 12/8/16.
 */

public class ChooseSubjectsActivity extends AppCompatActivity {

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


    }
}
