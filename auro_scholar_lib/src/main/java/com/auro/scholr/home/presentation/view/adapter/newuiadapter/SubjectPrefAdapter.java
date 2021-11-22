package com.auro.scholr.home.presentation.view.adapter.newuiadapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.SubjectsPrefItemLayoutBinding;
import com.auro.scholr.home.data.model.CategorySubjectResModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ViewUtil;

import java.util.List;

public class SubjectPrefAdapter extends RecyclerView.Adapter<SubjectPrefAdapter.SubjectPrefHolder> {

    List<CategorySubjectResModel> list;
    CommonCallBackListner commonCallBackListner;
    Context contextApp;

    public SubjectPrefAdapter(Context context,List<CategorySubjectResModel> list, CommonCallBackListner commonCallBackListner) {
        this.list = list;
        this.contextApp = context;
        this.commonCallBackListner = commonCallBackListner;
    }

    @NonNull
    @Override
    public SubjectPrefHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        SubjectsPrefItemLayoutBinding languageItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.subjects_pref_item_layout, viewGroup, false);
        return new SubjectPrefHolder(languageItemLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectPrefHolder holder, int index) {
        CategorySubjectResModel resModel = list.get(index);
        AppLogger.e("onBindViewHolder --", "step 0--" + resModel.isLock());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resModel.isLock()) {
                    AppLogger.e("onBindViewHolder --", "step 1--" + resModel.isLock());
                    updateList(index);
                }
            }
        });

        holder.bindUser(list.get(index), index, commonCallBackListner);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubjectPrefHolder extends RecyclerView.ViewHolder {
        SubjectsPrefItemLayoutBinding binding;

        public SubjectPrefHolder(@NonNull SubjectsPrefItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bindUser(CategorySubjectResModel model, int position, CommonCallBackListner commonCallBackListner) {
            binding.RPTextViewTitle.setText(model.getSubjectname());
            binding.mainParentLayout.setBackground(model.getBackgroundImage());
            if (model.isLock()) {
                binding.lockLayout.setBackground(AppCompatResources.getDrawable(contextApp,R.drawable.disable_background));
                binding.lockLayout.setVisibility(View.VISIBLE);
                binding.checkIcon.setImageDrawable(AppCompatResources.getDrawable(contextApp,R.drawable.ic_auro_check_disable));
            } else {
                binding.checkIcon.setImageDrawable(AppCompatResources.getDrawable(contextApp,R.drawable.ic_auro_check));
                binding.lockLayout.setVisibility(View.GONE);
            }
            if (model.isSelected()) {
                binding.checkIcon.setVisibility(View.VISIBLE);
            } else {
                binding.checkIcon.setVisibility(View.GONE);
            }

        }
    }

    public void setData(List<CategorySubjectResModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    void updateList(int index) {
        int count = 0;
        for (CategorySubjectResModel resModel : list) {
            if (resModel.isSelected()) {
                count++;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            boolean val = list.get(index).isSelected();
            if (i == index) {
                if (val) {
                    list.get(index).setSelected(false);
                    if (commonCallBackListner != null) {
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(index, Status.REMOVE_ITEM, list.get(index)));
                    }
                } else {
                    if (count < 5) {
                        list.get(index).setSelected(true);
                        if (commonCallBackListner != null) {
                            commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(index, Status.ITEM_CLICK, list.get(index)));
                        }

                    } else {
                        ViewUtil.showToast("You can select max five subjects");
                    }
                }
            }

            AppLogger.e("updateList values -0 ", list.get(i).getSubjectname() + "--selecetd--" + list.get(i).isSelected() + "-is lock-" + list.get(i).isLock());
        }


        notifyDataSetChanged();
    }
}