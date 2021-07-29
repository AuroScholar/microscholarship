package com.auro.scholr.home.data.datasource.remote;

import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.CertificateResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.DynamiclinkResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.auro.scholr.home.data.model.SaveImageReqModel;
import com.auro.scholr.home.data.model.SendOtpReqModel;
import com.auro.scholr.home.data.model.StudentProfileModel;
import com.auro.scholr.home.data.model.VerifyOtpReqModel;
import com.auro.scholr.home.data.model.passportmodels.PassportReqModel;
import com.auro.scholr.home.data.repository.HomeRepo;
import com.auro.scholr.teacher.data.model.request.SendInviteNotificationReqModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
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

public class HomeRemoteDataSourceImp implements HomeRepo.DashboardRemoteData {

    HomeRemoteApi homeRemoteApi;

    public HomeRemoteDataSourceImp(HomeRemoteApi homeRemoteApi) {
        this.homeRemoteApi = homeRemoteApi;
    }


    @Override
    public Single<Response<JsonObject>> getDashboardData(AuroScholarDataModel model) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, model.getMobileNumber());
        params.put(AppConstant.DashBoardParams.STUDENT_CLASS, model.getStudentClass());
        params.put(AppConstant.DashBoardParams.REGISTRATION_SOURCE, model.getRegitrationSource());
        params.put(AppConstant.DashBoardParams.PARTNER_SOURCE, model.getPartnerSource());
        params.put(AppConstant.DashBoardParams.USER_PARTNER_ID, model.getUserPartnerid());
        params.put(AppConstant.DashBoardParams.IP_ADDRESS, AppUtil.getIpAdress(AuroApp.getAppContext()));
        params.put(AppConstant.DashBoardParams.BUILD_VERSION, AppUtil.getSDKTag(AuroApp.getAppContext()));
        params.put(AppConstant.DashBoardParams.SCHOOL_NAME, model.getSchoolName());
        params.put(AppConstant.DashBoardParams.BOARD_TYPE, model.getBoardType());
        params.put(AppConstant.DashBoardParams.SCHOOL_TYPE, model.getSchoolType());
        params.put(AppConstant.DashBoardParams.GENDER, model.getGender());
        params.put(AppConstant.DashBoardParams.EMAIL, model.getEmail());
        AppLogger.e("HomeRemoteDataSourceImp", "Calling  Generic SDK");
        return homeRemoteApi.getDashboardSDKData(params);


    }


    @Override
    public Single<Response<JsonObject>> getAzureData(AssignmentReqModel azureReqModel) {

        RequestBody registration_id = RequestBody.create(okhttp3.MultipartBody.FORM, azureReqModel.getRegistration_id());
        RequestBody exam_id = RequestBody.create(okhttp3.MultipartBody.FORM, azureReqModel.getEklavvya_exam_id());
        RequestBody exam_name = RequestBody.create(okhttp3.MultipartBody.FORM, azureReqModel.getExam_name());
        RequestBody quiz_attempt = RequestBody.create(okhttp3.MultipartBody.FORM, azureReqModel.getQuiz_attempt());
        RequestBody subject = RequestBody.create(okhttp3.MultipartBody.FORM, azureReqModel.getSubject());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), azureReqModel.getImageBytes());
        MultipartBody.Part student_photo = MultipartBody.Part.createFormData("exam_face_img", "image.jpg", requestFile);
        return homeRemoteApi.getAzureApiData(registration_id, exam_id, exam_name, quiz_attempt, subject, student_photo);

    }

    @Override
    public Single<Response<JsonObject>> inviteFriendListApi() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, AuroApp.getAuroScholarModel().getMobileNumber());
        return homeRemoteApi.inviteFriendListApi(params);
    }

    @Override
    public Single<Response<JsonObject>> sendInviteNotificationApi(SendInviteNotificationReqModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.SendInviteNotificationApiParam.SENDER_MOBILE_NUMBER, reqModel.getSender_mobile_no());
        params.put(AppConstant.SendInviteNotificationApiParam.RECEIVER_MOBILE_NUMBER, reqModel.getReceiver_mobile_no());
        params.put(AppConstant.SendInviteNotificationApiParam.NOTIFICATION_TITLE, reqModel.getNotification_title());
        params.put(AppConstant.SendInviteNotificationApiParam.NOTIFICATION_MESSAGE, reqModel.getNotification_message());
        return homeRemoteApi.sendInviteNotificationApi(params);
    }

    @Override
    public Single<Response<JsonObject>> uploadProfileImage(List<KYCDocumentDatamodel> list, KYCInputModel kycInputModel) {
        RequestBody phonenumber = RequestBody.create(okhttp3.MultipartBody.FORM, kycInputModel.getUser_phone());
        RequestBody aadhar_phone = RequestBody.create(okhttp3.MultipartBody.FORM, kycInputModel.getAadhar_phone());
        RequestBody aadhar_dob = RequestBody.create(okhttp3.MultipartBody.FORM, kycInputModel.getAadhar_dob());
        RequestBody aadhar_name = RequestBody.create(okhttp3.MultipartBody.FORM, kycInputModel.getAadhar_name());
        RequestBody aadhar_no = RequestBody.create(okhttp3.MultipartBody.FORM, kycInputModel.getAadhar_no());
        RequestBody school_phone = RequestBody.create(okhttp3.MultipartBody.FORM, kycInputModel.getSchool_phone());
        RequestBody school_dob = RequestBody.create(okhttp3.MultipartBody.FORM, kycInputModel.getSchool_dob());
        MultipartBody.Part id_proof_front = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(0));
        MultipartBody.Part id_proof_back = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(1));
        MultipartBody.Part school_id_card = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(2));
        MultipartBody.Part student_photo = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(3));

        return homeRemoteApi.uploadImage(phonenumber,
                aadhar_name,
                aadhar_dob,
                aadhar_phone,
                aadhar_no,
                school_dob,
                school_phone,
                id_proof_front,
                id_proof_back,
                school_id_card,
                student_photo);
    }

    @Override
    public Single<Response<JsonObject>> postDemographicData(DemographicResModel demographicResModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, demographicResModel.getPhonenumber());
        params.put(AppConstant.DemographicType.GENDER, demographicResModel.getGender());
        params.put(AppConstant.DemographicType.BOARD_TYPE, demographicResModel.getBoard_type());
        params.put(AppConstant.DemographicType.SCHOOL_TYPE, demographicResModel.getSchool_type());
        params.put(AppConstant.DemographicType.LANGUAGE, demographicResModel.getLanguage());
        params.put(AppConstant.DemographicType.LATITUDE, demographicResModel.getLatitude());
        params.put(AppConstant.DemographicType.LONGITUDE, demographicResModel.getLongitude());
        params.put(AppConstant.DemographicType.MOBILE_MODEL, demographicResModel.getMobileModel());
        params.put(AppConstant.DemographicType.MOBILE_MANUFACTURER, demographicResModel.getManufacturer());
        params.put(AppConstant.DemographicType.MOBILE_VERSION, demographicResModel.getMobileVersion());
        params.put(AppConstant.DemographicType.IS_PRIVATE_TUTION, demographicResModel.getIsPrivateTution());
        params.put(AppConstant.DemographicType.PRIVATE_TUTION_TYPE, demographicResModel.getPrivateTutionType());
        return homeRemoteApi.postDemographicData(params);
    }

    @Override
    public Single<Response<JsonObject>> getAssignmentId(AssignmentReqModel assignmentReqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.AssignmentApiParams.REGISTRATION_ID, assignmentReqModel.getRegistration_id());
        params.put(AppConstant.AssignmentApiParams.EXAM_NAME, assignmentReqModel.getExam_name());
        params.put(AppConstant.AssignmentApiParams.QUIZ_ATTEMPT, assignmentReqModel.getQuiz_attempt());
        params.put(AppConstant.AssignmentApiParams.EXAMLANG, assignmentReqModel.getExamlang());
        params.put(AppConstant.AzureApiParams.SUBJECT, assignmentReqModel.getSubject());

        return homeRemoteApi.getAssignmentId(params);
    }


    @Override
    public Single<Response<JsonObject>> acceptInviteApi(SendInviteNotificationReqModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.SendInviteNotificationApiParam.CHALLENGE_BY, reqModel.getSender_mobile_no());
        params.put(AppConstant.SendInviteNotificationApiParam.CHALLENGE_TO, reqModel.getReceiver_mobile_no());
        return homeRemoteApi.acceptInviteApi(params);
    }

    @Override
    public Single<Response<JsonObject>> upgradeClass(AuroScholarDataModel model) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, model.getMobileNumber());
        params.put(AppConstant.DashBoardParams.STUDENT_CLASS, model.getStudentClass());
        return homeRemoteApi.gradeUpgrade(params);
    }

    public Single<Response<JsonObject>> findFriendApi(double lat, double longt, double radius) {
        Map<String, Double> params = new HashMap<String, Double>();
        params.put(AppConstant.DemographicType.LATITUDE, lat);
        params.put(AppConstant.DemographicType.LONGITUDE, longt);
        params.put(AppConstant.DemographicType.RADIUS, radius);
        return homeRemoteApi.findFriendApi(params);
    }

    @Override
    public Single<Response<JsonObject>> sendFriendRequestApi(int requested_by_id, int requested_user_id, String requested_by_phone, String requested_user_phone) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("requested_by_id", requested_by_id);
        params.put("requested_user_id", requested_user_id);
        params.put("requested_by_phone", requested_by_phone);
        params.put("requested_user_phone", requested_user_phone);
        return homeRemoteApi.sendFriendRequestApi(params);
    }

    @Override
    public Single<Response<JsonObject>> friendRequestListApi(int requested_user_id) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("requested_user_id", requested_user_id);
        return homeRemoteApi.friendRequestListApi(params);
    }

    @Override
    public Single<Response<JsonObject>> friendAcceptApi(int friend_request_id, String request_status) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("friend_request_id", String.valueOf(friend_request_id));
        params.put("request_status", request_status);
        return homeRemoteApi.friendAcceptApi(params);
    }

    @Override
    public Single<Response<JsonObject>> getDynamicDataApi(DynamiclinkResModel model) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.RefferalApiCode.REFFERAL_MOBILENO, model.getRefferalMobileno());
        params.put(AppConstant.RefferalApiCode.REFFER_MOBILENO, model.getRefferMobileno());
        params.put(AppConstant.RefferalApiCode.SOURCE, model.getSource());
        params.put(AppConstant.RefferalApiCode.NAVIGATION_TO, model.getNavigationTo());
        params.put(AppConstant.RefferalApiCode.REFFER_TYPE, model.getReffer_type());
        return homeRemoteApi.getRefferalapi(params);
    }

    @Override
    public Single<Response<JsonObject>> uploadStudentExamImage(SaveImageReqModel reqModel) {
        RequestBody exam_id = RequestBody.create(okhttp3.MultipartBody.FORM, reqModel.getExamId());
        RequestBody registration_id = RequestBody.create(okhttp3.MultipartBody.FORM, reqModel.getRegistration_id());
        RequestBody is_mobile = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
        RequestBody quiz_id = RequestBody.create(okhttp3.MultipartBody.FORM, reqModel.getQuizId());
        RequestBody image_normal_path = RequestBody.create(okhttp3.MultipartBody.FORM, reqModel.getImgNormalPath());
        RequestBody image_path = RequestBody.create(okhttp3.MultipartBody.FORM, reqModel.getImgPath());
        MultipartBody.Part student_photo = ConversionUtil.INSTANCE.makeMultipartRequestForExamImage(reqModel.getImageBytes());
        return homeRemoteApi.uploadImage(exam_id, registration_id, is_mobile, quiz_id, image_normal_path, image_path,
                student_photo);
    }

    @Override
    public Single<Response<JsonObject>> passportApi(PassportReqModel reqModel) {
        Map<String, String> headers = new HashMap<>();
        return homeRemoteApi.passportApi(headers, reqModel);
    }


    @Override
    public Single<Response<JsonObject>> studentUpdateProfile(StudentProfileModel model) {
        RequestBody phonenumber = RequestBody.create(okhttp3.MultipartBody.FORM, model.getPhonenumber());
        RequestBody firstName = RequestBody.create(okhttp3.MultipartBody.FORM, model.getUserName());
        RequestBody gender = RequestBody.create(okhttp3.MultipartBody.FORM, model.getGender());
        RequestBody schoolType = RequestBody.create(okhttp3.MultipartBody.FORM, model.getSchool_type());
        RequestBody boardType = RequestBody.create(okhttp3.MultipartBody.FORM, model.getBoard_type());
        RequestBody language = RequestBody.create(okhttp3.MultipartBody.FORM, model.getLanguage());
        RequestBody latitude = RequestBody.create(okhttp3.MultipartBody.FORM, model.getLatitude());
        RequestBody longitude = RequestBody.create(okhttp3.MultipartBody.FORM, model.getLongitude());
        RequestBody mobileModel = RequestBody.create(okhttp3.MultipartBody.FORM, model.getMobile_model());
        RequestBody manufacturer = RequestBody.create(okhttp3.MultipartBody.FORM, model.getManufacturer());
        RequestBody mobileVersion = RequestBody.create(okhttp3.MultipartBody.FORM, model.getMobileVersion());
        RequestBody isPrivateTutor = RequestBody.create(okhttp3.MultipartBody.FORM, model.getIsPrivateTution());
        RequestBody privateTuterType = RequestBody.create(okhttp3.MultipartBody.FORM, model.getPrivateTutionType());
        RequestBody getEmailId = RequestBody.create(okhttp3.MultipartBody.FORM, model.getEmailID());
        MultipartBody.Part id_proof_front = ConversionUtil.INSTANCE.makeMultipartRequestProfile(model.getImageBytes());
        return homeRemoteApi.studentUpdateProfile(phonenumber, firstName, getEmailId, gender, schoolType, boardType, language, mobileModel, manufacturer, mobileVersion, latitude, longitude, isPrivateTutor, privateTuterType, id_proof_front);
    }


    @Override
    public Single<Response<JsonObject>> getCertificateApi(CertificateResModel model) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, model.getMobileNumber());
        params.put(AppConstant.DashBoardParams.RESGISTRATION_ID, model.getRegistrationId());
        if (!TextUtil.isEmpty(model.getStudentName())) {
            params.put(AppConstant.DashBoardParams.STUDENT_NAME, model.getStudentName());
        }
        return homeRemoteApi.getCertificateApi(params);
    }


    @Override
    public Single<Response<JsonObject>> sendOtpHomeRepo(SendOtpReqModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.SendOtpRequest.PHONENUMBER, reqModel.getMobileNo());
        params.put(AppConstant.SendOtpRequest.USER_TYPE, AppConstant.userTypeLogin.API_PARAM_STUDENT);
        return homeRemoteApi.sendOTP(params);
    }

    @Override
    public Single<Response<JsonObject>> verifyOtpHomeRepo(VerifyOtpReqModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.SendOtpRequest.PHONENUMBER, reqModel.getMobileNumber());
        params.put(AppConstant.SendOtpRequest.OTPVERIFY, reqModel.getOtpVerify());
        return homeRemoteApi.verifyOTP(params);
    }

}
