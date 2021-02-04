package com.auro.scholr.teacher.domain;

import android.util.Log;
import android.widget.EditText;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.ValidationModel;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.teacher.data.model.SelectResponseModel;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.MonthDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;
import com.auro.scholr.teacher.data.model.request.SelectClassesSubject;
import com.auro.scholr.teacher.data.model.request.SendInviteNotificationReqModel;
import com.auro.scholr.teacher.data.model.request.TeacherReqModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomStudentResModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomTeacherResModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.DateUtil;
import com.auro.scholr.util.TextUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.List;

public class TeacherUseCase {

    public ArrayList<QuizResModel> makeDummyQuizList() {
        ArrayList<QuizResModel> productModelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productModelList.add(new QuizResModel());
        }
        return productModelList;
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


    public List<MonthDataModel> monthDataModelList() {
        List<MonthDataModel> list = new ArrayList<>();

        MonthDataModel model = new MonthDataModel();
        model.setMonth("June 2020");
        list.add(model);

        MonthDataModel model1 = new MonthDataModel();
        model1.setMonth("July 2020");
        list.add(model1);

        MonthDataModel model2 = new MonthDataModel();
        model2.setMonth("August 2020");
        list.add(model2);

        MonthDataModel model3 = new MonthDataModel();
        model3.setMonth("September 2020");
        list.add(model3);

        MonthDataModel model4 = new MonthDataModel();
        model4.setMonth("October 2020");
        list.add(model4);

        MonthDataModel model5 = new MonthDataModel();
        model5.setMonth("November 2020");
        list.add(model5);

        return list;

    }

    public List<MyClassRoomStudentResModel> makeStudentList(List<MyClassRoomStudentResModel> resModelList, int month, int year) {
        List<MyClassRoomStudentResModel> list = new ArrayList<>();
        AppLogger.e("Date  current", "months--" + month + "-Year-" + year);
        for (MyClassRoomStudentResModel model : resModelList) {
            setValues(model);
            if (model.getMonthNumber() <= month && model.getYear() <= year) {
                list.add(model);
            }
        }
        return list;
    }


