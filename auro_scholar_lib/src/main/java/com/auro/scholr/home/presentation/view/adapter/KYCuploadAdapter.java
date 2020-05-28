package com.auro.scholr.home.presentation.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.QuizDocUploadLayoutBinding;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.util.AppUtil;

import java.util.ArrayList;


public class KYCuploadAdapter extends RecyclerView.Adapter<KYCuploadAdapter.ViewHolder> {

    ArrayList<KYCDocumentDatamodel> mValues;
    Context mContext;
    QuizDocUploadLayoutBinding binding;
    CommonCallBackListner commonCallBackListner;
    boolean progressStatus;

    public KYCuploadAdapter(Context context, ArrayList<KYCDocumentDatamodel> values, CommonCallBackListner commonCallBackListner) {
        this.commonCallBackListner = commonCallBackListner;
        mValues = values;
        mContext = context;
    }

    public void updateList(ArrayList<KYCDocumentDatamodel> values) {
        mValues = values;
        notifyDataSetChanged();
    }

    public void updateProgressValue(boolean progressStatus) {
        this.progressStatus = progressStatus;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        QuizDocUploadLayoutBinding binding;

        public ViewHolder(QuizDocUploadLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(KYCDocumentDatamodel kycDocumentDatamodel, int position) {
            this.binding.tvIdHead.setText(kycDocumentDatamodel.getDocumentName());
            binding.tvNoFileChoosen.setText(kycDocumentDatamodel.getDocumentFileName());
            binding.chooseFile.setText(kycDocumentDatamodel.getButtonText());

            if (!kycDocumentDatamodel.isModify() && kycDocumentDatamodel.isDocumentstatus()) {
                binding.progressBar.setVisibility(View.GONE);
                binding.chooseFile.setVisibility(View.GONE);
                binding.tvNoFileChoosen.setVisibility(View.GONE);
                binding.tvSucessfullyUploaded.setVisibility(View.VISIBLE);
                binding.chooseFile.setBackgroundColor(AuroApp.getAppContext().getResources().getColor(R.color.rich_electric_blue));
            } else {
                if (progressStatus && kycDocumentDatamodel.isProgress()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
                if (progressStatus && !kycDocumentDatamodel.isProgress()) {
                    binding.chooseFile.setEnabled(false);
                    binding.chooseFile.setBackgroundColor(AuroApp.getAppContext().getResources().getColor(R.color.grey_color));
                } else {
                    binding.chooseFile.setEnabled(true);
                    binding.chooseFile.setBackgroundColor(AuroApp.getAppContext().getResources().getColor(R.color.blue_color));
                }
            }

            binding.chooseFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonCallBackListner != null) {
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.KYC_BUTTON_CLICK, kycDocumentDatamodel));
                    }
                }
            });
        }

    }

    @Override
    public KYCuploadAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.quiz_doc_upload_layout, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position), position);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

}
