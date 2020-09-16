package com.auro.scholr.home.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.home.data.model.FriendRequestList;
import com.auro.scholr.home.presentation.view.adapter.FriendRequestListAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class FriendRequestListDialogFragment extends BottomSheetDialogFragment implements CommonCallBackListner {

    FriendRequestList friendRequestList;
    FriendRequestListAdapter friendRequestListAdapter;


    public FriendRequestListDialogFragment(FriendRequestList friendRequestList) {
        this.friendRequestList = friendRequestList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        friendRequestListAdapter = new FriendRequestListAdapter(getActivity(),friendRequestList.getFriends(),friendRequestList.getFriends().size(),this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_list_dialog, container, false);

    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {

    }
}
