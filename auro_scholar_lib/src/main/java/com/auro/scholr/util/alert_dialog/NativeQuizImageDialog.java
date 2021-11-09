package com.auro.scholr.util.alert_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.auro.scholr.R;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.databinding.CertificateDialogBinding;
import com.auro.scholr.util.ImageUtil;

public class NativeQuizImageDialog extends DialogFragment implements View.OnClickListener {

    public Context context;
    private String msg;
    Dialog dialog;
    String imageLink;



    public static NativeQuizImageDialog newInstance(String imageName) {
        NativeQuizImageDialog f = new NativeQuizImageDialog();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString(AppConstant.QuizNative.IMAGEINLARGE, imageName);
        f.setArguments(args);
        return f;
    }


    private CertificateDialogBinding binding;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            imageLink = getArguments().getString(AppConstant.QuizNative.IMAGEINLARGE);
        }
        dialog = new Dialog(getActivity(), R.style.FullWidth_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.certificate_dialog, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        ImageView closeButton = (ImageView) view.findViewById(R.id.close_button);
        ImageView download = (ImageView) view.findViewById(R.id.download_icon);
        download.setVisibility(View.GONE);
        closeButton.setOnClickListener(this);
        download.setOnClickListener(this);
        ImageUtil.loadNormalImage(imageView,imageLink);
        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.close_button) {
            dismiss();
        } else if (id == R.id.download_icon) {
        }

    }
}

