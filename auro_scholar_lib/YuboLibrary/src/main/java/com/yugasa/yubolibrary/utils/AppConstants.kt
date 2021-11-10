package com.yugasa.yubosdk.utils

object AppConstants {
    var DEVICE_TYPE = "android"
    const val GALLERY_PICTURE = 10
    const val CAMERA_PICTURE = 100
    var API_METHOD_GET = 1
    var API_METHOD_POST = 2
    const val READ_EXTERNAL_STORAGE = 2
    const val REQUEST_READ_PHONE_STATE = 0
    const val GrantPermission = "grantPermission"
    const val Constant_imei_id = "imei_id"
    const val SP_USERID = "userId"
    const val HTLM_MIME_TYPE = "text/html"
    const val String_encoding = "UTF-8"

    /**
     * The Constant APP_SHARED_PREF_NAME.
     */
    const val APP_SHARED_PREF_NAME = "profile"

    /**
     * The _is logging enabled.
     */
    var _isLoggingEnabled = false

    /**
     * The _is file logging enabled.
     */
    var _isFileLoggingEnabled = false

    /**
     * The _is exception logging enabled.
     */
    var _isFileExceptionLoggingEnabled = false

    /**
     * The Constant MB.
     */
    const val MB = 1 * 1024 * 1024

    /**
     * The Constant MAX_BITMAP_CACHE_SIZE.
     */
    const val MAX_MEMORY_CACHE_SIZE = 4 * MB

    /**
     * The Constant MAX_DISK_CACHE_SIZE.
     */
    const val MAX_DISK_CACHE_SIZE = 50 * MB

    /**
     * The Constant GENERIC_DIALOG_TAG.
     */
    const val GENERIC_DIALOG_TAG = "FragmentDialog"
    const val OTP_DELIMITER = "-"

    /***************
     * MEDIA TYPE
     */
    const val SP_KEY_USER_DETAIL = "UserDetail"
    const val SP_KEY_Current_User = "current_user"

    /***************
     * Service Url
     * //      */
    const val SERVICE_URL = "https://admin.helloyubo.com/app/get/"
    const val Service_Method_login = "login"
    const val Service_Method_Demo = "yugasa"
    const val Service_Method_Get_Msg = "message"
    const val Service_Method_Get_Chat = "chat"
    const val Service_Method_Auth= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRJZCI6IjVlNTYxM2UxNDIzODgzMTlkMjhkNGIxMyIsInVzZXJUb2tlbiI6IjgxTnFqdFluUnlrZ1dNeiIsImlhdCI6MTYwODI4NjYwMn0.hg-kK2ygW0Gyw0SYMtDikj33A9yRnit62HWE_V5bwWQ"
}