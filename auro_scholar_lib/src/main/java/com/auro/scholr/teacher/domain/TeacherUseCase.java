package com.auro.scholr.teacher.domain;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.teacher.data.model.SelectResponseModel;
import com.auro.scholr.teacher.data.model.TeacherDocumentModel;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.MonthDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;
import com.auro.scholr.teacher.data.model.request.SelectClassesSubject;
import com.auro.scholr.util.AppLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    public List<TeacherDocumentModel> makeListForTeacherDocumentModel() {

        List<TeacherDocumentModel> list = new ArrayList<>();
        TeacherDocumentModel document1 = new TeacherDocumentModel();
        document1.setUploadDocumentname("Upload Govt. ID");
        document1.setViewType(AppConstant.FriendsLeaderBoard.TEACHERDOCUMENTADAPTER);
        list.add(document1);


        TeacherDocumentModel document2 = new TeacherDocumentModel();
        document2.setUploadDocumentname("Upload Govt. ID Back Side");
        document2.setViewType(AppConstant.FriendsLeaderBoard.TEACHERDOCUMENTADAPTER);
        list.add(document2);

        TeacherDocumentModel document3 = new TeacherDocumentModel();
        document3.setUploadDocumentname("Upload School ID");
        document3.setViewType(AppConstant.FriendsLeaderBoard.TEACHERDOCUMENTADAPTER);
        list.add(document3);

        TeacherDocumentModel document4 = new TeacherDocumentModel();
        document4.setUploadDocumentname("Upload Picture");
        document4.setViewType(AppConstant.FriendsLeaderBoard.TEACHERDOCUMENTADAPTER);
        list.add(document4);
        return list;
    }

    public List<SelectResponseModel> makeListForSelectMessageModel() {

        List<SelectResponseModel> list = new ArrayList<>();
        SelectResponseModel document1 = new SelectResponseModel();
        document1.setMessage("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum.");
        document1.setViewType(AppConstant.FriendsLeaderBoard.SELECTMESSAGEADAPTER);
        list.add(document1);


        SelectResponseModel document2 = new SelectResponseModel();
        document2.setMessage("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum.");
        document2.setViewType(AppConstant.FriendsLeaderBoard.SELECTMESSAGEADAPTER);
        list.add(document2);

        SelectResponseModel document3 = new SelectResponseModel();
        document3.setMessage("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum.");
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

    public List<SelectClassesSubject> selectClass() {
        List<SelectClassesSubject> list = new ArrayList<>();

        SelectClassesSubject classes1 = new SelectClassesSubject();
        classes1.setText("1st");
        classes1.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes1);

        SelectClassesSubject classes2 = new SelectClassesSubject();
        classes2.setText("7th");
        classes2.setSelected(false);
        classes2.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes2);

        SelectClassesSubject classes3 = new SelectClassesSubject();
        classes3.setText("2nd");
        classes3.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes3);

        SelectClassesSubject classes4 = new SelectClassesSubject();
        classes4.setText("8th");
        classes4.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes4);

        SelectClassesSubject classes12 = new SelectClassesSubject();
        classes12.setText("3rd");
        classes12.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes12);

        SelectClassesSubject classes5 = new SelectClassesSubject();
        classes5.setText("9th");
        classes5.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes5);

        SelectClassesSubject classes6 = new SelectClassesSubject();
        classes6.setText("4th");
        classes6.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes6);

        SelectClassesSubject classes7 = new SelectClassesSubject();
        classes7.setText("10th");
        classes7.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes7);

        SelectClassesSubject classes8 = new SelectClassesSubject();
        classes8.setText("5th");
        classes8.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes8);

        SelectClassesSubject classes9 = new SelectClassesSubject();
        classes9.setText("11th");
        classes9.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes9);

        SelectClassesSubject classes10 = new SelectClassesSubject();
        classes10.setText("6th");
        classes10.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes10);

        SelectClassesSubject classes11 = new SelectClassesSubject();
        classes11.setText("12th");
        classes11.setViewType(AppConstant.FriendsLeaderBoard.CLASSESADAPTER);
        list.add(classes11);

        return  list;

    }

    public List<SelectClassesSubject> selectSubject() {
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
        return  list;

    }
   

}
