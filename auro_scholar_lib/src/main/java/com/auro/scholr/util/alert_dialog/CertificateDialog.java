package com.auro.scholr.util.alert_dialog;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.databinding.CertificateDialogBinding;
import com.auro.scholr.home.data.model.APIcertificate;
import com.auro.scholr.util.ImageUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;

import java.util.ArrayList;

public class CertificateDialog extends DialogFragment implements View.OnClickListener {

    public Context context;
    private String msg;
    Dialog dialog;
    APIcertificate apIcertificate;

    public static CertificateDialog newInstance(APIcertificate model) {
        CertificateDialog f = new CertificateDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(AppConstant.SENDING_DATA.CERTIFICATE_DATA, model);
        f.setArguments(args);
        return f;
    }

    private CertificateDialogBinding binding;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            apIcertificate = getArguments().getParcelable(AppConstant.SENDING_DATA.CERTIFICATE_DATA);
        }
        dialog = new Dialog(getActivity(), R.style.FullWidth_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.certificate_dialog, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        ImageView closeButton = (ImageView) view.findViewById(R.id.close_button);
        ImageView download = (ImageView) view.findViewById(R.id.download_icon);
        closeButton.setOnClickListener(this);
        download.setOnClickListener(this);
        ImageUtil.loaWithoutCropImage(imageView, apIcertificate.getCertificateImage());
        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }

  /*  public CertificateDialog(Context context, CustomDialogModel customDialogModel) {
        super(context);
        this.context = context;
        this.msg = customDialogModel.getContent();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.certificate_dialog, null, false);
        binding.closeButton.setOnClickListener(this);
        setContentView(binding.getRoot());
    }




*/

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.close_button) {
            dismiss();
        } else if (id == R.id.download_icon) {
            askPermission();
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
                if(!TextUtil.isEmpty(apIcertificate.getCertificateFile())) {
                    downloadFile(apIcertificate.getCertificateFile());
                }else
                {
                    ViewUtil.showToast("File not avaible for Download.");
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
}
