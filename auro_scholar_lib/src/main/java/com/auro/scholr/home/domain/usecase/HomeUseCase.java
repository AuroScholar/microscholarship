package com.auro.scholr.home.domain.usecase;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.ValidationModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.auro.scholr.home.data.model.KYCResItemModel;
import com.auro.scholr.home.data.model.MonthlyScholarShipModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.data.model.SubjectResModel;
import com.auro.scholr.home.data.model.newDashboardModel.ChapterResModel;
import com.auro.scholr.home.data.model.newDashboardModel.QuizTestDataModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeUseCase {


    public ArrayList<QuizResModel> makeDummyQuizList() {
        ArrayList<QuizResModel> productModelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productModelList.add(new QuizResModel());
        }
        return productModelList;
    }


    public ArrayList<KYCDocumentDatamodel> makeAdapterDocumentList(DashboardResModel dashboardResModel) {
        ArrayList<KYCDocumentDatamodel> kycDocumentList = new ArrayList<>();

        KYCDocumentDatamodel kyc_one = new KYCDocumentDatamodel();
        kyc_one.setDocumentId(AppConstant.DocumentType.ID_PROOF_FRONT_SIDE);
        kyc_one.setDocumentName(AuroApp.getAppContext().getString(R.string.id_proof_front_side));
        kyc_one.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_one.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        if (dashboardResModel != null) {
            kyc_one.setModify(dashboardResModel.isModify());
        }
        kyc_one.setId_name(AppConstant.DocumentType.ID_PROOF);
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getIdfront())) {
            kyc_one.setDocumentstatus(true);
            kyc_one.setDocumentUrl(dashboardResModel.getIdfront());
        }


        KYCDocumentDatamodel kyc_two = new KYCDocumentDatamodel();
        kyc_two.setDocumentId(AppConstant.DocumentType.ID_PROOF_BACK_SIDE);
        kyc_two.setDocumentName(AuroApp.getAppContext().getString(R.string.id_proof_back_side));
        kyc_two.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_two.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        if (dashboardResModel != null) {
            kyc_two.setModify(dashboardResModel.isModify());
        }
        kyc_two.setId_name(AppConstant.DocumentType.ID_PROOF_BACK);
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getIdback())) {
            kyc_two.setDocumentstatus(true);
            kyc_two.setDocumentUrl(dashboardResModel.getIdback());
        }

        KYCDocumentDatamodel kyc_three = new KYCDocumentDatamodel();
        kyc_three.setDocumentId(AppConstant.DocumentType.SCHOOL_ID_CARD);
        kyc_three.setDocumentName(AuroApp.getAppContext().getString(R.string.school_id_card));
        kyc_three.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_three.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        if (dashboardResModel != null) {
            kyc_three.setModify(dashboardResModel.isModify());
        }
        kyc_three.setId_name(AppConstant.DocumentType.SCHOOL_ID);
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getSchoolid())) {
            kyc_three.setDocumentstatus(true);
            kyc_three.setDocumentUrl(dashboardResModel.getSchoolid());
        }

        KYCDocumentDatamodel kyc_four = new KYCDocumentDatamodel();
        kyc_four.setDocumentId(AppConstant.DocumentType.UPLOAD_YOUR_PHOTO);
        kyc_four.setDocumentName(AuroApp.getAppContext().getString(R.string.upload_profile_pic));
        kyc_four.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_four.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        if (dashboardResModel != null) {
            kyc_four.setModify(dashboardResModel.isModify());
        }
        kyc_four.setId_name(AppConstant.DocumentType.STUDENT_PHOTO);
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getPhoto())) {
            kyc_four.setDocumentstatus(true);
            kyc_four.setDocumentUrl(dashboardResModel.getPhoto());
        }

        kycDocumentList.add(kyc_one);
        kycDocumentList.add(kyc_two);
        kycDocumentList.add(kyc_three);
        kycDocumentList.add(kyc_four);

        return kycDocumentList;

    }

    public boolean checkUploadButtonStatus(ArrayList<KYCDocumentDatamodel> list) {
        if (list.get(0).isDocumentstatus() && list.get(1).isDocumentstatus() &&
                list.get(2).isDocumentstatus() && list.get(3).isDocumentstatus()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUploadButtonDoc(ArrayList<KYCDocumentDatamodel> list) {
        String noFileChosen = AuroApp.getAppContext().getString(R.string.no_file_chosen);
        if (list.get(0).getDocumentFileName().equalsIgnoreCase(noFileChosen) && list.get(1).getDocumentFileName().equalsIgnoreCase(noFileChosen) &&
                list.get(2).getDocumentFileName().equalsIgnoreCase(noFileChosen) && list.get(3).getDocumentFileName().equalsIgnoreCase(noFileChosen)) {
            return false;
        } else {
            return true;
        }

    }


    public AssignmentReqModel getAssignmentRequestModel(DashboardResModel dashboardResModel, QuizResModel quizResModel) {
        AssignmentReqModel assignmentReqModel = new AssignmentReqModel();
        assignmentReqModel.setExam_name(String.valueOf(quizResModel.getNumber()));
        assignmentReqModel.setQuiz_attempt(String.valueOf((quizResModel.getAttempt() + 1)));
        assignmentReqModel.setRegistration_id(dashboardResModel.getAuroid());
        assignmentReqModel.setSubject(quizResModel.getSubjectName());
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (ViewUtil.getLanguage().equalsIgnoreCase(AppConstant.LANGUAGE_EN)) {
            assignmentReqModel.setExamlang("E");
        } else {
            assignmentReqModel.setExamlang("H");
        }
        return assignmentReqModel;
    }


    public ValidationModel validateDemographic(DemographicResModel demographicResModel) {
        if (demographicResModel.getGender().equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_GENDER)) {
            return new ValidationModel(false, AppConstant.SpinnerType.PLEASE_SELECT_GENDER);
        }
        if (demographicResModel.getSchool_type().equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_SCHOOL)) {
            return new ValidationModel(false, AppConstant.SpinnerType.PLEASE_SELECT_SCHOOL);
        }
        if (demographicResModel.getBoard_type().equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_BOARD)) {
            return new ValidationModel(false, AppConstant.SpinnerType.PLEASE_SELECT_BOARD);
        }
        if (demographicResModel.getLanguage().equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_LANGUAGE_MEDIUM)) {
            return new ValidationModel(false, AppConstant.SpinnerType.PLEASE_SELECT_LANGUAGE_MEDIUM);
        }

        if (demographicResModel.getIsPrivateTution().equalsIgnoreCase(AppConstant.DocumentType.YES)) {
            if (demographicResModel.getPrivateTutionType().equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_PRIVATE_TUTION) || TextUtil.isEmpty(demographicResModel.getPrivateTutionType())) {
                return new ValidationModel(false, AppConstant.SpinnerType.PLEASE_SELECT_PRIVATE_TUTION);
            }
        }


        return new ValidationModel(true, "");

    }

    public boolean checkKycStatus(DashboardResModel dashboardResModel) {
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getPhoto()) && !TextUtil.isEmpty(dashboardResModel.getSchoolid()) &&
                !TextUtil.isEmpty(dashboardResModel.getIdback()) && !TextUtil.isEmpty(dashboardResModel.getIdfront())) {
            return true;
        }

        return false;
    }

    public boolean checkDemographicStatus(DashboardResModel dashboardResModel) {
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getGender()) && !TextUtil.isEmpty(dashboardResModel.getSchool_type()) &&
                !TextUtil.isEmpty(dashboardResModel.getBoard_type()) && !TextUtil.isEmpty(dashboardResModel.getLanguage()) && !TextUtil.isEmpty(dashboardResModel.getIsPrivateTution())) {
            return true;
        }
        return false;
    }


    public int getQuizWonCount(List<QuizResModel> list) {
        int count = 0;
        for (QuizResModel resModel : list) {
            if (resModel.getScorepoints() > 7) {
                count = count + 1;
            }
        }
        for (int i = 0; i < count; i++) {
            list.get(i).setWonStatus(true);
        }

        return count;
    }


    public List<FriendsLeaderBoardModel> makeListForFriendsLeaderBoard(boolean status) {
        List<FriendsLeaderBoardModel> list = new ArrayList<>();
        FriendsLeaderBoardModel leaderBoardModel_01 = new FriendsLeaderBoardModel();
        leaderBoardModel_01.setScholarshipWon("1000");
        leaderBoardModel_01.setStudentName("Manish");
        leaderBoardModel_01.setStudentScore("90%");
        leaderBoardModel_01.setImagePath("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS3nRQYJ_9X8Z3LS-yOMwUNU7YGXXTB6SbEHcSqrgAnM7EoCqh_&usqp=CAU");
        if (status) {
            leaderBoardModel_01.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_TYPE);
        } else {
            leaderBoardModel_01.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_INVITE_TYPE);
        }
        list.add(leaderBoardModel_01);


        FriendsLeaderBoardModel leaderBoardModel_02 = new FriendsLeaderBoardModel();
        leaderBoardModel_02.setScholarshipWon("900");
        leaderBoardModel_02.setStudentName("Kuldip");
        leaderBoardModel_02.setStudentScore("80%");
        leaderBoardModel_02.setImagePath("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTHJi1kXOjoXE_tAA1jT6kjvukiCuH3g8q1BYe2apkTQfppxUBN&usqp=CAU");

        if (status) {
            leaderBoardModel_02.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_TYPE);
        } else {
            leaderBoardModel_02.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_INVITE_TYPE);
        }
        list.add(leaderBoardModel_02);

        FriendsLeaderBoardModel leaderBoardModel_03 = new FriendsLeaderBoardModel();
        leaderBoardModel_03.setScholarshipWon("600");
        leaderBoardModel_03.setStudentName("Rajat");
        leaderBoardModel_03.setStudentScore("60%");
        leaderBoardModel_03.setImagePath("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ-gpVj4yzkUuIi9vRM34su4nqucJrCsVx19sjDHqBXVUYvt5x2&usqp=CAU");
        if (status) {
            leaderBoardModel_03.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_TYPE);
        } else {
            leaderBoardModel_03.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_INVITE_TYPE);
        }
        list.add(leaderBoardModel_03);


        FriendsLeaderBoardModel leaderBoardModel_04 = new FriendsLeaderBoardModel();
        leaderBoardModel_04.setScholarshipWon("500");
        leaderBoardModel_04.setStudentName("Aakash");
        leaderBoardModel_04.setStudentScore("50%");
        leaderBoardModel_04.setImagePath("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQufQ4DogWSfi5pDnsXkUh0cXNX2O0Q0kFGkaM5YTl34eB-87pP&usqp=CAU");
        if (status) {
            leaderBoardModel_04.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_TYPE);
        } else {
            leaderBoardModel_04.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_INVITE_TYPE);
        }
        list.add(leaderBoardModel_04);

        return list;
    }


    public void getTextFromImage(Activity activity, Bitmap bitmap, KYCInputModel kycInputModel, boolean status) {
        TextRecognizer txtRecognizer = new TextRecognizer.Builder(activity).build();
        if (!txtRecognizer.isOperational()) {
            // Shows if your Google Play services is not up to date or OCR is not supported for the device
            AppLogger.e("TAG", "Detector dependencies are not yet available");
        } else {
            // Set the bitmap taken to the frame to perform OCR Operations.
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray items = txtRecognizer.detect(frame);
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock item = (TextBlock) items.valueAt(i);
                strBuilder.append(item.getValue());
                strBuilder.append("\n");
                // The following Process is used to show how to use lines & elements as well
                findNameFromAadharCard(items, item, kycInputModel);
            }

            AppLogger.e("TAG" + " Final String ", strBuilder.toString());
            if (status) {
                kycInputModel.setAadhar_phone(extractPhoneNumber(strBuilder.toString()));
                kycInputModel.setAadhar_dob(getDOB(strBuilder.toString()));
            } else {
                kycInputModel.setSchool_phone(extractPhoneNumber(strBuilder.toString()));
                kycInputModel.setSchool_dob(getDOB(strBuilder.toString()));
            }
            kycInputModel.setAadhar_no(validateAadharNumber(strBuilder.toString()));

        }
    }


    public String validateAadharNumber(String aadharNumber) {
        String aadharDigit = "";
        String[] items = aadharNumber.split("\n");
        for (String str : items) {
            str.replaceAll("\\s", "");
            String numberOnly = str.replaceAll("[^0-9]", "");
            if (numberOnly.length() == 12) {
                aadharDigit = numberOnly;
                AppLogger.e("KYCFragment", "Aadhar number == " + numberOnly);
            }
        }
        return aadharDigit;
    }

    public String extractPhoneNumber(String input) {
        List<String> list = new ArrayList<>();
        String phoneNumber = "";
        Iterator<PhoneNumberMatch> existsPhone = PhoneNumberUtil.getInstance().findNumbers(input, "IN").iterator();
        while (existsPhone.hasNext()) {
            phoneNumber = existsPhone.next().number().toString().replaceAll("[^0-9]", "");
            list.add(phoneNumber);
        }
        return phoneNumber;
    }


    public void findNameFromAadharCard(SparseArray items, TextBlock item, KYCInputModel kycInputModel) {
        String name = "";
        for (int j = 0; j < items.size(); j++) {
            for (int k = 0; k < item.getComponents().size(); k++) {
                //extract scanned text lines here
                Text line = item.getComponents().get(k);
                AppLogger.e("KYCFragment" + " lines", line.getValue());

                String lineString = line.getValue().toLowerCase();
                if (lineString.contains("name")) {
                    String[] names = lineString.split(":");
                    name = names[1];
                    AppLogger.e("KYCFragment", "name here:" + name);
                    if (!name.isEmpty()) {
                        kycInputModel.setAadhar_name(name);
                    }
                } else {
                    if (lineString.contains("dob")) {
                        Text dobline = item.getComponents().get(k - 1);
                        name = dobline.getValue();
                        AppLogger.e("KYCFragment", "dob name here:" + name);
                        if (!name.isEmpty()) {
                            kycInputModel.setAadhar_name(name);
                        }
                    }
                }

            }
        }

        AppLogger.e("KYCFragment", "Final name here:" + name);
    }

    public String getDOB(String desc) {
        ArrayList<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d").matcher(desc);
        while (m.find()) {
            allMatches.add(m.group());
        }

        Matcher match_second = Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(desc);
        while (m.find()) {
            AppLogger.e("KYCFragment", "Date == " + match_second.group(0));
            allMatches.add(match_second.group(0));
        }
        if (TextUtil.checkListIsEmpty(allMatches)) {
            return "";
        } else {
            return allMatches.get(0);
        }

    }

    public boolean checkAllUploadedOrNot(List<KYCResItemModel> list) {
        if (TextUtil.checkListIsEmpty(list)) {
            return false;
        }
        int count = 0;
        for (KYCResItemModel resItemModel : list) {
            if (!TextUtil.isEmpty(resItemModel.getUrl())) {
                count++;
            }
        }
        if (count == 4) {
            return true;
        }
        return false;
    }

    public List<MonthlyScholarShipModel> makeListForMonthlyScholarShip() {

        List<MonthlyScholarShipModel> list = new ArrayList<>();
        MonthlyScholarShipModel month1 = new MonthlyScholarShipModel();
        month1.setMonthly("March ScholarShip");
        month1.setMoney("₹150");
        month1.setApproved("Approved");
        month1.setViewType(AppConstant.FriendsLeaderBoard.TRANSACTIONS_ADAPTER);
        list.add(month1);


        MonthlyScholarShipModel month2 = new MonthlyScholarShipModel();
        month2.setMonthly("December ScholarShip");
        month2.setMoney("₹150");
        month2.setApproved("");
        month2.setViewType(AppConstant.FriendsLeaderBoard.TRANSACTIONS_ADAPTER);
        list.add(month2);
        return list;
    }


    public String getWalletBalance(DashboardResModel resModel) {
        if (resModel != null && !TextUtil.isEmpty(resModel.getApproved_scholarship_money()) && !TextUtil.isEmpty(resModel.getUnapproved_scholarship_money())) {
            int approved = ConversionUtil.INSTANCE.convertStringToInteger(resModel.getApproved_scholarship_money());
            int inprogressAmount = ConversionUtil.INSTANCE.convertStringToInteger(resModel.getUnapproved_scholarship_money());
            int totalAmount = approved + inprogressAmount;
            return String.valueOf(totalAmount);
        }
        return "0";
    }

    public boolean checkAllQuizAreFinishedOrNot(DashboardResModel dashboardResModel) {
        int totalAttempt = 0;
        for (SubjectResModel subjectResModel : dashboardResModel.getSubjectResModelList()) {
            for (QuizResModel quizResModel : subjectResModel.getChapter()) {
                totalAttempt = quizResModel.getAttempt() + totalAttempt;
            }
        }
        if (totalAttempt == 60) {
            return true;
        } else {
            return false;
        }

    }

    public List<QuizTestDataModel> makeDummyList() {
        List<QuizTestDataModel> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                list.add(makeQuizTestModel("Maths", 50));
            }
            if (i == 1) {
                list.add(makeQuizTestModel("Science", 70));
            }
            if (i == 2) {
                list.add(makeQuizTestModel("English", 10));
            }
            if (i == 3) {
                list.add(makeQuizTestModel("Physics", 30));
            }
            if (i == 4) {
                list.add(makeQuizTestModel("Social Science", 60));
            }
        }
        return list;
    }

    private QuizTestDataModel makeQuizTestModel(String subjectName, int percentage) {
        QuizTestDataModel quizTestDataModel = new QuizTestDataModel();
        quizTestDataModel.setSubject(subjectName);
        quizTestDataModel.setScorePercentage(percentage);
        quizTestDataModel.setChapter(makeChapterList());
        return quizTestDataModel;
    }

    private List<ChapterResModel> makeChapterList() {
        List<ChapterResModel> chapterResModelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                chapterResModelList.add(chapterModel(1, 1, 50, "Polynomials", 6));
            }
            if (i == 1) {
                chapterResModelList.add(chapterModel(2, 2, 50, "Natural Numbers", 10));
            }
            if (i == 2) {
                chapterResModelList.add(chapterModel(2, 3, 50, "Prime Numbers", 6));
            }
            if (i == 3) {
                chapterResModelList.add(chapterModel(3, 4, 50, "Odd Numbers", 10));
            }
        }
        return chapterResModelList;
    }

    private ChapterResModel chapterModel(int attmept, int quizNumer, int amount, String name, int points) {
        ChapterResModel chapterResModel = new ChapterResModel();
        chapterResModel.setAttempt(attmept);
        chapterResModel.setNumber(quizNumer);
        chapterResModel.setScholarshipamount(amount);
        chapterResModel.setName(name);
        chapterResModel.setTotalpoints(points);
        return chapterResModel;
    }

}
