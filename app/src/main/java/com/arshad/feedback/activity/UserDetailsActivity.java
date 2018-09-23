package com.arshad.feedback.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arshad.feedback.R;
import com.arshad.feedback.global.DbHelper;
import com.arshad.feedback.global.GlobalFunctions;
import com.arshad.feedback.global.SharedPrefsGetSet;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 8/8/16.
 */
public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.etName)
    EditText etName;

    @Bind(R.id.etEmail)
    EditText etEmail;

    @Bind(R.id.etPhone)
    EditText etPhone;

    @Bind(R.id.etAddress)
    EditText etAddress;

    @Bind(R.id.tvSubmit)
    TextView tvSubmit;

    String email, phone, name, address;

    DbHelper db;
    ProgressDialog dialog;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
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
        tvToolbarTitle.setText("User Information");

        if(SharedPrefsGetSet.getUserId(mContext) != 0) {
            startQuestionActivity();
        }

        tvSubmit.setOnClickListener(this);
    }

    private boolean validateFields(){
        email = etEmail.getText().toString();
        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        address = etAddress.getText().toString();

        if(email.isEmpty() || email.equalsIgnoreCase("")) {
            GlobalFunctions.toastShort(mContext, "Enter email");
            return false;
        } else if(name.isEmpty() || name.equalsIgnoreCase("")) {
            GlobalFunctions.toastShort(mContext, "Enter name");
            return false;
        } else if(phone.isEmpty() || phone.equalsIgnoreCase("")){
            GlobalFunctions.toastShort(mContext, "Enter phone");
            return false;
        } else if(address.isEmpty() || address.equalsIgnoreCase("")) {
            GlobalFunctions.toastShort(mContext, "Enter address");
            return false;
        } else {
            return true;
        }
    }

    private void startQuestionActivity(){
        Intent i = new Intent(mContext, QuestionActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSubmit:
                if(validateFields()){
                    GlobalFunctions.startProgressDialog(dialog, "Please wait");
                    int userId = db.addUser(email, name, phone, address);
                    SharedPrefsGetSet.setUserId(mContext, userId);
                    GlobalFunctions.stopProgressDialog(dialog);

                    startQuestionActivity();
                }
                break;
        }
    }
}
