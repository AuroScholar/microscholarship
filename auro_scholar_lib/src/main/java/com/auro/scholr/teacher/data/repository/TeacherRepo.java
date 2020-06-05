package com.auro.scholr.teacher.data.repository;

import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

public interface TeacherRepo {

    interface TeacherRemoteData {
        Single<Response<JsonObject>> getTeacherData(AuroScholarDataModel model);




    }


    interface TeacherDbData {
        Single<Integer> getTeacherDataCount();

    }

}
