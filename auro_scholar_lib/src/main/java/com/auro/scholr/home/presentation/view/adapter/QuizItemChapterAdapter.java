package com.auro.scholr.home.presentation.view.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.databinding.QuizItemLayout2Binding;
import com.auro.scholr.databinding.QuizLevelItemLayoutBinding;
import com.auro.scholr.home.data.model.newDashboardModel.ChapterResModel;
import com.auro.scholr.home.data.model.newDashboardModel.QuizTestDataModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class QuizItemChapterAdapter extends RecyclerView.Adapter<QuizItemChapterAdapter.ViewHolder> {

    List<ChapterResModel> list;
    Context mContext;


    public QuizItemChapterAdapter(Context context, List<ChapterResModel> list) {
        mContext = context;
        this.list = list;
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
        int progressCount = 1;

        public ViewHolder(QuizLevelItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(ChapterResModel model, int position) {
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
            binding.levelThree.txtScorePoints.setText("" + model.getTotalpoints());

            /*for level four*/
            binding.levelFour.txtTopic.setVisibility(View.GONE);
            binding.levelFour.layoutScore.setVisibility(View.GONE);
            if (model.getTotalpoints() >= 8) {
                binding.levelFour.retakeLayout.setVisibility(View.GONE);
            } else {
                binding.levelFour.retakeLayout.setVisibility(View.VISIBLE);
            }

            binding.levelFour.txtTopic.setVisibility(View.GONE);

            count = 0;
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
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
                    });
                }
            }, 0, 10);


        }

    }
}
