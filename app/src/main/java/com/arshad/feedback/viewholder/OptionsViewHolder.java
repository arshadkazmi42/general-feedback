package com.arshad.feedback.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.arshad.feedback.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Arshad on 08-03-2016.
 */
public class OptionsViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.cbOptions)
    public RadioButton cbOption;

    public OptionsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
