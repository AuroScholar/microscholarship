package com.auro.scholr.home.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.FragmentCertificateItemBinding;
import com.auro.scholr.home.data.model.APIcertificate;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.ViewHolder> {

    List<APIcertificate> mValues;
    Context mContext;
    FragmentCertificateItemBinding binding;
    CommonCallBackListner listner;

    public CertificateAdapter(Context context, List<APIcertificate> values, CommonCallBackListner listner) {
        mValues = values;
        mContext = context;
        this.listner = listner;
    }

    public void updateList(ArrayList<APIcertificate> values) {
        mValues = values;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        FragmentCertificateItemBinding binding;

        public ViewHolder(FragmentCertificateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(APIcertificate resModel, int position) {
            if (resModel.isSelect()) {
                binding.selectImg.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_check));
            } else {
                binding.selectImg.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_uncheck));
            }
            //resModel.setCertificateImage("https://image.slidesharecdn.com/b1c107f5-eaf4-4dd9-8acc-df180578c33c-160501092731/95/ismail-british-council-certificate-1-638.jpg?cb=1462094874");
            ImageUtil.loadNormalImage(binding.certificateImg, resModel.getCertificateImage());

        }

    }

    @Override
    public CertificateAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.fragment_certificate_item, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position), position);
        Vholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listner != null) {
                    listner.commonEventListner(AppUtil.getCommonClickModel(1, Status.ITEM_CLICK, mValues.get(position)));
                }
            }
        });

        Vholder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                APIcertificate icertificate = mValues.get(position);
                if (icertificate.isSelect()) {
                    icertificate.setSelect(false);
                } else {
                    icertificate.setSelect(true);
                }
                if (listner != null) {
                    listner.commonEventListner(AppUtil.getCommonClickModel(position, Status.ITEM_LONG_CLICK, icertificate));
                }
                notifyDataSetChanged();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void updateData() {

    }

}
