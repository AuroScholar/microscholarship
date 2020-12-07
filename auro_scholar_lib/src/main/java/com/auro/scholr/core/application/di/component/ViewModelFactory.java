package com.auro.scholr.core.application.di.component;


import com.auro.scholr.home.domain.usecase.HomeDbUseCase;
import com.auro.scholr.home.domain.usecase.HomeRemoteUseCase;
import com.auro.scholr.home.domain.usecase.HomeUseCase;
import com.auro.scholr.home.presentation.viewmodel.CardViewModel;
import com.auro.scholr.home.presentation.viewmodel.CongratulationsDialogViewModel;
import com.auro.scholr.home.presentation.viewmodel.DemographicViewModel;
import com.auro.scholr.home.presentation.viewmodel.FriendsInviteViewModel;
import com.auro.scholr.home.presentation.viewmodel.FriendsLeaderShipViewModel;
import com.auro.scholr.home.presentation.viewmodel.HomeViewModel;
import com.auro.scholr.home.presentation.viewmodel.InviteFriendViewModel;
import com.auro.scholr.home.presentation.viewmodel.KYCViewModel;
import com.auro.scholr.home.presentation.viewmodel.QuizTestViewModel;
import com.auro.scholr.home.presentation.viewmodel.QuizViewModel;
import com.auro.scholr.home.presentation.viewmodel.QuizViewNewModel;
import com.auro.scholr.home.presentation.viewmodel.ScholarShipViewModel;
import com.auro.scholr.home.presentation.viewmodel.TransactionsViewModel;
import com.auro.scholr.payment.domain.PaymentRemoteUseCase;
import com.auro.scholr.payment.domain.PaymentUseCase;
import com.auro.scholr.payment.presentation.viewmodel.SendMoneyViewModel;
import com.auro.scholr.teacher.domain.TeacherDbUseCase;
import com.auro.scholr.teacher.domain.TeacherRemoteUseCase;
import com.auro.scholr.teacher.domain.TeacherUseCase;
import com.auro.scholr.teacher.presentation.viewmodel.MyClassroomViewModel;
import com.auro.scholr.teacher.presentation.viewmodel.SelectYourAppointmentDialogModel;
import com.auro.scholr.teacher.presentation.viewmodel.SelectYourMessageDialogModel;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherInfoViewModel;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherKycViewModel;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherProfileViewModel;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherSaveDetailViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {


    /*Home Module*/
    private HomeUseCase homeUseCase;
    private HomeDbUseCase homeDbUseCase;
    private HomeRemoteUseCase homeRemoteUseCase;

    /*Payment Module*/
    private PaymentUseCase paymentUseCase;
    private PaymentRemoteUseCase paymentRemoteUseCase;

    /*Teacher module*/
    TeacherUseCase teacherUseCase;
    TeacherRemoteUseCase teacherRemoteUseCase;
    TeacherDbUseCase teacherDbUseCase;

    public ViewModelFactory(Object objectOne, Object objectTwo) {
        if (objectOne instanceof PaymentUseCase && objectTwo instanceof PaymentRemoteUseCase) {
            this.paymentUseCase = (PaymentUseCase) objectOne;
            this.paymentRemoteUseCase = (PaymentRemoteUseCase) objectTwo;
        }

    }

    public ViewModelFactory(Object objectOne, Object objectTwo, Object objectThree) {

        if (objectOne instanceof HomeUseCase && objectTwo instanceof HomeDbUseCase && objectThree instanceof HomeRemoteUseCase) {
            this.homeUseCase = (HomeUseCase) objectOne;
            this.homeDbUseCase = (HomeDbUseCase) objectTwo;
            this.homeRemoteUseCase = (HomeRemoteUseCase) objectThree;
        } else if (objectOne instanceof TeacherUseCase && objectTwo instanceof TeacherDbUseCase && objectThree instanceof TeacherRemoteUseCase) {
            this.teacherUseCase = (TeacherUseCase) objectOne;
            this.teacherDbUseCase = (TeacherDbUseCase) objectTwo;
            this.teacherRemoteUseCase = (TeacherRemoteUseCase) objectThree;
        }

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {

            return (T) new HomeViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(CardViewModel.class)) {

            return (T) new CardViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(QuizViewModel.class)) {

            return (T) new QuizViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(KYCViewModel.class)) {

            return (T) new KYCViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        }
        else if (modelClass.isAssignableFrom(ScholarShipViewModel.class)) {

            return (T) new ScholarShipViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(DemographicViewModel.class)) {

            return (T) new DemographicViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(QuizTestViewModel.class)) {

            return (T) new QuizTestViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(SendMoneyViewModel.class)) {
            return (T) new SendMoneyViewModel(paymentUseCase, paymentRemoteUseCase);

        } else if (modelClass.isAssignableFrom(FriendsLeaderShipViewModel.class)) {

            return (T) new FriendsLeaderShipViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(FriendsInviteViewModel.class)) {

            return (T) new FriendsInviteViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(InviteFriendViewModel.class)) {

            return (T) new InviteFriendViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);
        } else if (modelClass.isAssignableFrom(CongratulationsDialogViewModel.class)) {

            return (T) new CongratulationsDialogViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);
        } else if (modelClass.isAssignableFrom(TransactionsViewModel.class)) {

            return (T) new TransactionsViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);
        } else if (modelClass.isAssignableFrom(MyClassroomViewModel.class)) {

            return (T) new MyClassroomViewModel(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
        }else if (modelClass.isAssignableFrom(TeacherKycViewModel.class)) {

            return (T) new TeacherKycViewModel(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
        } else if (modelClass.isAssignableFrom(TeacherInfoViewModel.class)) {

            return (T) new TeacherInfoViewModel(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
        }else if (modelClass.isAssignableFrom(TeacherSaveDetailViewModel.class)) {

            return (T) new TeacherSaveDetailViewModel(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
        }else if (modelClass.isAssignableFrom(SelectYourMessageDialogModel.class)) {

            return (T) new SelectYourMessageDialogModel(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
        }else if (modelClass.isAssignableFrom(TeacherProfileViewModel.class)) {

            return (T) new TeacherProfileViewModel(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
        } else if (modelClass.isAssignableFrom(SelectYourAppointmentDialogModel.class)) {

            return (T) new SelectYourAppointmentDialogModel(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
        }else if(modelClass.isAssignableFrom(QuizViewNewModel.class)){

            return (T) new QuizViewNewModel(homeUseCase,homeDbUseCase,homeRemoteUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}


