package com.auro.scholr.home.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.MessgeNotifyStatus;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.domain.usecase.HomeDbUseCase;
import com.auro.scholr.home.domain.usecase.HomeRemoteUseCase;
import com.auro.scholr.home.domain.usecase.HomeUseCase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.auro.scholr.core.common.Status.AZURE_API;
import static com.auro.scholr.core.common.Status.DASHBOARD_API;
import static com.auro.scholr.core.common.Status.GRADE_UPGRADE;


public class QuizViewModel extends ViewModel {

    CompositeDisposable compositeDisposable;
    public MutableLiveData<ResponseApi> serviceLiveData = new MutableLiveData<>();
    private MutableLiveData<MessgeNotifyStatus> notifyLiveData = new MutableLiveData<>();

    public MutableLiveData<String> mobileNumber = new MutableLiveData<>();
    public HomeUseCase homeUseCase;
    private HomeDbUseCase homeDbUseCase;
    private HomeRemoteUseCase homeRemoteUseCase;

    public MutableLiveData<String> walletBalance = new MutableLiveData<>();

    public QuizViewModel(HomeUseCase homeUseCase, HomeDbUseCase homeDbUseCase, HomeRemoteUseCase homeRemoteUseCase) {
        this.homeUseCase = homeUseCase;
        this.homeDbUseCase = homeDbUseCase;
        this.homeRemoteUseCase = homeRemoteUseCase;
    }

    public void getDashBoardData(AuroScholarDataModel model) {

        Disposable disposable = homeRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                dashBoardApi(model);
            } else {
                // please check your internet
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), Status.NO_INTERNET));
            }
        });

        getCompositeDisposable().add(disposable);

    }
    public void getAzureRequestData(AssignmentReqModel model){
        Disposable disposable = homeRemoteUseCase.isAvailInternet().subscribe(hasInternet ->{
            if(hasInternet){
                azureRequestApi(model);
            }else{
               // serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET,AuroApp.getAppContext().getString(R.string.internet_check),Status.NO_INTERNET));
            }
        });
        getCompositeDisposable().add(disposable);
    }
    private  void azureRequestApi(AssignmentReqModel model){
        getCompositeDisposable().add(homeRemoteUseCase.getAzureData(model).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {

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
                                        defaultError(AZURE_API);
                                    }
                                }));
    }



    private void dashBoardApi(AuroScholarDataModel model) {
        getCompositeDisposable()
                .add(homeRemoteUseCase.getDashboardData(model)
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
                                        defaultError(DASHBOARD_API);                                    }
                                }));

    }

    public void gradeUpgrade(AuroScholarDataModel model) {

        Disposable disposable = homeRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                gradeClassUpgrade(model);
            } else {
                // please check your internet
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), Status.NO_INTERNET));
            }
        });

        getCompositeDisposable().add(disposable);

    }

    private  void gradeClassUpgrade(AuroScholarDataModel model){
        getCompositeDisposable().add(homeRemoteUseCase.upgradeStudentGrade(model).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

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
                                defaultError(GRADE_UPGRADE);
                            }
                        }));
    }




    private void defaultError(Status status) {
        serviceLiveData.setValue(new ResponseApi(Status.FAIL, AuroApp.getAppContext().getResources().getString(R.string.default_error), status));
    }


    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }





    private CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }


    public MutableLiveData<MessgeNotifyStatus> getNotifyLiveData() {
        return notifyLiveData;
    }

    public LiveData<ResponseApi> serviceLiveData() {

        return serviceLiveData;

    }

}
