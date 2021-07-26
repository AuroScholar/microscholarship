package com.auro.scholr.home.presentation.view.fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.FragmentCertificateBinding;
import com.auro.scholr.home.data.model.APIcertificate;
import com.auro.scholr.home.data.model.CertificateResModel;
import com.auro.scholr.home.data.model.DashboardResModel;

import com.auro.scholr.home.presentation.view.activity.newDashboard.StudentMainDashboardActivity;
import com.auro.scholr.home.presentation.view.adapter.CertificateAdapter;
import com.auro.scholr.home.presentation.viewmodel.TransactionsViewModel;
import com.auro.scholr.payment.presentation.view.fragment.SendMoneyFragment;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.AskNameCustomDialog;
import com.auro.scholr.util.alert_dialog.CertificateDialog;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

public class CertificateFragment extends BaseFragment implements View.OnClickListener, CommonCallBackListner {


    @Inject
    @Named("CertificateFragment")
    ViewModelFactory viewModelFactory;
    FragmentCertificateBinding binding;
    TransactionsViewModel viewModel;
    DashboardResModel dashboardResModel;
    String TAG = "CertificateFragment";
    CertificateResModel certificateResModel;
    HashMap<Integer, String> hashMap = new HashMap<>();
    private String comingFrom;
    String studentName = "";

