package com.auro.scholr.teacher.presentation.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.FragmentUtil;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.common.ValidationModel;
import com.auro.scholr.databinding.FragmentTeacherProfileBinding;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;
import com.auro.scholr.teacher.data.model.request.SelectClassesSubject;
import com.auro.scholr.teacher.data.model.request.TeacherReqModel;
import com.auro.scholr.teacher.data.model.response.MyProfileResModel;
import com.auro.scholr.teacher.data.model.response.TeacherResModel;
import com.auro.scholr.teacher.presentation.view.adapter.DistrictSpinnerAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.ProfileScreenAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.StateSpinnerAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherProfileViewModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;


public class TeacherProfileFragment extends BaseFragment implements TextWatcher, CommonCallBackListner {

    @Inject
    @Named("TeacherProfileFragment")
    ViewModelFactory viewModelFactory;
    TeacherProfileViewModel viewModel;
    TeacherReqModel teacherReqModel = new TeacherReqModel();

    FragmentTeacherProfileBinding binding;
    boolean isStateRestore;
    List<StateDataModel> stateDataModelList;
    List<DistrictDataModel> districtDataModels;
    List<String> classesList;
    List<String> subjectlist;
    HashMap<String, String> subjectHashmap = new HashMap<>();
    HashMap<String, String> classHashmap = new HashMap<>();
    String subject = "English, Maths, Social Science, Science,";
    String classes = "4th, 8th, 10th, 3rd, 9th, 7th, 2nd,";

    String distict = "52";
    String state =  "5";



