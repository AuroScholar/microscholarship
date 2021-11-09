package com.auro.scholr.util.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.ViewGroup;

import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.utility.GraphicOverlay;
import com.google.android.gms.common.images.Size;

import java.io.IOException;

public class CameraSourcePreview extends ViewGroup {
    private static final String TAG = "CameraSourcePreview";

    //PREVIEW VISUALIZERS FOR BOTH CAMERA1 AND CAMERA2 API.
    private SurfaceView mSurfaceView;
    private com.auro.scholr.util.camera.AutoFitTextureView mAutoFitTextureView;

    private boolean usingCameraOne;
    private boolean mStartRequested;
    private boolean mSurfaceAvailable;
    private boolean viewAdded = false;

    //CAMERA SOURCES FOR BOTH CAMERA1 AND CAMERA2 API.
    private com.auro.scholr.util.camera.CameraSource mCameraSource;
    private Camera2Source mCamera2Source;

    private GraphicOverlay mOverlay;
    private int screenWidth;
    private int screenHeight;
    private int screenRotation;

    public CameraSourcePreview(Context context) {
        super(context);
        screenHeight = ViewUtil.getScreenHeight(context);
        screenWidth = ViewUtil.getScreenWidth(context);
        screenRotation = ViewUtil.getScreenRotation(context);
        mStartRequested = false;
        mSurfaceAvailable = false;
        mSurfaceView = new SurfaceView(context);
        mSurfaceView.getHolder().addCallback(mSurfaceViewListener);
        mAutoFitTextureView = new com.auro.scholr.util.camera.AutoFitTextureView(context);
        mAutoFitTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
    }
    
