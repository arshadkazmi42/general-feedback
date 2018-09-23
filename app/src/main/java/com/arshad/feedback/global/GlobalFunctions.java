package com.arshad.feedback.global;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.arshad.feedback.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GlobalFunctions {

    private static final String TAG = "GlobalFunctions";

    private static String app_name = "com.arshad";

    /**
     * Storing string value into SharedPrefs
     *
     * @param c
     * @param key
     * @param value
     */
    public static void setSharedPrefs(Context c, String key, String value) {

        SharedPreferences.Editor editor = c.getSharedPreferences(app_name,
                Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();

    }

    /**
     * Storing int value into SharedPrefs
     *
     * @param c
     * @param key
     * @param value
     */
    public static void setSharedPrefs(Context c, String key, int value) {

        SharedPreferences.Editor editor = c.getSharedPreferences(app_name,
                Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();

    }

    /**
     * Storing float value into SharedPrefs
     *
     * @param c
     * @param key
     * @param value
     */
    public static void setSharedPrefs(Context c, String key, float value) {

        SharedPreferences.Editor editor = c.getSharedPreferences(app_name,
                Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.apply();

    }

    /**
     * Fetching float value from SharedPrefs
     *
     * @param c
     * @param key
     * @param default_value
     * @return
     */
    public static float getSharedPrefs(Context c, String key, float default_value) {
        if (c == null) {
            return default_value;
        } else {
            SharedPreferences prefs = c.getSharedPreferences(app_name,
                    Context.MODE_PRIVATE);
            return prefs.getFloat(key, default_value);
        }
    }

    /**
     * Fetching String value from SharedPrefs
     *
     * @param c
     * @param key
     * @param default_value
     * @return
     */
    public static String getSharedPrefs(Context c, String key,
                                        String default_value) {
        if (c == null) {
            return default_value;
        } else {
            SharedPreferences prefs = c.getSharedPreferences(app_name,
                    Context.MODE_PRIVATE);
            return prefs.getString(key, default_value);
        }
    }

    /**
     * Fetching int value from SharedPrefs
     *
     * @param c
     * @param key
     * @param default_value
     * @return
     */
    public static int getSharedPrefs(Context c, String key, int default_value) {
        if (c == null) {
            return default_value;
        } else {
            SharedPreferences prefs = c.getSharedPreferences(app_name,
                    Context.MODE_PRIVATE);
            return prefs.getInt(key, default_value);
        }
    }

    /**
     * Checking if SharedPrefs contains key
     *
     * @param c
     * @param key
     * @return
     */
    public static boolean checkSharedPrefs(Context c, String key) {
        if (c == null) {
            return false;
        } else {
            SharedPreferences prefs = c.getSharedPreferences(app_name,
                    Context.MODE_PRIVATE);
            return prefs.contains(key);
        }
    }

    /**
     * Removing from SharedPrefs
     *
     * @param c
     * @param key
     */
    public static void removeSharedPrefs(Context c, String key) {
        SharedPreferences.Editor editor = c.getSharedPreferences(app_name,
                Context.MODE_PRIVATE).edit();
        editor.remove(key);
        //editor.commit();
        editor.apply();
    }


    /**
     * Clear SharedPrefs
     *
     * @param c
     */
    public static void clearSharedPrefs(Context c) {
        SharedPreferences.Editor editor = c.getSharedPreferences(app_name,
                Context.MODE_PRIVATE).edit();
        //editor.commit();
        editor.clear();
        editor.apply();
    }

    /**
     * Showing Short Toast Message
     */
    public static void toastShort(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Long Toast Message
     *
     * @param c
     * @param msg
     */
    public static void toastLong(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Hide Keypad - removing focus from selected Edittext
     *
     * @param c
     * @param et
     */
    public static void hideKeyboard(Context c, EditText et) {
        InputMethodManager imm = (InputMethodManager) c
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    /**
     * Disable screen touch
     *
     * @param window
     */
    public static void disableTouch(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * Enable screen touch
     *
     * @param window
     */
    public static void enableTouch(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * Validate Email
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    /**
     * Make View Visible
     *
     * @param views
     */
    public static void viewVisible(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Make View Hidden
     *
     * @param views
     */
    public static void viewHidden(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Make View Invisible
     *
     * @param views
     */
    public static void viewInvisible(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * Creating HorizontalLayout for RecyclerView
     *
     * @param mContext
     * @param recyclerViews
     */
    public static void createHorizontalRecyclerView(Context mContext, RecyclerView... recyclerViews) {
        for (RecyclerView recyclerView : recyclerViews) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    /**
     * Creating VerticalLayout for RecyclerView
     *
     * @param mContext
     * @param recyclerViews
     */
    public static void createVerticalRecyclerView(Context mContext, RecyclerView... recyclerViews) {
        for (RecyclerView recyclerView : recyclerViews) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        }
    }

    /**
     * Start ProgressDialog loading
     *
     * @param dialog
     * @param message
     */
    public static void startProgressDialog(ProgressDialog dialog, String message) {
        try {
            dialog.setMessage(message);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            dialog.setContentView(R.layout.global_progress_dialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop progress dialog loading
     *
     * @param dialog
     */
    public static void stopProgressDialog(ProgressDialog dialog) {
        try {
            if (dialog != null && dialog.isShowing()) {
                Log.e(TAG, "Dismissing Dialog");
                dialog.dismiss();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Formating data-time from custom format to custom format
     *
     * @param date
     * @param fromFormat
     * @param toFormat
     * @return
     */
    public static String formatDateTime(String date, String fromFormat, String toFormat) {
        Date d = null;
        try {
            d = new SimpleDateFormat(fromFormat, Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(toFormat, Locale.US).format(d);
    }

    /**
     * Convert String date to Calendar object
     * @param date
     * @param format
     * @return
     */
    public static Calendar convertStringDateToCalendar(String date, String format){
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            cal.setTime(sdf.parse(date));
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Convert 24Hour - hour to 12 hour with am/pm
     * @param hour
     * @return
     */
    public static String convert24HourTo12Hour(int hour){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        int h = cal.get(Calendar.HOUR);
        if(h == 0){
            h = 12;
        }
        Log.e(TAG, "Hour: " + String.valueOf(h) + " " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
        if(hour < 12) {
            return h + " am";
        } else {
            return h + " pm";
        }
    }


}
