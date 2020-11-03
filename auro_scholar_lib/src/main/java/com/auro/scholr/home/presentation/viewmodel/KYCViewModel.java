package com.auro.scholr.home.presentation.viewmodel;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.MessgeNotifyStatus;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.auro.scholr.home.domain.usecase.HomeDbUseCase;
import com.auro.scholr.home.domain.usecase.HomeRemoteUseCase;
import com.auro.scholr.home.domain.usecase.HomeUseCase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static com.auro.scholr.core.common.Status.UPLOAD_PROFILE_IMAGE;


public class KYCViewModel extends ViewModel {
    CompositeDisposable compositeDisposable;
    public MutableLiveData<ResponseApi> serviceLiveData = new MutableLiveData<>();


    public MutableLiveData<String> mobileNumber = new MutableLiveData<>();
    public HomeUseCase homeUseCase;
    public HomeDbUseCase homeDbUseCase;
    public HomeRemoteUseCase homeRemoteUseCase;

    public KYCViewModel(HomeUseCase homeUseCase, HomeDbUseCase homeDbUseCase, HomeRemoteUseCase homeRemoteUseCase) {
        this.homeUseCase = homeUseCase;
        this.homeDbUseCase = homeDbUseCase;
        this.homeRemoteUseCase = homeRemoteUseCase;
    }


    public void uploadProfileImage(List<KYCDocumentDatamodel> list, KYCInputModel kycInputModel) {

        Disposable disposable = homeRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                callUploadImageApi(list, kycInputModel);

            } else {

                serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET, AuroApp.getAppContext().getString(R.string.internet_check), Status.NO_INTERNET));
            }

        });
        getCompositeDisposable().add(disposable);

    }


    private CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    private void callUploadImageApi(List<KYCDocumentDatamodel> list, KYCInputModel kycInputModel) {

        getCompositeDisposable().add(homeRemoteUseCase.uploadProfileImage(list, kycInputModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable __) throws Exception {
                serviceLiveData.setValue(ResponseApi.loading(Status.UPLOAD_PROFILE_IMAGE));
            }
        })
                .subscribe(

                        new Consumer<ResponseApi>() {
                            @Override
                            public void accept(ResponseApi checkUpdate) throws Exception {

                                serviceLiveData.setValue(checkUpdate);

                            }
                        },

                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                                serviceLiveData.setValue(new ResponseApi(Status.FAIL, AuroApp.getAppContext().getResources().getString(R.string.default_error), UPLOAD_PROFILE_IMAGE));
                            }
                        }));

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

    public long  bytesIntoHumanReadable(long bytes) {
        long kilobyte = 1024;
        long megabyte = kilobyte * 1024;
        long gigabyte = megabyte * 1024;
        long terabyte = gigabyte * 1024;

        return (bytes / megabyte);

/*
        if ((bytes >= 0) && (bytes < kilobyte)) {
            return bytes + " B";

        } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
            return (bytes / kilobyte) + " KB";

        } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
            return (bytes / megabyte) + " MB";

        } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
            return (bytes / gigabyte) + " GB";

        } else if (bytes >= terabyte) {
            return (bytes / terabyte) + " TB";

        } else {
            return bytes + " Bytes";
        }*/
    }
    public static byte[] encodeToBase64(Bitmap image,int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOS);
        byte[] byteArray = byteArrayOS.toByteArray();
        return byteArray;
    }

    public void sendAzureImageData(AssignmentReqModel model) {
        Disposable disposable = homeRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                azureRequestApi(model);
            } else {
                // serviceLiveData.setValue(new ResponseApi(Status.NO_INTERNET,AuroApp.getAppContext().getString(R.string.internet_check),Status.NO_INTERNET));
            }
        });
        getCompositeDisposable().add(disposable);
    }

    private void azureRequestApi(AssignmentReqModel model) {
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
                                if (!tryOnError(throwable)) {
                                    RxJavaPlugins.onError(throwable);
                                }
                                defaultError();
                            }
                        }));
    }

    private void defaultError() {
        serviceLiveData.setValue(new ResponseApi(Status.FAIL, AuroApp.getAppContext().getResources().getString(R.string.default_error), null));
    }
    public boolean tryOnError(Throwable t) {
        if (t == null) {
            t = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        return false;
    }

    public LiveData<ResponseApi> serviceLiveData() {

        return serviceLiveData;

    }

}
