package com.auro.scholr.home.presentation.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.FriendsLeoboardListLayoutBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.ChallengeAccepResModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.FriendListResDataModel;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.data.model.SubjectResModel;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;
import com.auro.scholr.home.presentation.view.adapter.LeaderBoardAdapter;
import com.auro.scholr.home.presentation.viewmodel.FriendsLeaderShipViewModel;
import com.auro.scholr.teacher.data.model.request.SendInviteNotificationReqModel;
import com.auro.scholr.teacher.data.model.response.TeacherResModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.firebase.FirebaseEventUtil;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import static android.app.Activity.RESULT_OK;
import static com.auro.scholr.core.common.Status.ACCEPT_INVITE_CLICK;
import static com.auro.scholr.core.common.Status.AZURE_API;
import static com.auro.scholr.core.common.Status.DASHBOARD_API;
import static com.auro.scholr.core.common.Status.INVITE_FRIENDS_LIST;
import static com.auro.scholr.core.common.Status.SEND_INVITE_API;

public class FriendsLeaderBoardListFragment extends BaseFragment implements View.OnClickListener, CommonCallBackListner {

    @Inject
    @Named("FriendsLeaderBoardListFragment")
    ViewModelFactory viewModelFactory;

    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private static final String TAG = FriendsLeaderBoardListFragment.class.getSimpleName();
    FriendsLeoboardListLayoutBinding binding;
    FriendsLeaderShipViewModel viewModel;
    InviteFriendDialog mInviteBoxDialog;
    FriendListResDataModel resModel;

