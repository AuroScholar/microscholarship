package com.auro.scholr.teacher.data.datasource.remote;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.teacher.data.model.request.TeacherReqModel;
import com.auro.scholr.teacher.data.repository.TeacherRepo;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.Response;

public class TeacherRemoteDataSourceImp implements TeacherRepo.TeacherRemoteData {

    TeacherRemoteApi teacherRemoteApi;

    public TeacherRemoteDataSourceImp(TeacherRemoteApi teacherRemoteApi) {
        this.teacherRemoteApi = teacherRemoteApi;
    }




    @Override
    public Single<Response<JsonObject>> updateTeacherProfileApi(TeacherReqModel model) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.TeacherProfileParams.MOBILE_NUMBER, model.getMobile_no());
        params.put(AppConstant.TeacherProfileParams.TEACHER_NAME, model.getTeacher_name());
        params.put(AppConstant.TeacherProfileParams.TEACHER_EMAIL, model.getTeacher_email());
        params.put(AppConstant.TeacherProfileParams.SCHOOL_NAME, model.getSchool_name());
        params.put(AppConstant.TeacherProfileParams.STATE_ID, model.getState_id());
        params.put(AppConstant.TeacherProfileParams.DISTRICT_ID, model.getDistrict_id());
        params.put(AppConstant.TeacherProfileParams.TEACHER_CLASS, model.getTeacher_class());
        params.put(AppConstant.TeacherProfileParams.TEACHER_SUBJECT, model.getTeacher_subject());
        return teacherRemoteApi.updateTeacherProfileApi(params);

    }

    @Override
    public Single<Response<JsonObject>> getTeacherDashboardApi(String mobileNumber) {
        Map<String, String> params = new HashMap<String, String>();
            params.put(AppConstant.MOBILE_NUMBER,mobileNumber);
            return teacherRemoteApi.getTeacherDashboardApi(params);
    }

    @Override
    public Single<Response<JsonObject>> getProfileTeacherApi(String mobileNumber) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER,mobileNumber);
        return teacherRemoteApi.getProfileTeacherApi(params);
    }
}
