package com.auro.scholr.teacher.presentation.viewmodel;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.teacher.domain.TeacherDbUseCase;
import com.auro.scholr.teacher.domain.TeacherRemoteUseCase;
import com.auro.scholr.teacher.domain.TeacherUseCase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TeacherInfoViewModel extends ViewModel {
    CompositeDisposable compositeDisposable;
    MutableLiveData<ResponseApi> serviceLiveData = new MutableLiveData<>();

    public TeacherUseCase teacherUseCase;
    TeacherRemoteUseCase teacherRemoteUseCase;
    TeacherDbUseCase teacherDbUseCase;

    public TeacherInfoViewModel(TeacherUseCase teacherUseCase, TeacherDbUseCase teacherDbUseCase, TeacherRemoteUseCase teacherRemoteUseCase) {
        this.teacherDbUseCase = teacherDbUseCase;
        this.teacherUseCase = teacherUseCase;
        this.teacherRemoteUseCase = teacherRemoteUseCase;
    }

    public void getTeacherProgressData(String mobileNumber) {

        Disposable disposable = teacherRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                getTeacherProgressApi(mobileNumber);
            } else {
                // please check your internet
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), Status.GET_TEACHER_PROGRESS_API));
            }

        });

        getCompositeDisposable().add(disposable);

    }

    public void getZohoAppointment() {

        Disposable disposable = teacherRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                getCompositeDisposable()
                        .add(teacherRemoteUseCase.getZohoAppointments()
                                     .subscribeOn(Schedulers.io())
                                     .observeOn(AndroidSchedulers.mainThread())
                                     .doOnSubscribe(new Consumer<Disposable>() {
                                         @Override
                                         public void accept(Disposable __) throws Exception {
                                             /*Do code here*/
                                             serviceLiveData.setValue(ResponseApi.loading(Status.GET_ZOHO_APPOINTMENT));
                                         }
                                     })
                                     .subscribe(
                                             new Consumer<ResponseApi>() {
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
                                             }
                                     ));
            } else {
                // please check your internet
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), Status.GET_ZOHO_APPOINTMENT));
            }

        });

        getCompositeDisposable().add(disposable);



    }



    private void getTeacherProgressApi(String mobileNumber) {
        getCompositeDisposable()
                .add(teacherRemoteUseCase.getTeacherProgressApi(mobileNumber)
                             .subscribeOn(Schedulers.io())
                             .observeOn(AndroidSchedulers.mainThread())
                             .doOnSubscribe(new Consumer<Disposable>() {
                                 @Override
                                 public void accept(Disposable __) throws Exception {
                                     /*Do code here*/
                                     serviceLiveData.setValue(ResponseApi.loading(Status.GET_TEACHER_PROGRESS_API));
                                 }
                             })
                             .subscribe(
                                     new Consumer<ResponseApi>() {
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
                                     }
                             ));

    }

    public void getTeacherDashboardData(String mobileNumber) {

        Disposable disposable = teacherRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                getTeacherDashboardApi(mobileNumber);
            } else {
                // please check your internet
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getResources().getString(R.string.internet_check), Status.NO_INTERNET));
            }

        });

        getCompositeDisposable().add(disposable);

    }


    private void getTeacherDashboardApi(String mobileNumber) {
        getCompositeDisposable()
                .add(teacherRemoteUseCase.getTeacherDashboardApi(mobileNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable __) throws Exception {
                                /*Do code here*/
                                serviceLiveData.setValue(ResponseApi.loading(Status.GET_TEACHER_DASHBOARD_API));
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
