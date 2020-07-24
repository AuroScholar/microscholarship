package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.auro.scholr.databinding.DialogCongratulations2Binding;
import com.auro.scholr.databinding.DialogLessScoreCongratulationsBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.presentation.viewmodel.CongratulationsDialogViewModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.TextUtil;
import com.bumptech.glide.Glide;
import com.google.android.material.shape.CornerFamily;
import com.google.gson.Gson;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.Random;

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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CongratulationsDialogViewModel.class);
        binding.setLifecycleOwner(this);
        init();
        setListener();
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
        finishedTestPos = ConversionUtil.INSTANCE.convertStringToInteger(assignmentReqModel.getExam_name());
        marks = dashboardResModel.getQuiz().get(finishedTestPos - 1).getScorepoints();
        marks = marks * 10;
        for (int i = 1; i <= marks; i++) {
            binding.tickerView.setText(i + "%");
        }

        if (ConversionUtil.INSTANCE.convertStringToInteger(assignmentReqModel.getQuiz_attempt()) < 3) {
            binding.txtRetakeQuiz.setVisibility(View.VISIBLE);
            binding.btntutor.setVisibility(View.GONE);
        } else {
            binding.txtRetakeQuiz.setVisibility(View.GONE);
            binding.btntutor.setVisibility(View.GONE);
        }

       /* if (checkAllQuizAreFinishedOrNot()) {
            binding.btnShare.setVisibility(View.VISIBLE);
        }*/

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
            sendClickCallBack(dashboardResModel.getQuiz().get(finishedTestPos - 1));
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
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mcontext.getResources().getString(R.string.share_msg));
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        dismiss();
        mcontext.startActivity(shareIntent);
    }

    private void makeQuiz() {
        for (QuizResModel quizResModel : dashboardResModel.getQuiz()) {
            if (String.valueOf(quizResModel.getNumber()).equalsIgnoreCase(assignmentReqModel.getExam_name()) && quizResModel.getScorepoints() >= 1) {
                {
                    if (!checkAllQuizAreFinishedOrNot()) {
                        if (quizResModel.getNumber() == 3) {
                            if (dashboardResModel.getQuiz().get(0).getAttempt() < 3) {
                                //go to the quiz one
                                sendClickCallBack(dashboardResModel.getQuiz().get(0));
                            } else if (dashboardResModel.getQuiz().get(1).getAttempt() < 3) {
                                //go to the quiz two
                                sendClickCallBack(dashboardResModel.getQuiz().get(1));
                            }
                        } else if (quizResModel.getNumber() == 2) {
                            if (dashboardResModel.getQuiz().get(2).getAttempt() < 3) {
                                //go to the quiz three
                                sendClickCallBack(dashboardResModel.getQuiz().get(2));
                            } else if (dashboardResModel.getQuiz().get(0).getAttempt() < 3) {
                                //go to the quiz one
                                sendClickCallBack(dashboardResModel.getQuiz().get(0));
                            }

                        } else if (quizResModel.getNumber() == 1) {
                            if (dashboardResModel.getQuiz().get(1).getAttempt() < 3) {
                                //go to the quiz Two
                                sendClickCallBack(dashboardResModel.getQuiz().get(1));
                            } else if (dashboardResModel.getQuiz().get(2).getAttempt() < 3) {
                                //go to the quiz three
                                sendClickCallBack(dashboardResModel.getQuiz().get(2));
                            }
                        }

                    }

                }
            }

        }
    }


    private boolean checkAllQuizAreFinishedOrNot() {
        int totalAttempt = 0;
        for (QuizResModel quizResModel : dashboardResModel.getQuiz()) {
            totalAttempt = quizResModel.getAttempt() + totalAttempt;
        }
        if (totalAttempt == 9) {
            return true;
        } else {
            return false;
        }
    }

    private void sendClickCallBack(QuizResModel quizResModel) {
        if (commonCallBackListner != null) {
            commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(0, Status.NEXT_QUIZ_CLICK, quizResModel));
        }
    }


}
