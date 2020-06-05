package com.auro.scholr.teacher.domain;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
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
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.TextUtil;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeacherUseCase {


    public ArrayList<QuizResModel> makeDummyQuizList() {
        ArrayList<QuizResModel> productModelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productModelList.add(new QuizResModel());
        }
        return productModelList;
    }



}
