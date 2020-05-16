package com.auro.scholr.util.alert_dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.CustomUiSnackbarBinding;
import com.auro.scholr.home.data.model.CustomSnackBarModel;
import com.auro.scholr.util.AppUtil;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public enum CustomSnackBar implements View.OnClickListener {
    INSTANCE;

    private static Snackbar cartSnackbar;
    private CustomUiSnackbarBinding binding;
    Context context;
    View view;
    CustomSnackBarModel customSnackBarModel;
    CommonCallBackListner commonCallBackListner;


    public void showCartSnackbar(CustomSnackBarModel customSnackBarModel) {
        this.view = customSnackBarModel.getView();
        this.context = customSnackBarModel.getContext();
        this.customSnackBarModel = customSnackBarModel;
        this.commonCallBackListner = customSnackBarModel.getCommonCallBackListner();
        if (cartSnackbar == null) {
            LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            binding = DataBindingUtil.inflate(inflater, R.layout.custom_ui_snackbar, null, false);
        }

        if (cartSnackbar == null) {
            openSnackBar();
        }

    }


    private void openSnackBar() {
        if (view == null) {
            return;
        }
        if (view.getParent() == null) {
            return;
        }
        cartSnackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        // White background
        cartSnackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        // binding.textMsg.setOnClickListener(this);
        if (customSnackBarModel.getStatus() == 1) {
            binding.parentLayout.setBackgroundColor(view.getResources().getColor(R.color.color_red));
            binding.arrow.setVisibility(View.GONE);
            binding.kycMsg.setText("Your KYC is pending from backend for");
        }

        binding.arrow.setOnClickListener(this);

        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) cartSnackbar.getView();
        if (binding.getRoot().getParent() != null) {
            ((ViewGroup) binding.getRoot().getParent()).removeView(binding.getRoot());

        }
        snackBarView.addView(binding.getRoot(), 0);
        snackBarView.setPadding(0, 0, 0, 0);
        cartSnackbar.setDuration(BaseTransientBottomBar.LENGTH_INDEFINITE);
        cartSnackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                //see Snackbar.Callback docs for event details
                cartSnackbar = null;
            }

            @Override
            public void onShown(Snackbar snackbar) {
//Do code here
            }
        });

        if (cartSnackbar != null) {
            cartSnackbar.getView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    if (cartSnackbar != null) {
                        cartSnackbar.getView().getViewTreeObserver().removeOnPreDrawListener(this);
                        ((CoordinatorLayout.LayoutParams) cartSnackbar.getView().getLayoutParams()).setBehavior(null);
                    }
                    return true;
                }
            });
        }
        cartSnackbar.show();
    }

    public void dismissCartSnackbar() {
        if (cartSnackbar != null && cartSnackbar.isShown()) {
            cartSnackbar.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.arrow) {
            if (commonCallBackListner != null) {
                commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(-1, Status.FRIEND_LEADER_BOARD_CLICK, ""));
            }

        }
    }

}
