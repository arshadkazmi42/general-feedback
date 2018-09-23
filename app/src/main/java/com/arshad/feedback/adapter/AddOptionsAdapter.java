package com.arshad.feedback.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arshad.feedback.R;
import com.arshad.feedback.global.GlobalFunctions;
import com.arshad.feedback.viewholder.AddOptionsViewHolder;

import java.util.List;

/**
 * Created by Arshad on 08-03-2016.
 */
public class AddOptionsAdapter extends RecyclerView.Adapter<AddOptionsViewHolder> {

    private static final String TAG = AddOptionsAdapter.class.getSimpleName();

    public static List<String> options;
    private Context mContext;

    public AddOptionsAdapter(Context mContext, List<String> options) {
        this.mContext = mContext;
        this.options = options;
    }

    @Override
    public int getItemCount() {
        return (null != options ? options.size() : 0);

    }

    @Override
    public void onBindViewHolder(final AddOptionsViewHolder holder, final int position) {

        holder.etOption.setHint(options.get(position));
        holder.etOption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                options.set(position, holder.etOption.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.etOption.getText().toString().isEmpty()) {
                    GlobalFunctions.toastShort(mContext, "Add option");
                } else {
                    GlobalFunctions.hideKeyboard(mContext, holder.etOption);
                    options.set(position, holder.etOption.getText().toString());
                    options.add("option");
                    notifyItemInserted(options.size() - 1);
                }
            }
        });
    }

    @Override
    public AddOptionsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.single_row_add_option, viewGroup, false);
        return new AddOptionsViewHolder(mainGroup);

    }

}