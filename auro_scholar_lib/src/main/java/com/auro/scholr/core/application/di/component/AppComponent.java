package com.auro.scholr.core.application.di.component;

import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.fragment.CardFragment;
import com.auro.scholr.core.application.di.module.AppModule;
import com.auro.scholr.core.application.di.module.HomeModule;
import com.auro.scholr.core.application.di.module.UtilsModule;
import com.auro.scholr.home.presentation.view.fragment.KYCFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;
import com.auro.scholr.home.presentation.view.fragment.ScholarShipFragment;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, UtilsModule.class, HomeModule.class,})
@Singleton
public interface AppComponent {

    void injectAppContext(AuroApp reciprociApp);


    void doInjection(HomeActivity homeActivity);

    void doInjection(ScholarShipFragment scholarShipFragment);

    void doInjection(QuizHomeFragment quizHomeFragment);

    void doInjection(CardFragment cardFragment);

    void doInjection(KYCFragment kycFragment);

}
