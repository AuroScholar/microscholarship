package com.auro.scholr.util;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;


import java.util.List;

public class TextUtil {

    public static void setBoldText(TextView pTextView, String pStringMsg, int pStartPoint, int pEndPoint) {
        SpannableString spanString = new SpannableString(pStringMsg);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), pStartPoint, pEndPoint, 0);
        pTextView.setText(spanString);
    }

    public static boolean isEmpty(String text) {
        if (text != null && !text.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    public static String getCamelCase(String text) {

        String upper = text.toUpperCase();
        return upper.substring(0, 1) + upper.substring(1).toLowerCase();

    }





    public static boolean checkListIsEmpty(List data) {
        if (data != null && !data.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }
    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public static String removeAllSpace(String text) {
        return text.replaceAll("\\s+", "");
    }

}
