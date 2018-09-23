package com.arshad.feedback.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arshad.feedback.R;
import com.arshad.feedback.adapter.OptionsAdapter;
import com.arshad.feedback.global.DbHelper;
import com.arshad.feedback.global.GlobalFunctions;
import com.arshad.feedback.global.SharedPrefsGetSet;
import com.arshad.feedback.model.OptionsData;
import com.arshad.feedback.model.QuestionData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 8/8/16.
 */
public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = QuestionActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tvQuestion)
    TextView tvQuestion;

    @Bind(R.id.rvOptions)
    RecyclerView rvOptions;

    @Bind(R.id.tvSubmit)
    TextView tvSubmit;

    String question, option;
    int userId;
    List<OptionsData> options = new ArrayList<>();
    Context mContext;
    DbHelper db;
    ProgressDialog dialog;
    OptionsAdapter adapter;
    List<QuestionData> questions = new ArrayList<>();
    int position = 0, totalQuestions;
    boolean isSubmitted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        mContext = this;
        db = new DbHelper(mContext);
        dialog = new ProgressDialog(mContext);

        setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) toolbar.findViewById(R.id.tvToolbarTitle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tvToolbarTitle.setText("Feedback Question");

        userId = SharedPrefsGetSet.getUserId(mContext);

        GlobalFunctions.createVerticalRecyclerView(mContext, rvOptions);

        questions = db.getQuestion();
        totalQuestions = questions.size();
        if(questions.size() > 0) {
            setupQuestion(position);
        } else {
            SharedPrefsGetSet.removeUserId(mContext);
            GlobalFunctions.toastShort(mContext, "Add Questions First");
            Intent i = new Intent(mContext, UserDetailsActivity.class);
            startActivity(i);
            finish();
        }

        tvSubmit.setOnClickListener(this);
    }

    private void setupQuestion(int position) {
        tvQuestion.setText(questions.get(position).getQuestion());
        options = createOptionsData(questions.get(position).getOptions());
        adapter = new OptionsAdapter(mContext, options);
        rvOptions.setAdapter(adapter);
        Log.e(TAG, questions.get(position).getOptions().toString() + " ");
    }

    private List<OptionsData> createOptionsData(List<String> options){
        List<OptionsData> optionsDatas = new ArrayList<>();
        for(String option: options) {
            OptionsData data = new OptionsData(option, false);
            optionsDatas.add(data);
        }
        return optionsDatas;
    }

    private boolean validateOptions(){
        for(OptionsData data: options) {
            if(data.isSelected()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSubmit:
                if(validateOptions()) {
                    isSubmitted = true;
                    db.addUserResponse(SharedPrefsGetSet.getUserId(mContext), tvQuestion.getText().toString(), SharedPrefsGetSet.getAnswer(mContext));
                    GlobalFunctions.toastShort(mContext, "Response submitted");
                    SharedPrefsGetSet.removeAnswer(mContext);
                    if((position+1) < totalQuestions) {
                        isSubmitted = false;
                        position = position + 1;
                        setupQuestion(position);
                    } else {
                        SharedPrefsGetSet.removeUserId(mContext);
                        //Go to thank you page
                        Intent i = new Intent(mContext, ThankyouActivity.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    GlobalFunctions.toastShort(mContext, "Select your choice");
                }
                break;

        }
    }
}
