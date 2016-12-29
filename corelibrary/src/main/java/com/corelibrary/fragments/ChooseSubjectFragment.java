package com.corelibrary.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corelibrary.R;

/**
 * Created by kamalverma on 25/12/16.
 */

public class ChooseSubjectFragment extends Fragment {


    public static String TAG= ChooseSubjectFragment.class.getName();
    public static ChooseSubjectFragment getInstance() {

        return new ChooseSubjectFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_subject, null);


        RecyclerView rvSubjects = (RecyclerView) view.findViewById(R.id.rv_subjects);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSubjects.setLayoutManager(layoutManager);
        rvSubjects.setAdapter(new SubjectAdapter());


        VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(20);
        rvSubjects.addItemDecoration(dividerItemDecoration);
        return view;
    }


    public class SubjectAdapter extends RecyclerView.Adapter<SubjectViewHolder> {


        @Override
        public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View row = getActivity().getLayoutInflater().inflate(R.layout.row_subject,parent, false);

            return new SubjectViewHolder(row);
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public void onBindViewHolder(SubjectViewHolder holder, int position) {


        }
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvSubject;
        public AppCompatTextView tvSubjectDesc;

        public SubjectViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}
