package com.auro.scholr.home.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.home.data.model.CertificateResModel;
import com.auro.scholr.home.data.model.passportmodels.PassportReqModel;
import com.auro.scholr.home.domain.usecase.HomeDbUseCase;
import com.auro.scholr.home.domain.usecase.HomeRemoteUseCase;
import com.auro.scholr.home.domain.usecase.HomeUseCase;
import com.auro.scholr.util.AppLogger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.auro.scholr.core.common.Status.CERTIFICATE_API;
import static com.auro.scholr.core.common.Status.PASSPORT_API;

public class TransactionsViewModel extends ViewModel {
    CompositeDisposable compositeDisposable;
    public MutableLiveData<ResponseApi> serviceLiveData = new MutableLiveData<>();


    public MutableLiveData<String> mobileNumber = new MutableLiveData<>();
    public HomeUseCase homeUseCase;
    public HomeDbUseCase homeDbUseCase;
    public HomeRemoteUseCase homeRemoteUseCase;

    public TransactionsViewModel(HomeUseCase homeUseCase, HomeDbUseCase homeDbUseCase, HomeRemoteUseCase homeRemoteUseCase) {
        this.homeUseCase = homeUseCase;
        this.homeDbUseCase=homeDbUseCase;
        this.homeRemoteUseCase=homeRemoteUseCase;
    }

    private CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    public void getCertificate(CertificateResModel reqModel) {
        Disposable disposable = homeRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                getCertificateApi(reqModel);
            } else {
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), CERTIFICATE_API));
            }

        });
        getCompositeDisposable().add(disposable);
    }

    private void getCertificateApi(CertificateResModel reqModel) {
        getCompositeDisposable().add(homeRemoteUseCase.getCertificateApi(reqModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        // serviceLiveData.setValue(ResponseApi.loading(Status.ACCEPT_INVITE_CLICK));
                        serviceLiveData.setValue(ResponseApi.loading(CERTIFICATE_API));
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
                                defaultError(CERTIFICATE_API);
                            }
                        }));
    }


    public void getPassportInternetCheck(PassportReqModel reqModel) {
        Disposable disposable = homeRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                getPassportApi(reqModel);
            } else {
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), PASSPORT_API));
            }

        });
        getCompositeDisposable().add(disposable);
    }

    private void getPassportApi(PassportReqModel reqModel) {
        getCompositeDisposable().add(homeRemoteUseCase.getPassportApi(reqModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        serviceLiveData.setValue(ResponseApi.loading(PASSPORT_API));
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
                                AppLogger.e("TransactionsFragment--exception--",throwable.getMessage());
                                defaultError(PASSPORT_API);
                            }
                        }));
    }


    private void defaultError(Status status) {
        serviceLiveData.setValue(new ResponseApi(Status.FAIL, AuroApp.getAppContext().getResources().getString(R.string.default_error), null));
    }

    public LiveData<ResponseApi> serviceLiveData() {

        return serviceLiveData;

    }

}
