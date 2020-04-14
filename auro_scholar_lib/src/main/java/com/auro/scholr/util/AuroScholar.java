package com.auro.scholr.util;

import android.content.Context;
import android.os.Bundle;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.FragmentUtil;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;

public class AuroScholar {

    public static void openQuizFragment(Context mContext, String mobileNumber, int fragmentContainerUiId) {
        QuizHomeFragment quizHomeFragment = new QuizHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.MOBILE_NUMBER, mobileNumber);
        quizHomeFragment.setArguments(bundle);
        FragmentUtil.replaceFragment(mContext, quizHomeFragment, fragmentContainerUiId, false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }


}