    public CertificateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_transactions, container, false);

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TransactionsViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        setToolbar();
        setListener();
    }

    @Override
    protected void init() {

        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
            comingFrom = getArguments().getString(AppConstant.COMING_FROM);

        }
        ViewUtil.setLanguageonUi(getActivity());
        ViewUtil.setProfilePic(binding.imageView6);

        callCertificateApi();


        setListener();

        //setAdapter();
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        ((StudentMainDashboardActivity)getActivity()).setListingActiveFragment(StudentMainDashboardActivity.CERTIFICATES_FRAGMENT);
        ((StudentMainDashboardActivity) getActivity()).loadPartnerLogo(binding.auroScholarLogo);

        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }

       // binding.headerParent.cambridgeHeading.setVisibility(View.VISIBLE);
        binding.toolbarLayout.backArrow.setOnClickListener(this);
        binding.toolbarLayout.langEng.setOnClickListener(this);
        binding.downloadIcon.setOnClickListener(this);
        binding.backButton.setOnClickListener(this);
        binding.cardView2.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_certificate;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.backButton) {
            getActivity().onBackPressed();
        } else if (id == R.id.download_icon) {
            askPermission();
        }else if (id ==  R.id.cardView2){
            ((StudentMainDashboardActivity)getActivity()).openProfileFragment();
        }
    }

    private void askPermission() {
        String rationale = "Please provide storage permission for download the certificate.";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mStorage, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                if (certificateResModel != null && !TextUtil.checkListIsEmpty(certificateResModel.getAPIcertificate())) {
                    if (hashMap.size() > 0) {
                        for (Map.Entry<Integer, String> map : hashMap.entrySet()) {
                            downloadFile(map.getValue());
                        }
                    } else {
                        ViewUtil.showSnackBar(binding.getRoot(), "Please select the certificate first, using long press on it.");
                    }
                } else {
                    ViewUtil.showSnackBar(binding.getRoot(), "No Certificate for Download.");

                }
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.

            }
        });
    }

    private void downloadFile(String url) {
        if (!TextUtil.isEmpty(url)) {
            DownloadManager downloadManager = (DownloadManager) AuroApp.getAppContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());
            downloadManager.enqueue(request);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void openSendMoneyFragment() {
        Bundle bundle = new Bundle();
        SendMoneyFragment sendMoneyFragment = new SendMoneyFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        sendMoneyFragment.setArguments(bundle);
        openFragment(sendMoneyFragment);
    }

    private void openFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, CertificateFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


    public void setAdapter() {
        // List<CertificateResModel> list = viewModel.homeUseCase.makeCertificateList();
        binding.certificateRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.certificateRecyclerView.setHasFixedSize(true);
        CertificateAdapter kyCuploadAdapter = new CertificateAdapter(getActivity(), certificateResModel.getAPIcertificate(), this);
        binding.certificateRecyclerView.setAdapter(kyCuploadAdapter);
    }

    private void openCertificateDialog(APIcertificate object) {
        CertificateDialog yesNoAlert = CertificateDialog.newInstance(object);
        yesNoAlert.show(getParentFragmentManager(), null);
        //  new CertificateDialog().show(getParentFragmentManager(), null);
       /* CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        CertificateDialog certificateDialog = new CertificateDialog(getActivity(), customDialogModel);
        certificateDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        *//*WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
       // lp.copyFrom(certificateDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        certificateDialog.getWindow().setAttributes(lp);*//*
        Objects.requireNonNull(certificateDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        certificateDialog.setCancelable(true);
        certificateDialog.show();*/
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case ITEM_CLICK:
                openCertificateDialog((APIcertificate) commonDataModel.getObject());
                break;

            case ITEM_LONG_CLICK:
                APIcertificate apIcertificate = (APIcertificate) commonDataModel.getObject();
                if (apIcertificate.isSelect()) {
                    hashMap.put(commonDataModel.getSource(), apIcertificate.getCertificateFile());
                } else {
                    hashMap.remove(commonDataModel.getSource());
                }
                break;

            case LISTNER_SUCCESS:
                handleNavigationProgress(1, "");
               /* dashboardResModel = (DashboardResModel) commonDataModel.getObject();
                ((AuroScholarDashBoardActivity) getActivity()).dashboardModel(dashboardResModel);
                callCertificateApi();*/
                break;

            case LISTNER_FAIL:
                handleNavigationProgress(2, (String) commonDataModel.getObject());

                break;

            case NAME_DONE_CLICK:
                studentName = (String) commonDataModel.getObject();
                callCertificateApi();
                break;

            default:

                break;
        }
    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    /*do coding here*/
                    break;

                case SUCCESS:
                    certificateResModel = (CertificateResModel) responseApi.data;
                    if (certificateResModel.getError()) {
                        if (!TextUtil.isEmpty(certificateResModel.getStudentNameVerified())&&certificateResModel.getStudentNameVerified().equalsIgnoreCase(AppConstant.DocumentType.NO)) {
                            openAskNameDialog();
                        }
                        handleProgress(3, certificateResModel.getMessage());

                    } else {
                        if (!TextUtil.checkListIsEmpty(certificateResModel.getAPIcertificate())) {
                            handleProgress(1, "");
                            setAdapter();
                        } else {
                            handleProgress(3, "No Certificate.");
                        }
                    }
                    break;

                case NO_INTERNET:
                    handleProgress(2, (String) responseApi.data);
                    break;

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


    private void handleProgress(int status, String msg) {
        switch (status) {
            case 0:
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.certificateRecyclerView.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.GONE);
                break;
            case 1:
                binding.progressBar.setVisibility(View.GONE);
                binding.certificateRecyclerView.setVisibility(View.VISIBLE);
                binding.errorConstraint.setVisibility(View.GONE);
                break;

            case 2:
                binding.progressBar.setVisibility(View.GONE);
                binding.certificateRecyclerView.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.VISIBLE);
                binding.errorLayout.textError.setText(msg);
                binding.errorLayout.errorIcon.setVisibility(View.GONE);
                binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callCertificateApi();
                    }
                });
                break;
            case 3:
                binding.progressBar.setVisibility(View.GONE);
                binding.certificateRecyclerView.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.VISIBLE);
                binding.errorLayout.textError.setText(msg);
                binding.errorLayout.errorIcon.setVisibility(View.GONE);
                binding.errorLayout.btRetry.setVisibility(View.GONE);

                break;
        }
    }

    private void callCertificateApi() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel.getDashboardResModel() != null) {
            handleProgress(0, "");
            CertificateResModel certificateResModel = new CertificateResModel();
            certificateResModel.setMobileNumber(prefModel.getDashboardResModel().getPhonenumber());
            certificateResModel.setRegistrationId(prefModel.getDashboardResModel().getAuroid());
            certificateResModel.setStudentName(studentName);
         /*   certificateResModel.setMobileNumber("9654234507");
            certificateResModel.setRegistrationId("14");*/
            viewModel.getCertificate(certificateResModel);
        }
    }

    private void handleNavigationProgress(int status, String msg) {
        if (status == 0) {
            binding.transparet.setVisibility(View.VISIBLE);
            binding.certificate.setVisibility(View.VISIBLE);

            binding.errorConstraint.setVisibility(View.GONE);
        } else if (status == 1) {
            binding.transparet.setVisibility(View.GONE);
            binding.certificate.setVisibility(View.VISIBLE);

            binding.errorConstraint.setVisibility(View.GONE);
        } else if (status == 2) {
            binding.transparet.setVisibility(View.GONE);
            binding.certificate.setVisibility(View.GONE);
            binding.errorConstraint.setVisibility(View.VISIBLE);
            binding.errorLayout.textError.setText(msg);
            binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callCertificateApi();
                }
            });
        }
    }

    private void openAskNameDialog() {
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        customDialogModel.setTitle(AuroApp.getAppContext().getResources().getString(R.string.update_auroscholar));
        customDialogModel.setContent(AuroApp.getAppContext().getResources().getString(R.string.updateMessage));
        AskNameCustomDialog updateCustomDialog = new AskNameCustomDialog(getActivity(), customDialogModel, this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(updateCustomDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        updateCustomDialog.getWindow().setAttributes(lp);
        Objects.requireNonNull(updateCustomDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updateCustomDialog.setCancelable(true);
        updateCustomDialog.show();
    }
}