    private void setValues(MyClassRoomStudentResModel model) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date a = dateFormat.parse(model.getInvite_date());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(a);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            model.setMonthNumber(month);
            model.setYear(year);
            AppLogger.e("Date exception", "months--" + month + "-Year-" + year);
        } catch (Exception e) {
            AppLogger.e("Date exception", "months--" + e.getMessage());
        }
    }

    public List<MonthDataModel> monthDataModelList(String start) {
        int count = 0;
        AppLogger.e("Date DateUtil-", start);
        HashMap<Integer, String> monthHashmap = new HashMap<>();
        monthHashmap.put(1, "January");
        monthHashmap.put(2, "Febuary");
        monthHashmap.put(3, "March");
        monthHashmap.put(4, "April");
        monthHashmap.put(5, "May");
        monthHashmap.put(6, "June");
        monthHashmap.put(7, "July");
        monthHashmap.put(8, "August");
        monthHashmap.put(9, "September");
        monthHashmap.put(10, "October");
        monthHashmap.put(11, "November");
        monthHashmap.put(12, "December");
        List<MonthDataModel> list = new ArrayList<>();
        try {
            // start="2019-06-15 18:21:18";
            Date b = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date a = dateFormat.parse(start);
            Calendar calenda1r = Calendar.getInstance();
            calenda1r.setTime(a);
            int year = calenda1r.get(Calendar.YEAR);
            int month = calenda1r.get(Calendar.MONTH);
            AppLogger.e("Date month name-", year + "--" + month);
            int currentYear = DateUtil.getcurrentYearNumber();
            int currentMonth = DateUtil.getcurrentMonthNumber();
            count = month + 1;
            AppLogger.e("DateUtil count-", count + "--" + month);
            while (year < currentYear) {
                MonthDataModel model = new MonthDataModel();
                model.setMonth(monthHashmap.get(count) + " " + year);
                model.setMonthNumber(count);
                model.setYear(year);
                list.add(model);
                AppLogger.e("DateUtil", year + "--" + monthHashmap.get(count));
                if (count == 12) {
                    year++;
                    count = 1;
                } else {
                    count++;
                }
            }

            while (year == currentYear && count <= (currentMonth + 1)) {
                MonthDataModel model = new MonthDataModel();
                model.setMonth(monthHashmap.get(count) + " " + year);
                model.setMonthNumber(count);
                model.setYear(year);
                list.add(model);
                AppLogger.e("DateUtil", year + "--" + monthHashmap.get(count));
                if (count == 12) {
                    year++;
                    count = 1;
                } else {
                    count++;
                }
            }
          /*  for (Date d : datesBetween(a, b)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                String monthName = new SimpleDateFormat("MMMM").format(calendar.getTime());
               // int year = calendar.get(Calendar.YEAR);
              //  int month = calendar.get(Calendar.MONTH);
                MonthDataModel model = new MonthDataModel();
                model.setMonth(monthName + " " + year);
                model.setMonthNumber(month);
                model.setYear(year);
                list.add(model);
                AppLogger.e("Date month name-", year + "--" + monthName);
            }*/
            //  AppLogger.e("Date convert", "months" + datesBetween(a, b).size());
        } catch (Exception e) {
            AppLogger.e("Date exception", "months--" + e.getMessage());
        }
        return list;

    }


    private List<Date> datesBetween(Date d1, Date d2) {
        List<Date> ret = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(d1);
        while (c.getTimeInMillis() < d2.getTime()) {
            ret.add(c.getTime());
            c.add(Calendar.MONTH, 0);
        }
        return ret;
    }


    public ArrayList<KYCDocumentDatamodel> makeAdapterDocumentList() {

        ArrayList<KYCDocumentDatamodel> kycDocumentList = new ArrayList<>();
        KYCDocumentDatamodel kyc_one = new KYCDocumentDatamodel();
        kyc_one.setDocumentId(AppConstant.DocumentType.ID_PROOF_FRONT_SIDE);
        kyc_one.setDocumentName(AuroApp.getAppContext().getString(R.string.upload_govt_id));
        kyc_one.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_one.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_one.setViewType(AppConstant.FriendsLeaderBoard.TEACHER_DOC_ADAPTER);
        kyc_one.setId_name(AppConstant.DocumentType.GOVT_ID_FRONT);


        KYCDocumentDatamodel kyc_two = new KYCDocumentDatamodel();
        kyc_two.setDocumentId(AppConstant.DocumentType.ID_PROOF_BACK_SIDE);
        kyc_two.setDocumentName(AuroApp.getAppContext().getString(R.string.upload_govt_id_back_side));
        kyc_two.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_two.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_two.setViewType(AppConstant.FriendsLeaderBoard.TEACHER_DOC_ADAPTER);
        kyc_two.setId_name(AppConstant.DocumentType.GOVT_ID_BACK);


        KYCDocumentDatamodel kyc_three = new KYCDocumentDatamodel();
        kyc_three.setDocumentId(AppConstant.DocumentType.SCHOOL_ID_CARD);
        kyc_three.setDocumentName(AuroApp.getAppContext().getString(R.string.upload_school_id));
        kyc_three.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_three.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_three.setViewType(AppConstant.FriendsLeaderBoard.TEACHER_DOC_ADAPTER);
        kyc_three.setId_name(AppConstant.DocumentType.SCHOOL_ID);


        KYCDocumentDatamodel kyc_four = new KYCDocumentDatamodel();
        kyc_four.setDocumentId(AppConstant.DocumentType.UPLOAD_YOUR_PHOTO);
        kyc_four.setDocumentName(AuroApp.getAppContext().getString(R.string.upload_picture));
        kyc_four.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_four.setViewType(AppConstant.FriendsLeaderBoard.TEACHER_DOC_ADAPTER);
        kyc_four.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_four.setId_name(AppConstant.DocumentType.TEACHER_PHOTO);

        if (AppUtil.myClassRoomResModel != null) {
            MyClassRoomTeacherResModel model = AppUtil.myClassRoomResModel;
            if (!TextUtil.isEmpty(model.getGovt_id_front())) {
                kyc_one.setModify(true);
            }

            if (!TextUtil.isEmpty(model.getGovt_id_back())) {
                kyc_two.setModify(true);
            }


            if (!TextUtil.isEmpty(model.getSchool_id_card())) {
                kyc_three.setModify(true);
            }

            if (!TextUtil.isEmpty(model.getTeacher_photo())) {
                kyc_four.setModify(true);
            }
        }

        kycDocumentList.add(kyc_one);
        kycDocumentList.add(kyc_two);
        kycDocumentList.add(kyc_three);
        kycDocumentList.add(kyc_four);

        return kycDocumentList;

    }


    public List<SelectResponseModel> makeListForSelectMessageModel() {

        List<SelectResponseModel> list = new ArrayList<>();
        SelectResponseModel document1 = new SelectResponseModel();
        document1.setMessage("Don't wait, take quiz now & get Scholarship! I know you can do it!");
        document1.setViewType(AppConstant.FriendsLeaderBoard.SELECTMESSAGEADAPTER);
        list.add(document1);


        SelectResponseModel document2 = new SelectResponseModel();
        document2.setMessage("Congratulations on winning Scholarship! Keep learning and make us proud!");
        document2.setViewType(AppConstant.FriendsLeaderBoard.SELECTMESSAGEADAPTER);
        list.add(document2);

        SelectResponseModel document3 = new SelectResponseModel();
        document3.setMessage("You are almost there! Take the quiz again and don't give up!");
        document3.setViewType(AppConstant.FriendsLeaderBoard.SELECTMESSAGEADAPTER);
        list.add(document3);


        return list;
    }


    public List<StateDataModel> readStateData() {
        List<StateDataModel> stateList = new ArrayList<>();
        InputStream inStream = AuroApp.getAppContext().getResources().openRawResource(R.raw.state);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        try {
            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split(",");
                if (colums.length != 7) {
                    AppLogger.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                } else {
                    StateDataModel stateDataModel = new StateDataModel();
                    stateDataModel.setState_code(colums[0].replaceAll("\"", ""));
                    stateDataModel.setState_name(colums[1].replaceAll("\"", ""));
                    stateDataModel.setShort_name(colums[2].replaceAll("\"", ""));
                    stateDataModel.setActive_status(colums[3].replaceAll("\"", ""));
                    stateDataModel.setFlag(colums[6].replaceAll("\"", ""));
                    stateList.add(stateDataModel);
                    AppLogger.d("CSVParser", "state_name" + colums[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stateList;
    }


    public List<DistrictDataModel> readDistrictData() {
        List<DistrictDataModel> districtList = new ArrayList<>();
        InputStream inStream = AuroApp.getAppContext().getResources().openRawResource(R.raw.district);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        try {
            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split(",");
                if (colums.length != 7) {
                    AppLogger.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                } else {
                    DistrictDataModel districtDataModel = new DistrictDataModel();
                    districtDataModel.setState_code(colums[0].replaceAll("\"", ""));
                    districtDataModel.setDistrict_code(colums[1].replaceAll("\"", ""));
                    districtDataModel.setDistrict_name(colums[2].replaceAll("\"", ""));
                    districtDataModel.setActive_status(colums[3].replaceAll("\"", ""));
                    districtDataModel.setFlag(colums[6].replaceAll("\"", ""));
                    districtList.add(districtDataModel);
                    AppLogger.d("CSVParser", "district_name" + colums[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return districtList;
    }

    public List<SelectClassesSubject> selectClass(String classes) {


        List<SelectClassesSubject> list = new ArrayList<>();

        SelectClassesSubject classes1 = new SelectClassesSubject();
        classes1.setText("1st");
        classes1.setSelected(false);
        classes1.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes1);

        SelectClassesSubject classes2 = new SelectClassesSubject();
        classes2.setText("7th");
        classes2.setSelected(false);
        classes2.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes2);

        SelectClassesSubject classes3 = new SelectClassesSubject();
        classes3.setText("2nd");
        classes3.setSelected(false);
        classes3.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes3);

        SelectClassesSubject classes4 = new SelectClassesSubject();
        classes4.setText("8th");
        classes4.setSelected(false);
        classes4.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes4);

        SelectClassesSubject classes12 = new SelectClassesSubject();
        classes12.setText("3rd");
        classes12.setSelected(false);
        classes12.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes12);

        SelectClassesSubject classes5 = new SelectClassesSubject();
        classes5.setText("9th");
        classes5.setSelected(false);
        classes5.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes5);

        SelectClassesSubject classes6 = new SelectClassesSubject();
        classes6.setText("4th");
        classes6.setSelected(false);
        classes6.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes6);

        SelectClassesSubject classes7 = new SelectClassesSubject();
        classes7.setText("10th");
        classes7.setSelected(false);
        classes7.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes7);

        SelectClassesSubject classes8 = new SelectClassesSubject();
        classes8.setText("5th");
        classes8.setSelected(false);
        classes8.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes8);

        SelectClassesSubject classes9 = new SelectClassesSubject();
        classes9.setText("11th");
        classes9.setSelected(false);
        classes9.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes9);

        SelectClassesSubject classes10 = new SelectClassesSubject();
        classes10.setText("6th");
        classes10.setSelected(false);
        classes10.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes10);

        SelectClassesSubject classes11 = new SelectClassesSubject();
        classes11.setText("12th");
        classes11.setSelected(false);
        classes11.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes11);
        if (classes != null) {
            List<String> newData = getStringList(classes);
            if (!newData.isEmpty()) {
                for (int i = 0; i < newData.size(); i++) {
                    for (int j = 0; j < list.size(); j++) {
                        if (newData.get(i).equalsIgnoreCase(list.get(j).getText())) {
                            SelectClassesSubject classes13 = new SelectClassesSubject();
                            classes13.setText(newData.get(i).toString());
                            classes13.setSelected(true);
                            classes13.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
                            list.set(j, classes13);

                        }
                    }
                }
            }
        }
        return list;

    }

    public List<SelectClassesSubject> selectSubject(String Subject) {

        List<SelectClassesSubject> list = new ArrayList<>();
        SelectClassesSubject classes1 = new SelectClassesSubject();
        classes1.setText("Maths");

        classes1.setViewType(AppConstant.FriendsLeaderBoard.SUBJECTADAPTER);
        list.add(classes1);

        SelectClassesSubject classes2 = new SelectClassesSubject();
        classes2.setText("Social Science");

        classes2.setViewType(AppConstant.FriendsLeaderBoard.SUBJECTADAPTER);
        list.add(classes2);

        SelectClassesSubject classes3 = new SelectClassesSubject();
        classes3.setText("English");

        classes3.setViewType(AppConstant.FriendsLeaderBoard.SUBJECTADAPTER);
        list.add(classes3);
        SelectClassesSubject classes4 = new SelectClassesSubject();
        classes4.setText("Science");

        classes4.setViewType(AppConstant.FriendsLeaderBoard.SUBJECTADAPTER);
        list.add(classes4);
        SelectClassesSubject classes5 = new SelectClassesSubject();
        classes5.setText("Hindi");

        classes5.setViewType(AppConstant.FriendsLeaderBoard.SUBJECTADAPTER);
        list.add(classes5);
        SelectClassesSubject classes6 = new SelectClassesSubject();
        classes6.setText("Other");

        classes6.setViewType(AppConstant.FriendsLeaderBoard.SUBJECTADAPTER);
        list.add(classes6);
        if (Subject != null) {
            List<String> newData = getStringList(Subject);
            if (!newData.isEmpty()) {
                for (int i = 0; i < newData.size(); i++) {
                    for (int j = 0; j < list.size(); j++) {
                        if (newData.get(i).equalsIgnoreCase(list.get(j).getText())) {
                            SelectClassesSubject classes13 = new SelectClassesSubject();
                            classes13.setText(newData.get(i).toString());
                            classes13.setSelected(true);
                            classes13.setViewType(AppConstant.FriendsLeaderBoard.SUBJECTADAPTER);
                            list.set(j, classes13);

                        }
                    }
                }
            }
        }
        return list;

    }

    public List<String> getStringList(String data) {
        String[] array = data.split(",");
        List<String> datainlist = new ArrayList<>(Arrays.asList(array));
        return datainlist;
    }


    public ValidationModel checkTeacherProfileValidation(TeacherReqModel reqModel) {
        if (TextUtil.isEmpty(reqModel.getTeacher_name())) {
            return new ValidationModel(false, "Please enter the teacher name");
        } else if (TextUtil.isEmpty(reqModel.getTeacher_email())) {
            return new ValidationModel(false, "Please enter the email");
        } else if (!emailValidation(reqModel.getTeacher_email())) {
            return new ValidationModel(false, "Please enter the valid email");
        } else if (TextUtil.isEmpty(reqModel.getMobile_no())) {
            return new ValidationModel(false, "Please enter the mobile number");
        } else if (TextUtil.isEmpty(reqModel.getTeacher_class())) {
            return new ValidationModel(false, "Please enter the select the class");
        }
        if (TextUtil.isEmpty(reqModel.getTeacher_subject())) {
            return new ValidationModel(false, "Please enter the select the subject");
        }
        if (TextUtil.isEmpty(reqModel.getState_id())) {
            return new ValidationModel(false, "Please select  the state");
        }
        if (TextUtil.isEmpty(reqModel.getDistrict_id())) {
            return new ValidationModel(false, "Please select  the state city");
        }
        return new ValidationModel(true, "");

    }


    public boolean emailValidation(String emailtext) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailtext.trim().matches(emailPattern)) {
            return true;
        } else {

            return false;
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    public boolean checkUploadButtonDoc(ArrayList<KYCDocumentDatamodel> list) {
        String noFileChosen = AuroApp.getAppContext().getString(R.string.no_file_chosen);
        if (!list.get(0).getDocumentFileName().equalsIgnoreCase(noFileChosen) && !list.get(1).getDocumentFileName().equalsIgnoreCase(noFileChosen) &&
                !list.get(2).getDocumentFileName().equalsIgnoreCase(noFileChosen) && !list.get(3).getDocumentFileName().equalsIgnoreCase(noFileChosen)) {
            return true;
        } else {
            return false;
        }

    }

    public ValidationModel validateSendInviteReq(SendInviteNotificationReqModel reqModel) {
        if (TextUtil.isEmpty(reqModel.getNotification_message())) {
            return new ValidationModel(false, "Please select the message");
        }
        return new ValidationModel(true, "");
    }
}
