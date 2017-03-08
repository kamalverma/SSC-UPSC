package com.corelibrary.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.corelibrary.R;
import com.corelibrary.WebViewActivity;
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
    private boolean mFromAdapter;


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

                if (!listQuestions.isEmpty()) {
                    mAdapter.notifyDataSetChanged();
                    rvSubjects.smoothScrollToPosition(0);
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

                mcqViewHolder.rgOptions.setTag(position);
                // for (String option : listQuestions.get(position).getOpts()) {

                for (int i = 0; i < listQuestions.get(position).getOpts().length; i++) {

                    String option = listQuestions.get(position).getOpts()[i];
                    AppCompatRadioButton radioButtonView = new AppCompatRadioButton(getActivity());
                    radioButtonView.setGravity(Gravity.CENTER_VERTICAL);

                    radioButtonView.setText(Html.fromHtml(option).toString().trim());


                    mcqViewHolder.rgOptions.addView(radioButtonView, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
                }

                if (listQuestions.get(position).isAttempted()) {

                    mFromAdapter = true;
                    AppCompatRadioButton radioButton = (AppCompatRadioButton) mcqViewHolder.rgOptions.getChildAt(listQuestions.get(position).getUserAnswer());
                    radioButton.setChecked(true);
                    mcqViewHolder.tvResult.setVisibility(View.VISIBLE);
                    if (listQuestions.get(position).getUserAnswer() == listQuestions.get(position).getAnswer()) {
                        mcqViewHolder.tvResult.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.right_answer_color));
                        mcqViewHolder.tvResult.setText("Right Answer");
                    } else {
                        String option = listQuestions.get(position).getOpts()[listQuestions.get(position).getAnswer()];
                        mcqViewHolder.tvResult.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.wrong_answer_color));
                        mcqViewHolder.tvResult.setText("Answer is " + Html.fromHtml(option).toString().trim());
                    }
                } else {
                    mcqViewHolder.tvResult.setVisibility(View.GONE);
                }

            } else if (holder instanceof QuickTipViewHolder) {

                QuickTipViewHolder quickTipViewHolder = (QuickTipViewHolder) holder;
                quickTipViewHolder.tvAnswer.setText(Html.fromHtml(listQuestions.get(position).getExplanation()));
                quickTipViewHolder.tvQuestion.setText(Html.fromHtml(listQuestions.get(position).getQnText()));

            } else if (holder instanceof ArticleViewHolder) {

                holder.itemView.setTag(listQuestions.get(position));
                ((ArticleViewHolder) holder).btnReadMore.setTag(listQuestions.get(position));
                ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
                articleViewHolder.tvTitle.setText(Html.fromHtml(listQuestions.get(position).getQnText()));
                articleViewHolder.tvPreview.setText(Html.fromHtml(listQuestions.get(position).getPreviewText()));

                if (listQuestions.get(position).getSource() != null && listQuestions.get(position).getSource().length() > 0) {
                    articleViewHolder.tvSource.setText(listQuestions.get(position).getSource());//TODO add source for article in question model and backend
                } else {
                    articleViewHolder.tvSource.setText("Not Available");
                }

            }
        }
    }

    private class MCQViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvSubject, tvResult;
        private RadioGroup rgOptions;

        private MCQViewHolder(View itemView) {
            super(itemView);

            tvSubject = (AppCompatTextView) itemView.findViewById(R.id.tv_subject_name);
            tvResult = (AppCompatTextView) itemView.findViewById(R.id.tv_result);
            rgOptions = (RadioGroup) itemView.findViewById(R.id.options);

            rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (mFromAdapter) {
                        mFromAdapter = false;
                        return;
                    }
                    int pos = (int) group.getTag();
                    listQuestions.get(pos).setAttempted(true);
                    View radioButton = group.findViewById(checkedId);
                    int index = group.indexOfChild(radioButton);
                    listQuestions.get(pos).setUserAnswer(index);

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });

                }
            });

        }
    }

    private class QuickTipViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvQuestion, tvAnswer;

        private QuickTipViewHolder(View itemView) {
            super(itemView);

            tvQuestion = (AppCompatTextView) itemView.findViewById(R.id.tv_question);
            tvAnswer = (AppCompatTextView) itemView.findViewById(R.id.tv_answer);

        }
    }

    private class ArticleViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvTitle, tvPreview, tvSource;
        private AppCompatButton btnReadMore;

        private ArticleViewHolder(View itemView) {
            super(itemView);

            tvTitle = (AppCompatTextView) itemView.findViewById(R.id.tv_article_title);
            tvPreview = (AppCompatTextView) itemView.findViewById(R.id.tv_article_preview);
            tvSource = (AppCompatTextView) itemView.findViewById(R.id.tv_source);
            btnReadMore = (AppCompatButton) itemView.findViewById(R.id.btn_read_more);

            btnReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Question question = (Question) v.getTag();

                    loadArticle(question);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Question question = (Question) v.getTag();
                    loadArticle(question);


                }
            });
        }


        private void loadArticle(Question question) {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);

            if (!question.getExplanation().isEmpty()) {
                intent.putExtra(AppConstants.CONTENT, question.getExplanation());
            } else {
                intent.putExtra(AppConstants.URL, question.getExtLink());
            }
            startActivity(intent);
            getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
                        if (listQuestions.isEmpty()) {
                            listQuestions = dbQuestions.getAllBySubject(subject.getCatId());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mTvMore.setVisibility(View.VISIBLE);
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
