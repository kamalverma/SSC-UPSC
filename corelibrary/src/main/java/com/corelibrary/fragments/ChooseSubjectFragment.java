package com.corelibrary.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.corelibrary.R;
import com.corelibrary.common.database.DatabaseHelper;
import com.corelibrary.common.database.DbSubjects;
import com.corelibrary.common.engine.ApiUtills;
import com.corelibrary.common.engine.RetrofitClient;
import com.corelibrary.models.Subject;
import com.corelibrary.models.SubjectResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;

/**
 * Created by kamalverma on 25/12/16.
 */

public class ChooseSubjectFragment extends Fragment {


    public static String TAG = ChooseSubjectFragment.class.getName();

    private SubjectAdapter mAdapter;
    private List<Subject> listSubjects;

    private ProgressBar mProgressBar;

    private DbSubjects dbSubjects;
    private CallBacks mListener;

    public static ChooseSubjectFragment getInstance() {
        return new ChooseSubjectFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbSubjects = new DbSubjects(DatabaseHelper.getInstance(getActivity()));
        listSubjects = dbSubjects.getAll();

        if (listSubjects == null) {
            listSubjects = new ArrayList<>();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_subject, null);


        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        RecyclerView rvSubjects = (RecyclerView) view.findViewById(R.id.rv_subjects);
        rvSubjects.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);

        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rvSubjects.setLayoutManager(layoutManager);


        mAdapter = new SubjectAdapter();
        rvSubjects.setAdapter(mAdapter);


        if (listSubjects.isEmpty()) {
            mProgressBar.setVisibility(View.VISIBLE);
            loadSubjects();
        }
        return view;
    }


    public class SubjectAdapter extends RecyclerView.Adapter<SubjectViewHolder> {

        @Override
        public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = getActivity().getLayoutInflater().inflate(R.layout.row_subject, parent, false);
            return new SubjectViewHolder(row);
        }

        @Override
        public int getItemCount() {
            return listSubjects.size();
        }

        @Override
        public void onBindViewHolder(SubjectViewHolder holder, int position) {
            holder.tvSubject.setText(listSubjects.get(position).getCatName());
            holder.btnLearn.setTag(listSubjects.get(position));
            holder.btnTakeQuiz.setTag(listSubjects.get(position));
        }
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvSubject;
        public AppCompatButton btnLearn, btnTakeQuiz;

        public SubjectViewHolder(View itemView) {
            super(itemView);

            tvSubject = (AppCompatTextView) itemView.findViewById(R.id.tv_subject_name);
            btnLearn = (AppCompatButton) itemView.findViewById(R.id.tv_study);
            btnTakeQuiz = (AppCompatButton) itemView.findViewById(R.id.tv_quiz);


            btnLearn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Subject subject = (Subject) v.getTag();
                    if (mListener != null) {
                        mListener.onSubjectSelected(subject);
                    }
                }
            });

            btnTakeQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Subject subject = (Subject) v.getTag();
                    if (mListener != null) {
                        mListener.onQuizSelected(subject);
                    }
                }
            });
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


    public void loadSubjects() {
        ApiUtills apiService =
                RetrofitClient.getClient().create(ApiUtills.class);
        Call<SubjectResponse> call = apiService.getSubjectList("prepup");


        call.enqueue(new Callback<SubjectResponse>() {
            @Override
            public void onResponse(Call<SubjectResponse> call, Response<SubjectResponse> response) {
                listSubjects = response.body().getCategories();
                mAdapter.notifyDataSetChanged();


                //Save all subjects in local database
                for (Subject subject : listSubjects) {
                    dbSubjects.create(subject);
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SubjectResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CallBacks) {
            mListener = (CallBacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Callbacks");
        }
    }

    public interface CallBacks {
        void onSubjectSelected(Subject subject);

        void onQuizSelected(Subject subject);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
