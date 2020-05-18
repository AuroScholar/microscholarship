package com.auro.scholr.core.network;


public interface URLConstant {

    String BASE_URL = "https://auroscholar.com/api/"; // PRODUCTION
    // String BASE_URL = "http://3.210.192.55:6060/"; // UAT
//    String BASE_URL = "http://14.142.204.99:7070/"; // SIT
    // String BASE_URL = BuildConfig.BASE_URL;

    String DASHBOARD_API = BASE_URL + "dashboard.php";

    String DEMOGRAPHIC_API = BASE_URL + "demographics.php";

    String AZURE_API = BASE_URL + "faceimg.php";


    String UPLOAD_IMAGE_URL = BASE_URL + "kyc.php";
    String GET_ASSIGNMENT_ID = BASE_URL + "statrquizeaction.php ";
    String TEST_URL = "https://assessment.eklavvya.com/exam/StartExam?";
    String PRIVACY_POLICY = "https://auroscholar.com/privacy_policy.php";


}
