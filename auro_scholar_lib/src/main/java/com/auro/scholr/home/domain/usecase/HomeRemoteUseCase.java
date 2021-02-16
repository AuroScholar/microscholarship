package com.auro.scholr.home.domain.usecase;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.NetworkUtil;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.network.NetworkUseCase;
import com.auro.scholr.home.data.model.AcceptInviteRequest;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AssignmentResModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.AzureResModel;
import com.auro.scholr.home.data.model.ChallengeAccepResModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.DynamiclinkResModel;
import com.auro.scholr.home.data.model.FriendListResDataModel;
import com.auro.scholr.home.data.model.FriendRequestList;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.auro.scholr.home.data.model.KYCResListModel;
import com.auro.scholr.home.data.model.NearByFriendList;
import com.auro.scholr.home.data.model.SaveImageReqModel;
import com.auro.scholr.home.data.repository.HomeRepo;
import com.auro.scholr.teacher.data.model.request.SendInviteNotificationReqModel;
import com.auro.scholr.teacher.data.model.response.TeacherResModel;
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
import static com.auro.scholr.core.common.Status.ACCEPT_INVITE_CLICK;
import static com.auro.scholr.core.common.Status.ACCEPT_INVITE_REQUEST;
import static com.auro.scholr.core.common.Status.ASSIGNMENT_STUDENT_DATA_API;
import static com.auro.scholr.core.common.Status.AZURE_API;
import static com.auro.scholr.core.common.Status.DASHBOARD_API;
import static com.auro.scholr.core.common.Status.DEMOGRAPHIC_API;
import static com.auro.scholr.core.common.Status.DYNAMIC_LINK_API;
import static com.auro.scholr.core.common.Status.GRADE_UPGRADE;
import static com.auro.scholr.core.common.Status.FIND_FRIEND_DATA;
import static com.auro.scholr.core.common.Status.FRIENDS_REQUEST_LIST;
import static com.auro.scholr.core.common.Status.INVITE_FRIENDS_LIST;
import static com.auro.scholr.core.common.Status.SEND_FRIENDS_REQUEST;
import static com.auro.scholr.core.common.Status.SEND_INVITE_API;
import static com.auro.scholr.core.common.Status.UPLOAD_EXAM_FACE_API;

public class HomeRemoteUseCase extends NetworkUseCase {
    HomeRepo.DashboardRemoteData dashboardRemoteData;
    Gson gson = new Gson();

    public HomeRemoteUseCase(HomeRepo.DashboardRemoteData dashboardRemoteData) {
        this.dashboardRemoteData = dashboardRemoteData;
    }


