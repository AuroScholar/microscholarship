package com.auro.scholr.core.util.uiwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.auro.scholr.R;

public class RPEditText extends AppCompatEditText {

    Context context;
    public RPEditText(Context context) {
        super(context);
        this.context = context;
    }

    public RPEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RPView, 0, 0);
        String font = a.getString(R.styleable.RPView_Font);
        boolean changeStar = a.getBoolean(R.styleable.RPView_starPin, false);
        if(font != null){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + font);
            if(typeface != null){
                setTypeface(typeface);
            }
        }
        if(changeStar){
            this.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        }
        a.recycle();
    }

    public void setTypeFace(String ttfFile){
        if(ttfFile != null){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + ttfFile);
            if(typeface != null){
                setTypeface(typeface);
            }
        }
    }

}
