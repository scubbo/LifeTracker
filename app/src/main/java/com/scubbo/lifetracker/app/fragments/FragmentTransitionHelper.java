package com.scubbo.lifetracker.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.scubbo.lifetracker.app.R;

public class FragmentTransitionHelper {

    private static FragmentTransitionHelper sInstance;
    public static FragmentTransitionHelper getInstance(FragmentActivity activity) {
        if (sInstance == null) {
            sInstance = new FragmentTransitionHelper(activity.getSupportFragmentManager());
        }
        return sInstance;
    }

    private final FragmentManager mFragmentManager;

    private FragmentTransitionHelper(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }


    public void setUpFragmentTransition(Context context,
                                        String originalFragmentTag,
                                        Class newFragmentClass,
                                        String newFragmentTag) {
        setUpFragmentTransition(context, originalFragmentTag, newFragmentClass, newFragmentTag, false, null);
    }

    public void setUpFragmentTransition(Context context,
                                        String originalFragmentTag,
                                        Class newFragmentClass,
                                        String newFragmentTag,
                                        Boolean reversed) {
        setUpFragmentTransition(context, originalFragmentTag, newFragmentClass, newFragmentTag, reversed, null);
    }

    public void setUpFragmentTransition(Context context,
                                        String originalFragmentTag,
                                        Class newFragmentClass,
                                        String newFragmentTag,
                                        Bundle args) {
        setUpFragmentTransition(context, originalFragmentTag, newFragmentClass, newFragmentTag, false, args);
    }

    public void setUpFragmentTransition(Context context,
                                        String originalFragmentTag,
                                        Class newFragmentClass,
                                        String newFragmentTag,
                                        Boolean reversed,
                                        Bundle args) {

        //http://stackoverflow.com/questions/4932462/animate-the-transition-between-fragments
        //http://trickyandroid.com/fragments-translate-animation/
        Fragment originalFragment = mFragmentManager.findFragmentByTag(originalFragmentTag);

        Fragment newFragment;
        if (args == null) {
            newFragment = Fragment.instantiate(context, newFragmentClass.getName());
        } else {
            newFragment = Fragment.instantiate(context, newFragmentClass.getName(), args);
        }

        int anim1, anim2, anim3, anim4;
        if (reversed) {
            anim1 = R.animator.slide_in_left;
            anim2 = R.animator.slide_out_right;
            anim3 = R.animator.slide_in_right;
            anim4 = R.animator.slide_out_left;
        } else {
            anim1 = R.animator.slide_in_right;
            anim2 = R.animator.slide_out_left;
            anim3 = R.animator.slide_in_left;
            anim4 = R.animator.slide_out_right;

        }

        mFragmentManager.beginTransaction()
                .setCustomAnimations(anim1,
                        anim2,
                        anim3,
                        anim4)
                .replace(originalFragment.getId(), newFragment, newFragmentTag)
                .addToBackStack(null).commit();
    }

    public void clearBackStack() {
        mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
