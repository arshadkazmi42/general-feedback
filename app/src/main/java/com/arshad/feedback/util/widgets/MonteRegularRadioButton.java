package com.arshad.feedback.util.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.arshad.feedback.global.Constants;

/**
 * Created by Arshad on 04-06-2016.
 */
public class MonteRegularRadioButton extends RadioButton {

    /*
     * Caches typefaces based on their file path and name,
     * so that they don't have to be created every time when they are referenced.
     */
    private static Typeface mTypeface;

    public MonteRegularRadioButton(Context context) {
        super(context);
        init(context);
    }

    public MonteRegularRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MonteRegularRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void init(Context context) {
        try {
            if (mTypeface == null) {
                mTypeface = Typeface.createFromAsset(context.getAssets(), Constants.FONT_REGULAR);
            }
            setTypeface(mTypeface);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
