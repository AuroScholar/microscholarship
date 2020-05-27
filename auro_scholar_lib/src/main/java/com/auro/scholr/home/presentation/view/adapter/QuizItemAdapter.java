package com.auro.scholr.home.presentation.view.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.QuizItemLayoutBinding;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.util.AppUtil;

import java.util.List;


public class QuizItemAdapter extends RecyclerView.Adapter<QuizItemAdapter.ViewHolder> {

    List<QuizResModel> mValues;
    Context mContext;
    CommonCallBackListner commonCallBackListner;

    public QuizItemAdapter(Context context, List<QuizResModel> values, CommonCallBackListner commonCallBackListner) {

        this.commonCallBackListner = commonCallBackListner;
        mValues = values;
        mContext = context;
    }

    public void updateList(List<QuizResModel> values) {
        mValues = values;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        QuizItemLayoutBinding binding;

        public ViewHolder(QuizItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void setData(QuizResModel quizResModel, int position) {
            binding.quizAmount.setText(quizResModel.getScholarshipamount());
            binding.quizNum.setText(AuroApp.getAppContext().getString(R.string.quiz) + " " + "0" + quizResModel.getNumber());
            binding.quizName.setText(quizResModel.getName());

            if (quizResModel.getScorepoints() == 0) {
                binding.retakeQuiz.setVisibility(View.INVISIBLE);
                binding.totalNoPoints.setText(AuroApp.getAppContext().getString(R.string.total_no_of_points) + " -/" + quizResModel.getTotalpoints());
            } else {
                binding.retakeQuiz.setVisibility(View.VISIBLE);
                binding.totalNoPoints.setText(AuroApp.getAppContext().getString(R.string.total_no_of_points) + quizResModel.getScorepoints() + "/" + quizResModel.getTotalpoints());
            }
            if (quizResModel.getStatus().equalsIgnoreCase(AppConstant.TRUE)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.quizMainLayout.setForeground(null);
                }
                binding.lockLayout.setVisibility(View.GONE);
                binding.quizButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (commonCallBackListner != null) {
                            commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.START_QUIZ_BUTON, quizResModel));
                        }
                    }
                });
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.quizMainLayout.setForeground(mContext.getDrawable(R.drawable.transparentbg));
                }
                binding.lockLayout.setVisibility(View.VISIBLE);
            }
            startAnimationQuizButton(binding);
            if (quizResModel.getAttempt() == 3 && quizResModel.getStatus().equalsIgnoreCase(AppConstant.FALSE)) {
                binding.quizAttemptLayout.setVisibility(View.VISIBLE);
                binding.lockLayout.setVisibility(View.GONE);
                binding.quizMainLayout.setVisibility(View.GONE);
            }


        }

    }

    private void startAnimationQuizButton(QuizItemLayoutBinding binding) {
        //Animation on button
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.fadein);

        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                startAnimationFadeOutButton(binding);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });
        binding.quizButton.startAnimation(anim);

    }


    private void startAnimationFadeOutButton(QuizItemLayoutBinding binding) {
        //Animation on button
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.fadeout);

        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                startAnimationQuizButton(binding);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });
        binding.quizButton.startAnimation(anim);
    }


    @Override
    public QuizItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        QuizItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.quiz_item_layout, viewGroup, false);
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
