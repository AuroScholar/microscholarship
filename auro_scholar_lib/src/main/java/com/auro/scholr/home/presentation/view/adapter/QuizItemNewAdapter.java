package com.auro.scholr.home.presentation.view.adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.databinding.QuizItemLayout2Binding;
import com.auro.scholr.home.data.model.SubjectResModel;
import com.auro.scholr.home.data.model.newDashboardModel.QuizNewDashboardModel;
import com.auro.scholr.home.data.model.newDashboardModel.QuizTestDataModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class QuizItemNewAdapter extends RecyclerView.Adapter<QuizItemNewAdapter.ViewHolder> {

    List<SubjectResModel> list;
    Context mContext;
    CommonCallBackListner commonCallBackListner;

    public QuizItemNewAdapter(Context context, List<SubjectResModel> list, CommonCallBackListner commonCallBackListner) {
        mContext = context;
        this.list = list;
        this.commonCallBackListner = commonCallBackListner;
    }

    @NonNull
    @Override
    public QuizItemNewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        QuizItemLayout2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.quiz_item_layout_2, viewGroup, false);
        return new QuizItemNewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizItemNewAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        QuizItemLayout2Binding binding;
        int count = 0;

        public ViewHolder(QuizItemLayout2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(SubjectResModel model, int position) {
            binding.txtAmount.setText("" + model.getQuizWonAmount());
            binding.subjectTitle.setText(model.getSubject());
            binding.progressBar.setProgress(model.getQuizWonAmount());
            setChapterListAdapter(model);
        }

        private void setChapterListAdapter(SubjectResModel model) {
            binding.subjectsChapterRecyclerview.setLayoutManager(new LinearLayoutManager(AuroApp.getAppContext()));
            binding.subjectsChapterRecyclerview.setHasFixedSize(true);
            QuizItemChapterAdapter quizItemAdapter = new QuizItemChapterAdapter(mContext, model.getChapter(),commonCallBackListner,model.getSubject());
            binding.subjectsChapterRecyclerview.setAdapter(quizItemAdapter);
            binding.view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expand(binding.subjectsChapterRecyclerview, 1000);
                }
            });
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (count == model.getQuizWonAmount()) {
                        timer.cancel();
                    } else {
                        binding.progressBar.setProgress(count);
                        count++;

                    }
                }


            }, 0, 10);
        }

        public void expand(final View v, int duration) {
            final boolean expand = v.getVisibility() != View.VISIBLE;
            if (expand) {
                binding.downArrowIcon.setRotation(180);
            } else {
                binding.downArrowIcon.setRotation(0);
            }

            int prevHeight = v.getHeight();
            int height = 0;
            if (expand) {
                int measureSpecParams = View.MeasureSpec.getSize(View.MeasureSpec.UNSPECIFIED);
                v.measure(measureSpecParams, measureSpecParams);
                height = v.getMeasuredHeight();
            }

            ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, height);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    v.getLayoutParams().height = (int) animation.getAnimatedValue();
                    v.requestLayout();
                }
            });

            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (expand) {
                        v.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!expand) {
                        v.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.setDuration(duration);
            valueAnimator.start();
        }

    }
}
