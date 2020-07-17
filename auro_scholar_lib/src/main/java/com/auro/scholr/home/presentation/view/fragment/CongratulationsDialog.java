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
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.databinding.DialogCongratulations2Binding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.presentation.viewmodel.CongratulationsDialogViewModel;
import com.auro.scholr.util.AppUtil;
import com.bumptech.glide.Glide;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.List;
import java.util.Random;


import javax.inject.Inject;
import javax.inject.Named;

public class CongratulationsDialog extends BaseDialog implements View.OnClickListener {


    @Inject
    @Named("CongratulationsDialog")
    ViewModelFactory viewModelFactory;
    DialogCongratulations2Binding binding;
    CongratulationsDialogViewModel viewModel;
    Context mcontext;
    DashboardResModel dashboardResModel;
    AssignmentReqModel assignmentReqModel;
    CommonCallBackListner commonCallBackListner;


    private static final String TAG = CongratulationsDialog.class.getSimpleName();

    public CongratulationsDialog(Context mcontext, DashboardResModel dashboardResModel, AssignmentReqModel assignmentReqModel, CommonCallBackListner commonCallBackListner) {
        this.mcontext = mcontext;
        this.dashboardResModel = dashboardResModel;
        this.assignmentReqModel = assignmentReqModel;
        this.commonCallBackListner = commonCallBackListner;
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
        binding.btnShare.setOnClickListener(this);
        binding.icClose.setOnClickListener(this);
        binding.txtStartQuiz.setOnClickListener(this);
        binding.txtRetakeQuiz.setOnClickListener(this);


        Glide.with(this).load(R.raw.spracle).into(binding.backgroundSprincle);
        // create random object
        Random randomno = new Random();
        binding.tickerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.DOWN);
        binding.tickerView.setCharacterLists(TickerUtils.provideNumberList());
        for (int i = 1; i <= 50; i++) {
            binding.tickerView.setText(i + "%");
        }
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_congratulations_2;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnShare) {
            shareWithFriends();
        } else if (id == R.id.icClose) {
            dismiss();
        } else if (id == R.id.txtRetakeQuiz) {
        } else if (id == R.id.txtStartQuiz) {
            makeQuiz();
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

    private String generateChars(Random random, String list, int numDigits) {
        final char[] result = new char[numDigits];
        for (int i = 0; i < numDigits; i++) {
            result[i] = list.charAt(random.nextInt(list.length()));
        }
        return new String(result);
    }


    private void makeQuiz() {

        for (QuizResModel quizResModel : dashboardResModel.getQuiz()) {
            if (String.valueOf(quizResModel.getNumber()).equalsIgnoreCase(assignmentReqModel.getExam_name()) && quizResModel.getScorepoints() >= 8) {
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
            commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(0,Status.NEXT_QUIZ_CLICK,quizResModel));
        }
    }

}
