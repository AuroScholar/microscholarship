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
    // String MOBILE_NUMBER = "MOBILE_NUMBER";
    String TRUE = "true";
    String FALSE = "false";
    String PROFILE_IMAGE_PATH = "PROFILE_IMAGE_PATH";
    String DASHBOARD_RES_MODEL = "DASHBOARD_RES_MODEL";
    String QUIZ_RES_MODEL = "QUIZ_TEST_MODEL";
    String AURO_DATA_MODEL = "AURO_DATA_MODEL";
    String MOBILE_NUMBER = "mobile_no";

    int CAMERA_REQUEST_CODE = 007;
    String FAILED = "failed";
    String LANGUAGE_EN = "en";
    String LANGUAGE_HI = "hi";
    String ENGLISH = "English";
    String HINDI = "हिंदी";


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

    interface DashBoardParams {
        String SCHOLAR_ID = "scholr_id";
        String STUDENT_CLASS = "student_class";
        String REGISTRATION_SOURCE = "regitration_source";
        String SHARE_TYPE = "share_type";
        String SHARE_IDENTITY = "share_identity";
        String IS_KYC_UPLOADED = "is_kyc_uploaded";
        String IS_KYC_VERIFIED = "is_kyc_verified";
        String IS_PAYMENT_LASTMONTH = "is_payment_lastmonth";
    }


    interface TeacherProfileParams {
        String MOBILE_NUMBER = "mobile_no";
        String TEACHER_NAME = "teacher_name";
        String TEACHER_EMAIL = "teacher_email";
        String SCHOOL_NAME = "school_name";
        String STATE_ID = "state_id";
        String DISTRICT_ID = "district_id";
        String TEACHER_CLASS = "teacher_class";
        String TEACHER_SUBJECT = "teacher_subject";
    }

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

        String AADHAR_PHONE = "aadhar_phone";
        String AADHAR_NO = "aadhar_no";
        String AADHAR_DOB = "aadhar_dob";
        String AADHAR_NAME = "aadhar_name";
        String SCHOOL_PHONE = "school_phone";
        String SCHOOL_DOB = "school_dob";

        String YES = "Yes";
        String NO = "No";
        String IN_PROCESS = "Inprocess";
        String REJECTED = "Rejected";
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

    interface FriendsLeaderBoard {
        int LEADERBOARD_TYPE = 100;
        int LEADERBOARD_INVITE_TYPE = 101;
        int TRANSACTIONS_ADAPTER = 102;
        String QUIZ_DASHBOARD_WEB = "QUIZ_DASHBOARD_WEB";
        int TEACHERDOCUMENTADAPTER = 103;
        int SELECTMESSAGEADAPTER = 104;
        int SUBJECTADAPTER = 105;
        int CLASSESADAPTER = 106;

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

    interface AzureApiParams {
        String REGISTRATION_ID = "registration_id";
        String EXAM_NAME = "exam_name";
        String QUIZ_ATTEMPT = "quiz_attempt";
        String EKLAVVYA_EXAM_ID = "eklavvya_exam_id";
        String EXAM_FACE_IMG = "exam_face_img";
    }


    interface SdkType {
        int TEACHER_SDK = 100;
        int STUDENT_SDK = 101;
    }

    interface SdkFragmentType {
        int TEACHER_DASHBOARD_FRAGMENT = 100;
        int TEACHER_PROFILE_FRAGMENT = 102;
        int TEACHER_KYC_FRAGMENT = 103;
    }
}
