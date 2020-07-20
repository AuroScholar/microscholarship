package com.auro.scholr.home.presentation.view.viewholder;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.util.uiwidget.RPTextView;
import com.auro.scholr.databinding.FriendsBoardItemLayoutBinding;
import com.auro.scholr.databinding.QuizItemLayoutBinding;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ImageUtil;
import com.auro.scholr.util.TextUtil;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.auro.scholr.core.application.AuroApp;

import java.util.List;

public class LeaderBoardItemViewHolder extends RecyclerView.ViewHolder {
    FriendsBoardItemLayoutBinding layoutBinding;
    Animation startAnimation;
    Animation endanimation;

    public LeaderBoardItemViewHolder(@NonNull FriendsBoardItemLayoutBinding layoutBinding) {
        super(layoutBinding.getRoot());
        this.layoutBinding = layoutBinding;
    }


    public void bindUser(FriendsLeaderBoardModel model, List<FriendsLeaderBoardModel> list, int position, CommonCallBackListner commonCallBackListner) {
        if (list.size() - 1 == position) {
            layoutBinding.viewLine.setVisibility(View.GONE);
        } else {
            layoutBinding.viewLine.setVisibility(View.VISIBLE);
        }

        if (model.isChallengedYou()) {
            if (!TextUtil.isEmpty(model.getSentText()) && model.getSentText().equalsIgnoreCase(AuroApp.getAppContext().getString(R.string.accept))) {
                layoutBinding.sentTxt.setText("Accepted");
            }
            layoutBinding.challengeText.setVisibility(View.VISIBLE);
            layoutBinding.parentLayout.setBackgroundColor(AuroApp.getAppContext().getResources().getColor(R.color.blue_color));
           // startAnimationQuizButton(layoutBinding.parentLayout);
            layoutBinding.inviteText.setText(AuroApp.getAppContext().getResources().getString(R.string.accept));
        } else {
            if (startAnimation != null) {
                startAnimation.cancel();
            }

            if (endanimation != null) {
                endanimation.cancel();
            }
            layoutBinding.challengeText.setVisibility(View.GONE);
            layoutBinding.inviteText.setText(AuroApp.getAppContext().getResources().getString(R.string.challenge));
            layoutBinding.parentLayout.setBackgroundColor(AuroApp.getAppContext().getResources().getColor(R.color.white));
        }

        if (!TextUtil.isEmpty(model.getStudentName())) {
            layoutBinding.nameText.setText(model.getStudentName());
        } else if (!TextUtil.isEmpty(model.getMobileNo())) {
            layoutBinding.nameText.setText(model.getMobileNo());
        } else {
            layoutBinding.nameText.setVisibility(View.GONE);
        }
        if (model.isProgress()) {
            layoutBinding.inviteButtonLayout.setVisibility(View.GONE);
            layoutBinding.progressBar.setVisibility(View.VISIBLE);
            layoutBinding.sentLayout.setVisibility(View.GONE);
        } else {
            if (model.isSent()) {
                layoutBinding.inviteButtonLayout.setVisibility(View.GONE);
                layoutBinding.progressBar.setVisibility(View.GONE);
                layoutBinding.sentLayout.setVisibility(View.VISIBLE);
            } else {
                layoutBinding.inviteButtonLayout.setVisibility(View.VISIBLE);
                layoutBinding.progressBar.setVisibility(View.GONE);
            }
        }

        if (!TextUtil.isEmpty(model.getStudentScore())) {
            getSpannableString(layoutBinding.scoreText, model.getStudentScore());
        } else {
            layoutBinding.scoreText.setVisibility(View.GONE);
        }
        ImageUtil.loadCircleImage(layoutBinding.profileImage, (String) model.getImagePath());
        layoutBinding.inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutBinding.inviteText.getText().toString().equalsIgnoreCase(AuroApp.getAppContext().getResources().getString(R.string.challenge))) {
                    if (commonCallBackListner != null) {
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.SEND_INVITE_CLICK, model));
                    }
                } else {
                    if (commonCallBackListner != null) {
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.ACCEPT_INVITE_CLICK, model));
                    }
                }
            }
        });

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

    private void startAnimationQuizButton(ConstraintLayout binding) {
        //Animation on button
        startAnimation = AnimationUtils.loadAnimation(AuroApp.getAppContext(), R.anim.fadein);

        startAnimation.setAnimationListener(new Animation.AnimationListener() {

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
        binding.startAnimation(startAnimation);

    }


    private void startAnimationFadeOutButton(ConstraintLayout binding) {
        //Animation on button
        endanimation = AnimationUtils.loadAnimation(AuroApp.getAppContext(), R.anim.fadeout);

        endanimation.setAnimationListener(new Animation.AnimationListener() {

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
        binding.startAnimation(endanimation);
    }


}
