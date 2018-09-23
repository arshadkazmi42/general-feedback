package com.arshad.feedback.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arshad.feedback.R;
import com.arshad.feedback.adapter.AddOptionsAdapter;
import com.arshad.feedback.global.DbHelper;
import com.arshad.feedback.global.GlobalFunctions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddQuestionActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = AddQuestionActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.etQuestion)
    EditText etQuestion;

    @Bind(R.id.rvOptions)
    RecyclerView rvOptions;

    @Bind(R.id.tvAddQuestion)
    TextView tvAddQuestions;

    AddOptionsAdapter adapter;
    Context mContext;
    List<String> options = new ArrayList<>();
    DbHelper db;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);
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
        tvToolbarTitle.setText("Create Questions");

        GlobalFunctions.createVerticalRecyclerView(mContext, rvOptions);

        options.add("option");
        adapter = new AddOptionsAdapter(mContext, options);
        rvOptions.setAdapter(adapter);

        tvAddQuestions.setOnClickListener(this);

    }

    private void addQuestionToDB(){
        String question = etQuestion.getText().toString();
        db.addQuestion(question, removeJunkOptions());
        GlobalFunctions.stopProgressDialog(dialog);

        Intent i = new Intent(mContext, AddQuestionActivity.class);
        startActivity(i);
        finish();
    }

    private List<String> removeJunkOptions(){
        List<String> opt = new ArrayList<>();
        Log.e(TAG, "Option: " + options.toString());
        for(String option: AddOptionsAdapter.options) {
            Log.e(TAG, "Option: " + option);
            if(!option.equalsIgnoreCase("option")){
                opt.add(option);
            }
        }
        return opt;
    }

    private boolean checkOptions(){
        for(String option: AddOptionsAdapter.options) {
            if(option.equalsIgnoreCase("") || option.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvAddQuestion:
                GlobalFunctions.startProgressDialog(dialog, "Please wait");
                String question = etQuestion.getText().toString();
                if(question.isEmpty() || question.equalsIgnoreCase("")) {
                    GlobalFunctions.toastShort(mContext, "Enter question");
                } else if(!checkOptions()) {
                    GlobalFunctions.toastShort(mContext, "Enter options for question");
                } else {
                    addQuestionToDB();
                }
                break;
        }
    }
}
