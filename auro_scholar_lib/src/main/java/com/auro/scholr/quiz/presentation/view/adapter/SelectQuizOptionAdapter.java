package com.auro.scholr.quiz.presentation.view.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.SendQuizItemLayoutBinding;
import com.auro.scholr.quiz.data.model.submitQuestionModel.OptionResModel;
import com.auro.scholr.util.AppUtil;

import java.util.List;

public class SelectQuizOptionAdapter extends RecyclerView.Adapter<SelectQuizOptionAdapter.ViewHolder> {

    List<OptionResModel> list;
    Context mContext;
    CommonCallBackListner commonCallBackListner;


    public SelectQuizOptionAdapter(Context context, List<OptionResModel> list, CommonCallBackListner commonCallBackListner) {
        mContext = context;
        this.list = list;
        this.commonCallBackListner = commonCallBackListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        SendQuizItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.send_quiz_item_layout, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // holder.setData(list.get(position), position);
        holder.bindUser(list.get(position), position, commonCallBackListner);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<OptionResModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        SendQuizItemLayoutBinding binding;


        public ViewHolder(SendQuizItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindUser(OptionResModel model, int position, CommonCallBackListner commonCallBackListner) {
            binding.msgText.setText(Html.fromHtml(model.getOption()));

            if (model.isCheck()) {
                binding.checkIcon.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_auro_check));
            } else {
                binding.checkIcon.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.circle_auro_outline));

            }
            binding.llayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < list.size(); i++) {
                        if (i == position) {
                            list.get(i).setCheck(true);
                        } else {
                            list.get(i).setCheck(false);
                        }
                    }

                    if (commonCallBackListner != null) {
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.OPTION_SELECT_API, model));
                    }
                    notifyDataSetChanged();
                }
            });


        }
    }
}

