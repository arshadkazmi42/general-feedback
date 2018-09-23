package com.arshad.feedback.global;

import android.content.Context;

/**
 * Created by root on 8/8/16.
 */
public class SharedPrefsGetSet {

    public static final String KEY_USERID = "userId";
    public static final String KEY_ANSWER = "userResponse";

    public static void setUserId(Context mContext, int userId){
        GlobalFunctions.setSharedPrefs(mContext, KEY_USERID, userId);
    }

    public static int getUserId(Context mContext) {
        return GlobalFunctions.getSharedPrefs(mContext, KEY_USERID, 0);
    }

    public static void removeUserId(Context mContext) {
        GlobalFunctions.removeSharedPrefs(mContext, KEY_USERID);
    }

    public static void setAnswer(Context mContext, String option){
        GlobalFunctions.setSharedPrefs(mContext, KEY_ANSWER, option);
    }

    public static String getAnswer(Context mContext) {
        return GlobalFunctions.getSharedPrefs(mContext, KEY_ANSWER, "");
    }

    public static void removeAnswer(Context mContext) {
        GlobalFunctions.removeSharedPrefs(mContext, KEY_ANSWER);
    }

}
