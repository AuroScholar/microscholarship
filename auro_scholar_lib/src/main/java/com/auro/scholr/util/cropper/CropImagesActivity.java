// "Therefore those skilled at the unorthodox
// are infinite as heaven and earth,
// inexhaustible as the great rivers.
// When they come to an end,
// they begin again,
// like the days and months;
// they die and are reborn,
// like the four seasons."
//
// - Sun Tsu,
// "The Art of War"

package com.auro.scholr.util.cropper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.auro.scholr.R;

import java.io.File;
import java.io.IOException;

/**
 * Built-in activity for image cropping.<br>
 * Use {@link CropImages#activity(Uri)} to create a builder to start this activity.
 */
public class CropImagesActivity extends AppCompatActivity
    implements CropImageViews.OnSetImageUriCompleteListener,
        CropImageViews.OnCropImageCompleteListener {

  /** The crop image view library widget used in the activity */
  private CropImageViews mCropImageViews;

  /** Persist URI image to crop URI if specific permissions are required */
  private Uri mCropImageUri;

  /** the options that were set for the crop image */
  private CropImagesOptions mOptions;

  @Override
  @SuppressLint("NewApi")
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.auro_crop_image_activity);

    mCropImageViews = findViewById(R.id.cropImageView);

    Bundle bundle = getIntent().getBundleExtra(CropImages.CROP_IMAGE_EXTRA_BUNDLE);
    mCropImageUri = bundle.getParcelable(CropImages.CROP_IMAGE_EXTRA_SOURCE);
    mOptions = bundle.getParcelable(CropImages.CROP_IMAGE_EXTRA_OPTIONS);

    if (savedInstanceState == null) {
      if (mCropImageUri == null || mCropImageUri.equals(Uri.EMPTY)) {
        if (CropImages.isExplicitCameraPermissionRequired(this)) {
          // request permissions and handle the result in onRequestPermissionsResult()
          requestPermissions(
              new String[] {Manifest.permission.CAMERA},
              CropImages.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
        } else {
          CropImages.startPickImageActivity(this);
        }
      } else if (CropImages.isReadExternalStoragePermissionsRequired(this, mCropImageUri)) {
        // request permissions and handle the result in onRequestPermissionsResult()
        requestPermissions(
            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
            CropImages.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
      } else {
        // no permissions required or already grunted, can start crop image activity
        mCropImageViews.setImageUriAsync(mCropImageUri);
      }
    }

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      CharSequence title = mOptions != null &&
          mOptions.activityTitle != null && mOptions.activityTitle.length() > 0
              ? mOptions.activityTitle
              : this.getResources().getString(R.string.crop_image_activity_title);
      actionBar.setTitle(title);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    mCropImageViews.setOnSetImageUriCompleteListener(this);
    mCropImageViews.setOnCropImageCompleteListener(this);
  }

  @Override
  protected void onStop() {
    super.onStop();
    mCropImageViews.setOnSetImageUriCompleteListener(null);
    mCropImageViews.setOnCropImageCompleteListener(null);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.crop_image_menu, menu);

    if (!mOptions.allowRotation) {
      menu.removeItem(R.id.crop_image_menu_rotate_left);
      menu.removeItem(R.id.crop_image_menu_rotate_right);
    } else if (mOptions.allowCounterRotation) {
      menu.findItem(R.id.crop_image_menu_rotate_left).setVisible(true);
    }

    if (!mOptions.allowFlipping) {
      menu.removeItem(R.id.crop_image_menu_flip);
    }

    if (mOptions.cropMenuCropButtonTitle != null) {
      menu.findItem(R.id.crop_image_menu_crop).setTitle(mOptions.cropMenuCropButtonTitle);
    }

    Drawable cropIcon = null;
    try {
      if (mOptions.cropMenuCropButtonIcon != 0) {
        cropIcon = ContextCompat.getDrawable(this, mOptions.cropMenuCropButtonIcon);
        menu.findItem(R.id.crop_image_menu_crop).setIcon(cropIcon);
      }
    } catch (Exception e) {
      Log.w("AIC", "Failed to read menu crop drawable", e);
    }

    if (mOptions.activityMenuIconColor != 0) {
      updateMenuItemIconColor(
          menu, R.id.crop_image_menu_rotate_left, mOptions.activityMenuIconColor);
      updateMenuItemIconColor(
          menu, R.id.crop_image_menu_rotate_right, mOptions.activityMenuIconColor);
      updateMenuItemIconColor(menu, R.id.crop_image_menu_flip, mOptions.activityMenuIconColor);
      if (cropIcon != null) {
        updateMenuItemIconColor(menu, R.id.crop_image_menu_crop, mOptions.activityMenuIconColor);
      }
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.crop_image_menu_crop) {
      cropImage();
      return true;
    }
    if (item.getItemId() == R.id.crop_image_menu_rotate_left) {
      rotateImage(-mOptions.rotationDegrees);
      return true;
    }
    if (item.getItemId() == R.id.crop_image_menu_rotate_right) {
      rotateImage(mOptions.rotationDegrees);
      return true;
    }
    if (item.getItemId() == R.id.crop_image_menu_flip_horizontally) {
      mCropImageViews.flipImageHorizontally();
      return true;
    }
    if (item.getItemId() == R.id.crop_image_menu_flip_vertically) {
      mCropImageViews.flipImageVertically();
      return true;
    }
    if (item.getItemId() == android.R.id.home) {
      setResultCancel();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    setResultCancel();
  }

  @Override
  @SuppressLint("NewApi")
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    // handle result of pick image chooser
    if (requestCode == CropImages.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
      if (resultCode == Activity.RESULT_CANCELED) {
        // User cancelled the picker. We don't have anything to crop
        setResultCancel();
      }

      if (resultCode == Activity.RESULT_OK) {
        mCropImageUri = CropImages.getPickImageResultUri(this, data);

        // For API >= 23 we need to check specifically that we have permissions to read external
        // storage.
        if (CropImages.isReadExternalStoragePermissionsRequired(this, mCropImageUri)) {
          // request permissions and handle the result in onRequestPermissionsResult()
          requestPermissions(
              new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
              CropImages.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
        } else {
          // no permissions required or already grunted, can start crop image activity
          mCropImageViews.setImageUriAsync(mCropImageUri);
        }
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(
          int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
    if (requestCode == CropImages.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
      if (mCropImageUri != null
          && grantResults.length > 0
          && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        // required permissions granted, start crop image activity
        mCropImageViews.setImageUriAsync(mCropImageUri);
      } else {
        Toast.makeText(this, R.string.crop_image_activity_no_permissions, Toast.LENGTH_LONG).show();
        setResultCancel();
      }
    }

    if (requestCode == CropImages.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
      // Irrespective of whether camera permission was given or not, we show the picker
      // The picker will not add the camera intent if permission is not available
      CropImages.startPickImageActivity(this);
    }
  }

  @Override
  public void onSetImageUriComplete(CropImageViews view, Uri uri, Exception error) {
    if (error == null) {
      if (mOptions.initialCropWindowRectangle != null) {
        mCropImageViews.setCropRect(mOptions.initialCropWindowRectangle);
      }
      if (mOptions.initialRotation > -1) {
        mCropImageViews.setRotatedDegrees(mOptions.initialRotation);
      }
    } else {
      setResult(null, error, 1);
    }
  }

  @Override
  public void onCropImageComplete(CropImageViews view, CropImageViews.CropResult result) {
    setResult(result.getUri(), result.getError(), result.getSampleSize());
  }

  // region: Private methods

  /** Execute crop image and save the result tou output uri. */
  protected void cropImage() {
    if (mOptions.noOutputImage) {
      setResult(null, null, 1);
    } else {
      Uri outputUri = getOutputUri();
      mCropImageViews.saveCroppedImageAsync(
          outputUri,
          mOptions.outputCompressFormat,
          mOptions.outputCompressQuality,
          mOptions.outputRequestWidth,
          mOptions.outputRequestHeight,
          mOptions.outputRequestSizeOptions);
    }
  }

  /** Rotate the image in the crop image view. */
  protected void rotateImage(int degrees) {
    mCropImageViews.rotateImage(degrees);
  }

  /**
   * Get Android uri to save the cropped image into.<br>
   * Use the given in options or create a temp file.
   */
  protected Uri getOutputUri() {
    Uri outputUri = mOptions.outputUri;
    if (outputUri == null || outputUri.equals(Uri.EMPTY)) {
      try {
        String ext =
            mOptions.outputCompressFormat == Bitmap.CompressFormat.JPEG
                ? ".jpg"
                : mOptions.outputCompressFormat == Bitmap.CompressFormat.PNG ? ".png" : ".webp";
        outputUri = Uri.fromFile(File.createTempFile("cropped", ext, getCacheDir()));
      } catch (IOException e) {
        throw new RuntimeException("Failed to create temp file for output image", e);
      }
    }
    return outputUri;
  }

  /** Result with cropped image data or error if failed. */
  protected void setResult(Uri uri, Exception error, int sampleSize) {
    int resultCode = error == null ? RESULT_OK : CropImages.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;
    setResult(resultCode, getResultIntent(uri, error, sampleSize));
    finish();
  }

  /** Cancel of cropping activity. */
  protected void setResultCancel() {
    setResult(RESULT_CANCELED);
    finish();
  }

  /** Get intent instance to be used for the result of this activity. */
  protected Intent getResultIntent(Uri uri, Exception error, int sampleSize) {
    CropImages.ActivityResult result =
        new CropImages.ActivityResult(
            mCropImageViews.getImageUri(),
            uri,
            error,
            mCropImageViews.getCropPoints(),
            mCropImageViews.getCropRect(),
            mCropImageViews.getRotatedDegrees(),
            mCropImageViews.getWholeImageRect(),
            sampleSize);
    Intent intent = new Intent();
    intent.putExtras(getIntent());
    intent.putExtra(CropImages.CROP_IMAGE_EXTRA_RESULT, result);
    return intent;
  }

  /** Update the color of a specific menu item to the given color. */
  private void updateMenuItemIconColor(Menu menu, int itemId, int color) {
    MenuItem menuItem = menu.findItem(itemId);
    if (menuItem != null) {
      Drawable menuItemIcon = menuItem.getIcon();
      if (menuItemIcon != null) {
        try {
          menuItemIcon.mutate();
          menuItemIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
          menuItem.setIcon(menuItemIcon);
        } catch (Exception e) {
          Log.w("AIC", "Failed to update menu item color", e);
        }
      }
    }
  }
  // endregion
}
