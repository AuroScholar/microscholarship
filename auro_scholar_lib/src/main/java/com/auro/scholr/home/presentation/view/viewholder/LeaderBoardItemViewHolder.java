package com.auro.scholr.home.presentation.view.viewholder;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.util.uiwidget.RPTextView;
import com.auro.scholr.databinding.FriendsBoardItemLayoutBinding;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ImageUtil;
import com.auro.scholr.util.TextUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.databinding.FriendsBoardItemLayoutBinding;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class LeaderBoardItemViewHolder extends RecyclerView.ViewHolder {
    FriendsBoardItemLayoutBinding layoutBinding;

    public LeaderBoardItemViewHolder(@NonNull FriendsBoardItemLayoutBinding layoutBinding) {
        super(layoutBinding.getRoot());
        this.layoutBinding = layoutBinding;
    }


    public void bindUser(FriendsLeaderBoardModel model, int position, CommonCallBackListner commonCallBackListner) {
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
                if (commonCallBackListner != null) {
                    commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.SEND_INVITE_CLICK, model));
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
}
