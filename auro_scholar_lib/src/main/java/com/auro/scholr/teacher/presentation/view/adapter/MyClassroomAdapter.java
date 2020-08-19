package com.auro.scholr.teacher.presentation.view.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.util.uiwidget.RPTextView;
import com.auro.scholr.databinding.StudentClassItemLayoutBinding;
import com.auro.scholr.teacher.data.model.response.MyClassRoomStudentResModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ImageUtil;
import com.auro.scholr.util.TextUtil;

import java.util.ArrayList;
import java.util.List;


public class MyClassroomAdapter extends RecyclerView.Adapter<MyClassroomAdapter.ViewHolder> {

    List<MyClassRoomStudentResModel> mValues;
    Context mContext;
    StudentClassItemLayoutBinding binding;
    CommonCallBackListner commonCallBackListner;
    boolean progressStatus;

    public MyClassroomAdapter(Context context, List<MyClassRoomStudentResModel> values, CommonCallBackListner commonCallBackListner) {
        this.commonCallBackListner = commonCallBackListner;
        mValues = values;
        mContext = context;
    }

    public void updateList(List<MyClassRoomStudentResModel> values) {
        mValues = values;
        notifyDataSetChanged();
    }

    public void updateProgressValue(boolean progressStatus) {
        this.progressStatus = progressStatus;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        StudentClassItemLayoutBinding binding;

        public ViewHolder(StudentClassItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(List<MyClassRoomStudentResModel> mValues,MyClassRoomStudentResModel model, int position) {

            if(mValues.size()-1 == position){
                binding.viewLine.setVisibility(View.GONE);
            }else{
                binding.viewLine.setVisibility(View.VISIBLE);
            }
            if (model != null && !TextUtil.isEmpty(model.getSudentName())) {
                binding.nameText.setText(model.getSudentName());
            } else {
                binding.nameText.setVisibility(View.GONE);
            }

            if (model != null && !TextUtil.isEmpty(model.getSudentMobile())) {
                binding.mobileText.setText(model.getSudentMobile());
            }else
            {
                binding.mobileText.setVisibility(View.GONE);
            }


            if (model != null && !TextUtil.isEmpty(model.getTotalScore())) {
                getSpannableString(binding.scoreText, model.getTotalScore());
            } else {
                getSpannableString(binding.scoreText, "0");
            }
            if (model != null && !TextUtil.isEmpty(model.getStudentPhoto())) {
                ImageUtil.loadCircleImage(binding.profileImage, model.getStudentPhoto());
            }
            binding.numberText.setText("" + (position + 1) + ".");

            binding.sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonCallBackListner != null) {
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(AppConstant.CLickType.SEND_MESSAGE_CLICK, Status.SEND_MESSAGE_CLICK, model));
                    }
                }
            });
        }

    }

    @Override
    public MyClassroomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.student_class_item_layout, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues,mValues.get(position), position);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public void getSpannableString(RPTextView textview, String score) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableStringBuilder span1 = new SpannableStringBuilder("Score : ");
        ForegroundColorSpan color1 = new ForegroundColorSpan(ContextCompat.getColor(AuroApp.getAppContext(), R.color.light_grey));
        span1.setSpan(color1, 0, span1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        builder.append(span1);

        SpannableStringBuilder span2 = new SpannableStringBuilder("" + score);
        ForegroundColorSpan color2 = new ForegroundColorSpan(AuroApp.getAppContext().getResources().getColor(R.color.color_red));
        span2.setSpan(color2, 0, span2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        span2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(span2);

        textview.setText(builder, TextView.BufferType.SPANNABLE);
    }
}
