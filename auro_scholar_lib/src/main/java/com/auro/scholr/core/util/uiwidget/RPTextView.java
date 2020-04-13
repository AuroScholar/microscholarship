package com.auro.scholr.core.util.uiwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

import com.auro.scholr.R;


@BindingMethods({
        @BindingMethod(
                type = AppCompatTextView.class,
                attribute = "app:onTextClick",
                method = "setOnClickListener"
        ),
})
public class RPTextView extends AppCompatTextView {
    public RPTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RPView, 0, 0);
        String font = a.getString(R.styleable.RPView_Font);
        if(font != null){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + font);
            if(typeface != null){
                setTypeface(typeface);
            }
        }
        a.recycle();
    }
}
