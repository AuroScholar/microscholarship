package com.auro.scholr.quiz.data.datasource.remote;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.SaveImageReqModel;
import com.auro.scholr.quiz.data.model.response.SaveQuestionResModel;
import com.auro.scholr.quiz.data.repository.QuizRepo;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.TextUtil;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class QuizRemoteDataSourceImp implements QuizRepo.QuizRemoteData {

    QuizRemoteApi quizRemoteApi;// = APIClient.getClient().create(QuizRemoteApi.class);

    public QuizRemoteDataSourceImp( QuizRemoteApi quizRemoteApi) {
        this.quizRemoteApi = quizRemoteApi;
    }




    @Override
    public Single<Response<JsonObject>> getFetchQuizData(AssignmentReqModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.AssignmentApiParams.REGISTRATION_ID, reqModel.getRegistration_id());
        params.put(AppConstant.AssignmentApiParams.EXAM_NAME, reqModel.getExam_name());
        params.put(AppConstant.AssignmentApiParams.QUIZ_ATTEMPT, reqModel.getQuiz_attempt());
        params.put(AppConstant.AssignmentApiParams.SUBJECT, reqModel.getSubject());
        params.put(AppConstant.AssignmentApiParams.EXAMLANG, reqModel.getExamlang());
        return quizRemoteApi.fetchQuizData(params);
    }


    @Override
    public Single<Response<JsonObject>> saveFetchQuizData(SaveQuestionResModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.AssignmentApiParams.EXAM_ASSIGNMENT_ID, reqModel.getExamAssignmentID());
        params.put(AppConstant.AssignmentApiParams.QUESTION_ID, reqModel.getQuestionID());
        params.put(AppConstant.AssignmentApiParams.ANSWER_ID, reqModel.getAnswerID());
        params.put(AppConstant.AssignmentApiParams.QUESTION_SERIAL_NUMBER, reqModel.getQuestionSerialNo());
        return quizRemoteApi.saveQuizData(params);
    }

    @Override
    public Single<Response<JsonObject>> finishQuizApi(SaveQuestionResModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.AssignmentApiParams.EXAM_ASSIGNMENT_ID, reqModel.getExamAssignmentID());
        params.put(AppConstant.AssignmentApiParams.COMPLETE_BY, reqModel.getComplete_by());

        return quizRemoteApi.finishQuizApi(params);
    }

/*    @Override
    public Single<Response<JsonObject>> uploadStudentExamImage(SaveImageReqModel saveQuestionResModel) {
        RequestBody exam_id = RequestBody.create(okhttp3.MultipartBody.FORM,saveQuestionResModel.getExamAssignmentID());
        MultipartBody.Part student_photo = ConversionUtil.INSTANCE.makeMultipartRequestForExamImage(saveQuestionResModel.getImageBytes());
        return quizRemoteApi.uploadImage(exam_id,
                student_photo);
    }*/

    @Override
    public Single<Response<JsonObject>> uploadStudentExamImage(SaveImageReqModel reqModel) {
        RequestBody exam_id = RequestBody.create(MultipartBody.FORM, reqModel.getExamId());
        RequestBody registration_id = RequestBody.create(MultipartBody.FORM, reqModel.getRegistration_id());
        RequestBody is_mobile = RequestBody.create(MultipartBody.FORM, "1");
        RequestBody quiz_id = RequestBody.create(MultipartBody.FORM, reqModel.getQuizId());
        RequestBody img_normal_path = RequestBody.create(MultipartBody.FORM, reqModel.getImgNormalPath());
        RequestBody img_path = RequestBody.create(MultipartBody.FORM, reqModel.getImgPath());
        MultipartBody.Part student_photo = ConversionUtil.INSTANCE.makeMultipartRequestForExamImage(reqModel.getImageBytes());
        return quizRemoteApi.uploadImage(exam_id, registration_id, is_mobile, quiz_id, img_normal_path, img_path,
                student_photo);


       /* RequestBody exam_id = RequestBody.create(okhttp3.MultipartBody.FORM,saveQuestionResModel.getExamAssignmentID());
        MultipartBody.Part student_photo = ConversionUtil.INSTANCE.makeMultipartRequestForExamImage(saveQuestionResModel.getImageBytes());
        return quizRemoteApi.uploadImage(exam_id,
                student_photo);*/
    }

    @Override
    public Single<Response<JsonObject>> getDashboardData(AuroScholarDataModel model) {
        Map<String, String> params = new HashMap<String, String>();
        if (TextUtil.isEmpty(model.getScholrId())) {
            params.put(AppConstant.MOBILE_NUMBER, model.getMobileNumber());
            params.put(AppConstant.DashBoardParams.STUDENT_CLASS, model.getStudentClass());
            params.put(AppConstant.DashBoardParams.REGISTRATION_SOURCE, model.getRegitrationSource());
            params.put(AppConstant.DashBoardParams.PARTNER_SOURCE, model.getPartnerSource());
            params.put(AppConstant.DashBoardParams.REFERRER_MOBILE, model.getShareIdentity());
            AppLogger.e("HomeRemoteDataSourceImp", "Calling  Generic SDK");
            return quizRemoteApi.getDashboardSDKData(params);
        } else {
            params.put(AppConstant.MOBILE_NUMBER, model.getMobileNumber());
            params.put(AppConstant.DashBoardParams.SCHOLAR_ID, model.getScholrId());
            params.put(AppConstant.DashBoardParams.STUDENT_CLASS, model.getStudentClass());
            params.put(AppConstant.DashBoardParams.REGISTRATION_SOURCE, model.getRegitrationSource());
            params.put(AppConstant.DashBoardParams.SHARE_TYPE, model.getShareType());
            params.put(AppConstant.DashBoardParams.SHARE_IDENTITY, model.getShareIdentity());
            params.put(AppConstant.DashBoardParams.IS_EMAIL_VERIFIED, "" + model.isEmailVerified());
            params.put(AppConstant.DashBoardParams.PARTNER_SOURCE, model.getPartnerSource());
            AppLogger.e("HomeRemoteDataSourceImp", "Calling  Scholar SDK");
            return quizRemoteApi.getDashboardData(params);
        }
    }





}
