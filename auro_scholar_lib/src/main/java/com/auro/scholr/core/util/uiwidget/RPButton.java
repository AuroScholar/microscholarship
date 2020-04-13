package com.auro.scholr.core.util.uiwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.auro.scholr.R;

public class RPButton extends AppCompatButton {
    public RPButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RPView, 0, 0);
        String font = a.getString(R.styleable.RPView_Font);
        if(font != null){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + "azosans_medium.ttf");
            if(typeface != null){
                setTypeface(typeface);
            }
        }
        a.recycle();
    }
}
