package com.auro.scholr.core.application.di.component;

import android.app.Activity;

import com.auro.scholr.core.application.di.module.AppModule;
import com.auro.scholr.core.application.di.module.HomeModule;
import com.auro.scholr.core.application.di.module.PaymentModule;
import com.auro.scholr.core.application.di.module.UtilsModule;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.fragment.DemographicFragment;
import com.auro.scholr.home.presentation.view.fragment.FriendsInviteBoardFragment;
import com.auro.scholr.home.presentation.view.fragment.FriendsLeaderBoardFragment;
import com.auro.scholr.home.presentation.view.fragment.InviteFriendDialog;
import com.auro.scholr.home.presentation.view.fragment.KYCFragment;
import com.auro.scholr.home.presentation.view.fragment.KYCViewFragment;
import com.auro.scholr.home.presentation.view.fragment.PrivacyPolicyFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizTestFragment;
import com.auro.scholr.home.presentation.view.fragment.ScholarShipFragment;
import com.auro.scholr.payment.domain.PaymentUseCase;
import com.auro.scholr.payment.presentation.view.fragment.BankFragment;
import com.auro.scholr.payment.presentation.view.fragment.PaytmFragment;
import com.auro.scholr.payment.presentation.view.fragment.SendMoneyFragment;
import com.auro.scholr.payment.presentation.view.fragment.UPIFragment;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, UtilsModule.class, HomeModule.class, PaymentModule.class,})
@Singleton
public interface AppComponent {

    void injectAppContext(Activity reciprociApp);


    void doInjection(HomeActivity homeActivity);

    void doInjection(KYCViewFragment kycViewFragment);

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

    void doInjection(InviteFriendDialog inviteFriendDialog);

}
