package com.auro.scholr.core.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ResponseApi {

    public final Status status;
    public final Status apiTypeStatus;
    @Nullable
    public final Object data;


    public ResponseApi(Status status, @Nullable Object data,Status apiTypeStatus ) {
        this.status = status;
        this.data = data;
        this.apiTypeStatus = apiTypeStatus;
    }

    public static ResponseApi loading(Status apiTypeStatus) {
        return new ResponseApi(Status.LOADING, null,apiTypeStatus);
    }

    public static ResponseApi success(@NonNull Object data, Status apiTypeStatus) {
        return new ResponseApi(Status.SUCCESS, data,apiTypeStatus);
    }

    public static ResponseApi fail400(@NonNull Object data, Status apiTypeStatus) {
        return new ResponseApi(Status.FAIL_400, data,apiTypeStatus);
    }

    public static ResponseApi authFail(@NonNull Object data) {
        return new ResponseApi(Status.AUTH_FAIL, null,null);
    }

    public static ResponseApi fail(@NonNull Object data, Status apiTypeStatus) {
        return new ResponseApi(Status.FAIL, data,apiTypeStatus);
    }

    public static ResponseApi failDb(@NonNull String data, Status apiTypeStatus) {
        return new ResponseApi(Status.FAIL_DB, data,apiTypeStatus);
    }

}
