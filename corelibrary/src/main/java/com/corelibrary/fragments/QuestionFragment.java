package com.corelibrary.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
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
import com.corelibrary.common.engine.ApiUtils;
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

public class QuestionFragment extends Fragment {


    public static String TAG = QuestionFragment.class.getName();

    private QuestionAdapter mAdapter;
    private List<Question> listQuestions;

    private Subject subject;

    private ProgressBar mProgressBar;

    private DbQuestions dbQuestions;

    private AppCompatTextView mTvMore;


    public static QuestionFragment getInstance(Subject subject) {

        QuestionFragment fragment = new QuestionFragment();

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
        final RecyclerView rvSubjects = (RecyclerView) view.findViewById(R.id.rv_questions);
        rvSubjects.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvSubjects.setLayoutManager(layoutManager);

        mTvMore = (AppCompatTextView) view.findViewById(R.id.tv_more_data);
        mTvMore.setVisibility(View.GONE);


        mTvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listQuestions = dbQuestions.getAllBySubject(subject.getCatId());

                if (listQuestions.isEmpty()) {
                    mAdapter.notifyDataSetChanged();
                    rvSubjects.scrollToPosition(0);
                }

                mTvMore.setVisibility(View.GONE);
            }
        });

        mAdapter = new QuestionAdapter();
        rvSubjects.setAdapter(mAdapter);


        if (listQuestions.isEmpty()) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        loadQuestions(subject.getCatId());
        return view;
    }


    public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        private static final int VIEW_TYPE_MCQ = 1;
        private static final int VIEW_TYPE_QUICK_TIP = 2;
        private static final int VIEW_TYPE_ARTICLE = 3;

        @Override
        public int getItemViewType(int position) {

            if (listQuestions.get(position).getQnType().equalsIgnoreCase("MCQ")) {
                return VIEW_TYPE_MCQ;
            } else if (listQuestions.get(position).getQnType().equalsIgnoreCase("SQ")) {
                return VIEW_TYPE_QUICK_TIP;
            } else if (listQuestions.get(position).getQnType().equalsIgnoreCase("ARTICLE")) {
                return VIEW_TYPE_ARTICLE;
            }
            return VIEW_TYPE_MCQ;
        }


        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = null;
            if (viewType == VIEW_TYPE_MCQ) {
                row = getActivity().getLayoutInflater().inflate(R.layout.row_question_mcq, parent, false);
                return new MCQViewHolder(row);
            } else if (viewType == VIEW_TYPE_QUICK_TIP) {
                row = getActivity().getLayoutInflater().inflate(R.layout.row_question_quick_tip, parent, false);
                return new QuickTipViewHolder(row);
            } else if (viewType == VIEW_TYPE_ARTICLE) {
                row = getActivity().getLayoutInflater().inflate(R.layout.row_article, parent, false);
                return new ArticleViewHolder(row);
            }

            return new MCQViewHolder(row);
        }

        @Override
        public int getItemCount() {
            return listQuestions.size();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof MCQViewHolder) {

                MCQViewHolder mcqViewHolder = (MCQViewHolder) holder;

                mcqViewHolder.tvSubject.setText(Html.fromHtml(listQuestions.get(position).getQnText()));
                mcqViewHolder.rgOptions.removeAllViews();

                for (String option : listQuestions.get(position).getOpts()) {
                    AppCompatRadioButton radioButtonView = new AppCompatRadioButton(getActivity());
                    radioButtonView.setGravity(Gravity.CENTER_VERTICAL);

                    radioButtonView.setText(Html.fromHtml(option).toString().trim());

                    mcqViewHolder.rgOptions.addView(radioButtonView);
                }

                mcqViewHolder.rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                    }
                });

            } else if (holder instanceof QuickTipViewHolder) {

                QuickTipViewHolder quickTipViewHolder = (QuickTipViewHolder) holder;
                quickTipViewHolder.tvAnswer.setText(Html.fromHtml(listQuestions.get(position).getExplanation()));
                quickTipViewHolder.tvQuestion.setText(Html.fromHtml(listQuestions.get(position).getQnText()));

            } else if (holder instanceof ArticleViewHolder) {

                ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
                articleViewHolder.tvTitle.setText(Html.fromHtml(listQuestions.get(position).getQnText()));
                articleViewHolder.tvPreview.setText(Html.fromHtml(listQuestions.get(position).getExplanation()));

                if (listQuestions.get(position).getSource() != null && listQuestions.get(position).getSource().length() > 0) {
                    articleViewHolder.tvSource.setText(listQuestions.get(position).getSource());//TODO add source for article in question model and backend
                } else {
                    articleViewHolder.tvSource.setText("Not Available");
                }

            }
        }
    }

    public class MCQViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvSubject;
        public RadioGroup rgOptions;

        public MCQViewHolder(View itemView) {
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

    public class QuickTipViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvQuestion, tvAnswer;

        public QuickTipViewHolder(View itemView) {
            super(itemView);

            tvQuestion = (AppCompatTextView) itemView.findViewById(R.id.tv_question);
            tvAnswer = (AppCompatTextView) itemView.findViewById(R.id.tv_answer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Question question = (Question) v.getTag();

                }
            });
        }
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvTitle, tvPreview, tvSource;
        public AppCompatButton btnReadMore;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            tvTitle = (AppCompatTextView) itemView.findViewById(R.id.tv_article_title);
            tvPreview = (AppCompatTextView) itemView.findViewById(R.id.tv_article_preview);
            tvSource = (AppCompatTextView) itemView.findViewById(R.id.tv_source);
            btnReadMore = (AppCompatButton) itemView.findViewById(R.id.btn_read_more);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Question question = (Question) v.getTag();
                }
            });
        }
    }


    public void loadQuestions(int catId) {
        ApiUtils apiService =
                RetrofitClient.getClient().create(ApiUtils.class);
        Call<QuestionResponse> call = apiService.getQuestionList(catId);

        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {

                if (response.isSuccessful()) {
                    List<Question> list = response.body().getQuestions();

                    if (list.isEmpty()) {
                        if (listQuestions.isEmpty()) {
                            Toast.makeText(getActivity(), "No data found for this subject", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        for (Question question : list) {
                            dbQuestions.create(question);
                        }

                        mTvMore.setVisibility(View.VISIBLE);
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
