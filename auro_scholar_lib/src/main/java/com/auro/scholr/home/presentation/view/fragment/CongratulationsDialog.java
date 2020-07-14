package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseDialog;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.databinding.DialogCongratulations2Binding;
import com.auro.scholr.databinding.DialogCongratulationsBinding;
import com.auro.scholr.home.presentation.viewmodel.CongratulationsDialogViewModel;
import com.auro.scholr.home.presentation.viewmodel.InviteFriendViewModel;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;
import com.bumptech.glide.Glide;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.ArrayList;
import java.util.Random;


import javax.inject.Inject;
import javax.inject.Named;

public class CongratulationsDialog extends BaseDialog implements View.OnClickListener {


    @Inject
    @Named("CongratulationsDialog")
    ViewModelFactory viewModelFactory;
    DialogCongratulations2Binding binding;
    CongratulationsDialogViewModel viewModel;
    Context mcontext;



    private static final String TAG = CongratulationsDialog.class.getSimpleName();

    public CongratulationsDialog(Context mcontext) {
        this.mcontext = mcontext;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CongratulationsDialogViewModel.class);
        binding.setLifecycleOwner(this);
        init();
        setListener();
        return binding.getRoot();
    }

    @Override
    protected void init() {
        binding.btnShare.setOnClickListener(this);
        binding.icClose.setOnClickListener(this);
        Glide.with(this).load(R.raw.spracle).into(binding.backgroundSprincle);

        // create random object
        Random randomno = new Random();
        binding.tickerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.DOWN);
        binding.tickerView.setCharacterLists(TickerUtils.provideNumberList());
        for(int i=1 ;i<=50;i++){
           binding.tickerView.setText(i+"%");
        }



    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_congratulations_2;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnShare) {

            shareWithFriends();

        } else if (id == R.id.icClose) {
            dismiss();
        }
    }

    public void shareWithFriends() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mcontext.getResources().getString(R.string.share_msg));
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        dismiss();
        mcontext.startActivity(shareIntent);
    }
    private String generateChars(Random random, String list, int numDigits) {
        final char[] result = new char[numDigits];
        for (int i = 0; i < numDigits; i++) {
            result[i] = list.charAt(random.nextInt(list.length()));
        }
        return new String(result);
    }


}
