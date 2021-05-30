package com.auro.scholr.home.presentation.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseActivity;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.FragmentUserProfileBinding;
import com.auro.scholr.home.data.model.GetStudentUpdateProfile;
import com.auro.scholr.home.data.model.StudentProfileModel;
import com.auro.scholr.home.presentation.viewmodel.StudentProfileViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.cropper.CropImageViews;
import com.auro.scholr.util.cropper.CropImages;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import static com.auro.scholr.core.common.Status.UPDATE_STUDENT;


public class UserProfileActivity extends BaseActivity implements View.OnFocusChangeListener, View.OnTouchListener, View.OnClickListener {

    @Inject
    @Named("UserProfileActivity")
    ViewModelFactory viewModelFactory;
    FragmentUserProfileBinding binding;
    StudentProfileViewModel viewModel;
    StudentProfileModel studentProfileModel = new StudentProfileModel();
    String TAG = com.auro.scholr.home.presentation.view.activity.UserProfileActivity.class.getSimpleName();

    public UserProfileActivity() {
        // Required empty public constructor
    }

    List<String> genderLines;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayout());
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StudentProfileViewModel.class);
        binding.setLifecycleOwner(this);

        init();
        setListener();

    }


    @Override
    protected void init() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        binding.etPhoneNumber.setText(prefModel.getUserMobile());
        addDropDownGender();
    }


    @Override
    protected void setListener() {
        binding.skipForNow.setOnClickListener(this);
        binding.nextButton.setOnClickListener(this);
        binding.profileImage.setOnClickListener(this);
        binding.editImage.setOnClickListener(this);
        binding.etGender.setOnFocusChangeListener(this);
        binding.etGender.setOnTouchListener(this);
        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_user_profile;
    }

    public void addDropDownGender() {
        genderLines = Arrays.asList(getResources().getStringArray(R.array.genderlist_profile));

        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, genderLines);
        //Getting the instance of AutoCompleteTextView
        binding.etGender.setThreshold(1);//will start working from first character
        binding.etGender.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        binding.etGender.setTextColor(Color.BLACK);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus)
            binding.etGender.showDropDown();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        binding.etGender.showDropDown();
        return false;
    }


    public void callingStudentUpdateProfile() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        String name = binding.etFullName.getText().toString();
        String gender = binding.etGender.getText().toString();
        AppLogger.e("callingStudentUpdateProfile- ", "i am calling");
        AppLogger.e("callingStudentUpdateProfile-", gender);

        studentProfileModel.setUserName(name);
        studentProfileModel.setGender(gender);
        studentProfileModel.setPhonenumber(prefModel.getUserMobile());
        if (TextUtil.isEmpty(name)) {
            showSnackbarError("Please enter the name");
            return;
        } else if (TextUtil.isEmpty(gender)) {
            showSnackbarError("Please enter the gender");
            return;
        } else {
            handleProgress(0, "");
            viewModel.sendStudentProfileInternet(studentProfileModel);
        }
    }

    private void observeServiceResponse() {
        viewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {
                case SUCCESS:
                    if (responseApi.apiTypeStatus == UPDATE_STUDENT) {
                        GetStudentUpdateProfile getStudentUpdateProfile = (GetStudentUpdateProfile) responseApi.data;
                        startDashboardActivity();
                        handleProgress(1, "");
                    }
                    break;

                case NO_INTERNET:
                case AUTH_FAIL:
                case FAIL_400:
                    handleProgress(2, (String) responseApi.data);
                    break;

                default:
                    handleProgress(2, (String) responseApi.data);
                    break;
            }

        });
    }

    private void handleProgress(int val, String message) {
        switch (val) {
            case 0:
                binding.progressbar.pgbar.setVisibility(View.VISIBLE);
                break;

            case 1:
                binding.progressbar.pgbar.setVisibility(View.GONE);
                break;
            case 2:
                binding.progressbar.pgbar.setVisibility(View.GONE);
                showSnackbarError(message);
                break;
        }
    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.editImage || id == R.id.profile_image) {
            CropImages.activity()
                    .setGuidelines(CropImageViews.Guidelines.ON)
                    .start(this);
        } else if (id == R.id.skip_for_now) {
            startDashboardActivity();
        } else if (id == R.id.nextButton) {
            callingStudentUpdateProfile();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLogger.e("StudentProfile", "fragment requestCode=" + requestCode);

        if (requestCode == CropImages.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImages.ActivityResult result = CropImages.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    AppLogger.e("StudentProfile", "image path=" + result.getUri().getPath());

                    Bitmap picBitmap = BitmapFactory.decodeFile(result.getUri().getPath());
                    byte[] bytes = AppUtil.encodeToBase64(picBitmap, 100);
                    long mb = AppUtil.bytesIntoHumanReadable(bytes.length);
                    int file_size = Integer.parseInt(String.valueOf(bytes.length / 1024));

                    AppLogger.e("StudentProfile", "image size=" + result.getUri().getPath());
                    if (file_size >= 500) {
                        studentProfileModel.setImageBytes(AppUtil.encodeToBase64(picBitmap, 50));
                    } else {
                        studentProfileModel.setImageBytes(bytes);
                    }
                    int new_file_size = Integer.parseInt(String.valueOf(studentProfileModel.getImageBytes().length / 1024));
                    AppLogger.d(TAG, "Image Path  new Size kb- " + mb + "-bytes-" + new_file_size);


                    loadimage(picBitmap);
                } catch (Exception e) {
                    AppLogger.e("StudentProfile", "fragment exception=" + e.getMessage());
                }

            } else if (resultCode == CropImages.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                AppLogger.e("StudentProfile", "fragment error=" + error.getMessage());
            }
        }
    }

    private void loadimage(Bitmap picBitmap) {
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(binding.profileimage.getContext().getResources(), picBitmap);
        circularBitmapDrawable.setCircular(true);
        binding.profileimage.setImageDrawable(circularBitmapDrawable);
        binding.editImage.setVisibility(View.VISIBLE);
    }

    private void startDashboardActivity() {
        Intent i = new Intent(this, StudentDashboardActivity.class);
        startActivity(i);
    }
}