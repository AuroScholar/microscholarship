package com.auro.scholr.teacher.domain;

import android.util.Log;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.NetworkUtil;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.network.NetworkUseCase;
import com.auro.scholr.teacher.data.model.request.TeacherReqModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomResModel;
import com.auro.scholr.teacher.data.model.response.MyProfileResModel;
import com.auro.scholr.teacher.data.model.response.TeacherResModel;
import com.auro.scholr.teacher.data.repository.TeacherRepo;
import com.auro.scholr.util.AppUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Response;

import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_200;
import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_400;
import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_401;
import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_FAIL;

public class TeacherRemoteUseCase extends NetworkUseCase {
    TeacherRepo.TeacherRemoteData teacherRemoteData;
    Gson gson = new Gson();

    public TeacherRemoteUseCase(TeacherRepo.TeacherRemoteData teacherRemoteData) {
        this.teacherRemoteData = teacherRemoteData;
    }


    public Single<ResponseApi> updateTeacherProfileApi(TeacherReqModel reqModel) {
        Log.e("Request","TeacherDistrict "+reqModel.getDistrict_id() + " State "+reqModel.getState_id());
        return teacherRemoteData.updateTeacherProfileApi(reqModel).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {


                    return handleResponse(response, Status.UPDATE_TEACHER_PROFILE_API);


                } else {

                    return responseFail(null);
                }
            }
        });
    }

    public Single<ResponseApi> getProfileTeacherApi(String mobileNumber) {

        return teacherRemoteData.getProfileTeacherApi(mobileNumber).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, Status.GET_PROFILE_TEACHER_API);

                } else {

                    return responseFail(null);
                }
            }
        });
    }
    public Single<ResponseApi> getTeacherDashboardApi(String mobileNumber) {

        return teacherRemoteData.getTeacherDashboardApi(mobileNumber).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, Status.GET_TEACHER_DASHBOARD_API);

                } else {

                    return responseFail(null);
                }
            }
        });
    }


    private ResponseApi handleResponse(Response<JsonObject> response, Status apiTypeStatus) {

        switch (response.code()) {

            case RES_200:
                return response200(response, apiTypeStatus);

            case RES_401:
                return response401(apiTypeStatus);

            case RES_400:
                return responseFail400(response, apiTypeStatus);

            case RES_FAIL:
                return responseFail(apiTypeStatus);

            default:
                return ResponseApi.fail(AuroApp.getAppContext().getString(R.string.default_error), apiTypeStatus);
        }
    }

    @Override
    public Single<Boolean> isAvailInternet() {
        return NetworkUtil.hasInternetConnection();
    }

    @Override
    public ResponseApi response200(Response<JsonObject> response, Status status) {
        switch (status) {
            case UPDATE_TEACHER_PROFILE_API:
                TeacherResModel teacherResModel = gson.fromJson(response.body(), TeacherResModel.class);
                if (!teacherResModel.getError()) {
                    return ResponseApi.success(teacherResModel, status);
                } else {
                    return ResponseApi.fail(teacherResModel.getMessage(), status);
                }

            case GET_TEACHER_DASHBOARD_API:
                MyClassRoomResModel myClassRoomResModel = gson.fromJson(response.body(), MyClassRoomResModel.class);
                return ResponseApi.success(myClassRoomResModel, status);

            case GET_PROFILE_TEACHER_API:
                MyProfileResModel resModel = gson.fromJson(response.body(), MyProfileResModel.class);
                return ResponseApi.success(resModel, status);

        }


        return ResponseApi.fail(null, status);
    }

    @Override
    public ResponseApi response401(Status status) {
        return ResponseApi.authFail(401, status);
    }

    @Override
    public ResponseApi responseFail400(Response<JsonObject> response, Status status) {
        try {
            String errorJson = response.errorBody().string();
            String errorMessage = AppUtil.errorMessageHandler(AuroApp.getAppContext().getString(R.string.default_error), errorJson);
            return ResponseApi.fail400(errorMessage, null);
        } catch (Exception e) {
            return ResponseApi.fail(AuroApp.getAppContext().getResources().getString(R.string.default_error), status);
        }
    }


    @Override
    public ResponseApi responseFail(Status status) {
        return ResponseApi.fail(AuroApp.getAppContext().getString(R.string.default_error), status);
    }

}
