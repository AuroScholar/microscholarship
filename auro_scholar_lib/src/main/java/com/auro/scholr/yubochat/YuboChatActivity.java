package com.auro.scholr.yubochat;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.auro.scholr.R;
import com.auro.scholr.core.application.base_component.BaseActivity;
import com.auro.scholr.databinding.ActivityYuboChatBinding;


public class YuboChatActivity extends BaseActivity {


    ActivityYuboChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView();
        binding = DataBindingUtil.setContentView(this, getLayout());

        init();


    }

    @Override
    protected void init() {

        loadFragment(new YuboChatBotFragment());
        setListener();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_yubo_chat;
    }

    public boolean  loadFragment(Fragment fragment){
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, fragment).addToBackStack("back")
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
     finish();

    }

}