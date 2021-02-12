package com.auro.scholr.payment.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.MessgeNotifyStatus;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.payment.data.model.request.PaytmWithdrawalByBankAccountReqModel;
import com.auro.scholr.payment.data.model.request.PaytmWithdrawalByUPIReqModel;
import com.auro.scholr.payment.data.model.request.PaytmWithdrawalReqModel;

import com.auro.scholr.payment.domain.PaymentRemoteUseCase;
import com.auro.scholr.payment.domain.PaymentUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SendMoneyViewModel extends ViewModel {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<ResponseApi> serviceLiveData = new MutableLiveData<>();

    private MutableLiveData<MessgeNotifyStatus> notifyLiveData = new MutableLiveData<>();

    public MutableLiveData<String> mobileNumber = new MutableLiveData<>();
    public  PaymentUseCase paymentUseCase;
    PaymentRemoteUseCase paymentRemoteUseCase;

    public SendMoneyViewModel(PaymentUseCase paymentUseCase, PaymentRemoteUseCase paymentRemoteUseCase) {
        this.paymentUseCase = paymentUseCase;

        this.paymentRemoteUseCase = paymentRemoteUseCase;
    }



    public MutableLiveData<MessgeNotifyStatus> getNotifyLiveData() {
        return notifyLiveData;
    }


    public void paytmWithdrawal(PaytmWithdrawalReqModel reqModel) {
        Disposable disposable = paymentRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                paytmWithdrawalApi(reqModel);
            } else {
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), Status.ACCEPT_INVITE_CLICK));
            }
        });
        getCompositeDisposable().add(disposable);
    }

    public void paytmWithdrawalByAccount(PaytmWithdrawalByBankAccountReqModel reqModel) {
        Disposable disposable = paymentRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                paytmWithdrawalByBankAccountApi(reqModel);
            } else {
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), Status.ACCEPT_INVITE_CLICK));
            }
        });
        getCompositeDisposable().add(disposable);
    }

    public void paytmWithdrawalByUPI(PaytmWithdrawalByUPIReqModel reqModel) {
        Disposable disposable = paymentRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                paytmWithdrawalByUpiApi(reqModel);
            } else {
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), Status.ACCEPT_INVITE_CLICK));
            }
        });
        getCompositeDisposable().add(disposable);
    }

    private void paytmWithdrawalApi(PaytmWithdrawalReqModel reqModel) {
        getCompositeDisposable().add(paymentRemoteUseCase.paytmWithdrawalApi(reqModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                       // serviceLiveData.setValue(ResponseApi.loading(Status.ACCEPT_INVITE_CLICK));
                        serviceLiveData.setValue(ResponseApi.loading(Status.PAYTM_WITHDRAWAL));
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


    private void paytmWithdrawalByUpiApi(PaytmWithdrawalByUPIReqModel reqModel) {
        getCompositeDisposable().add(paymentRemoteUseCase.paytmByUpiApi(reqModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        // serviceLiveData.setValue(ResponseApi.loading(Status.ACCEPT_INVITE_CLICK));
                        serviceLiveData.setValue(ResponseApi.loading(Status.PAYTM_UPI_WITHDRAWAL));
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

    private void paytmWithdrawalByBankAccountApi(PaytmWithdrawalByBankAccountReqModel reqModel) {
        getCompositeDisposable().add(paymentRemoteUseCase.paytmByAccountApi(reqModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        // serviceLiveData.setValue(ResponseApi.loading(Status.ACCEPT_INVITE_CLICK));
                        serviceLiveData.setValue(ResponseApi.loading(Status.PAYTM_ACCOUNT_WITHDRAWAL));
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

    public void paytmentTransfer(PaytmWithdrawalByBankAccountReqModel reqModel) {
        Disposable disposable = paymentRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                paytmentTransferApi(reqModel);
            } else {
                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), Status.ACCEPT_INVITE_CLICK));
            }
        });
        getCompositeDisposable().add(disposable);
    }

    private void paytmentTransferApi(PaytmWithdrawalByBankAccountReqModel reqModel) {
        getCompositeDisposable().add(paymentRemoteUseCase.paymentTransferApi(reqModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        // serviceLiveData.setValue(ResponseApi.loading(Status.ACCEPT_INVITE_CLICK));
                        serviceLiveData.setValue(ResponseApi.loading(Status.PAYMENT_TRANSFER_API));
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


    private CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    private void defaultError() {
        serviceLiveData.setValue(new ResponseApi(Status.FAIL, AuroApp.getAppContext().getResources().getString(R.string.default_error), null));
    }

    public LiveData<ResponseApi> serviceLiveData() {

        return serviceLiveData;

    }

}
