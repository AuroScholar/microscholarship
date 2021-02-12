package com.auro.scholr.payment.presentation.view.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.PaytmError;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.common.ValidationModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.databinding.BankFragmentLayoutBinding;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.payment.data.model.request.PaytmWithdrawalByBankAccountReqModel;
import com.auro.scholr.payment.data.model.request.PaytmWithdrawalReqModel;
import com.auro.scholr.payment.data.model.response.PaytmResModel;
import com.auro.scholr.payment.presentation.viewmodel.SendMoneyViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.DateUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.CustomDialog;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.alert_dialog.CustomPaymentTranferDialog;
import com.auro.scholr.util.alert_dialog.CustomProgressDialog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static com.auro.scholr.core.common.Status.PAYMENT_TRANSFER_API;
import static com.auro.scholr.core.common.Status.PAYTM_ACCOUNT_WITHDRAWAL;
import static com.auro.scholr.core.common.Status.PAYTM_WITHDRAWAL;


public class BankFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("BankFragment")
    ViewModelFactory viewModelFactory;
    BankFragmentLayoutBinding binding;
    SendMoneyViewModel viewModel;
    boolean isStateRestore;
    private String TAG = "BankFragment";
    CustomProgressDialog customProgressDialog;
    DashboardResModel mdashboard;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SendMoneyViewModel.class);
        binding.setLifecycleOwner(this);
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }


    @Override
    protected void init() {

        if (getArguments() != null) {
            mdashboard = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
        }

        binding.walletBalText.setText("₹"+mdashboard.getApproved_scholarship_money()+".00");

        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }
    }



    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.sendButton.setOnClickListener(this);
        setKeyListner();
    }


    @Override
    protected int getLayout() {
        return R.layout.bank_fragment_layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {

        }
        init();
        setToolbar();
        setListener();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {

    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.send_button){
            paytmentTransferApi();
        }

    }


    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private void paytmwithdrawalAmountApi() {
        String accountnumber = binding.accountNumber.getText().toString();
        String confirmaccountnumber = binding.confirmAccountNumber.getText().toString();
        String ifscCode = binding.ifscCode.getText().toString();

        ValidationModel bankAccountvalidation = viewModel.paymentUseCase.isValidBankAccountNumber(accountnumber,ifscCode,confirmaccountnumber);

        if(bankAccountvalidation.isStatus()){
            PaytmWithdrawalByBankAccountReqModel reqModel = new PaytmWithdrawalByBankAccountReqModel();
            reqModel.setMobileNo(AuroApp.getAuroScholarModel().getMobileNumber());
            reqModel.setDisbursementMonth(DateUtil.getcurrentYearMothsNumber());
            reqModel.setDisbursement(mdashboard.getApproved_scholarship_money());
            reqModel.setBankaccountno(confirmaccountnumber);
            reqModel.setIfsccode(ifscCode);
            reqModel.setPurpose("OTHERS");
            viewModel.paytmWithdrawalByAccount(reqModel);
        }else{
            showSnackbarError(bankAccountvalidation.getMessage());
        }

    }

    private void paytmentTransferApi() {
        String accountnumber = binding.accountNumber.getText().toString();
        String confirmaccountnumber = binding.confirmAccountNumber.getText().toString();
        String ifscCode = binding.ifscCode.getText().toString();

        ValidationModel bankAccountvalidation = viewModel.paymentUseCase.isValidBankAccountNumber(accountnumber,ifscCode,confirmaccountnumber);

        if(bankAccountvalidation.isStatus()){
            PaytmWithdrawalByBankAccountReqModel reqModel = new PaytmWithdrawalByBankAccountReqModel();
            reqModel.setMobileNo(AuroApp.getAuroScholarModel().getMobileNumber());
            reqModel.setStudentId(AppPref.INSTANCE.getModelInstance().getDashboardResModel().getStudent_id());
            /*reqModel.setMobileNo("9654234507");
            reqModel.setStudentId("4077466");*/
            reqModel.setPaymentMode(AppConstant.PaymenMode.BANK_Transfer);
            reqModel.setDisbursementMonth(DateUtil.getMonthName());
            reqModel.setBeneficiary_name("");
            reqModel.setBankaccountno(accountnumber);
            reqModel.setIfsccode(ifscCode);
            reqModel.setAmount(mdashboard.getApproved_scholarship_money());
            //  reqModel.setAmount("1");
            reqModel.setPurpose("Payment Transfer");
            viewModel.paytmentTransfer(reqModel);
        }else{
            showSnackbarError(bankAccountvalidation.getMessage());
        }

    }
    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    //For ProgressBar

                        openProgressDialog();

                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == PAYTM_ACCOUNT_WITHDRAWAL) {
                        closeDialog();
                        PaytmResModel mpaytm = (PaytmResModel) responseApi.data;
                        mpaytm.getResponse().replaceAll("\\\\", "");
                        //openPaymentDialog(mpaytm.getResponse());
                    }else if(responseApi.apiTypeStatus == PAYMENT_TRANSFER_API)
                    {
                        closeDialog();
                        PaytmResModel mpaytm = (PaytmResModel) responseApi.data;
                        openPaymentDialog(mpaytm);
                    }

                    break;

                case NO_INTERNET:

                    closeDialog();
                    showSnackbarError((String) responseApi.data);

                    break;
                case AUTH_FAIL:
                case FAIL_400:

                    closeDialog();
                    showSnackbarError((String) responseApi.data);
                    break;

                default:
                    Log.d(TAG, "observeServiceResponse: default");
                    closeDialog();
                    showSnackbarError((String) responseApi.data);
                    break;
            }

        });
    }

    private void openProgressDialog() {
        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            return;
        }
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        customDialogModel.setTitle("Processing your payment...");
        customDialogModel.setTwoButtonRequired(true);
        customProgressDialog = new CustomProgressDialog(customDialogModel);
        Objects.requireNonNull(customProgressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customProgressDialog.setCancelable(false);
        customProgressDialog.show();
        customProgressDialog.updateDataUi(0);
    }
    public void closeDialog() {
        if (customProgressDialog != null) {
            customProgressDialog.dismiss();
        }
    }
    private void openPaymentDialog(PaytmResModel resModel) {
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        customDialogModel.setTitle(getActivity().getResources().getString(R.string.information));
        customDialogModel.setContent(resModel.getMessage());
     /*   if (message.contains(AppConstant.PaytmResponseCode.DE_002)) {
            customDialogModel.setContent(getActivity().getResources().getString(R.string.requested_accepted));
        } else {
            customDialogModel.setContent(getActivity().getResources().getString(R.string.payment_failed_error_msg));
            HashMap<String, String> stringStringHashMap = PaytmError.initMapping();
            for (Map.Entry<String, String> entry : stringStringHashMap.entrySet()) {
                String key = entry.getKey();
                if (message.contains(key)) {
                    customDialogModel.setContent(entry.getValue());
                }
            }
        }*/
        customDialogModel.setTwoButtonRequired(true);
        CustomPaymentTranferDialog customDialog = new CustomPaymentTranferDialog(getActivity(), customDialogModel);
        customDialog.setSecondBtnTxt("Ok");
        customDialog.setSecondCallcack(new CustomDialog.SecondCallcack() {
            @Override
            public void clickYesCallback() {
                if (!resModel.isError()) {
                    ((SendMoneyFragment) getParentFragment()).backButton();
                    customDialog.dismiss();
                } else {
                    customDialog.dismiss();
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDialog.getWindow().setAttributes(lp);
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.setCancelable(false);
        customDialog.show();

    }
    private void setKeyListner() {
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    ((SendMoneyFragment)getParentFragment()).onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }

}
