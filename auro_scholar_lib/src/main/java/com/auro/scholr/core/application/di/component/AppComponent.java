package com.auro.scholr.core.application.di.component;

import android.app.Activity;

import com.auro.scholr.core.application.di.module.AppModule;
import com.auro.scholr.core.application.di.module.HomeModule;
import com.auro.scholr.core.application.di.module.UtilsModule;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.fragment.DemographicFragment;
import com.auro.scholr.home.presentation.view.fragment.FriendsInviteBoardFragment;
import com.auro.scholr.home.presentation.view.fragment.FriendsLeaderBoardFragment;
import com.auro.scholr.home.presentation.view.fragment.KYCFragment;
import com.auro.scholr.home.presentation.view.fragment.KYCViewFragment;
import com.auro.scholr.home.presentation.view.fragment.PrivacyPolicyFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizTestFragment;
import com.auro.scholr.home.presentation.view.fragment.ScholarShipFragment;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, UtilsModule.class, HomeModule.class,})
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


    void doInjection(PrivacyPolicyFragment privacyPolicyFragment);

    void doInjection(FriendsInviteBoardFragment friendsInviteBoardFragment);

    void doInjection(FriendsLeaderBoardFragment friendsLeaderBoardFragment);

}
