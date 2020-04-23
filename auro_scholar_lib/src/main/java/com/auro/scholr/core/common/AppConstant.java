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
    String MOBILE_NUMBER = "MOBILE_NUMBER";
    String TRUE = "true";
    String FALSE = "false";
    String PROFILE_IMAGE_PATH = "PROFILE_IMAGE_PATH";
    String DASHBOARD_RES_MODEL = "DASHBOARD_RES_MODEL";
    String QUIZ_RES_MODEL = "QUIZ_TEST_MODEL";
    String AURO_DATA_MODEL = "AURO_DATA_MODEL";
    String PHONE_NUMBER = "Phonenumber";
    int CAMERA_REQUEST_CODE = 007;
    String FAILED = "failed";


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
        String YYYY_MM_DD = "yyyy-MM-dd";
        String DD_MMM = "dd-MMM";
        String DD_MMMM_YYYY = "dd MMMM yyyy"; // 02 August 2019
        String dd_MMM_yyyy = "dd MMM, yyyy"; // 02 August 2019
    }

    interface DocumentType {
        int ID_PROOF_BACK_SIDE = 783;
        int ID_PROOF_FRONT_SIDE = 784;
        int SCHOOL_ID_CARD = 785;
        int UPLOAD_YOUR_PHOTO = 786;

        String ID_PROOF = "id_proof";
        String ID_PROOF_BACK = "id_proof_back";
        String SCHOOL_ID = "school_id_card";
        String STUDENT_PHOTO = "student_photo";
    }


    interface DemographicType {
        String GENDER = "gender";
        String SCHOOL_TYPE = "school_type";
        String BOARD_TYPE = "board_type";
        String LANGUAGE = "language";
    }

    interface ScreenType {
        String QUIZ_DASHBOARD = "QUIZ_DASHBOARD";
        String QUIZ_DASHBOARD_WEB = "QUIZ_DASHBOARD_WEB";

    }

    interface SpinnerType {
        String PLEASE_SELECT_GENDER = "Please Select Gender";
        String PLEASE_SELECT_LANGUAGE_MEDIUM = "Please Select Language Medium";
        String PLEASE_SELECT_BOARD = "Please Select Board";
        String PLEASE_SELECT_SCHOOL = "Please Select School";
    }


    interface AssignmentApiParams {
        String REGISTRATION_ID = "registration_id";
        String EXAM_NAME = "exam_name";
        String QUIZ_ATTEMPT = "quiz_attempt";
        String EXAMLANG = "examlang";
    }

}
