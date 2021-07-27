package com.auro.scholr.home.presentation.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.ValidationModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.FragmentStudentProfile2Binding;
import com.auro.scholr.home.data.model.AuroScholarInputModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.GetStudentUpdateProfile;
import com.auro.scholr.home.data.model.StudentProfileModel;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.activity.StudentDashboardActivity;
import com.auro.scholr.home.presentation.view.activity.newDashboard.StudentMainDashboardActivity;
import com.auro.scholr.home.presentation.viewmodel.StudentProfileViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ImageUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.alert_dialog.CustomProgressDialog;
import com.auro.scholr.util.cropper.CropImageViews;
import com.auro.scholr.util.cropper.CropImages;
import com.auro.scholr.util.permission.LocationHandler;
import com.auro.scholr.util.permission.LocationModel;
import com.auro.scholr.util.permission.LocationUtil;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;
import static com.auro.scholr.core.common.Status.UPDATE_STUDENT;

public class StudentProfileFragment extends BaseFragment implements View.OnClickListener, TextWatcher, View.OnTouchListener,/* View.OnFocusChangeListener,*/ CommonCallBackListner/*, GradeChangeFragment.OnClickButton*/ {


    @Inject
    @Named("StudentProfileFragment")
    ViewModelFactory viewModelFactory;
    FragmentStudentProfile2Binding binding;
    StudentProfileViewModel viewModel;
    List<String> genderLines;
    List<String> schooltypeLines;
    List<String> boardLines;
    List<String> languageLines;
    List<String> privateTutionList;
    List<String> privateTutionTypeList;
    DashboardResModel dashboardResModel;
    private String comingFrom;
    StudentProfileModel studentProfileModel;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    GetStudentUpdateProfile getStudentUpdateProfile;
    CommonCallBackListner commonCallBackListner;
    boolean isUserEditFieldOpen = true;
    LocationHandler locationHandlerUpdate;
    CustomProgressDialog customProgressDialog;
    boolean firstTimeCome;
    String TAG = "StudentProfileFragment";
    PrefModel prefModel;
    AuroScholarInputModel inputModel;

    public StudentProfileFragment() {
        // Required empty public constructor
    }


    public static StudentProfileFragment newInstance(String param1, String param2) {
        StudentProfileFragment fragment = new StudentProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StudentProfileViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);

        init();
        setListener();
        setToolbar();
        // setscreenTouch();