    LeaderBoardAdapter leaderBoardAdapter;
    boolean isFriendList = true;
    Resources resources;
    boolean isStateRestore;
    FirebaseEventUtil firebaseEventUtil;
    Map<String, String> logparam;
    FriendsLeaderBoardModel boardModel;
    DashboardResModel dashboardResModel;
    AssignmentReqModel assignmentReqModel;
    QuizResModel quizResModel;
    int itemPos;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FriendsLeaderShipViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }


    @Override
    protected void init() {

        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && TextUtil.isEmpty(prefModel.getUserLanguage())) {
            ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
        }
        firebaseEventUtil = new FirebaseEventUtil(getContext());
        logparam = new HashMap<>();

        setListener();

        setDataUi();

        viewModel.getDashBoardData(AuroApp.getAuroScholarModel());

    }

    private void setDataUi() {
        if (isFriendList) {
            binding.noFriendLayout.setVisibility(View.GONE);
            binding.boardListLayout.setVisibility(View.VISIBLE);

        } else {

            binding.boardListLayout.setVisibility(View.GONE);
            binding.noFriendLayout.setVisibility(View.VISIBLE);

        }



    }


    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.inviteNow.setOnClickListener(this);

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
                    if (responseApi.apiTypeStatus == INVITE_FRIENDS_LIST) {
                        handleProgress(0, "");
                    } else if (responseApi.apiTypeStatus == ACCEPT_INVITE_CLICK) {
                        updateData(true, true);
                    } else if (responseApi.apiTypeStatus == SEND_INVITE_API) {
                        updateData(true, true);
                    }

                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == INVITE_FRIENDS_LIST) {
                        resModel = (FriendListResDataModel) responseApi.data;
                        if (resModel.getError()) {
                            handleProgress(2, resModel.getMessage());
                        } else {
                            handleProgress(1, "");
                            setAdapter();
                        }
                    } else if (responseApi.apiTypeStatus == SEND_INVITE_API) {
                        TeacherResModel resModel = (TeacherResModel) responseApi.data;
                        updateData(false, resModel.getError());
                    } else if (responseApi.apiTypeStatus == ACCEPT_INVITE_CLICK) {
                        ChallengeAccepResModel accepResModel = (ChallengeAccepResModel) responseApi.data;
                        updateData(false, accepResModel.getError());
                        sendToNextQuiz();
                    } else if (responseApi.apiTypeStatus == DASHBOARD_API) {
                        dashboardResModel = (DashboardResModel) responseApi.data;
                    } else if (responseApi.apiTypeStatus == AZURE_API) {

                    }
                    break;


                case NO_INTERNET:
                case FAIL_400:
                    if (responseApi.apiTypeStatus == INVITE_FRIENDS_LIST) {
                        handleProgress(3, (String) responseApi.data);
                    } else if (responseApi.apiTypeStatus == ACCEPT_INVITE_CLICK) {
                        updateData(false, true);
                    } else if (responseApi.apiTypeStatus == SEND_INVITE_API) {
                        updateData(false, true);
                    } else if (responseApi.apiTypeStatus == AZURE_API) {
                        setImageInPref(assignmentReqModel);
                    }
                    showSnackbarError((String) responseApi.data);
                    break;

                default:
                    if (responseApi.apiTypeStatus == INVITE_FRIENDS_LIST) {
                        handleProgress(3, (String) responseApi.data);
                    } else if (responseApi.apiTypeStatus == ACCEPT_INVITE_CLICK) {
                        updateData(false, true);
                    } else if (responseApi.apiTypeStatus == SEND_INVITE_API) {
                        updateData(false, true);
                    } else if (responseApi.apiTypeStatus == AZURE_API) {
                        setImageInPref(assignmentReqModel);
                    }
                    showSnackbarError((String) responseApi.data);
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
                binding.errorConstraint.setVisibility(View.GONE);
                binding.errorLayout.errorIcon.setVisibility(View.GONE);
                binding.errorLayout.btRetry.setVisibility(View.GONE);
                binding.errorLayout.textError.setText(msg);
                binding.noFriendLayout.setVisibility(View.VISIBLE);
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
        return R.layout.friends_leoboard_list_layout;
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
        if (!TextUtil.checkListIsEmpty(resModel.getFriends())) {
            for (FriendsLeaderBoardModel model : resModel.getFriends()) {
                model.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_TYPE);
            }
            binding.friendsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.friendsList.setHasFixedSize(true);
            leaderBoardAdapter = new LeaderBoardAdapter(resModel.getFriends(), this);
            binding.friendsList.setAdapter(leaderBoardAdapter);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setKeyListner();
        viewModel.getFriendsListData();
    }


    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.back_arrow) {
//            getActivity().getSupportFragmentManager().popBackStack();
//            openFragment(new QuizHomeFragment());
//        } else if (v.getId() == R.id.invite_button) {
//            logparam.put(getResources().getString(R.string.log_invite_button), "true");
//            firebaseEventUtil.logEvent(getResources().getString(R.string.log_friend_leader_board_student), logparam);
//            openShareDefaultDialog();
//           /* mInviteBoxDialog = new InviteFriendDialog(getContext());
//            openFragmentDialog(mInviteBoxDialog);*/
//        }else if (v.getId() == R.id.invite_now) {
//            logparam.put(getResources().getString(R.string.log_invite_button), "true");
//            firebaseEventUtil.logEvent(getResources().getString(R.string.log_friend_leader_board_student), logparam);
//            openShareDefaultDialog();
//        }
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


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        itemPos = commonDataModel.getSource();
        boardModel = (FriendsLeaderBoardModel) commonDataModel.getObject();
        switch (commonDataModel.getClickType()) {
            case SEND_INVITE_CLICK:
                callSendInviteApi(boardModel);
                break;

            case ACCEPT_INVITE_CLICK:
                acceptChallengeApi();
                break;
        }
    }

    private void callSendInviteApi(FriendsLeaderBoardModel model) {
        if (!TextUtil.isEmpty(model.getMobileNo())) {
            SendInviteNotificationReqModel reqModel = new SendInviteNotificationReqModel();
            reqModel.setReceiver_mobile_no(model.getMobileNo());
            reqModel.setSender_mobile_no(AuroApp.getAuroScholarModel().getMobileNumber());
            reqModel.setNotification_title("Challenged You");
            String msg = getString(R.string.challenge_msg);
            if (!TextUtil.isEmpty(model.getStudentName())) {
                msg = model.getStudentName() + " " + msg;
            }
            reqModel.setNotification_message(msg);
            viewModel.sendInviteNotificationApi(reqModel);
        }
    }

    private void acceptChallengeApi() {
        if (!TextUtil.isEmpty(boardModel.getMobileNo())) {
            SendInviteNotificationReqModel reqModel = new SendInviteNotificationReqModel();
            reqModel.setReceiver_mobile_no(AuroApp.getAuroScholarModel().getMobileNumber());
            reqModel.setSender_mobile_no(boardModel.getMobileNo());
            viewModel.acceptChalange(reqModel);
        }
    }

    private void updateData(boolean status, boolean sent) {
        if (resModel != null && !TextUtil.checkListIsEmpty(resModel.getFriends())) {
            resModel.getFriends().get(itemPos).setProgress(status);
            if (!sent) {
                if (boardModel.isChallengedYou()) {
                    resModel.getFriends().get(itemPos).setSentText(AuroApp.getAppContext().getString(R.string.accept));
                } else {
                    resModel.getFriends().get(itemPos).setSentText(AuroApp.getAppContext().getString(R.string.challenge));
                }
                resModel.getFriends().get(itemPos).setSent(true);
            }
            leaderBoardAdapter.setDataList(resModel.getFriends());
        }
    }


    private void setKeyListner() {
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });
    }


    private void sendToNextQuiz() {
        if (dashboardResModel == null) {
            return;
        }
        if (!viewModel.homeUseCase.checkAllQuizAreFinishedOrNot(dashboardResModel)) {
            for (int i = 0; i < dashboardResModel.getSubjectResModelList().size(); i++) {
                SubjectResModel subjectResModel = dashboardResModel.getSubjectResModelList().get(i);
                for (QuizResModel quizResModel : subjectResModel.getChapter()) {
                    if (quizResModel.getAttempt() < 3) {
                        quizResModel.setSubjectPos(i);
                        this.quizResModel = quizResModel;
                    }
                    break;
                }
            }
        } else {
            openAlerDialog();
        }
        if (quizResModel != null) {
            askPermission();
        }
    }


    public void setImageInPref(AssignmentReqModel assignmentReqModel) {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && prefModel.getListAzureImageList() != null) {
            prefModel.getListAzureImageList().add(assignmentReqModel);
            AppPref.INSTANCE.setPref(prefModel);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    String path = data.getStringExtra(AppConstant.PROFILE_IMAGE_PATH);
                    azureImage(path);
                    openQuizTestFragment(dashboardResModel);
                    logparam.put(getResources().getString(R.string.log_start_quiz), "true");
                    firebaseEventUtil.logEvent(getResources().getString(R.string.log_quiz_home_fragment_student), logparam);
                    // loadImageFromStorage(path);
                } catch (Exception e) {

                }

            } else {

            }
        }
    }

    private void azureImage(String path) {
        try {
            AppLogger.d(TAG, "Image Path" + path);
            assignmentReqModel = viewModel.homeUseCase.getAssignmentRequestModel(dashboardResModel, quizResModel);
            assignmentReqModel.setEklavvya_exam_id("");
            assignmentReqModel.setSubject(quizResModel.getSubjectName());
            Bitmap picBitmap = BitmapFactory.decodeFile(path);
            byte[] bytes = AppUtil.encodeToBase64(picBitmap, 100);
            long mb = AppUtil.bytesIntoHumanReadable(bytes.length);
            if (mb > 1.5) {
                assignmentReqModel.setImageBytes(AppUtil.encodeToBase64(picBitmap, 50));
            } else {
                assignmentReqModel.setImageBytes(bytes);
            }

            viewModel.getAzureRequestData(assignmentReqModel);
        } catch (Exception e) {
            /*Do code here when error occur*/
        }
    }

    public void openQuizTestFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        QuizTestFragment quizTestFragment = new QuizTestFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        bundle.putParcelable(AppConstant.QUIZ_RES_MODEL, quizResModel);
        quizTestFragment.setArguments(bundle);
        openFragment(quizTestFragment);
    }


    private void askPermission() {
        String rationale = getString(R.string.permission_error_msg);
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mCameraPermissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {

                //   openQuizTestFragment(dashboardResModel);
                openCameraPhotoFragment();

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                ViewUtil.showSnackBar(binding.getRoot(), rationale);
            }
        });
    }

    public void openCameraPhotoFragment() {
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, AppConstant.CAMERA_REQUEST_CODE);
    }

    public void openAlerDialog() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                getActivity());

// Setting Dialog Title
        alertDialog2.setTitle("Info");

// Setting Dialog Message
        alertDialog2.setMessage("You have taken all your quizzes for the month. \nPractise more and come back next month.");

// Setting Icon to Dialog
        // alertDialog2.setIcon(R.drawable.);
        alertDialog2.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        // ViewUtil.showToast("You clicked on NO");
                        dialog.cancel();
                    }
                });

// Showing Alert Dialog
        alertDialog2.show();
    }

}

