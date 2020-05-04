package com.auro.scholr.home.data.model;

import android.content.Context;
import android.view.View;

public class CustomSnackBarModel {

    View view;
    Context context;
    int status;

    public CustomSnackBarModel() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
