package com.auro.scholr.teacher.data.datasource.remote;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.datasource.remote.HomeRemoteApi;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.auro.scholr.home.data.repository.HomeRepo;
import com.auro.scholr.teacher.data.repository.TeacherRepo;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.TextUtil;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class TeacherRemoteDataSourceImp implements TeacherRepo.TeacherRemoteData {

    TeacherRemoteApi homeRemoteApi;

    public TeacherRemoteDataSourceImp(TeacherRemoteApi homeRemoteApi) {
        this.homeRemoteApi = homeRemoteApi;
    }


    @Override
    public Single<Response<JsonObject>> getTeacherData(AuroScholarDataModel model) {
        return null;
    }
}
