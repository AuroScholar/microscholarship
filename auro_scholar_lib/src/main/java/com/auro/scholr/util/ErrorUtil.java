package com.auro.scholr.util;

import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.network.ErrorResponseModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.auro.scholr.R;
import com.auro.scholr.core.network.ErrorDetail;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Response;

public class ErrorUtil {

    public static String errorMessageHandler(Response<JsonObject> response) {

        JSONObject errorJson = null;
        try {
            if(response!=null)
            {
                Gson gson = new Gson();

                ErrorResponseModel errorResponseModel = gson.fromJson(response.errorBody().string(), ErrorResponseModel.class);

                if (errorResponseModel != null) {

                    if(errorResponseModel.getErrorDetails() != null){

                        List<ErrorDetail> errorDetails = errorResponseModel.getErrorDetails();

                        if(!errorDetails.isEmpty()) {

                            ErrorDetail errorDetail = errorDetails.get(0);

                            if (errorDetail != null) {

                                return errorDetail.getDescription();

                            } else {

                                return AuroApp.getAppContext().getString(R.string.default_error);

                            }
                        }else {
                            return AuroApp.getAppContext().getString(R.string.default_error);

                        }

                    } else {

                        return errorResponseModel.getMessage();

                    }


                } else {

                    return AuroApp.getAppContext().getString(R.string.default_error);

                }



               /* String responseData = response.errorBody().string();
                if(responseData!=null && !responseData.equals(""))
                {
                    errorJson = new JSONObject(responseData);
                    return errorJson.optString(ERROR_MSG);
                }
                else {
                    return AuroApp.getAppContext().getString(R.string.default_error);
                }*/
            }
            else
            {
                return AuroApp.getAppContext().getString(R.string.default_error);
            }

        } catch (Exception e) {
            return AuroApp.getAppContext().getString(R.string.default_error);
        }
    }

}
