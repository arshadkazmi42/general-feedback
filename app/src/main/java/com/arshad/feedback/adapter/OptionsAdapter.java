package com.arshad.feedback.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arshad.feedback.R;
import com.arshad.feedback.global.SharedPrefsGetSet;
import com.arshad.feedback.model.OptionsData;
import com.arshad.feedback.viewholder.OptionsViewHolder;

import java.util.List;

/**
 * Created by Arshad on 08-03-2016.
 */
public class OptionsAdapter extends RecyclerView.Adapter<OptionsViewHolder> {

    private static final String TAG = OptionsAdapter.class.getSimpleName();

    private List<OptionsData> options;
    private Context mContext;

    public OptionsAdapter(Context mContext, List<OptionsData> options) {
        this.mContext = mContext;
        this.options = options;
    }

    @Override
    public int getItemCount() {
        return (null != options ? options.size() : 0);

    }

    @Override
    public void onBindViewHolder(OptionsViewHolder holder, final int position) {

        holder.cbOption.setText(options.get(position).getOption().toUpperCase());
        if(options.get(position).isSelected()) {
            holder.cbOption.setChecked(true);
        } else {
            holder.cbOption.setChecked(false);
        }

        holder.cbOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(options.get(position).isSelected()) {
                    options.get(position).setSelected(false);
                } else {
                    options.get(position).setSelected(true);
                    SharedPrefsGetSet.setAnswer(mContext, options.get(position).getOption());
                }
                updateOptions(position);
            }
        });
    }

    private void updateOptions(int position){
        for(int i=0;i<options.size();i++){
            if(i!=position) {
                Log.e(TAG, options.get(i).getOption());
                options.get(i).setSelected(false);
            } else {
                Log.e(TAG, options.get(i).getOption());
                options.get(i).setSelected(true);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public OptionsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.single_row_options, viewGroup, false);
        return new OptionsViewHolder(mainGroup);

    }

}