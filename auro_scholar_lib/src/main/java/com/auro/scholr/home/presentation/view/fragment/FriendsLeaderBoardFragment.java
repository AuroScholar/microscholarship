package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.FriendsLeoboardLayoutBinding;
import com.auro.scholr.home.data.model.FriendListResDataModel;
import com.auro.scholr.home.presentation.view.adapter.LeaderBoardAdapter;
import com.auro.scholr.home.presentation.viewmodel.FriendsLeaderShipViewModel;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.google.firebase.abt.component.AbtRegistrar;

import javax.inject.Inject;
import javax.inject.Named;


import androidx.fragment.app.FragmentTransaction;

import static com.auro.scholr.core.common.Status.INVITE_FRIENDS_LIST;


public class FriendsLeaderBoardFragment extends BaseFragment implements View.OnClickListener {

    @Inject
    @Named("FriendsLeaderBoardFragment")
    ViewModelFactory viewModelFactory;

    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private static final String TAG = FriendsLeaderBoardFragment.class.getSimpleName();
    FriendsLeoboardLayoutBinding binding;
    FriendsLeaderShipViewModel viewModel;
    InviteFriendDialog mInviteBoxDialog;

    LeaderBoardAdapter leaderBoardAdapter;
    boolean isFriendList = true;
    Resources resources;
    boolean isStateRestore;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FriendsLeaderShipViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);
        return binding.getRoot();
    }


    @Override
    protected void init() {
        setListener();
        binding.headerTopParent.cambridgeHeading.setVisibility(View.GONE);
        setDataUi();

        if (!isStateRestore) {
            viewModel.getFriendsListData();
        }

    }

    private void setDataUi() {
        if (isFriendList) {
            binding.noFriendLayout.setVisibility(View.GONE);
            binding.friendBoardBg.setBackgroundColor(AuroApp.getAppContext().getResources().getColor(R.color.blue_color));
            binding.friendBgImgLayout.setBackground(AuroApp.getAppContext().getResources().getDrawable(R.drawable.friend_background));
            binding.boardListLayout.setVisibility(View.VISIBLE);
            binding.friendsBoardText.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.white));

        } else {
            binding.friendBoardBg.setBackgroundColor(AuroApp.getAppContext().getResources().getColor(R.color.transparent));
            binding.friendBgImgLayout.setBackground(null);
            binding.boardListLayout.setVisibility(View.GONE);
            binding.noFriendLayout.setVisibility(View.VISIBLE);
            binding.friendsBoardText.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.black));
        }


        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel.getUserLanguage().equalsIgnoreCase(AppConstant.LANGUAGE_EN)) {
            setLanguageText(AppConstant.HINDI);
        } else {
            setLanguageText(AppConstant.ENGLISH);
        }

    }


    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.headerParent.cambridgeHeading.setVisibility(View.GONE);
        binding.toolbarLayout.backArrow.setOnClickListener(this);
        binding.inviteButton.setOnClickListener(this);
        binding.toolbarLayout.langEng.setOnClickListener(this);

        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }
    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    //For ProgressBar
                    handleProgress(0, "");

                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == INVITE_FRIENDS_LIST) {

                        FriendListResDataModel model = (FriendListResDataModel) responseApi.data;
                        if (model.getError()) {
                            handleProgress(2, model.getMessage());
                        } else {
                            handleProgress(1, "");
                            setAdapter();
                        }
                    }
                    break;


                case NO_INTERNET:
                case FAIL_400:
                    handleProgress(3, (String) responseApi.data);
                    // showSnackbarError((String) responseApi.data);
                    break;

                default:
                    handleProgress(3, (String) responseApi.data);
                    //   showSnackbarError(getString(R.string.default_error));
                    break;
            }

        });
    }

    private void handleProgress(int i, String msg) {
        switch (i) {
            case 0:
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.errorConstraint.setVisibility(View.GONE);
                binding.noFriendLayout.setVisibility(View.GONE);
                binding.boardListLayout.setVisibility(View.GONE);
                break;

            case 1:
                binding.progressBar.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.GONE);
                binding.noFriendLayout.setVisibility(View.GONE);
                binding.boardListLayout.setVisibility(View.VISIBLE);
                break;

            case 2:
                binding.progressBar.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.VISIBLE);
                binding.errorLayout.errorIcon.setVisibility(View.GONE);
                binding.errorLayout.btRetry.setVisibility(View.GONE);
                binding.errorLayout.textError.setText(msg);
                binding.noFriendLayout.setVisibility(View.GONE);
                binding.boardListLayout.setVisibility(View.GONE);
                break;

            case 3:
                binding.progressBar.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.VISIBLE);
                binding.errorLayout.errorIcon.setVisibility(View.VISIBLE);
                binding.errorLayout.textError.setText(msg);
                binding.noFriendLayout.setVisibility(View.GONE);
                binding.boardListLayout.setVisibility(View.GONE);
                binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.getFriendsListData();
                    }
                });
                break;

        }

    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }


    @Override
    protected int getLayout() {
        return R.layout.friends_leoboard_layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            // dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);

        }
        init();
        setToolbar();
        setListener();
    }

    private void setAdapter() {
        binding.friendsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.friendsList.setHasFixedSize(true);
        leaderBoardAdapter = new LeaderBoardAdapter(viewModel.homeUseCase.makeListForFriendsLeaderBoard(true));
        binding.friendsList.setAdapter(leaderBoardAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_arrow) {
            getActivity().getSupportFragmentManager().popBackStack();
        } else if (v.getId() == R.id.invite_button) {
            openShareDefaultDialog();
           /* mInviteBoxDialog = new InviteFriendDialog(getContext());
            openFragmentDialog(mInviteBoxDialog);*/
        } else if (v.getId() == R.id.lang_eng) {
            String text = binding.toolbarLayout.langEng.getText().toString();
            if (!TextUtil.isEmpty(text) && text.equalsIgnoreCase(AppConstant.HINDI)) {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_HI);
                resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.ENGLISH);
            } else {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
                resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.HINDI);
            }
            reloadFragment();
        }
    }

    private void openFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, InviteFriendDialog.class.getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

    private void openFragmentDialog(Fragment fragment) {
        /* getActivity().getSupportFragmentManager().popBackStack();*/
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(AuroApp.getFragmentContainerUiId(), fragment, InviteFriendDialog.class.getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }


    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }


    private void openShareDefaultDialog() {
        String completeLink = AuroApp.getAppContext().getResources().getString(R.string.invite_friend_refrral);
        if (AuroApp.getAuroScholarModel() != null && !TextUtil.isEmpty(AuroApp.getAuroScholarModel().getReferralLink())) {
            completeLink = completeLink + " " + AuroApp.getAuroScholarModel().getReferralLink();
        } else {
            completeLink = completeLink + " https://bit.ly/3b1puWr";
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, completeLink);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        getActivity().startActivity(shareIntent);
    }

    private void setLanguageText(String text) {
        binding.toolbarLayout.langEng.setText(text);
    }

}
