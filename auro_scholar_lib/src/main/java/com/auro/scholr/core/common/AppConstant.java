package com.auro.scholr.core.common;

/**
 * Created by AAK on 16-Mar-2019.
 * asif.abrar88@gmail.com
 * 9899982894, 9821900190 India.
 */
public interface AppConstant {

    String SUCCESS = "SUCCESS";
    String ERROR_MSG = "message";
    String PREF_OBJECT = "PREF_OBJECT";

    String DEVICE_TYPE = "DEVICE_TYPE";
    String PLATFORM = "PLATFORM";
    String LANGUAGE = "LANGUAGE";
    String PLATFORM_ANDROID = "ANDROID";
    String DEVICE_ID = "DEVICE_ID";
    String AUTH_TOKEN = "AUTH_TOKEN";
    String MODIFIED_TIME = "MODIFIED_TIME";
    int KYC_BUTTON_CLICK = 001;


    String COMING_FROM = "navigation_control";

    int TERMS_CONDITION_TEXT = 109;
    int PRIVACY_POLICY_TEXT = 110;
    int SELECT_PROFILE_ACTIVITY = 111;
    public static final String PUSH_NOTIFICATION = "pushNotification";

    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    String CONTENT_TYPE = "Content-Type";
    String APPLICATION_JSON = "application/json";

    public final static int LEFT_TO_RIGHT = 1;
    public final static int RIGHT_TO_LEFT = 2;
    public final static int NEITHER_LEFT_NOR_RIGHT = 3;
    public final static int TOP_TO_DOWN = 4;

    interface ResponseConstatnt {
        int RES_200 = 200;
        int RES_400 = 400;
        int RES_401 = 401;
        int RES_FAIL = -1;

    }

    interface FragmentTransition {
        int LEFT_TO_RIGHT = 1;
        int RIGHT_TO_LEFT = 2;
        int NEITHER_LEFT_NOR_RIGHT = 3;
        int TOP_TO_DOWN = 4;
        int DOWN_TO_TOP = 5;

    }


    interface DateFormats {

        String DD_MM_YY = "dd-MM-yy";  // 01-11-2019
        String DD_MM_YYYY = "dd-MM-yyyy";
        String DD_MMM_YYYY = "dd-MMM-yyyy";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy HH:mm:ss";
        String YYYY_MM_DD_HH_MM_AA = "yyyy-MM-dd hh:mm aa";
        String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
        String DD_MMM = "dd-MMM";
        String EE_MMM_d_yyyy = "EEE, MMM d, yyyy";
        String MMM = "MMM";
        String DD = "dd";
        String EEE = "EEE";
        String DD_MMMM_YYYY = "dd MMMM yyyy"; // 02 August 2019
        String dd_MMM_yyyy = "dd MMM, yyyy"; // 02 August 2019
    }

    interface PermissionCode {
        int LOCATION_PERMISSION_CODE = 783;
        int SEARCH_COUNTRY_REQUEST = 200;
        int STORAGE_PERMISSION_CODE = 784;
        int CAMERA_VIDEO_PERMISSION_CODE = 785;
        int READ_CONTACT_PERMISSION_CODE = 786;
        int REQUEST_CHECK_SETTINGS_GPS = 0x1;
        int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
        int REQUEST_ACTION_SETTINGS_GPS = 777;

    }


}
