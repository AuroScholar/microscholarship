package com.auro.scholr.core.application.base_component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.auro.scholr.R;

public abstract class BaseDialog  extends DialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void init();
    protected abstract void setToolbar();

    protected abstract void setListener();

    protected abstract int getLayout();
}
