package com.auro.scholr.home.presentation.view.adapter.newuiadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.SelectSubjectItemLayoutBinding;
import com.auro.scholr.home.data.model.SubjectResModel;
import com.auro.scholr.util.AppUtil;

import java.util.List;

public class SubjectSelectAdapter extends RecyclerView.Adapter<SubjectSelectAdapter.SubjectHolder> {

    List<SubjectResModel> list;
    CommonCallBackListner commonCallBackListner;

    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = 0;

    public SubjectSelectAdapter(List<SubjectResModel> list, CommonCallBackListner commonCallBackListner) {
        this.list = list;
        this.commonCallBackListner = commonCallBackListner;
    }

    @NonNull
    @Override
    public SubjectSelectAdapter.SubjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        SelectSubjectItemLayoutBinding languageItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.select_subject_item_layout, viewGroup, false);
        return new SubjectSelectAdapter.SubjectHolder(languageItemLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectSelectAdapter.SubjectHolder holder, int position) {
        holder.bindUser(list.get(position), position, commonCallBackListner);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubjectHolder extends RecyclerView.ViewHolder {
        SelectSubjectItemLayoutBinding binding;

        public SubjectHolder(@NonNull SelectSubjectItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void bindUser(SubjectResModel model, int position, CommonCallBackListner commonCallBackListner) {
            binding.RPSubject.setText(model.getSubject());
            String str = String.format("%02d", (position + 1));
            binding.RPQuiz.setText(str);

            Drawable drawable = null;
            if (position == 0) {
                drawable = AuroApp.getAppContext().getResources().getDrawable(R.drawable.auro_math);
            } else if (position == 1) {
                drawable = AuroApp.getAppContext().getResources().getDrawable(R.drawable.auro_english);
            } else if (position == 2) {
                drawable = AuroApp.getAppContext().getResources().getDrawable(R.drawable.auro_hindi);
            } else if (position == 3) {
                drawable = AuroApp.getAppContext().getResources().getDrawable(R.drawable.auro_sst);
            } else {
                drawable = AuroApp.getAppContext().getResources().getDrawable(R.drawable.auro_science);
            }
            binding.icSubjctBackground.setImageDrawable(getImageFromCode(model));

            binding.itemSubject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonCallBackListner != null) {
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.SUBJECT_CLICKED, model));
                    }
                }
            });


        }
    }

    public void setData(List<SubjectResModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    private Drawable getImageFromCode(SubjectResModel subjectResModel) {
        switch (subjectResModel.getSubjectCode()) {
            case AppConstant.SubjectCodes.Mathematics:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_maths_vertical);

            case AppConstant.SubjectCodes.English:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_english_vertical);

            case AppConstant.SubjectCodes.Hindi:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_hindi_vertical);


            case AppConstant.SubjectCodes.Social_Science:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_social_science_vertical);

            case AppConstant.SubjectCodes.Science:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_science_vertical);

            case AppConstant.SubjectCodes.Physics:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_physics_vertical);

            case AppConstant.SubjectCodes.Chemistry:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_chemistry_vertical);

            case AppConstant.SubjectCodes.Biology:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_biology_vertical);

            case AppConstant.SubjectCodes.History:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_history_vertical);

            case AppConstant.SubjectCodes.Political_Science:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_political_science_vertical);

            case AppConstant.SubjectCodes.Geography:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_geographic_vertical);

            default:
                return AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_physics_vertical);

        }

    }
}