    public CameraSourcePreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        screenHeight = ViewUtil.getScreenHeight(context);
        screenWidth = ViewUtil.getScreenWidth(context);
        screenRotation = ViewUtil.getScreenRotation(context);
        mStartRequested = false;
        mSurfaceAvailable = false;
        mSurfaceView = new SurfaceView(context);
        mSurfaceView.getHolder().addCallback(mSurfaceViewListener);
        mAutoFitTextureView = new com.auro.scholr.util.camera.AutoFitTextureView(context);
        mAutoFitTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
    }

    public void start(com.auro.scholr.util.camera.CameraSource cameraSource, GraphicOverlay overlay) throws IOException {
        usingCameraOne = true;
        mOverlay = overlay;
        start(cameraSource);
    }

    public void start(Camera2Source camera2Source, GraphicOverlay overlay) throws IOException {
        usingCameraOne = false;
        mOverlay = overlay;
        start(camera2Source);
    }

    private void start(com.auro.scholr.util.camera.CameraSource cameraSource) throws IOException {
        if (cameraSource == null) {stop();}
        mCameraSource = cameraSource;
        if (mCameraSource != null) {
            mStartRequested = true;
            if(!viewAdded) {
                addView(mSurfaceView);
                viewAdded = true;
            }
            try {startIfReady();} catch (IOException e) {
                Log.e(TAG, "Could not start camera source.", e);}
        }
    }

    private void start(Camera2Source camera2Source) throws IOException {
        if (camera2Source == null) {stop();}
        mCamera2Source = camera2Source;
        if(mCamera2Source != null) {
            mStartRequested = true;
            if(!viewAdded) {
                addView(mAutoFitTextureView);
                viewAdded = true;
            }
            try {startIfReady();} catch (IOException e) {
                Log.e(TAG, "Could not start camera source.", e);}
        }
    }

    public void stop() {
        mStartRequested = false;
        if(usingCameraOne) {
            if (mCameraSource != null) {
                mCameraSource.stop();
            }
        } else {
            if(mCamera2Source != null) {
                mCamera2Source.stop();
            }
        }
    }

    private void startIfReady() throws IOException {
        if (mStartRequested && mSurfaceAvailable) {
            try {
                if(usingCameraOne) {
                    mCameraSource.start(mSurfaceView.getHolder());
                    if (mOverlay != null) {
                        Size size = mCameraSource.getPreviewSize();
                        if(size != null) {
                            int min = Math.min(size.getWidth(), size.getHeight());
                            int max = Math.max(size.getWidth(), size.getHeight());
                            // FOR GRAPHIC OVERLAY, THE PREVIEW SIZE WAS REDUCED TO QUARTER
                            // IN ORDER TO PREVENT CPU OVERLOAD
                            mOverlay.setCameraInfo(min/4, max/4, mCameraSource.getCameraFacing());
                            mOverlay.clear();
                        } else {
                            stop();
                        }
                    }
                    mStartRequested = false;
                } else {
                    mCamera2Source.start(mAutoFitTextureView, screenRotation);
                    if (mOverlay != null) {
                        Size size = mCamera2Source.getPreviewSize();
                        if(size != null) {
                            int min = Math.min(size.getWidth(), size.getHeight());
                            int max = Math.max(size.getWidth(), size.getHeight());
                            // FOR GRAPHIC OVERLAY, THE PREVIEW SIZE WAS REDUCED TO QUARTER
                            // IN ORDER TO PREVENT CPU OVERLOAD
                            mOverlay.setCameraInfo(min/4, max/4, mCamera2Source.getCameraFacing());
                            mOverlay.clear();
                        } else {
                            stop();
                        }
                    }
                    mStartRequested = false;
                }
            } catch (SecurityException e) {
                Log.d(TAG, "SECURITY EXCEPTION: "+e);}
        }
    }

    private final SurfaceHolder.Callback mSurfaceViewListener = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surface) {
            mSurfaceAvailable = true;
            mOverlay.bringToFront();
            try {startIfReady();} catch (IOException e) {
                Log.e(TAG, "Could not start camera source.", e);}
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder surface) {
            mSurfaceAvailable = false;
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
    };

    private final TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            mSurfaceAvailable = true;
            mOverlay.bringToFront();
            try {startIfReady();} catch (IOException e) {
                Log.e(TAG, "Could not start camera source.", e);}
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {}
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            mSurfaceAvailable = false;
            return true;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {}
    };

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width =400;
        int height = 400;
        if(usingCameraOne) {
            if (mCameraSource != null) {
                Size size = mCameraSource.getPreviewSize();
                if (size != null) {
                    // Swap width and height sizes when in portrait, since it will be rotated 90 degrees

                }
            }
        } else {
            if (mCamera2Source != null) {
                Size  size = mCamera2Source.getPreviewSize();
                if (size != null) {
                    // Swap width and height sizes when in portrait, since it will be rotated 90 degrees
                    width = size.getWidth();
                    height = size.getHeight();
                }
            }
        }

        //RESIZE PREVIEW IGNORING ASPECT RATIO. THIS IS ESSENTIAL.
        int newWidth = (width * screenWidth) / screenHeight;
        int newHeight = (height * screenWidth)/screenHeight;

  /*      final int layoutWidth = newWidth;// right - left;
        final int layoutHeight =newWidth;; //bottom - top;*/
        final int layoutWidth = right - left;
        final int layoutHeight = bottom - top;
        // Computes height and width for potentially doing fit width.
        int childWidth = layoutWidth;
        int childHeight = (int)(((float) layoutWidth / (float) newWidth) * height);
        // If height is too tall using fit width, does fit height instead.
        if (childHeight > layoutHeight) {
            childHeight = layoutHeight;
            childWidth =layoutWidth;// (int)(((float) layoutHeight / (float) height) * layoutWidth);
        }
        for (int i = 0; i < getChildCount(); ++i) {getChildAt(i).layout(0, 0, childWidth, childHeight);}
        try {startIfReady();} catch (IOException e) {
            Log.e(TAG, "Could not start camera source.", e);}
    }


}
