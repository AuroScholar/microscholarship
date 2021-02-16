package com.auro.scholr.util;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public enum ConversionUtil {
    INSTANCE;

    public int convertStringToInteger(String value) {
        int val = 0;
        try {
            if (value != null && !value.isEmpty()) {
                val = Integer.parseInt(value);
                return val;
            } else {
                return val;
            }
        } catch (Exception e) {
            return val;
        }
    }

    public double convertStringToDouble(String value) {
        double val = 0;
        try {
            if (value != null && !value.isEmpty()) {
                val = Double.parseDouble(value);
                return val;
            } else {
                return val;
            }
        } catch (Exception e) {
            return val;
        }
    }


    public MultipartBody.Part makeMultipartRequest(KYCDocumentDatamodel kycDocumentDatamodel) {
        if (kycDocumentDatamodel.getImageBytes() != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), kycDocumentDatamodel.getImageBytes());
            return MultipartBody.Part.createFormData(kycDocumentDatamodel.getId_name(), "image.jpg", requestFile);
        } else {
            return null;
        }
    }

    public MultipartBody.Part makeMultipartRequestForExamImage(byte[] bytes) {
        if (bytes!= null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            return MultipartBody.Part.createFormData(AppConstant.AssignmentApiParams.EXAM_FACE_IMAGE, "image.jpg", requestFile);
        } else {
            return null;
        }
    }

}