        return binding.getRoot();

    }

    @Override
    protected void init() {
        binding.headerTopParent.cambridgeHeading.setVisibility(View.GONE);
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        firstTimeCome = true;
        //HomeActivity.setListingActiveFragment(HomeActivity.STUDENT_PROFILE);


        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
            //comingFrom = getArguments().getString(AppConstant.COMING_FROM);
            AppLogger.v("studentprofile", dashboardResModel + "");

        }

        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }
        callingStudentUpdateProfile();
        //AppUtil.commonCallBackListner = this;

        setSpinner();
        ((StudentMainDashboardActivity) getActivity()).loadPartnerLogo(binding.auroScholarLogo);


    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        ((StudentMainDashboardActivity)getActivity()).setListingActiveFragment(StudentMainDashboardActivity.PROFILE_FRAGMENT);

        binding.profileImage.setOnClickListener(this);
        binding.editImage.setOnClickListener(this);
        binding.editemail.addTextChangedListener(this);
        binding.submitbutton.setOnClickListener(this);
        binding.UserName.setOnClickListener(this);
        binding.cancelUserNameIcon.setOnClickListener(this);
        binding.backButton.setOnClickListener(this);

        binding.gradeChnage.setOnClickListener(this);
        binding.walletBalText.setOnClickListener(this);
        binding.editemail.setOnClickListener(this);
        binding.inputemailedittext.setOnClickListener(this);
        binding.tilteachertxt.setOnClickListener(this);

        binding.SpinnerGender.setOnTouchListener(this);
        binding.SpinnerSchoolType.setOnTouchListener(this);
        binding.SpinnerBoard.setOnTouchListener(this);
        binding.SpinnerLanguageMedium.setOnTouchListener(this);
        binding.spinnerPrivateTution.setOnTouchListener(this);
        binding.spinnerPrivateType.setOnTouchListener(this);
        binding.gradeLayout.setOnTouchListener(this);
        binding.linearLayout8.setOnTouchListener(this);
        binding.editUserNameIcon.setOnClickListener(this);
        binding.editPhonenew.setOnTouchListener(this);
        binding.editemail.setOnTouchListener(this);


        binding.SpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                studentProfileModel.setGender(binding.SpinnerGender.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                changeTheEditText();
            }
        });

        binding.SpinnerSchoolType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                studentProfileModel.setSchool_type(binding.SpinnerSchoolType.getSelectedItem().toString());
                changeTheEditText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                changeTheEditText();
            }
        });

        binding.SpinnerLanguageMedium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                studentProfileModel.setLanguage(binding.SpinnerLanguageMedium.getSelectedItem().toString());
                changeTheEditText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                changeTheEditText();
            }
        });

        binding.SpinnerBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                studentProfileModel.setBoard_type(binding.SpinnerBoard.getSelectedItem().toString());
                changeTheEditText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                changeTheEditText();
            }
        });

        binding.spinnerPrivateTution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String value = binding.spinnerPrivateTution.getSelectedItem().toString();
                studentProfileModel.setIsPrivateTution(binding.spinnerPrivateTution.getSelectedItem().toString());
                changeTheEditText();
                if (value.equalsIgnoreCase(AppConstant.DocumentType.YES)) {
                    binding.spinnerPrivateType.setVisibility(View.VISIBLE);
                    binding.tvPrivateType.setVisibility(View.VISIBLE);
                    binding.privateTypeArrow.setVisibility(View.VISIBLE);


                } else {
                    binding.spinnerPrivateType.setVisibility(View.GONE);
                    binding.tvPrivateType.setVisibility(View.GONE);
                    binding.privateTypeArrow.setVisibility(View.GONE);
                    studentProfileModel.setPrivateTutionType("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                changeTheEditText();
            }
        });

        binding.spinnerPrivateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String value = binding.spinnerPrivateType.getSelectedItem().toString();
                if (value.equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_PRIVATE_TUTION)) {
                    studentProfileModel.setPrivateTutionType("");
                    changeTheEditText();

                } else {
                    studentProfileModel.setPrivateTutionType(binding.spinnerPrivateType.getSelectedItem().toString());
                    changeTheEditText();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                changeTheEditText();
            }
        });


    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_student_profile_2;
    }

    @Override
    public void onResume() {
        super.onResume();
      //  init();
       // setListener();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.back_arrow) {
            getActivity().onBackPressed();
        } else if (id == R.id.editImage) {//  selectImage(getContext());
            askPermission();
        } else if (id == R.id.submitbutton) {
            changeTheEditText();
            sendProfileScreenApi();
        } else if (id == R.id.editUserNameIcon) {
            AppLogger.v("TextEdit", "   UserName");
            binding.UserName.setVisibility(View.GONE);
            binding.editUserNameIcon.setVisibility(View.GONE);
            binding.editProfileName.setVisibility(View.VISIBLE);
            binding.cancelUserNameIcon.setImageResource(R.drawable.ic_cancel_icon);
            binding.editProfile.setText(binding.UserName.getText().toString());
            binding.editProfile.requestFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(binding.editProfile, InputMethodManager.SHOW_IMPLICIT);
            binding.cancelUserNameIcon.setVisibility(View.VISIBLE);
        } else if (id == R.id.cancelUserNameIcon) {
            AppLogger.v("TextEdit", "   cancelUserNameIcon");
            if (!TextUtil.isEmpty(binding.editProfile.getText().toString())) {
                binding.UserName.setVisibility(View.VISIBLE);
                binding.editUserNameIcon.setVisibility(View.VISIBLE);
                binding.editUserNameIcon.setImageResource(R.drawable.ic_edit_profile);
                binding.editProfileName.setVisibility(View.GONE);
                binding.cancelUserNameIcon.setVisibility(View.GONE);
                binding.UserName.setText(binding.editProfile.getText().toString());
            } else {
                binding.editProfileName.setError("Enter Your Name");
            }
        } else if (id == R.id.gradeChnage) {
            if (!TextUtil.isEmpty(binding.editProfile.getText().toString())) {
                changeTheEditText();
                //openGradeChangeFragment(AppConstant.SENDING_DATA.STUDENT_PROFILE);
            } else {
                binding.editProfileName.setError("Enter Your Name");
            }
        } else if (id == R.id.wallet_bal_text) {
            if (!TextUtil.isEmpty(binding.editProfile.getText().toString())) {
                changeTheEditText();
                if (viewModel.homeUseCase.checkKycStatus(dashboardResModel)) {
                    openKYCViewFragment(dashboardResModel);
                } else {
                    openKYCFragment(dashboardResModel);
                }
            } else {
                binding.editProfileName.setError("Enter Your Name");
            }
        } else if (id == R.id.linearLayout8) {
            if (!TextUtil.isEmpty(binding.editProfile.getText().toString())) {
                changeTheEditText();
            } else {
                binding.editProfileName.setError("Enter Your Name");
            }
        } else if (id == R.id.editPhone) {
            if (!TextUtil.isEmpty(binding.editProfile.getText().toString())) {
                changeTheEditText();
            } else {
                binding.editProfileName.setError("Enter Your Name");
            }
        } else if (id == R.id.editemail) {
            if (!TextUtil.isEmpty(binding.editProfile.getText().toString())) {
                changeTheEditText();
            } else {
                binding.editProfileName.setError("Enter Your Name");
            }
        } else if (id == R.id.tilteachertxt) {
            if (!TextUtil.isEmpty(binding.editProfile.getText().toString())) {
                changeTheEditText();
            } else {
                binding.editProfileName.setError("Enter Your Name");
            }
        } else if (id == R.id.inputemailedittext) {
            if (!TextUtil.isEmpty(binding.editProfile.getText().toString())) {
                changeTheEditText();
            } else {
                binding.editProfileName.setError("Enter Your Name");
            }
        } else if(id == R.id.backButton)
        {
            getActivity().onBackPressed();
        }

    }

  /*  public void openGradeChangeFragment(String source) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.COMING_FROM, source);
        GradeChangeFragment gradeChangeFragment = new GradeChangeFragment(this);
        gradeChangeFragment.setArguments(bundle);
        openFragment(gradeChangeFragment);
    }*/


    public void openActivity() {
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, AppConstant.CAMERA_REQUEST_CODE);
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
    }

    public void spinnermethodcall(List<String> languageLines, AppCompatSpinner spinner) {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_list, languageLines);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }


    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    openActivity();

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    break;
                case SUCCESS:
                    if (responseApi.apiTypeStatus == UPDATE_STUDENT) {
                        studentProfileModel = new StudentProfileModel();
                        AppLogger.v("callApi", firstTimeCome + "");
                        if (firstTimeCome) {
                            AppLogger.v("callApi", firstTimeCome + "   main");
                            handleProgress(1, "");
                            getStudentUpdateProfile = (GetStudentUpdateProfile) responseApi.data;
                            setDataonUi();
                            AppLogger.v("apiResponse", getStudentUpdateProfile + "");
                        } else {
                            AppLogger.v("callApi", firstTimeCome + "  second");
                            handleSubmitProgress(1, "");
                            getStudentUpdateProfile = (GetStudentUpdateProfile) responseApi.data;
                            showSnackbarError("Successfully  Save", Color.GREEN);
                            setDataonUi();
                            AppLogger.v("apiResponse", getStudentUpdateProfile + "");
                        }
                    }

                    break;

                case NO_INTERNET:
