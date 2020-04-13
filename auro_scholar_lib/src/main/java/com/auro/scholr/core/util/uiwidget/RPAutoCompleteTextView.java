package com.auro.scholr.core.util.uiwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.auro.scholr.R;


/**
 * Created by ashutosh on 4/5/18.
 */

public class RPAutoCompleteTextView extends androidx.appcompat.widget.AppCompatAutoCompleteTextView {
    public RPAutoCompleteTextView(Context context) {
        super(context);
    }

    public RPAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RPView, 0, 0);

        String font = a.getString(R.styleable.RPView_Font);
        if(font != null){
            Typeface typeface = getTypeface(font, context);
            if(typeface != null){
                setTypeface(typeface);
            }
        }
        a.recycle();
    }


    private Typeface getTypeface(String fontname, Context context) {

        Typeface typeface = null;
        try {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/"+fontname);
        } catch (Exception e) {
            return null;
        }
        return typeface;
    }
}
