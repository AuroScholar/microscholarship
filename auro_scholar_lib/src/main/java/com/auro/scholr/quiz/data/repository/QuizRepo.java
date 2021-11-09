package com.auro.scholr.quiz.data.repository;


import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.SaveImageReqModel;
import com.auro.scholr.quiz.data.model.response.SaveQuestionResModel;
import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.Response;


public interface QuizRepo {

    interface QuizRemoteData {
        Single<Response<JsonObject>> getFetchQuizData(AssignmentReqModel params);
        Single<Response<JsonObject>> saveFetchQuizData(SaveQuestionResModel params);
        Single<Response<JsonObject>> finishQuizApi(SaveQuestionResModel params);
        Single<Response<JsonObject>> uploadStudentExamImage(SaveImageReqModel params);
        Single<Response<JsonObject>> getDashboardData(AuroScholarDataModel model);


    }




}
