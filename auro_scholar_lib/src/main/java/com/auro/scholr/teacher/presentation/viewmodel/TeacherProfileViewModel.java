package com.auro.scholr.teacher.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;
import com.auro.scholr.teacher.domain.TeacherDbUseCase;
import com.auro.scholr.teacher.domain.TeacherRemoteUseCase;
import com.auro.scholr.teacher.domain.TeacherUseCase;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.TextUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TeacherProfileViewModel extends ViewModel {

    CompositeDisposable compositeDisposable;
    MutableLiveData<ResponseApi> serviceLiveData = new MutableLiveData<>();

    public TeacherUseCase teacherUseCase;
    TeacherRemoteUseCase teacherRemoteUseCase;
    TeacherDbUseCase teacherDbUseCase;

    public TeacherProfileViewModel(TeacherUseCase teacherUseCase, TeacherDbUseCase teacherDbUseCase, TeacherRemoteUseCase teacherRemoteUseCase) {
        this.teacherDbUseCase = teacherDbUseCase;
        this.teacherUseCase = teacherUseCase;
        this.teacherRemoteUseCase = teacherRemoteUseCase;
    }


    public void getStateListData() {
        getCompositeDisposable().add(teacherDbUseCase.getStateList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<StateDataModel>>() {
                            @Override
                            public void accept(List<StateDataModel> value) throws Exception {
                                if (!TextUtil.checkListIsEmpty(value)) {
                                    serviceLiveData.setValue(new ResponseApi(Status.STATE_LIST_ARRAY, value, Status.STATE_LIST_ARRAY));
                                } else {
                                    insertStateData();
                                }
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


    public void insertStateData() {
        getCompositeDisposable().add(teacherDbUseCase.insertStateList(teacherUseCase.readStateData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<Long[]>() {
                            @Override
                            public void accept(Long[] value) throws Exception {
                                AppLogger.e("chhonker-", "insert data-" + value.length);
                                getStateListData();
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


    public void getDistrictListData() {
        getCompositeDisposable().add(teacherDbUseCase.getDistrictList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<DistrictDataModel>>() {
                            @Override
                            public void accept(List<DistrictDataModel> value) throws Exception {
                                if (TextUtil.checkListIsEmpty(value)) {
                                    insertDistrictData();
                                } else {

                                }
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


    public void insertDistrictData() {
        getCompositeDisposable().add(teacherDbUseCase.insertDistrictList(teacherUseCase.readDistrictData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<Long[]>() {
                            @Override
                            public void accept(Long[] value) throws Exception {
                                AppLogger.e("chhonker-", "insert data-" + value.length);

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


    public void getStateDistrictData(String stateCode) {
        getCompositeDisposable().add(teacherDbUseCase.getDistrictList(stateCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<DistrictDataModel>>() {
                            @Override
                            public void accept(List<DistrictDataModel> value) throws Exception {
                                if (!TextUtil.checkListIsEmpty(value)) {
                                    serviceLiveData.setValue(new ResponseApi(Status.DISTRICT_LIST_DATA, value, Status.DISTRICT_LIST_DATA));
                                }

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
