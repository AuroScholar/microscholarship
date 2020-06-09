package com.auro.scholr.teacher.presentation.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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
import android.widget.Toast;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.FragmentTeacherProfileBinding;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;
import com.auro.scholr.teacher.data.model.request.TeacherReqModel;
import com.auro.scholr.teacher.presentation.view.adapter.DistrictSpinnerAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.ProfileScreenAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.StateSpinnerAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.TeacherKycDocumentAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherProfileViewModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class TeacherProfileFragment extends BaseFragment implements TextWatcher {

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
        init();
        setListener();
        setRecycleView();
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


    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.editteachername.addTextChangedListener(this);
        binding.editemail.addTextChangedListener(this);


    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_teacher_profile;
    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {

                case LOADING:
                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == Status.UPDATE_TEACHER_PROFILE_API) {

                    }
                    break;

                case FAIL:
                case NO_INTERNET:

                    showSnackbarError((String) responseApi.data);
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
            binding.stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        viewModel.getStateDistrictData(stateDataModelList.get(position).getState_code());
                        binding.cityTitle.setVisibility(View.VISIBLE);
                        binding.cityView.setVisibility(View.VISIBLE);
                        binding.citySpinner.setVisibility(View.VISIBLE);

                    }else if(binding.stateSpinner.getSelectedItem().toString().trim().equalsIgnoreCase("PleaseSelectstate")){
                        binding.cityTitle.setVisibility(View.GONE);
                        binding.cityView.setVisibility(View.GONE);
                        binding.citySpinner.setVisibility(View.GONE);
                    }else{
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
    public void setRecycleView(){
        //for class recycleview
        classesList = Arrays.asList(getResources().getStringArray(R.array.classes));
        GridLayoutManager gridlayout = new GridLayoutManager(getActivity(),2);
        gridlayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recycleViewclass.setLayoutManager(gridlayout);
        binding.recycleViewclass.setHasFixedSize(true);
        binding.recycleViewclass.setNestedScrollingEnabled(false);
        ProfileScreenAdapter mProfileScreenAdapterAdapter = new ProfileScreenAdapter(viewModel.teacherUseCase.selectClass(),getContext());
        binding.recycleViewclass.setAdapter(mProfileScreenAdapterAdapter);

        //for subject recycle view
        subjectlist = Arrays.asList(getResources().getStringArray(R.array.subject));
        GridLayoutManager gridlayout2 = new GridLayoutManager(getActivity(),2);
        gridlayout2.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recycleViewsubject.setLayoutManager(gridlayout2);
        binding.recycleViewsubject.setHasFixedSize(true);
        binding.recycleViewsubject.setNestedScrollingEnabled(false);
        ProfileScreenAdapter mProfileScreenAdapterAdapter1 = new ProfileScreenAdapter(viewModel.teacherUseCase.selectSubject(),getContext());
        binding.recycleViewsubject.setAdapter(mProfileScreenAdapterAdapter1);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        //teacher name validation
        if(charSequence.hashCode() == binding.editteachername.getText().hashCode()){
            if(charSequence.length()>=5){
                binding.icteachername.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
            }else{
                binding.icteachername.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
            }
        }
        //email validation
        if(charSequence.hashCode() == binding.editemail.getText().hashCode()){

            if(emailValidation(binding.editemail)){
                binding.icteachername.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
            }else{
                binding.icteachername.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
            }
        }
        if(charSequence.hashCode() == binding.editPhoneNumber.getText().hashCode()){
            if(binding.editPhoneNumber.getText().toString().trim().length()>=10){
                binding.icteachername.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
            }else{
                binding.icteachername.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
            }
        }
        if(charSequence.hashCode() == binding.editSchoolName.getText().hashCode()){
            if(charSequence.length()<=10){
                binding.icteachername.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
            }else{
                binding.icteachername.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public boolean emailValidation(EditText emailtext){

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(emailtext.getText().toString().isEmpty()) {


            return false;

        }else {
            if (emailtext.getText().toString().trim().matches(emailPattern)) {

               return true;

            } else {

                binding.inputemailedittext.setError("Invalid email address");
                return false;

            }
        }
    }
}
