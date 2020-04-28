package com.auro.scholr.home.presentation.view.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.databinding.FriendsBoardItemLayoutBinding;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class LeaderBoardItemViewHolder extends RecyclerView.ViewHolder {
    FriendsBoardItemLayoutBinding layoutBinding;

    public LeaderBoardItemViewHolder(@NonNull FriendsBoardItemLayoutBinding layoutBinding) {
        super(layoutBinding.getRoot());
        this.layoutBinding = layoutBinding;
    }


    public void bindUser(FriendsLeaderBoardModel model, int position) {
        int reminder = position % 2;
        if (reminder == 0) {
            layoutBinding.parentLayout.setBackgroundColor(layoutBinding.parentLayout.getContext().getColor(R.color.white));
        } else {
            layoutBinding.parentLayout.setBackgroundColor(layoutBinding.parentLayout.getContext().getColor(R.color.item_bg_color));
        }
        layoutBinding.nameText.setText(model.getStudentName());
        layoutBinding.scoreText.setText(model.getStudentScore());
        layoutBinding.scholarshipWonText.setText(model.getScholarshipWon());
        Glide.with(layoutBinding.profileImage.getContext())
                .load((String) model.getImagePath())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))
                        .dontAnimate()
                        .priority(Priority.IMMEDIATE)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(layoutBinding.profileImage);


    }
}
