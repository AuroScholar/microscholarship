package com.auro.scholr.core.network;

public interface URLConstant {

    String BASE_URL = "https://auroscholar.com/api/"; // PRODUCTION
    //String BASE_URL = "http://demo.auroscholar.com/api/";  // SIT

   // String ZOHO_BASE_URL = "https://auroscholar.com/api/";

   // String DASHBOARD_API = BASE_URL + "dashboard.php";

    String DASHBOARD_API = BASE_URL + "dashboard_multiple.php";

    String DASHBOARD_SDK_API = BASE_URL + "dashboard_sdk_multiple.php";

    String DEMOGRAPHIC_API = BASE_URL + "demographics.php";

   // String AZURE_API = BASE_URL + "faceimg.php";

    String AZURE_API = BASE_URL + "faceimg_multiple.php";

    String UPLOAD_IMAGE_URL = BASE_URL + "kyc.php";
   // String GET_ASSIGNMENT_ID = BASE_URL + "statrquizeaction.php ";
    String GET_ASSIGNMENT_ID = BASE_URL + "statrquizeaction_multiple.php";
    String TEST_URL = "https://assessment.eklavvya.com/exam/StartExam?";
    String PRIVACY_POLICY = "https://auroscholar.com/privacy_policy.php";

    String TEACHER_PROFILE_UPDATE_API = BASE_URL + "teacher_profile_data_api.php ";

    String GET_PROFILE_UPDATE_API = BASE_URL + "students_score_data_api.php ";

    String GET_PROFILE_TEACHER_API = BASE_URL + "teacher_profile_display_api.php ";

    String GET_TEACHER_PROGRESS_API = BASE_URL + "teacher_progress.php ";

    String TEACHER_KYC_API = BASE_URL + "teacher_kyc_api.php ";

    String INVITE_FRIEND_LIST_API = BASE_URL + "student_referral_data.php";

    String SEND_NOTIFICATION_API = BASE_URL + "push_notification.php";

    String GET_ZOHO_APPOINTMENT = BASE_URL + "get_zoho_slot.php";

    String BOOK_ZOHO_APPOINTMENT = BASE_URL + "book_zoho_slot.php";
    String ACCEPT_STUDENT_INVITE = BASE_URL + "student_challenge_accepted.php";

    String PAYTM_API = BASE_URL + "paytm_wallet_transfer.php";

    String PAYTM_UPI_TRANSFER = BASE_URL + "paytm_upi_transfer.php";

    String  PAYTM_ACCOUNT_TRANSFRER = BASE_URL + "paytm_account_transfer.php";

}
