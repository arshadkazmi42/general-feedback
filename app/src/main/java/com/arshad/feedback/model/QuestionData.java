package com.arshad.feedback.model;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by root on 8/8/16.
 */
public class QuestionData {

    int id;
    String question;
    List<String> options;

    public QuestionData(){}

    public QuestionData(int id, String question, List<String> options){
        this.question = question;
        this.options = options;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getQuestion(){
        return question;
    }

    public void setOptions(List<String> options){
        this.options = options;
    }

    public List<String> getOptions(){
        return options;
    }

}
