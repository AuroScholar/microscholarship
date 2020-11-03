package com.auro.scholr.teacher.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.teacher.data.model.request.SendInviteNotificationReqModel;
import com.auro.scholr.teacher.domain.TeacherDbUseCase;
import com.auro.scholr.teacher.domain.TeacherRemoteUseCase;
import com.auro.scholr.teacher.domain.TeacherUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SelectYourMessageDialogModel extends ViewModel {

    CompositeDisposable compositeDisposable;
    MutableLiveData<ResponseApi> serviceLiveData = new MutableLiveData<>();

    public TeacherUseCase teacherUseCase;
    TeacherRemoteUseCase teacherRemoteUseCase;
    TeacherDbUseCase teacherDbUseCase;

    public SelectYourMessageDialogModel(TeacherUseCase teacherUseCase, TeacherDbUseCase teacherDbUseCase, TeacherRemoteUseCase teacherRemoteUseCase) {
        this.teacherDbUseCase = teacherDbUseCase;
        this.teacherUseCase = teacherUseCase;
        this.teacherRemoteUseCase = teacherRemoteUseCase;
    }


    public void sendInviteNotificationApi(SendInviteNotificationReqModel reqModel ) {

        Disposable disposable = teacherRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                sendInviteApi(reqModel);
            } else {
                // please check your internet
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getResources().getString(R.string.internet_check), Status.NO_INTERNET));
            }

        });

        getCompositeDisposable().add(disposable);

    }


    private void sendInviteApi(SendInviteNotificationReqModel reqModel ) {
        getCompositeDisposable()
                .add(teacherRemoteUseCase.sendInviteApi(reqModel)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable __) throws Exception {
                                /*Do code here*/
                                serviceLiveData.setValue(ResponseApi.loading(null));
                            }
                        })
                        .subscribe(new Consumer<ResponseApi>() {
                                       @Override
                                       public void accept(ResponseApi responseApi) throws Exception {
                                           serviceLiveData.setValue(responseApi);
                                       }
                                   },

                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        defaultError();
                                    }
                                }));

    }



    private void defaultError() {
        serviceLiveData.setValue(new ResponseApi(Status.FAIL, AuroApp.getAppContext().getResources().getString(R.string.default_error), null));
    }

    private CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    public LiveData<ResponseApi> serviceLiveData() {

        return serviceLiveData;

    }
}
