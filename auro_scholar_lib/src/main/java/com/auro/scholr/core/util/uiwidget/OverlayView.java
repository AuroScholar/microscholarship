package com.auro.scholr.core.util.uiwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;


import com.auro.scholr.R;

public class OverlayView extends View {

    public static final int HIGHLIGHT_SHAPE_OVAL = 5;
    public static final int HIGHLIGHT_SHAPE_RECTANGULAR = 1;
    private static final int mDefaultOverlayAlphaRes = R.integer.simpletooltip_overlay_alpha;


    private View mAnchorView;
    private Bitmap bitmap;

    private boolean invalidated = true;
    private final int highlightShape;
    private final float mOffset;
    private final int overlayWindowBackground;

    OverlayView(Context context, View anchorView, int highlightShape, float offset, int overlayWindowBackground) {
        super(context);
        this.mAnchorView = anchorView;
        this.mOffset = offset;
        this.highlightShape = highlightShape;
        this.overlayWindowBackground=overlayWindowBackground;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (invalidated || bitmap == null || bitmap.isRecycled())
            createWindowFrame();
        // The bitmap is checked again because of Android memory cleanup behavior. (See #42)
        if (bitmap != null && !bitmap.isRecycled())
            canvas.drawBitmap(bitmap, 0, 0, null);
    }

    private void createWindowFrame() {
        final int width = getMeasuredWidth(), height = getMeasuredHeight();
        if (width <= 0 || height <= 0)
            return;

        if (bitmap != null && !bitmap.isRecycled())
            bitmap.recycle();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas osCanvas = new Canvas(bitmap);

        RectF outerRectangle = new RectF(0, 0, width, height);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(overlayWindowBackground);
        paint.setAntiAlias(true);
        paint.setAlpha(getResources().getInteger(mDefaultOverlayAlphaRes));
        osCanvas.drawRect(outerRectangle, paint);

        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        RectF anchorRecr = SimpleTooltipUtils.calculeRectInWindow(mAnchorView);
        RectF overlayRecr = SimpleTooltipUtils.calculeRectInWindow(this);

        float left = anchorRecr.left - overlayRecr.left;
        float top = anchorRecr.top - overlayRecr.top;

        RectF rect = new RectF(left - mOffset, top - mOffset, left + mAnchorView.getMeasuredWidth() + mOffset, top + mAnchorView.getMeasuredHeight() + mOffset);

        if (highlightShape == HIGHLIGHT_SHAPE_RECTANGULAR) {
            osCanvas.drawRect(rect, paint);
        } else {
            osCanvas.drawOval(rect, paint);
        }

        invalidated = false;
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        invalidated = true;
    }

    public View getAnchorView() {
        return mAnchorView;
    }

    public void setAnchorView(View anchorView) {
        this.mAnchorView = anchorView;
        invalidate();
    }
}
