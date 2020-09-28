package com.auro.scholr.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.widget.ImageView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class ImageUtil {


    public static void loadImageFromGlide(ImageView view, String imageUrl, int width, int height, Drawable placeholder, boolean thumbNailStatus) {

        Glide.with(view.getContext())
                .asBitmap()
                .load(imageUrl)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                  view.setImageDrawable(placeholder);
                                  return true;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                                  // add image to the imageView here
                                  Bitmap bitmap = null;
                                  if (thumbNailStatus) {
                                      bitmap = ThumbnailUtils.extractThumbnail(resource, width, height);
                                      view.setImageBitmap(bitmap);
                                  } else if (!thumbNailStatus && width > 0 && height > 0) {
                                      bitmap = Bitmap.createScaledBitmap(resource, width, height, true);
                                      view.setImageBitmap(bitmap);
                                  }
                                  return true;
                              }
                          }
                ).submit();
    }


    public static File compressFile(File file) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            if (getFileExt(file.getName()).equals("png") || getFileExt(file.getName()).equals("PNG")) {
                o.inSampleSize = 6;
            } else {
                o.inSampleSize = 6;
            }

            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);

            ExifInterface ei = new ExifInterface(file.getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            setOrientation(selectedBitmap, orientation);

            inputStream.close();

            // here i override the original image file
            File folder = new File(Environment.getExternalStorageDirectory() + "/auroImageDir");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }
            if (success) {
                File newFile = new File(new File(folder.getAbsolutePath()), file.getName());
                if (newFile.exists()) {
                    newFile.delete();
                }
                FileOutputStream outputStream = new FileOutputStream(newFile);

                if (getFileExt(file.getName()).equals("png") || getFileExt(file.getName()).equals("PNG")) {
                    assert selectedBitmap != null;
                    selectedBitmap.compress(Bitmap.CompressFormat.PNG, 40, outputStream);
                } else {
                    assert selectedBitmap != null;
                    selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream);
                }

                return newFile;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    private static void setOrientation(Bitmap selectedBitmap, int orientation) {
        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotateImage(selectedBitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotateImage(selectedBitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotateImage(selectedBitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:

            default:
                break;
        }
    }

    private static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public static void loadNormalImage(ImageView view,String imgUrl)
    {
        Glide.with(view.getContext()).load(imgUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_image_placeholder)
                        .error(R.drawable.ic_image_placeholder)
                        .centerCrop()
                        .dontAnimate()
                        .priority(Priority.IMMEDIATE)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(view);
    }



    // [-100, +100] -> Default = 0
    public static Bitmap contrast(Bitmap src, double value) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap

        // create a mutable empty bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        // create a canvas so that we can draw the bmOut Bitmap from source bitmap
        Canvas c = new Canvas();
        c.setBitmap(bmOut);

        // draw bitmap to bmOut from src bitmap so we can modify it
        c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));


        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.green(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.blue(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        src.recycle();
        src = null;
        return bmOut;
    }


    public static void loadCircleImage(ImageView imageView, String imagePath)
    {

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(imagePath)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                  return true;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                                  // add image to the imageView here
                                  RoundedBitmapDrawable circularBitmapDrawable =
                                          RoundedBitmapDrawableFactory.create(AuroApp.getAppContext().getResources(), resource);
                                  circularBitmapDrawable.setCircular(true);

                                  AuroApp.getAppContext().runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          // TODO your Code
                                          imageView.setImageDrawable(circularBitmapDrawable);
                                      }
                                  });

                                  return true;
                              }
                          }
                ).submit();
    }

}
