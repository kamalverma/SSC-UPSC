package com.corelibrary.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.corelibrary.R;
import com.corelibrary.common.AppConstants;
import com.corelibrary.common.database.DatabaseHelper;
import com.corelibrary.common.database.DbQuestions;
import com.corelibrary.common.engine.ApiUtills;
import com.corelibrary.common.engine.RetrofitClient;
import com.corelibrary.models.Question;
import com.corelibrary.models.QuestionResponse;
import com.corelibrary.models.Subject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;

/**
 * Created by kamalverma on 25/12/16.
 */

public class TopQuestionsFragment extends Fragment {


    public static String TAG = TopQuestionsFragment.class.getName();

    private QuestionAdapter mAdapter;
    private List<Question> listQuestions;

    private Subject subject;

    private ProgressBar mProgressBar;

    private DbQuestions dbQuestions;


    public static TopQuestionsFragment getInstance(Subject subject) {

        TopQuestionsFragment fragment = new TopQuestionsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.CATEGORY, subject);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subject = (Subject) getArguments().getSerializable(AppConstants.CATEGORY);
        dbQuestions = new DbQuestions(DatabaseHelper.getInstance(getActivity()));
        listQuestions = dbQuestions.getAllBySubject(subject.getCatId());

        if (listQuestions == null) {
            listQuestions = new ArrayList<>();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_questions, null);


        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        RecyclerView rvSubjects = (RecyclerView) view.findViewById(R.id.rv_questions);
        rvSubjects.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvSubjects.setLayoutManager(layoutManager);


        mAdapter = new QuestionAdapter();
        rvSubjects.setAdapter(mAdapter);


        if (listQuestions.isEmpty()) {
            mProgressBar.setVisibility(View.VISIBLE);
            loadQuestions(subject.getCatId());
        }
        return view;
    }


    public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

        @Override
        public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = getActivity().getLayoutInflater().inflate(R.layout.row_question_mcq, parent, false);
            return new QuestionViewHolder(row);
        }

        @Override
        public int getItemCount() {
            return listQuestions.size();
        }

        @Override
        public void onBindViewHolder(QuestionViewHolder holder, int position) {
            holder.tvSubject.setText(Html.fromHtml(listQuestions.get(position).getQnText()));

            for (String option : listQuestions.get(position).getOpts()) {
                RadioButton radioButtonView = new RadioButton(getActivity());
                radioButtonView.setText(option);
                holder.rgOptions.addView(radioButtonView);
            }
        }
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvSubject;
        public RadioGroup rgOptions;

        public QuestionViewHolder(View itemView) {
            super(itemView);

            tvSubject = (AppCompatTextView) itemView.findViewById(R.id.tv_subject_name);
            rgOptions = (RadioGroup) itemView.findViewById(R.id.options);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Question question = (Question) v.getTag();

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


    public void loadQuestions(int catId) {
        ApiUtills apiService =
                RetrofitClient.getClient().create(ApiUtills.class);
        Call<QuestionResponse> call = apiService.getQuestionList(catId);

        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {

                if (response.isSuccessful()) {
                    listQuestions = response.body().getQuestions();

                    if (listQuestions.isEmpty()) {
                        Toast.makeText(getActivity(), "No data found for this subject", Toast.LENGTH_LONG).show();
                    } else {
                        mAdapter.notifyDataSetChanged();

                        for (Question question : listQuestions) {
                            dbQuestions.create(question);
                        }
                    }
                } else {
                    try {
                        Log.i("Error in response", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                mProgressBar.setVisibility(View.GONE);
            }


            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }


}