//On fail
                    handleProgress(2, (String) responseApi.data);
                    break;

                case AUTH_FAIL:
                case FAIL_400:
                    handleProgress(2, (String) responseApi.data);
                    break;
                default:
                    // handleProgress(1, (String) responseApi.data);
                    AppLogger.v("apiData", responseApi.data + "   pradeep");
                    handleProgress(2, (String) responseApi.data);
                    showSnackbarError(getString(R.string.default_error));
                    break;
            }

        });
    }

    private void handleProgress(int status, String message) {
        switch (status) {
            case 0:
                binding.mainParentLayout.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.GONE);
                binding.shimmerMyClassroom.setVisibility(View.VISIBLE);
                binding.shimmerMyClassroom.startShimmer();
                break;

            case 1:
                binding.mainParentLayout.setVisibility(View.VISIBLE);
                binding.errorConstraint.setVisibility(View.GONE);
                binding.shimmerMyClassroom.setVisibility(View.GONE);
                binding.shimmerMyClassroom.stopShimmer();
                break;

            case 2:
                binding.errorConstraint.setVisibility(View.VISIBLE);
                binding.mainParentLayout.setVisibility(View.GONE);
                binding.shimmerMyClassroom.setVisibility(View.GONE);
                binding.shimmerMyClassroom.stopShimmer();
                binding.errorLayout.textError.setText(message);
                binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callingStudentUpdateProfile();
                    }
                });
                break;
        }

    }

    private void handleSubmitProgress(int value, String message) {
        if (value == 0) {

            binding.submitbutton.setText("");
            binding.submitbutton.setEnabled(false);
            binding.progressBar.setVisibility(View.VISIBLE);

        } else if (value == 1) {

            binding.submitbutton.setText(getActivity().getString(R.string.save));
            binding.submitbutton.setEnabled(true);
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    private void showSnackbarError(String message, int color) {
        ViewUtil.showSnackBar(binding.getRoot(), message, color);
    }

    public void setDataonUi() {
        if (getStudentUpdateProfile != null) {
            binding.classStudent.setText(getStudentUpdateProfile.getGrade() + "");
            binding.walletBalText.setText(getStudentUpdateProfile.getScholarshipAmount() + "");
            binding.editPhonenew.setText(getStudentUpdateProfile.getMobileNo());
            if (TextUtil.isEmpty(getStudentUpdateProfile.getUsername())) {
                binding.UserName.setText("Guest");
                binding.editProfile.setText("Guest");
            } else {
                binding.UserName.setText(getStudentUpdateProfile.getUsername());
                binding.editProfile.setText(getStudentUpdateProfile.getUsername());
            }


            if (!TextUtil.isEmpty(getStudentUpdateProfile.getMobileNo())) {
                binding.icteachername.setVisibility(View.VISIBLE);
            } else {
                binding.icteachername.setVisibility(View.GONE);
            }
            binding.editemail.setText(getStudentUpdateProfile.getEmailId());
            //getStudentUpdateProfile.setUserProfileImage("https://www.edupreneurvillage.com/wp-content/plugins/sticky-buttons/public/partials/wap.png");
            AppLogger.e(TAG, "profile image path-" + getStudentUpdateProfile.getUserProfileImage());

            if (!TextUtil.isEmpty(getStudentUpdateProfile.getUserProfileImage())) {
                ImageUtil.loadCircleImage(binding.profileimage, getStudentUpdateProfile.getUserProfileImage());
            }

            setSpinner();
        }


    }

    public void setSpinner() {
        // Spinner Drop down Gender
        genderLines = Arrays.asList(getActivity().getResources().getStringArray(R.array.genderlist));
        spinnermethodcall(genderLines, binding.SpinnerGender);
        for (int i = 0; i < genderLines.size(); i++) {
            String gender = genderLines.get(i);
            if (getStudentUpdateProfile != null && !TextUtil.isEmpty(getStudentUpdateProfile.getGender()) && gender.equalsIgnoreCase(getStudentUpdateProfile.getGender())) {
                binding.SpinnerGender.setSelection(i);
                break;
            }

        }

        // Spinner Drop down schooltype
        schooltypeLines = Arrays.asList(getActivity().getResources().getStringArray(R.array.schooltypelist));
        spinnermethodcall(schooltypeLines, binding.SpinnerSchoolType);
        for (int i = 0; i < schooltypeLines.size(); i++) {
            String school = schooltypeLines.get(i);
            if (getStudentUpdateProfile != null && !TextUtil.isEmpty(getStudentUpdateProfile.getSchoolType()) && school.equalsIgnoreCase(getStudentUpdateProfile.getSchoolType())) {
                binding.SpinnerSchoolType.setSelection(i);
                break;
            }
        }

        // Spinner Drop down boardlist
        boardLines = Arrays.asList(getResources().getStringArray(R.array.boardlist));
        spinnermethodcall(boardLines, binding.SpinnerBoard);
        for (int i = 0; i < boardLines.size(); i++) {
            String board = boardLines.get(i);
            if (getStudentUpdateProfile != null && !TextUtil.isEmpty(getStudentUpdateProfile.getBoardType()) && board.equalsIgnoreCase(getStudentUpdateProfile.getBoardType())) {
                binding.SpinnerBoard.setSelection(i);
                break;
            }
        }

        // Spinner Drop down language
        languageLines = Arrays.asList(getResources().getStringArray(R.array.languagelist));
        spinnermethodcall(languageLines, binding.SpinnerLanguageMedium);
        for (int i = 0; i < languageLines.size(); i++) {
            String lang = languageLines.get(i);
            if (getStudentUpdateProfile != null && !TextUtil.isEmpty(getStudentUpdateProfile.getLanguage()) && lang.equalsIgnoreCase(getStudentUpdateProfile.getLanguage())) {
                binding.SpinnerLanguageMedium.setSelection(i);
                break;
            }
        }

        // Spinner Drop down tution
        privateTutionList = Arrays.asList(getResources().getStringArray(R.array.privateTutionList));
        spinnermethodcall(privateTutionList, binding.spinnerPrivateTution);
        for (int i = 0; i < privateTutionList.size(); i++) {
            String s = privateTutionList.get(i);
            if (getStudentUpdateProfile != null && !TextUtil.isEmpty(getStudentUpdateProfile.getIsPrivateTution()) && s.equalsIgnoreCase(getStudentUpdateProfile.getIsPrivateTution())) {
                binding.spinnerPrivateTution.setSelection(i);
                break;
            }
        }


        // Spinner Drop down tution  Type
        privateTutionTypeList = Arrays.asList(getResources().getStringArray(R.array.privateTypeList));
        spinnermethodcall(privateTutionTypeList, binding.spinnerPrivateType);
        for (int i = 0; i < privateTutionTypeList.size(); i++) {
            String lang = privateTutionTypeList.get(i);
            if (getStudentUpdateProfile != null && !TextUtil.isEmpty(getStudentUpdateProfile.getPrivateTutionType()) && lang.equalsIgnoreCase(getStudentUpdateProfile.getPrivateTutionType())) {
                binding.spinnerPrivateType.setSelection(i);
                break;
            }
        }
    }

    public void sendProfileScreenApi() {
        ((StudentMainDashboardActivity) getActivity()).setDashboardApiCallingInPref(true);
        firstTimeCome = false;
        AppLogger.v("callApi", firstTimeCome + "");
        String username = binding.UserName.getText().toString();
        String emailId = binding.editemail.getText().toString();
        studentProfileModel.setUserName(username);
        studentProfileModel.setEmailID(emailId);
        studentProfileModel.setPhonenumber(dashboardResModel.getPhonenumber());
        ValidationModel validation = viewModel.homeUseCase.validateStudentProfile(studentProfileModel);
        if (validation.isStatus()) {
            viewModel.sendStudentProfileInternet(studentProfileModel);
            handleSubmitProgress(0, "");
        } else {
            showSnackbarError(validation.getMessage());
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (binding.editemail.getText().toString().length() > 0 && TextUtil.isValidEmail(binding.editemail.getText().toString())) {

            AppLogger.v("afterTextChanged", "valid email address");
            binding.icemail.setVisibility(View.VISIBLE);

        } else {
            binding.icemail.setVisibility(View.GONE);
            AppLogger.v("afterTextChanged", "Invalid email address");
        }
    }

    public void callingStudentUpdateProfile() {
        if (dashboardResModel != null) {
            handleProgress(0, "");
            StudentProfileModel studentProfileModel = new StudentProfileModel();
            studentProfileModel.setPhonenumber(dashboardResModel.getPhonenumber());
            viewModel.sendStudentProfileInternet(studentProfileModel);
        }

    }



    private void changeTheEditText() {
        AppLogger.v(TAG, "focus lost");
        binding.UserName.setVisibility(View.VISIBLE);
        binding.editUserNameIcon.setVisibility(View.VISIBLE);
        binding.editUserNameIcon.setImageResource(R.drawable.ic_edit_profile);
        binding.editProfileName.setVisibility(View.GONE);
        binding.cancelUserNameIcon.setVisibility(View.GONE);
        if (!TextUtil.isEmpty(binding.editProfile.getText().toString())) {
            binding.UserName.setText(binding.editProfile.getText().toString());
        }
    }

    private void askPermission() {
        String rationale ="For Upload Profile Picture. Camera and Storage Permission is Must.";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mCameraPermissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                CropImages.activity()
                        .setGuidelines(CropImageViews.Guidelines.ON)
                        .start(getActivity());
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                ViewUtil.showSnackBar(binding.getRoot(), rationale);
            }
        });
    }

    private void callServiceWhenLocationReceived() {
        LocationModel locationModel = LocationUtil.getLocationData();
        openProgressDialog();
        if (locationModel != null && locationModel.getLatitude() != null && !locationModel.getLatitude().isEmpty()) {
            AppLogger.e("StudentProfile", "Location Found");
            if (customProgressDialog != null) {
                customProgressDialog.dismiss();
            }
            studentProfileModel.setLatitude(locationModel.getLatitude());
            studentProfileModel.setLongitude(locationModel.getLongitude());
        } else {
            Handler handler = new Handler();
            handler.postDelayed(this::callServiceWhenLocationReceived, 2000);
        }
    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case SCREEN_TOUCH:

                int value = binding.editProfileName.getVisibility();
                if (value == 0) {
                    binding.editProfileName.setFocusableInTouchMode(true);
                    binding.editProfileName.setFocusable(true);

                    AppLogger.v("SCREEN_TOUCH", binding.editProfileName.getVisibility() + "");
                } else {
                    AppLogger.v("SCREEN_TOUCH", binding.editProfileName.getVisibility() + "");
                }

                break;
        }
    }

    private void openProgressDialog() {
        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            return;
        }
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        customDialogModel.setTitle("Fetching Your Location...");
        customDialogModel.setTwoButtonRequired(true);
        customProgressDialog = new CustomProgressDialog(customDialogModel);
        Objects.requireNonNull(customProgressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customProgressDialog.setCancelable(false);
        customProgressDialog.show();
        customProgressDialog.updateDataUi(0);
    }


    public void openKYCFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        KYCFragment kycFragment = new KYCFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycFragment.setArguments(bundle);
        openFragment(kycFragment);
    }

    public void openKYCViewFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        KYCViewFragment kycViewFragment = new KYCViewFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycViewFragment.setArguments(bundle);
        openFragment(kycViewFragment);
    }

    private void openFragment(Fragment fragment) {
        ((AppCompatActivity) (this.getContext())).getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, QuizHomeFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

   /* @Override
    public void onClickListener() {
        ((HomeActivity) getActivity()).auroStudentscholarSdk(0);
    }*/

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int id = view.getId();
        if (id == R.id.SpinnerGender) {
            changeTheEditText();
            if (!binding.editProfile.getText().toString().equalsIgnoreCase("")) {
                binding.UserName.setText(binding.editProfile.getText().toString());
            }
        } else if (id == R.id.SpinnerSchoolType) {
            changeTheEditText();
            if (!binding.editProfile.getText().toString().equalsIgnoreCase("")) {
                binding.UserName.setText(binding.editProfile.getText().toString());
            }
        } else if (id == R.id.SpinnerBoard) {
            changeTheEditText();
            if (!binding.editProfile.getText().toString().equalsIgnoreCase("")) {
                binding.UserName.setText(binding.editProfile.getText().toString());
            }
        } else if (id == R.id.SpinnerLanguageMedium) {
            changeTheEditText();
            if (!binding.editProfile.getText().toString().equalsIgnoreCase("")) {
                binding.UserName.setText(binding.editProfile.getText().toString());
            }
        } else if (id == R.id.spinnerPrivateTution) {
            changeTheEditText();
            if (!binding.editProfile.getText().toString().equalsIgnoreCase("")) {
                binding.UserName.setText(binding.editProfile.getText().toString());
            }
        } else if (id == R.id.spinnerPrivateType) {
            changeTheEditText();
            if (!binding.editProfile.getText().toString().equalsIgnoreCase("")) {
                binding.UserName.setText(binding.editProfile.getText().toString());
            }
        } else if (id == R.id.grade_layout) {
            changeTheEditText();
            if (!binding.editProfile.getText().toString().equalsIgnoreCase("")) {
                binding.UserName.setText(binding.editProfile.getText().toString());
            }
        } else if (id == R.id.linearLayout8) {
            changeTheEditText();
            if (!binding.editProfile.getText().toString().equalsIgnoreCase("")) {
                binding.UserName.setText(binding.editProfile.getText().toString());
            }
        } else if (id == R.id.editPhone) {
            changeTheEditText();
            if (!binding.editProfile.getText().toString().equalsIgnoreCase("")) {
                binding.UserName.setText(binding.editProfile.getText().toString());
            }
        } else if (id == R.id.editemail) {
            changeTheEditText();
            if (!binding.editProfile.getText().toString().equalsIgnoreCase("")) {
                binding.UserName.setText(binding.editProfile.getText().toString());
            }
        }

        return false;
    }
}