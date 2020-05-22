package com.auro.scholr.core.util.uiwidget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StyleRes;

public final class SimpleTooltipUtils {

    private SimpleTooltipUtils() {

    }

    public static RectF calculeRectOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getMeasuredWidth(), location[1] + view.getMeasuredHeight());
    }

    public static RectF calculeRectInWindow(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return new RectF(location[0], location[1], location[0] + view.getMeasuredWidth(), location[1] + view.getMeasuredHeight());
    }

    public static float dpFromPx(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }

    public static float pxFromDp(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static void setWidth(View view, float width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams((int) width, view.getHeight());
        } else {
            params.width = (int) width;
        }
        view.setLayoutParams(params);
    }

    public static int tooltipGravityToArrowDirection(int tooltipGravity) {
        switch (tooltipGravity) {
            case Gravity.START:
                return ArrowDrawable.RIGHT;
            case Gravity.END:
                return ArrowDrawable.LEFT;
            case Gravity.TOP:
                return ArrowDrawable.BOTTOM;
            case Gravity.BOTTOM:
                return ArrowDrawable.TOP;
            case Gravity.CENTER:
                return ArrowDrawable.TOP;
            default:
                throw new IllegalArgumentException("Gravity must have be CENTER, START, END, TOP or BOTTOM.");
        }
    }

    public static void setX(View view, int x) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setX(x);
        } else {
            ViewGroup.MarginLayoutParams marginParams = getOrCreateMarginLayoutParams(view);
            marginParams.leftMargin = x - view.getLeft();
            view.setLayoutParams(marginParams);
        }
    }

    public static void setY(View view, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setY(y);
        } else {
            ViewGroup.MarginLayoutParams marginParams = getOrCreateMarginLayoutParams(view);
            marginParams.topMargin = y - view.getTop();
            view.setLayoutParams(marginParams);
        }
    }

    private static ViewGroup.MarginLayoutParams getOrCreateMarginLayoutParams(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null) {
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                return (ViewGroup.MarginLayoutParams) lp;
            } else {
                return new ViewGroup.MarginLayoutParams(lp);
            }
        } else {
            return new ViewGroup.MarginLayoutParams(view.getWidth(), view.getHeight());
        }
    }

    public static void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            //noinspection deprecation
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    public static void setTextAppearance(TextView tv, @StyleRes int textAppearanceRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setTextAppearance(textAppearanceRes);
        } else {
            //noinspection deprecation
            tv.setTextAppearance(tv.getContext(), textAppearanceRes);
        }
    }

    public static int getColor(Context context, @ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(colorRes);
        } else {
            //noinspection deprecation
            return context.getResources().getColor(colorRes);
        }
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getDrawable(drawableRes);
        } else {
            //noinspection deprecation
            return context.getResources().getDrawable(drawableRes);
        }
    }

    /**
     * Verify if the first child of the rootView is a FrameLayout.
     * Used for cases where the Tooltip is created inside a Dialog or DialogFragment.
     *
     * @param anchorView
     * @return FrameLayout or anchorView.getRootView()
     */
    public static ViewGroup findFrameLayout(View anchorView) {
        ViewGroup rootView = (ViewGroup) anchorView.getRootView();
        if (rootView.getChildCount() == 1 && rootView.getChildAt(0) instanceof FrameLayout) {
            rootView = (ViewGroup) rootView.getChildAt(0);
        }
        return rootView;
    }
}
