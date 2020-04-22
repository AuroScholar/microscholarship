package com.auro.scholr.core.common;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.auro.scholr.R;

import static com.auro.scholr.core.common.AppConstant.FragmentTransition.DOWN_TO_TOP;
import static com.auro.scholr.core.common.AppConstant.FragmentTransition.LEFT_TO_RIGHT;
import static com.auro.scholr.core.common.AppConstant.FragmentTransition.NEITHER_LEFT_NOR_RIGHT;
import static com.auro.scholr.core.common.AppConstant.FragmentTransition.RIGHT_TO_LEFT;
import static com.auro.scholr.core.common.AppConstant.FragmentTransition.TOP_TO_DOWN;


public class FragmentUtil {

    private FragmentUtil(){}

    public synchronized static void replaceFragment(Context context, Fragment fragment, int frameLayoutId, boolean removeStack, int animConstant) {

        try {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();


            switch (animConstant) {
                case LEFT_TO_RIGHT:
                    transaction.setCustomAnimations(R.anim.frag_exit_right, R.anim.frag_enter_left);
                    break;

                case RIGHT_TO_LEFT:
                    transaction.setCustomAnimations(R.anim.frag_enter_right, R.anim.frag_exit_left);
                    break;

                case NEITHER_LEFT_NOR_RIGHT:
                    break;

                case TOP_TO_DOWN:
                    transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
                    break;

                case DOWN_TO_TOP:
                    transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
                    break;

                default:
                    break;
            }


            if (removeStack) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction.replace(frameLayoutId, fragment);

            } else {
                transaction.replace(frameLayoutId, fragment);

                transaction.addToBackStack(null);
            }

            transaction.replace(frameLayoutId, fragment);
            transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public synchronized static void removeFragment(Context context, int animConstant, Fragment fragment)
    {
        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        switch (animConstant) {
            case LEFT_TO_RIGHT:
                transaction.setCustomAnimations(R.anim.frag_exit_right, R.anim.frag_enter_left);
                break;

            case RIGHT_TO_LEFT:
                transaction.setCustomAnimations(R.anim.frag_enter_right, R.anim.frag_exit_left);
                break;

            case NEITHER_LEFT_NOR_RIGHT:
                break;

            case TOP_TO_DOWN:
                transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
                break;

            case DOWN_TO_TOP:
                transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
                break;

            default:
                break;
        }
        transaction.remove(fragment)
                .addToBackStack(null)
                .commit();
    }

    public synchronized static void addFragment(Context context, Fragment fragment, int frameLayoutId, int animConstant) {

        try {

            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            switch (animConstant) {
                case LEFT_TO_RIGHT:
                    transaction.setCustomAnimations(R.anim.frag_exit_right, R.anim.frag_enter_left);
                    break;

                case RIGHT_TO_LEFT:
                    transaction.setCustomAnimations(R.anim.frag_enter_right, R.anim.frag_exit_left);
                    break;

                case NEITHER_LEFT_NOR_RIGHT:
                    break;

                case TOP_TO_DOWN:
                    transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
                    break;

                case DOWN_TO_TOP:
                    transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
                    break;

                default:
                    break;
            }

            transaction.add(frameLayoutId, fragment);
            //transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }



    }




    public static void setFragment(Fragment fragment, boolean removeStack, FragmentActivity activity, int mContainer, String tag, int animConstant) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ftTransaction = fragmentManager.beginTransaction();

        switch (animConstant) {
            case LEFT_TO_RIGHT:
                ftTransaction.setCustomAnimations(R.anim.frag_exit_right, R.anim.frag_enter_left);
                break;

            case RIGHT_TO_LEFT:
                ftTransaction.setCustomAnimations(R.anim.frag_enter_right, R.anim.frag_exit_left);
                break;

            case NEITHER_LEFT_NOR_RIGHT:
                break;

            case TOP_TO_DOWN:
                ftTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
                break;

            case DOWN_TO_TOP:
                ftTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
                break;

            default:
                break;
        }



        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if (tag != null)
                ftTransaction.replace(mContainer, fragment, tag);
            else
                ftTransaction.replace(mContainer, fragment);
        } else {
            if (tag != null)
                ftTransaction.replace(mContainer, fragment, tag);
            else
                ftTransaction.replace(mContainer, fragment);
            ftTransaction.addToBackStack(null);
        }
        ftTransaction.commit();
    }


}