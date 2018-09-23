package com.arshad.feedback.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.arshad.feedback.R;
import com.arshad.feedback.global.DbHelper;
import com.arshad.feedback.global.GlobalFunctions;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tvFeedback)
    TextView tvFeedback;

    @Bind(R.id.tvExportFeedback)
    TextView tvExportFeedback;

    @Bind(R.id.tvAddQuestion)
    TextView tvAddQuestion;

    @Bind(R.id.tvClear)
    TextView tvClear;

    private ProgressDialog dialog;
    private Context mContext;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        dialog = new ProgressDialog(mContext);
        db = new DbHelper(mContext);

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
        tvToolbarTitle.setText("Welcome");

        tvAddQuestion.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvExportFeedback.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
    }

    private void clearPopup() {

        final Dialog popupDialog = new Dialog(mContext);
        popupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupDialog.setContentView(R.layout.global_dialog);
        popupDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView tvClose = (TextView) popupDialog.findViewById(R.id.tvClose);
        TextView tvClearAll = (TextView) popupDialog.findViewById(R.id.tvClearAll);
        TextView tvClearFeedback = (TextView) popupDialog.findViewById(R.id.tvClearFeedback);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();
            }
        });

        tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.clearAll();
                GlobalFunctions.toastShort(mContext, "Feedback and Questions cleared");
                popupDialog.dismiss();
            }
        });

        tvClearFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.clearFeedback();
                GlobalFunctions.toastShort(mContext, "Feedback cleared");
                popupDialog.dismiss();
            }
        });

        try{
            popupDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        popupDialog.setCancelable(false);
        popupDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvFeedback:
                Intent feedbackIntent = new Intent(mContext, UserDetailsActivity.class);
                startActivity(feedbackIntent);
                break;

            case R.id.tvExportFeedback:
                db.exportData();
                GlobalFunctions.toastShort(mContext, "Feedback excel stored in SDCard");
                break;

            case R.id.tvAddQuestion:
                Intent questionIntent = new Intent(mContext, AddQuestionActivity.class);
                startActivity(questionIntent);
                break;

            case R.id.tvClear:
                clearPopup();
                break;
        }
    }
}
