package com.arshad.feedback.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arshad.feedback.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Arshad on 08-03-2016.
 */
public class AddOptionsViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.etOptions)
    public EditText etOption;

    @Bind(R.id.ivAdd)
    public ImageView ivAdd;

    public AddOptionsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
