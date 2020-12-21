package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseDialog;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.DialogLessScoreCongratulationsBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.data.model.SubjectResModel;
import com.auro.scholr.home.presentation.viewmodel.CongratulationsDialogViewModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.google.android.material.shape.CornerFamily;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import javax.inject.Inject;
import javax.inject.Named;

public class ConsgratuationLessScoreDialog extends BaseDialog implements View.OnClickListener {


    @Inject
    @Named("CongratulationsDialog")
    ViewModelFactory viewModelFactory;
    DialogLessScoreCongratulationsBinding binding;
    CongratulationsDialogViewModel viewModel;
    Context mcontext;
    CommonCallBackListner commonCallBackListner;
    DashboardResModel dashboardResModel;
    AssignmentReqModel assignmentReqModel;
    int marks;
    int finishedTestPos;
    SubjectResModel subjectResModel;
    QuizResModel quizResModel;


    private static final String TAG = ConsgratuationLessScoreDialog.class.getSimpleName();

    public ConsgratuationLessScoreDialog(Context mcontext, CommonCallBackListner commonCallBackListner, DashboardResModel dashboardResModel, AssignmentReqModel assignmentReqModel) {
        this.mcontext = mcontext;
        this.commonCallBackListner = commonCallBackListner;
        this.dashboardResModel = dashboardResModel;
        this.assignmentReqModel = assignmentReqModel;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CongratulationsDialogViewModel.class);
        binding.setLifecycleOwner(this);
        init();
        setListener();
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }

    @Override
    protected void init() {
        setListener();
        binding.tickerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.DOWN);
        float radius = getResources().getDimension(R.dimen._10sdp);
        binding.imgtryagain.setShapeAppearanceModel(binding.imgtryagain.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                .build());
        binding.tickerView.setCharacterLists(TickerUtils.provideNumberList());
        subjectResModel = dashboardResModel.getSubjectResModelList().get(assignmentReqModel.getSubjectPos());
        finishedTestPos = ConversionUtil.INSTANCE.convertStringToInteger(assignmentReqModel.getExam_name());
        quizResModel = subjectResModel.getChapter().get(finishedTestPos - 1);
        marks = quizResModel.getScorepoints() * 10;
        for (int i = 0; i <= marks; i++) {
            binding.tickerView.setText(i + "%");
        }

        if (ConversionUtil.INSTANCE.convertStringToInteger(assignmentReqModel.getQuiz_attempt()) < 3) {
            binding.txtRetakeQuiz.setVisibility(View.VISIBLE);
            binding.btntutor.setVisibility(View.GONE);
        } else {
            binding.txtRetakeQuiz.setVisibility(View.GONE);
            binding.btntutor.setVisibility(View.GONE);
        }

        if (checkAllQuizAreFinishedOrNot()) {
            binding.txtStartQuiz.setVisibility(View.VISIBLE);
            binding.txtRetakeQuiz.setVisibility(View.GONE);
            binding.btntutor.setVisibility(View.GONE);
        }

        binding.btnShare.setVisibility(View.VISIBLE);


        if (!TextUtil.isEmpty(dashboardResModel.getLeadQualified()) && dashboardResModel.getLeadQualified().equalsIgnoreCase(AppConstant.DocumentType.YES)) {
            binding.btntutor.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.icClose.setOnClickListener(this);
        binding.txtStartQuiz.setOnClickListener(this);
        binding.txtRetakeQuiz.setOnClickListener(this);
        binding.btntutor.setOnClickListener(this);
        binding.btnShare.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_less_score_congratulations;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnShare) {
            shareWithFriends();
            dismiss();
        } else if (id == R.id.icClose) {
            dismiss();
        } else if (id == R.id.txtRetakeQuiz) {
            sendClickCallBack(subjectResModel.getChapter().get(finishedTestPos - 1));
            dismiss();
        } else if (id == R.id.txtStartQuiz) {
            makeQuiz();
            dismiss();
        } else if (id == R.id.btntutor) {
            if (AuroApp.getAuroScholarModel() != null && AuroApp.getAuroScholarModel().getSdkcallback() != null) {
                AuroApp.getAuroScholarModel().getSdkcallback().commonCallback(Status.BOOK_TUTOR_SESSION_CLICK, "");
            }
        }

    }

    public void shareWithFriends() {
        String completeLink = AuroApp.getAppContext().getResources().getString(R.string.invite_friend_refrral);
        if (AuroApp.getAuroScholarModel() != null && !TextUtil.isEmpty(AuroApp.getAuroScholarModel().getReferralLink())) {
            completeLink = completeLink + " " + AuroApp.getAuroScholarModel().getReferralLink();
        } else {
            completeLink = completeLink + " https://rb.gy/np9uh5";
        }
        
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,completeLink);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        dismiss();
        mcontext.startActivity(shareIntent);
    }

    private void makeQuiz() {
        int lastPos = finishedTestPos - 1;
        for (int i = 0; i < subjectResModel.getChapter().size(); i++) {
            if (i != lastPos) {
                if (subjectResModel.getChapter().get(i).getAttempt() < 3) {
                    sendClickCallBack(subjectResModel.getChapter().get(i));
                    break;
                }
            }
        }
    }

    private boolean checkAllQuizAreFinishedOrNot() {
        int totalAttempt = 0;
        for (QuizResModel quizResModel : subjectResModel.getChapter()) {
            totalAttempt = quizResModel.getAttempt() + totalAttempt;
        }
        if (totalAttempt == 12) {
            return true;
        }
        return false;
    }

    private void sendClickCallBack(QuizResModel quizResModel) {
        if (commonCallBackListner != null) {
            commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(0, Status.NEXT_QUIZ_CLICK, quizResModel));
        }
    }

}
