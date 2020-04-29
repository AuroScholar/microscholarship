package com.example.aurosampleapplication;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.util.AuroScholar;
import com.example.aurosampleapplication.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_dashboard);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btSdk.setOnClickListener(this);
        binding.btOpen.setOnClickListener(this);
        binding.enterNumber.setText("8178307851");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sdk:

                String mobileNumber = binding.enterNumber.getText().toString().trim();
                if (mobileNumber != null && !mobileNumber.isEmpty()) {
                    //binding.homeContainer.setVisibility(View.VISIBLE);
                    //  binding.enterNumber.setVisibility(View.GONE);
                    // binding.btSdk.setVisibility(View.GONE);
                    openSdk(mobileNumber);
                    hideKeyboard(this);
                } else {
                    Toast.makeText(this, "Please enter the number", Toast.LENGTH_LONG);
                }
                break;
            case R.id.bt_open:
                openFragment(new SampleFragment());
                break;

        }
    }

    public static void hideKeyboard(Context context) {
        if (context == null) {
            return;
        }
        View view = ((AppCompatActivity) context).getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void openSdk(String mobileNumber) {
        AuroScholarDataModel auroScholarDataModel = new AuroScholarDataModel();
        auroScholarDataModel.setMobileNumber("9713680981");
        auroScholarDataModel.setScholrId("91003");
        auroScholarDataModel.setStudentClass("10");
        auroScholarDataModel.setRegitrationSource("Auro Scholar");
        auroScholarDataModel.setShareType("telecaller");
        auroScholarDataModel.setShareIdentity("9681032476");
        auroScholarDataModel.setActivity(this);
        auroScholarDataModel.setFragmentContainerUiId(R.id.home_container);
        AuroScholar.openAuroDashboardFragment(auroScholarDataModel);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //must param to get the acitivity
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void openFragment(Fragment fragment) {
        ((AppCompatActivity) (this)).getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.home_container, fragment, SampleFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();


    }

}
