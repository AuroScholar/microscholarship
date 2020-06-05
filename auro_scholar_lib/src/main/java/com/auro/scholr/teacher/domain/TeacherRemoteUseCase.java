package com.auro.scholr.teacher.domain;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.NetworkUtil;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.network.NetworkUseCase;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AssignmentResModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.AzureResModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.auro.scholr.home.data.model.KYCResListModel;
import com.auro.scholr.teacher.data.repository.TeacherRepo;
import com.auro.scholr.util.AppUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Response;

import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_200;
import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_400;
import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_401;
import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_FAIL;
import static com.auro.scholr.core.common.Status.ASSIGNMENT_STUDENT_DATA_API;
import static com.auro.scholr.core.common.Status.AZURE_API;
import static com.auro.scholr.core.common.Status.DASHBOARD_API;
import static com.auro.scholr.core.common.Status.DEMOGRAPHIC_API;

public class TeacherRemoteUseCase extends NetworkUseCase {
    TeacherRepo.TeacherRemoteData teacherRemoteData;
    Gson gson = new Gson();

    public TeacherRemoteUseCase( TeacherRepo.TeacherRemoteData teacherRemoteData) {
        this.teacherRemoteData = teacherRemoteData;
    }


    public Single<ResponseApi> uploadProfileImage(AuroScholarDataModel kycInputModel) {

        return teacherRemoteData.getTeacherData(kycInputModel).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, Status.UPLOAD_PROFILE_IMAGE);

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
        if (status == DASHBOARD_API) {
            DashboardResModel dashboardResModel = gson.fromJson(response.body(), DashboardResModel.class);
            return ResponseApi.success(dashboardResModel, status);
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
