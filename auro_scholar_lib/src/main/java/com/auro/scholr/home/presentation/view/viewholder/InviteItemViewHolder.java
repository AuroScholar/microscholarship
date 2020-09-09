package com.auro.scholr.home.presentation.view.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.databinding.FriendsInviteItemLayoutBinding;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class InviteItemViewHolder extends RecyclerView.ViewHolder {
    FriendsInviteItemLayoutBinding binding;

    public InviteItemViewHolder(@NonNull FriendsInviteItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    public void bindUser(FriendsLeaderBoardModel model,int position) {
        int reminder = position % 2;


        if (reminder == 0) {
            binding.parentLayout.setBackgroundColor(binding.parentLayout.getContext().getResources().getColor(R.color.white));
            binding.checkbox.setChecked(true);
        } else {
            binding.checkbox.setChecked(false);
            binding.parentLayout.setBackgroundColor(binding.parentLayout.getContext().getResources().getColor(R.color.item_bg_color));
        }
        binding.nameText.setText(model.getStudentName());
        Glide.with(binding.profileImage.getContext())
                .load((String) model.getImagePath())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20))
                        .dontAnimate()
                        .priority(Priority.IMMEDIATE)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(binding.profileImage);


    }
}
