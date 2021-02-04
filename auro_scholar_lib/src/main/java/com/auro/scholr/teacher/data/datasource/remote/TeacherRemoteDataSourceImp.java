package com.auro.scholr.teacher.data.datasource.remote;

import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.teacher.data.model.request.SendInviteNotificationReqModel;
import com.auro.scholr.teacher.data.model.request.TeacherReqModel;
import com.auro.scholr.teacher.data.repository.TeacherRepo;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ConversionUtil;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        params.put(AppConstant.REGISTRATION_SOURCE_UTM, model.getUTM_link());
        params.put(AppConstant.IP_ADDRESS,model.getIp_address());

        return teacherRemoteApi.updateTeacherProfileApi(params);

    }

    @Override
    public Single<Response<JsonObject>> getTeacherDashboardApi(String mobileNumber) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, mobileNumber);
        params.put(AppConstant.PARTNER_SOURCE,AuroApp.getAuroScholarModel().getPartnerSource());
        params.put(AppConstant.REGISTRATION_SOURCE_UTM,AuroApp.getAuroScholarModel().getUTMLink());
        params.put(AppConstant.IP_ADDRESS, AppUtil.getIpAdress(AuroApp.getAuroScholarModel().getActivity()));
        return teacherRemoteApi.getTeacherDashboardApi(params);
    }

    @Override
    public Single<Response<JsonObject>> getTeacherProgressApi(String mobileNumber) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, mobileNumber);
        return teacherRemoteApi.getTeacherProgressApi(params);
    }

    @Override
    public Single<Response<JsonObject>> getProfileTeacherApi(String mobileNumber) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, mobileNumber);
        return teacherRemoteApi.getProfileTeacherApi(params);
    }


    @Override
    public Single<Response<JsonObject>> uploadTeacherKYC(List<KYCDocumentDatamodel> list) {
        RequestBody phonenumber = RequestBody.create(okhttp3.MultipartBody.FORM, AuroApp.getAuroScholarModel().getMobileNumber());
        MultipartBody.Part id_proof_front = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(0));
        MultipartBody.Part id_proof_back = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(1));
        MultipartBody.Part school_id_card = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(2));
        MultipartBody.Part student_photo = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(3));
        return teacherRemoteApi.uploadTeacherKYC(phonenumber,
                id_proof_front,
                id_proof_back,
                school_id_card,
                student_photo);
    }

    @Override
    public Single<Response<JsonObject>> sendInviteNotificationApi(SendInviteNotificationReqModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.SendInviteNotificationApiParam.SENDER_MOBILE_NUMBER, reqModel.getSender_mobile_no());
        params.put(AppConstant.SendInviteNotificationApiParam.RECEIVER_MOBILE_NUMBER, reqModel.getReceiver_mobile_no());
        params.put(AppConstant.SendInviteNotificationApiParam.NOTIFICATION_TITLE, reqModel.getNotification_title());
        params.put(AppConstant.SendInviteNotificationApiParam.NOTIFICATION_MESSAGE, reqModel.getNotification_message());
        return teacherRemoteApi.sendInviteNotificationApi(params);
    }

    @Override
    public Single<Response<JsonObject>> getZohoAppointments() {
        return teacherRemoteApi.getZohoAppointments();
    }

    @Override
    public Single<Response<JsonObject>> bookZohoAppointments(String from_time, String name, String email, String phone_number) {
        return teacherRemoteApi.bookZohoAppointments(from_time, name, email, phone_number);
    }

}