    public TeacherProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding != null) {
            isStateRestore = true;
            return binding.getRoot();
        }
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TeacherProfileViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setTeacherProfileViewModel(viewModel);
        setRetainInstance(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.setListingActiveFragment(HomeActivity.TEACHER_PROFILE_FRAGMENT);
        init();
        setListener();
        setRecycleView();
        viewModel.getTeacherProfileData(AuroApp.getAuroScholarModel().getMobileNumber());
    }

    @Override
    protected void init() {
        binding.headerTopParent.cambridgeHeading.setVisibility(View.GONE);
        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }
        viewModel.getStateListData();
        viewModel.getDistrictListData();
        setRecycleView();
        setDataOnUI();

    }

    private void setDataOnUI() {
        if (AppUtil.myClassRoomResModel != null && AppUtil.myClassRoomResModel.getTeacherResModel() != null) {
            if (!TextUtil.isEmpty(String.valueOf(AppUtil.myClassRoomResModel.getTeacherResModel().getScoreTotal()))) {
                binding.points.setText("" + AppUtil.myClassRoomResModel.getTeacherResModel().getScoreTotal());
            } else {
                binding.points.setText("0");
            }

            if (!TextUtil.isEmpty(String.valueOf(AppUtil.myClassRoomResModel.getTeacherResModel().getWalletBalance()))) {
                binding.walletBal.setText(" " + AuroApp.getAppContext().getResources().getString(R.string.rs) + AppUtil.myClassRoomResModel.getTeacherResModel().getWalletBalance());
            } else {
                binding.walletBal.setText("0");
            }
        }
status


        if (!TextUtil.isEmpty(AuroApp.getAuroScholarModel().getMobileNumber())) {
            binding.editPhoneNumber.setText(AuroApp.getAuroScholarModel().getMobileNumber());
            binding.icmobilenumber.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.editteachername.addTextChangedListener(this);
        binding.editemail.addTextChangedListener(this);
        binding.editPhoneNumber.addTextChangedListener(this);
        binding.editSchoolName.addTextChangedListener(this);
        binding.txtGetNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new TeacherKycFragment());
                ((HomeActivity) getActivity()).selectNavigationMenu(2);
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSaveTeacherProfileApi();
            }
        });
    }


    public void openFragment(Fragment fragment) {
        FragmentUtil.replaceFragment(getActivity(), fragment, R.id.home_container, false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_teacher_profile;
    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {

                case LOADING:
                    if (responseApi.apiTypeStatus == Status.UPDATE_TEACHER_PROFILE_API) {
                        handleProgress(0);
                    }

                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == Status.UPDATE_TEACHER_PROFILE_API) {
                        handleProgress(1);
                    } else if (responseApi.apiTypeStatus == Status.GET_PROFILE_TEACHER_API) {
                        MyProfileResModel teacherResModel = (MyProfileResModel) responseApi.data;
                    }
                    break;

                case FAIL:
                case NO_INTERNET:
                    if (responseApi.apiTypeStatus == Status.UPDATE_TEACHER_PROFILE_API) {
                        handleProgress(1);
                        showSnackbarError((String) responseApi.data);
                    }
                    break;


                /*For state list*/
                case STATE_LIST_ARRAY:
                    stateDataModelList = (List<StateDataModel>) responseApi.data;
                    stateSpinner();
                    break;

                case DISTRICT_LIST_DATA:
                    districtDataModels = (List<DistrictDataModel>) responseApi.data;
                    districtSpinner();
                    break;

                default:
                        handleProgress(1);
                        showSnackbarError(getString(R.string.default_error));


                    break;
            }

        });
    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    private void stateSpinner() {
        if (!TextUtil.checkListIsEmpty(stateDataModelList)) {
            StateSpinnerAdapter stateSpinnerAdapter = new StateSpinnerAdapter(stateDataModelList);
            binding.stateSpinner.setAdapter(stateSpinnerAdapter);
            for(int i = 0; i<stateDataModelList.size();i++){
                if(state.equalsIgnoreCase(stateDataModelList.get(i).getState_code())){
                    binding.stateSpinner.setSelection(i);
                }
            }
            binding.stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position != 0) {
                        teacherReqModel.setState_id(stateDataModelList.get(position).getState_code());
                        viewModel.getStateDistrictData(stateDataModelList.get(position).getState_code());
                        binding.cityTitle.setVisibility(View.VISIBLE);
                        binding.cityView.setVisibility(View.VISIBLE);
                        binding.citySpinner.setVisibility(View.VISIBLE);

                    } else if (stateDataModelList.get(position).getState_name().trim().equalsIgnoreCase("pleaseselectstate")) {
                        binding.cityTitle.setVisibility(View.GONE);
                        binding.cityView.setVisibility(View.GONE);
                        binding.citySpinner.setVisibility(View.GONE);
                    } else {
                        binding.cityTitle.setVisibility(View.GONE);
                        binding.cityView.setVisibility(View.GONE);
                        binding.citySpinner.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


    }

    private void districtSpinner() {
        if (!TextUtil.checkListIsEmpty(districtDataModels)) {
            binding.cityTitle.setVisibility(View.VISIBLE);
            binding.citySpinner.setVisibility(View.VISIBLE);
            DistrictSpinnerAdapter stateSpinnerAdapter = new DistrictSpinnerAdapter(districtDataModels);
            binding.citySpinner.setAdapter(stateSpinnerAdapter);
            binding.citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    teacherReqModel.setDistrict_id(districtDataModels.get(position).getDistrict_code());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            binding.cityTitle.setVisibility(View.GONE);
            binding.citySpinner.setVisibility(View.GONE);
        }
    }

    public void setRecycleView() {
        //for class recycleview
        classesList = Arrays.asList(getResources().getStringArray(R.array.classes));
        GridLayoutManager gridlayout = new GridLayoutManager(getActivity(), 2);
        gridlayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recycleViewclass.setLayoutManager(gridlayout);
        binding.recycleViewclass.setHasFixedSize(true);
        binding.recycleViewclass.setNestedScrollingEnabled(false);
        ProfileScreenAdapter mProfileScreenAdapterAdapter = new ProfileScreenAdapter(viewModel.teacherUseCase.selectClass(classes), getContext(), this);
        binding.recycleViewclass.setAdapter(mProfileScreenAdapterAdapter);

        //for subject recycle view
        subjectlist = Arrays.asList(getResources().getStringArray(R.array.subject));
        GridLayoutManager gridlayout2 = new GridLayoutManager(getActivity(), 2);
        gridlayout2.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recycleViewsubject.setLayoutManager(gridlayout2);
        binding.recycleViewsubject.setHasFixedSize(true);
        binding.recycleViewsubject.setNestedScrollingEnabled(false);
        ProfileScreenAdapter mProfileScreenAdapterAdapter1 = new ProfileScreenAdapter(viewModel.teacherUseCase.selectSubject(subject), getContext(), this);
        binding.recycleViewsubject.setAdapter(mProfileScreenAdapterAdapter1);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    @Override
    public void afterTextChanged(Editable editable) {

        //teacher name validation
        if (editable == binding.editteachername.getEditableText()) {
            if (editable.length() >= 5) {
                binding.icteachername.setVisibility(View.VISIBLE);
            } else {
                binding.icteachername.setVisibility(View.GONE);
            }
        }
        //email validation
        if (editable == binding.editemail.getEditableText()) {
            if (emailValidation(binding.editemail)) {
                binding.icemail.setVisibility(View.VISIBLE);
            } else {
                binding.icemail.setVisibility(View.GONE);
            }
        }
        //
        if (editable == binding.editPhoneNumber.getEditableText()) {
            if (editable.length() == 10) {
                binding.icmobilenumber.setVisibility(View.VISIBLE);
            } else {
                binding.icmobilenumber.setVisibility(View.GONE);
            }
        }
        if (editable == binding.editSchoolName.getEditableText()) {
            if (editable.length() >= 5) {
                binding.icschoolname.setVisibility(View.VISIBLE);
            } else {
                binding.icschoolname.setVisibility(View.GONE);
            }
        }
    }

    public boolean emailValidation(EditText emailtext) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailtext.getText().toString().isEmpty()) {
            binding.inputemailedittext.setError(null);
            return false;
        } else {
            if (emailtext.getText().toString().trim().matches(emailPattern)) {
                binding.inputemailedittext.setError(null);
                return true;
            } else {
                binding.inputemailedittext.setError("Invalid email address");
                return false;

            }
        }
    }

    private void callSaveTeacherProfileApi() {
        if (subjectHashmap.size() > 0) {
            StringBuilder subjectBuilder = new StringBuilder();
            for (String key : subjectHashmap.keySet()) {
                subjectBuilder.append(subjectHashmap.get(key) + ", ");
            }
            teacherReqModel.setTeacher_subject(subjectBuilder.toString());
        }

        if (classHashmap.size() > 0) {
            StringBuilder classBuilder = new StringBuilder();
            for (String key : classHashmap.keySet()) {
                classBuilder.append(classHashmap.get(key) + ", ");
            }
            teacherReqModel.setTeacher_class(classBuilder.toString());
        }
        teacherReqModel.setMobile_no(AuroApp.getAuroScholarModel().getMobileNumber());
        teacherReqModel.setTeacher_name(binding.editteachername.getText().toString());
        teacherReqModel.setTeacher_email(binding.editemail.getText().toString());
        teacherReqModel.setSchool_name(binding.editSchoolName.getText().toString());
        ValidationModel validationModel = viewModel.teacherUseCase.checkTeacherProfileValidation(teacherReqModel);
        if (validationModel.isStatus()) {
            viewModel.updateTeacherProfileData(teacherReqModel);
        } else {
            showSnackbarError(validationModel.getMessage());
        }
    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case SUBJECT_CLICK:
                SelectClassesSubject subject = (SelectClassesSubject) commonDataModel.getObject();
                subjectHashmap.put(subject.getText(), subject.getText());
                break;

            case CLASS_CLICK:
                SelectClassesSubject class_click = (SelectClassesSubject) commonDataModel.getObject();
                classHashmap.put(class_click.getText(), class_click.getText());
                break;
        }
    }

    public void handleProgress(int status) {
        switch (status) {
            case 0:
                binding.button.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                break;
            case 1:
                binding.button.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                break;
        }
    }
}
