package com.auro.scholr.quiz.domain;

import android.content.Context;

import com.auro.scholr.R;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.quiz.data.model.QuizQuestionResponse;
import com.auro.scholr.quiz.data.repository.QuizRepo;
import com.auro.scholr.teacher.data.model.SelectResponseModel;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class QuizNativeUseCase {
    QuizRepo.QuizRemoteData quizRemoteDataRemoteData;
    Gson gson = new Gson();


    public List<SelectResponseModel> makeListForQuizMessageModel() {

        List<SelectResponseModel> list = new ArrayList<>();
        SelectResponseModel quiz1 = new SelectResponseModel();
        quiz1.setMessage("B.C. Matthews Hall");
        quiz1.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        list.add(quiz1);


        SelectResponseModel quiz2 = new SelectResponseModel();
        quiz2.setMessage("Carl A. Pollock Hall");
        quiz2.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        list.add(quiz2);

        SelectResponseModel quiz3 = new SelectResponseModel();
        quiz3.setMessage("I.L Neilson Hall");
        quiz3.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        list.add(quiz3);

        SelectResponseModel quiz4 = new SelectResponseModel();
        quiz4.setMessage("Douglas Wright Engineering Building");
        quiz4.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        list.add(quiz4);


        return list;
    }


    public List<QuizQuestionResponse> setDataInquestionList(Context context) {
        List<QuizQuestionResponse> listQuestion = new ArrayList<>();

        QuizQuestionResponse mquizQuestion1 = new QuizQuestionResponse();
        mquizQuestion1.setQuestion("Q. Jenny ___________ tired");
        mquizQuestion1.setSaveAndNext(false);
        mquizQuestion1.setQuestionImage(context.getResources().getDrawable(R.drawable.ic_clock_image));
        mquizQuestion1.setSubjecTtitle("English");

        List<SelectResponseModel> questionOption1 = new ArrayList<>();
        SelectResponseModel quiz11 = new SelectResponseModel();
        quiz11.setMessage("be");
        quiz11.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption1.add(quiz11);

        SelectResponseModel quiz12 = new SelectResponseModel();
        quiz12.setMessage("is");
        quiz12.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption1.add(quiz12);

        SelectResponseModel quiz13 = new SelectResponseModel();
        quiz13.setMessage("has");
        quiz13.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption1.add(quiz13);

        SelectResponseModel quiz14 = new SelectResponseModel();
        quiz14.setMessage("have");
        quiz14.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption1.add(quiz14);

        mquizQuestion1.setList(questionOption1);


        QuizQuestionResponse mquizQuestion2 = new QuizQuestionResponse();

        mquizQuestion2.setQuestion("Q. ___________ is she?\" \"She's my friend from London ");
        mquizQuestion2.setSaveAndNext(false);
        mquizQuestion2.setSubjecTtitle("English");
        mquizQuestion2.setQuestionImage(context.getResources().getDrawable(R.drawable.ic_clock_image));


        List<SelectResponseModel> questionOption2 = new ArrayList<>();

        SelectResponseModel quiz21 = new SelectResponseModel();
        quiz21.setMessage("Who");
        quiz21.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);


        SelectResponseModel quiz22 = new SelectResponseModel();
        quiz22.setMessage("Why");
        quiz22.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);


        SelectResponseModel quiz23 = new SelectResponseModel();
        quiz23.setMessage("Which");
        quiz23.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);


        SelectResponseModel quiz24 = new SelectResponseModel();
        quiz24.setMessage("What");
        quiz24.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);

        questionOption2.add(quiz21);
        questionOption2.add(quiz22);
        questionOption2.add(quiz23);
        questionOption2.add(quiz24);
        mquizQuestion2.setList(questionOption2);


        QuizQuestionResponse mquizQuestion3 = new QuizQuestionResponse();
        mquizQuestion3.setQuestion("Q. Today is Wednesday. Yesterday it ___________ Tuesday.");
        mquizQuestion3.setSaveAndNext(false);
        mquizQuestion3.setQuestionImage(context.getResources().getDrawable(R.drawable.ic_clock_image));
        mquizQuestion3.setSubjecTtitle("English");

        List<SelectResponseModel> questionOption3 = new ArrayList<>();
        SelectResponseModel quiz31 = new SelectResponseModel();
        quiz31.setMessage("were");
        quiz31.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption3.add(quiz31);

        SelectResponseModel quiz32 = new SelectResponseModel();
        quiz32.setMessage("is");
        quiz32.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption3.add(quiz32);

        SelectResponseModel quiz33 = new SelectResponseModel();
        quiz33.setMessage("be");
        quiz33.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption3.add(quiz33);

        SelectResponseModel quiz34 = new SelectResponseModel();
        quiz34.setMessage("was");
        quiz34.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption3.add(quiz34);
        mquizQuestion3.setList(questionOption3);

        QuizQuestionResponse mquizQuestion4 = new QuizQuestionResponse();
        mquizQuestion4.setQuestion("Q. It's Thursday today. Tomorrow it ___________ Friday.");
        mquizQuestion4.setSaveAndNext(false);
        mquizQuestion4.setQuestionImage(context.getResources().getDrawable(R.drawable.ic_clock_image));
        mquizQuestion4.setSubjecTtitle("English");


        List<SelectResponseModel> questionOption4 = new ArrayList<>();
        SelectResponseModel quiz41 = new SelectResponseModel();
        quiz41.setMessage("be");
        quiz41.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption4.add(quiz41);

        SelectResponseModel quiz42 = new SelectResponseModel();
        quiz42.setMessage("was");
        quiz42.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption4.add(quiz42);

        SelectResponseModel quiz43 = new SelectResponseModel();
        quiz43.setMessage("will be");
        quiz43.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption4.add(quiz43);

        SelectResponseModel quiz44 = new SelectResponseModel();
        quiz44.setMessage("will");
        quiz44.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption4.add(quiz44);
        mquizQuestion4.setList(questionOption4);


        QuizQuestionResponse mquizQuestion5 = new QuizQuestionResponse();
        mquizQuestion5.setQuestion("Q. ___________ lots of animals in the zoo.");
        mquizQuestion5.setSaveAndNext(false);
        mquizQuestion5.setQuestionImage(context.getResources().getDrawable(R.drawable.ic_clock_image));
        mquizQuestion5.setSubjecTtitle("English");

        List<SelectResponseModel> questionOption5 = new ArrayList<>();
        SelectResponseModel quiz51 = new SelectResponseModel();
        quiz51.setMessage("There");
        quiz51.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption5.add(quiz51);

        SelectResponseModel quiz52 = new SelectResponseModel();
        quiz52.setMessage("There is");
        quiz52.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption5.add(quiz52);

        SelectResponseModel quiz53 = new SelectResponseModel();
        quiz53.setMessage("There are");
        quiz53.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption5.add(quiz53);

        SelectResponseModel quiz54 = new SelectResponseModel();
        quiz54.setMessage("There aren't");
        quiz54.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption5.add(quiz54);
        mquizQuestion5.setList(questionOption5);


        QuizQuestionResponse mquizQuestion6 = new QuizQuestionResponse();
        mquizQuestion6.setQuestion("Q. How many people ___________ in your family?");
        mquizQuestion6.setSaveAndNext(false);
        mquizQuestion6.setQuestionImage(context.getResources().getDrawable(R.drawable.ic_clock_image));
        mquizQuestion6.setSubjecTtitle("English");

        List<SelectResponseModel> questionOption6 = new ArrayList<>();
        SelectResponseModel quiz61 = new SelectResponseModel();
        quiz61.setMessage("are there");
        quiz61.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption6.add(quiz61);

        SelectResponseModel quiz62 = new SelectResponseModel();
        quiz62.setMessage("is there");
        quiz62.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption6.add(quiz62);

        SelectResponseModel quiz63 = new SelectResponseModel();
        quiz63.setMessage("Member of high class society");
        quiz63.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption6.add(quiz63);

        SelectResponseModel quiz64 = new SelectResponseModel();
        quiz64.setMessage("Complain give verbally");
        quiz64.setViewType(AppConstant.QuizTestNativeScreen.QUIZOPTIONADAPTER);
        questionOption6.add(quiz64);
        mquizQuestion6.setList(questionOption6);

        listQuestion.add(mquizQuestion1);
        listQuestion.add(mquizQuestion2);
        listQuestion.add(mquizQuestion3);
        listQuestion.add(mquizQuestion4);
        listQuestion.add(mquizQuestion5);
        listQuestion.add(mquizQuestion6);

        return listQuestion;
    }

    public AssignmentReqModel getAssignmentRequestModel(DashboardResModel dashboardResModel, QuizResModel quizResModel) {
        AssignmentReqModel assignmentReqModel = new AssignmentReqModel();
        assignmentReqModel.setExam_name(String.valueOf(quizResModel.getNumber()));
        assignmentReqModel.setQuiz_attempt(String.valueOf((quizResModel.getAttempt() + 1)));
        assignmentReqModel.setRegistration_id(dashboardResModel.getAuroid());
        assignmentReqModel.setSubject(quizResModel.getSubjectName());
        if (ViewUtil.getLanguage().equalsIgnoreCase(AppConstant.LANGUAGE_EN)) {
            assignmentReqModel.setExamlang("E");
        } else {
            assignmentReqModel.setExamlang("H");
        }
        return assignmentReqModel;
    }

    public boolean checkDemographicStatus(DashboardResModel dashboardResModel) {
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getGender()) && !TextUtil.isEmpty(dashboardResModel.getSchool_type()) &&
                !TextUtil.isEmpty(dashboardResModel.getBoard_type()) && !TextUtil.isEmpty(dashboardResModel.getLanguage()) && !TextUtil.isEmpty(dashboardResModel.getIsPrivateTution())) {
            return true;
        }
        return false;
    }

}
