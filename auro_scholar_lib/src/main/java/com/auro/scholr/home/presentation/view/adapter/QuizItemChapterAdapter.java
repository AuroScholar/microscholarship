package com.auro.scholr.home.presentation.view.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.QuizItemLayout2Binding;
import com.auro.scholr.databinding.QuizLevelItemLayoutBinding;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.data.model.newDashboardModel.ChapterResModel;
import com.auro.scholr.home.data.model.newDashboardModel.QuizTestDataModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class QuizItemChapterAdapter extends RecyclerView.Adapter<QuizItemChapterAdapter.ViewHolder> {

    List<QuizResModel> list;
    Context mContext;
    CommonCallBackListner commonCallBackListner;
    int subjectPos;


    public QuizItemChapterAdapter(Context context, List<QuizResModel> list, CommonCallBackListner commonCallBackListner, int subjectPos) {
        mContext = context;
        this.list = list;
        this.commonCallBackListner = commonCallBackListner;
        this.subjectPos = subjectPos;
    }

    @NonNull
    @Override
    public QuizItemChapterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        QuizLevelItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.quiz_level_item_layout, viewGroup, false);
        return new QuizItemChapterAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizItemChapterAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        QuizLevelItemLayoutBinding binding;
        int count = 0;
        int progressCount = 0;

        public ViewHolder(QuizLevelItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(QuizResModel model, int position) {
            AppLogger.e("chhonker", "subject name-" + model.getSubjectName() + "-Pos-" + position + "-attmept-" + model.getAttempt() + "-chaptername-" + model.getName());
            setClickListner(binding, position);
            if (position == 0) {
                binding.walletIcon.setVisibility(View.VISIBLE);
            } else {
                binding.walletIcon.setVisibility(View.GONE);
            }
            /*for level two*/
            binding.levelTwo.layoutScore.setVisibility(View.GONE);
            binding.levelTwo.retakeLayout.setVisibility(View.GONE);
            binding.levelTwo.txtTopic.setText(model.getName());
            binding.levelTwo.txtTopic.setVisibility(View.VISIBLE);

            /*for level three*/
            binding.levelThree.txtTopic.setVisibility(View.GONE);
            binding.levelThree.layoutScore.setVisibility(View.VISIBLE);
            binding.levelThree.retakeLayout.setVisibility(View.GONE);
            binding.levelThree.txtTopic.setVisibility(View.GONE);

            if (model.getScorepoints() < 10) {
                binding.levelThree.txtScorePoints.setText("0" + model.getScorepoints());
            } else {
                binding.levelThree.txtScorePoints.setText("" + model.getScorepoints());
            }

            /*for level four*/
            binding.levelFour.txtTopic.setVisibility(View.GONE);
            binding.levelFour.layoutScore.setVisibility(View.GONE);
            if (model.getScorepoints() >= 0  && model.getAttempt()>0) {
                binding.levelFour.retakeLayout.setVisibility(View.VISIBLE);
                binding.levelFour.nextQuizLayout.setVisibility(View.GONE);
            } else {
                binding.levelFour.retakeLayout.setVisibility(View.GONE);
                binding.levelFour.nextQuizLayout.setVisibility(View.VISIBLE);
            }

            binding.levelFour.txtTopic.setVisibility(View.GONE);
            count = 0;

            if (model.getAttempt() == 1) {
                binding.levelTwo.pb.setProgress(100);
                binding.levelTwo.circleImage.setColorFilter(mContext.getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_IN);
            } else {
                binding.levelTwo.pb.setProgress(0);
                binding.levelTwo.circleImage.setColorFilter(mContext.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_IN);

            }

            if (model.getAttempt() == 2) {
                binding.levelTwo.pb.setProgress(100);
                binding.levelThree.pb.setProgress(100);
                binding.levelTwo.circleImage.setColorFilter(mContext.getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_IN);
                binding.levelThree.circleImage.setColorFilter(mContext.getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_IN);
            } else {
                binding.levelThree.pb.setProgress(0);
                binding.levelThree.circleImage.setColorFilter(mContext.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_IN);
            }

            if (model.getAttempt() == 3) {
                binding.levelTwo.pb.setProgress(100);
                binding.levelThree.pb.setProgress(100);
                binding.levelFour.pb.setProgress(100);
                binding.levelFour.retakeLayout.setVisibility(View.GONE);
                binding.levelFour.nextQuizLayout.setVisibility(View.GONE);
                binding.levelTwo.circleImage.setColorFilter(mContext.getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_IN);
                binding.levelThree.circleImage.setColorFilter(mContext.getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_IN);
                binding.levelFour.circleImage.setColorFilter(mContext.getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_IN);

            } else {
                binding.levelFour.pb.setProgress(0);
            }
            binding.childLayout.setAlpha(1);
            binding.icLockImg.setVisibility(View.GONE);
            for (int i = 1; i < list.size(); i++) {
                if (position == i && (list.get(i - 1).getAttempt() == 0)) {
                    binding.childLayout.setAlpha(0.25f);
                    binding.icLockImg.setVisibility(View.VISIBLE);
                    binding.levelFour.nextQuizLayout.setVisibility(View.GONE);
                } else {
                    binding.viewLeftTwo.setVisibility(View.GONE);
                }

            }

          /*
           if (position == 1 && list.get(0).getAttempt() == 0) {
                    binding.childLayout.setAlpha(0.25f);
                    binding.icLockImg.setVisibility(View.VISIBLE);
                    binding.levelFour.nextQuizLayout.setVisibility(View.GONE);
                } else if (position == 2 && list.get(1).getAttempt() == 0) {
                    binding.childLayout.setAlpha(0.25f);
                    binding.icLockImg.setVisibility(View.VISIBLE);
                    binding.levelFour.nextQuizLayout.setVisibility(View.GONE);
                }
                if (list.size() - 1 == position) {
                    binding.viewLeftTwo.setVisibility(View.GONE);
                }

          Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            progressHandling(timer, model);
                        }
                    });
                }
            }, 0, 10);*/

        }

        private void progressHandling(Timer timer, QuizResModel model) {
            if (count == model.getAttempt()) {
                timer.cancel();
            } else {
                if (progressCount == 100) {
                    if (count == 0) {
                        binding.levelTwo.circleImage.setColorFilter(AuroApp.getAppContext().getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_IN);
                    } else if (count == 1) {
                        binding.levelThree.circleImage.setColorFilter(AuroApp.getAppContext().getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_IN);
                    } else if (count == 2) {
                        binding.levelFour.circleImage.setColorFilter(AuroApp.getAppContext().getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_IN);
                    }
                    count++;
                    progressCount = 0;
                } else {
                    progressCount++;
                    if (count == 0) {
                        binding.levelTwo.pb.setProgress(progressCount);
                    } else if (count == 1) {
                        binding.levelThree.pb.setProgress(progressCount);
                    } else if (count == 2) {
                        binding.levelFour.pb.setProgress(progressCount);
                    }
                }
            }
        }

        private void setClickListner(QuizLevelItemLayoutBinding binding, int position) {
            binding.levelFour.nextQuizLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonCallBackListner != null) {
                        // list.get(position).setSubjectPos(subjectPos);
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.START_QUIZ_BUTON, list.get(position)));
                    }
                }
            });

            binding.levelFour.retakeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonCallBackListner != null) {
                        // list.get(position).setSubjectPos(subjectPos);
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.START_QUIZ_BUTON, list.get(position)));
                    }
                }
            });
        }

    }

}
