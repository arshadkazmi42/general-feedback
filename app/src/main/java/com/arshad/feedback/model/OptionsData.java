package com.arshad.feedback.model;

/**
 * Created by root on 8/8/16.
 */
public class OptionsData {
    String option;
    boolean selected;

    public OptionsData(){}

    public OptionsData(String option, boolean selected){
        this.option = option;
        this.selected = selected;
    }

    public void setOption(String option){
        this.option = option;
    }

    public String getOption(){
        return option;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected(){
        return selected;
    }
}