    public Single<ResponseApi> sendInviteApi(SendInviteNotificationReqModel reqModel) {
        return dashboardRemoteData.sendInviteNotificationApi(reqModel).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {


                    return handleResponse(response, Status.SEND_INVITE_API);


                } else {

                    return responseFail(Status.SEND_INVITE_API);
                }
            }
        });
    }

    public Single<ResponseApi> acceptInviteApi(SendInviteNotificationReqModel reqModel) {
        return dashboardRemoteData.acceptInviteApi(reqModel).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {
                    return handleResponse(response, Status.ACCEPT_INVITE_CLICK);
                } else {

                    return responseFail(Status.ACCEPT_INVITE_CLICK);
                }
            }
        });
    }

    public Single<ResponseApi> findFriendApi(double lat, double longt, double radius) {
        return dashboardRemoteData.findFriendApi(lat, longt, radius).

                map(new Function<Response<JsonObject>, ResponseApi>() {
                    @Override
                    public ResponseApi apply(Response<JsonObject> response) throws Exception {

                        if (response != null) {
                            return handleResponse(response, Status.FIND_FRIEND_DATA);
                        } else {

                            return responseFail(null);
                        }
                    }
                });
    }


    public Single<ResponseApi> inviteFriendListApi() {

        return dashboardRemoteData.inviteFriendListApi().map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, Status.INVITE_FRIENDS_LIST);

                } else {

                    return responseFail(Status.INVITE_FRIENDS_LIST);
                }
            }
        });
    }

    public Single<ResponseApi> sendFriendRequestApi(int requested_by_id, int requested_user_id, String requested_by_phone, String requested_user_phone) {

        return dashboardRemoteData.sendFriendRequestApi(requested_by_id, requested_user_id,requested_by_phone,requested_user_phone).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, Status.SEND_FRIENDS_REQUEST);

                } else {

                    return responseFail(null);
                }
            }
        });
    }

    public Single<ResponseApi> friendRequestListApi(int requested_user_id) {

        return dashboardRemoteData.friendRequestListApi(requested_user_id).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, Status.FRIENDS_REQUEST_LIST);

                } else {

                    return responseFail(null);
                }
            }
        });
    }

    public Single<ResponseApi> friendAcceptApi(int friend_request_id, String request_status) {

        return dashboardRemoteData.friendAcceptApi(friend_request_id, request_status).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, Status.ACCEPT_INVITE_REQUEST);

                } else {

                    return responseFail(null);
                }
            }
        });
    }

    public Single<ResponseApi> uploadProfileImage(List<KYCDocumentDatamodel> list, KYCInputModel kycInputModel) {

        return dashboardRemoteData.uploadProfileImage(list, kycInputModel).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, Status.UPLOAD_PROFILE_IMAGE);

                } else {

                    return responseFail(Status.UPLOAD_PROFILE_IMAGE);
                }
            }
        });
    }

    public Single<ResponseApi> getAzureData(AssignmentReqModel model) {

        return dashboardRemoteData.getAzureData(model).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, AZURE_API);

                } else {
                    return responseFail(AZURE_API);
                }

            }
        });
    }

    public Single<ResponseApi> getDashboardData(AuroScholarDataModel model) {

        return dashboardRemoteData.getDashboardData(model).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {
                    return handleResponse(response, DASHBOARD_API);

                } else {

                    return responseFail(DASHBOARD_API);
                }
            }
        });
    }


    public Single<ResponseApi> postDemographicData(DemographicResModel demographicResModel) {

        return dashboardRemoteData.postDemographicData(demographicResModel).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, DEMOGRAPHIC_API);

                } else {

                    return responseFail(null);
                }
            }
        });
    }

    public Single<ResponseApi> getAssignmentId(AssignmentReqModel demographicResModel) {

        return dashboardRemoteData.getAssignmentId(demographicResModel).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {

                    return handleResponse(response, ASSIGNMENT_STUDENT_DATA_API);

                } else {

                    return responseFail(null);
                }
            }
        });
    }


    public Single<ResponseApi> upgradeStudentGrade(AuroScholarDataModel model) {

        return dashboardRemoteData.upgradeClass(model).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {
                    return handleResponse(response, GRADE_UPGRADE);
                } else {
                    return responseFail(GRADE_UPGRADE);
                }
            }
        });
    }

    public Single<ResponseApi> getDynamicDataApi(DynamiclinkResModel model) {

        return dashboardRemoteData.getDynamicDataApi(model).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {
                    return handleResponse(response, DYNAMIC_LINK_API);
                } else {
                    return responseFail(DYNAMIC_LINK_API);
                }
            }
        });
    }

    public Single<ResponseApi> uploadStudentExamImage(SaveImageReqModel params) {
        return dashboardRemoteData.uploadStudentExamImage(params).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {
                    return handleResponse(response, UPLOAD_EXAM_FACE_API);
                } else {
                    return responseFail(UPLOAD_EXAM_FACE_API);
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
        String jsonString = new Gson().toJson(response.body());
        if (AuroApp.getAuroScholarModel() != null && AuroApp.getAuroScholarModel().getSdkcallback() != null) {
            AuroApp.getAuroScholarModel().getSdkcallback().callBack(jsonString);
        }

        if (status == DASHBOARD_API) {
            DashboardResModel dashboardResModel = gson.fromJson(response.body(), DashboardResModel.class);
            return ResponseApi.success(dashboardResModel, status);
        } else if (status == Status.UPLOAD_PROFILE_IMAGE) {
            KYCResListModel list = new Gson().fromJson(response.body(), KYCResListModel.class);
            return ResponseApi.success(list, status);
        } else if (status == DEMOGRAPHIC_API) {
            DemographicResModel demographicResModel = new Gson().fromJson(response.body(), DemographicResModel.class);
            return ResponseApi.success(demographicResModel, status);
        } else if (status == ASSIGNMENT_STUDENT_DATA_API) {
            AssignmentResModel assignmentResModel = new Gson().fromJson(response.body(), AssignmentResModel.class);
            return ResponseApi.success(assignmentResModel, status);
        } else if (status == AZURE_API) {
            AzureResModel azureResModel = new Gson().fromJson(response.body(), AzureResModel.class);
            return ResponseApi.success(azureResModel, status);
        } else if (status == INVITE_FRIENDS_LIST) {
            FriendListResDataModel resDataModel = new Gson().fromJson(response.body(), FriendListResDataModel.class);
            return ResponseApi.success(resDataModel, status);
        } else if (status == SEND_INVITE_API) {
            TeacherResModel teacherResModel1 = new Gson().fromJson(response.body(), TeacherResModel.class);
            return ResponseApi.success(teacherResModel1, status);
        } else if (status == ACCEPT_INVITE_CLICK) {
            ChallengeAccepResModel resModel = new Gson().fromJson(response.body(), ChallengeAccepResModel.class);
            return ResponseApi.success(resModel, status);
        } else if (status == GRADE_UPGRADE) {
            DashboardResModel dashboardResModel = gson.fromJson(response.body(), DashboardResModel.class);
            return ResponseApi.success(dashboardResModel, status);
        } else if (status == FIND_FRIEND_DATA) {
            NearByFriendList nearByFriendList = new Gson().fromJson(response.body(), NearByFriendList.class);
            return ResponseApi.success(nearByFriendList, status);
        } else if (status == SEND_FRIENDS_REQUEST) {
            NearByFriendList nearByFriendList = new Gson().fromJson(response.body(), NearByFriendList.class);
            return ResponseApi.success(nearByFriendList, status);
        } else if (status == FRIENDS_REQUEST_LIST) {
            FriendRequestList friendRequestList = new Gson().fromJson(response.body(), FriendRequestList.class);
            return ResponseApi.success(friendRequestList, status);
        } else if (status == ACCEPT_INVITE_REQUEST) {
            AcceptInviteRequest acceptInviteRequest = new Gson().fromJson(response.body(), AcceptInviteRequest.class);
            return ResponseApi.success(acceptInviteRequest, status);
        } else if(status ==  DYNAMIC_LINK_API){
            DynamiclinkResModel dynamiclinkResModel = new Gson().fromJson(response.body(),DynamiclinkResModel.class);
            return ResponseApi.success(dynamiclinkResModel,status);
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
            String errorMessage = AppUtil.errorMessageHandler(AuroApp.getAppContext().getResources().getString(R.string.default_error), errorJson);
            return ResponseApi.fail400(errorMessage, null);
        } catch (Exception e) {
            return ResponseApi.fail(AuroApp.getAppContext().getResources().getString(R.string.default_error), status);
        }
    }


    @Override
    public ResponseApi responseFail(Status status) {
        return ResponseApi.fail(AuroApp.getAppContext().getResources().getString(R.string.default_error), status);
    }

}
