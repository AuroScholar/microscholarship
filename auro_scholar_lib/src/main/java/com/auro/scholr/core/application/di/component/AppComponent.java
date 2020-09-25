package com.auro.scholr.core.application.di.component;

import android.app.Activity;

import com.auro.scholr.core.application.di.module.AppModule;
import com.auro.scholr.core.application.di.module.HomeModule;
import com.auro.scholr.core.application.di.module.PaymentModule;
import com.auro.scholr.core.application.di.module.TeacherModule;
import com.auro.scholr.core.application.di.module.UtilsModule;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.fragment.CongratulationsDialog;
import com.auro.scholr.home.presentation.view.fragment.ConsgratuationLessScoreDialog;
import com.auro.scholr.home.presentation.view.fragment.DemographicFragment;
import com.auro.scholr.home.presentation.view.fragment.FriendRequestListDialogFragment;
import com.auro.scholr.home.presentation.view.fragment.FriendsInviteBoardFragment;
import com.auro.scholr.home.presentation.view.fragment.FriendsLeaderBoardAddFragment;
import com.auro.scholr.home.presentation.view.fragment.FriendsLeaderBoardFragment;
import com.auro.scholr.home.presentation.view.fragment.FriendsLeaderBoardListFragment;
import com.auro.scholr.home.presentation.view.fragment.InviteFriendDialog;
import com.auro.scholr.home.presentation.view.fragment.KYCFragment;
import com.auro.scholr.home.presentation.view.fragment.KYCViewFragment;
import com.auro.scholr.home.presentation.view.fragment.PrivacyPolicyFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeNewFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizTestFragment;
import com.auro.scholr.home.presentation.view.fragment.ScholarShipFragment;
import com.auro.scholr.home.presentation.view.fragment.TransactionsFragment;
import com.auro.scholr.payment.presentation.view.fragment.BankFragment;
import com.auro.scholr.payment.presentation.view.fragment.PaytmFragment;
import com.auro.scholr.payment.presentation.view.fragment.SendMoneyFragment;
import com.auro.scholr.payment.presentation.view.fragment.UPIFragment;
import com.auro.scholr.teacher.presentation.view.fragment.MyClassroomFragment;
import com.auro.scholr.teacher.presentation.view.fragment.SelectYourAppointmentDialogFragment;
import com.auro.scholr.teacher.presentation.view.fragment.SelectYourMessageDialogFragment;
import com.auro.scholr.teacher.presentation.view.fragment.TeacherInfoFragment;
import com.auro.scholr.teacher.presentation.view.fragment.TeacherKycFragment;
import com.auro.scholr.teacher.presentation.view.fragment.TeacherProfileFragment;
import com.auro.scholr.teacher.presentation.view.fragment.TeacherSaveDetailFragment;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, UtilsModule.class, HomeModule.class, PaymentModule.class, TeacherModule.class,})
@Singleton
public interface AppComponent {

    void injectAppContext(Activity reciprociApp);


    void doInjection(HomeActivity homeActivity);

    void doInjection(KYCViewFragment kycViewFragment);

    void doInjection(MyClassroomFragment fragment);

    void doInjection(ScholarShipFragment scholarShipFragment);

    void doInjection(QuizHomeFragment quizHomeFragment);

    void doInjection(KYCFragment kycFragment);

    void doInjection(DemographicFragment kycFragment);

    void doInjection(QuizTestFragment quizTestFragment);

    void doInjection(SendMoneyFragment sendMoneyFragment);

    void doInjection(PaytmFragment paytmFragment);

    void doInjection(BankFragment bankFragment);

    void doInjection(UPIFragment bankFragment);


    void doInjection(PrivacyPolicyFragment privacyPolicyFragment);

    void doInjection(FriendsInviteBoardFragment friendsInviteBoardFragment);

    void doInjection(FriendsLeaderBoardFragment friendsLeaderBoardFragment);

    void doInjection(FriendsLeaderBoardAddFragment friendsLeaderBoardAddFragment);

    void doInjection(FriendsLeaderBoardListFragment friendsLeaderBoardListFragment);

    void doInjection(InviteFriendDialog inviteFriendDialog);

    void doInjection(CongratulationsDialog congratulationsDialog);

    void doInjection(TransactionsFragment transactionsFragment);

    void doInjection(TeacherKycFragment fragment);

    void doInjection(TeacherInfoFragment fragment);

    void doInjection(TeacherSaveDetailFragment fragment);

    void doInjection(SelectYourMessageDialogFragment dialog);

    void doInjection(TeacherProfileFragment fragment);

    void doInjection(SelectYourAppointmentDialogFragment dialog);

    void doInjection(ConsgratuationLessScoreDialog dialog);

    void doInjection(QuizHomeNewFragment fragment);

    void doInjection(FriendRequestListDialogFragment fragment);
}
