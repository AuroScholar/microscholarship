package com.auro.scholr.teacher.data.repository;

import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;
import com.auro.scholr.teacher.data.model.request.TeacherReqModel;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

public interface TeacherRepo {

    interface TeacherRemoteData {

        Single<Response<JsonObject>> updateTeacherProfileApi(TeacherReqModel model);


    }


    interface TeacherDbData {

        Single<Long[]> insertStateList(List<StateDataModel> stateDataModelList);

        Single<Long[]> insertDistrictList(List<DistrictDataModel> stateDataModelList);

        Single<List<StateDataModel>> getStateList();

        Single<List<DistrictDataModel>> getDistrictList();


        Single<List<DistrictDataModel>> getStateDistrictList(String stateCode);

    }

}
