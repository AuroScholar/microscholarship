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
    Context mcontext;

    public SubjectSelectAdapter(List<SubjectResModel> list, Context mcontext, CommonCallBackListner commonCallBackListner) {
        this.list = list;
        this.commonCallBackListner = commonCallBackListner;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public SubjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        SelectSubjectItemLayoutBinding languageItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.select_subject_item_layout, viewGroup, false);
        return new SubjectHolder(languageItemLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectHolder holder, int position) {
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
                drawable = mcontext.getResources().getDrawable(R.drawable.auro_math);
            } else if (position == 1) {
                drawable = mcontext.getResources().getDrawable(R.drawable.auro_english);
            } else if (position == 2) {
                drawable = mcontext.getResources().getDrawable(R.drawable.auro_hindi);
            } else if (position == 3) {
                drawable = mcontext.getResources().getDrawable(R.drawable.auro_sst);
            } else {
                drawable = mcontext.getResources().getDrawable(R.drawable.auro_science);
            }
            binding.icSubjctBackground.setImageDrawable(drawable);

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


}