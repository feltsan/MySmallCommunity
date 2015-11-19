package com.thinkmobiles.mysmallcommunity.managers;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;

/**
 * Created by feltsan on 19.11.15.
 */
public class FragmentNavigator {

    //_____________________ Private variables ____________________//
    private FragmentManager mFragmentManager;
    private int              mContainerId;

    /**
     * The method register this class in your activity or fragment
     * @param fragmentManager - fragment manager or child fragment manager
     * @param containerId - id container for fragments
     */
    public void register(FragmentManager fragmentManager, int containerId){
        this.mFragmentManager    = fragmentManager;
        this.mContainerId        = containerId;
    }

    /**
     * The method clear all fragments in fragment manager
     */
    public void clearAllFragments(){
        for(int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i)
            mFragmentManager.popBackStackImmediate();
    }

    /**
     * The method return true - if back stack not empty and true another. And call back stack in fragment manager
     */
    public boolean popBackStack(){
        return mFragmentManager.popBackStackImmediate();
    }

    /**
     * The method work replace fragment with back stack (key = class name fragment)
     * @param baseFragment - fragment for adding
     */
    public void showFragment(BaseFragment baseFragment){
        if (mFragmentManager != null)
            getTransaction()
                    .replace(mContainerId, baseFragment)
                    .addToBackStack(baseFragment.getClass().toString())
                    .commit();
    }

    /**
     * The method work replace fragment without back stack
     * @param baseFragment - fragment for replacing
     */
    public void replaceFragment(BaseFragment baseFragment){
        if (mFragmentManager != null)
            getTransaction()
                    .replace(mContainerId, baseFragment)
                    .commit();
    }

    public void addFragment(BaseFragment baseFragment){
        if (mFragmentManager != null)
            getTransaction()
                    .add(mContainerId, baseFragment)
                    .addToBackStack(baseFragment.getClass().getSimpleName())
                    .commit();
    }

    public void showFragmentWithAnimation(BaseFragment _baseFragment) {
        if (mFragmentManager == null)
            return;


        getTransaction()
                .setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left,
                        R.anim.slide_right_enter, R.anim.slide_right)
                .replace(mContainerId, _baseFragment)
                .addToBackStack(_baseFragment.getClass().toString())
                .commit();

    }

    /**
     * The method return fragment transaction
     */
    private FragmentTransaction getTransaction(){
        return mFragmentManager.beginTransaction();
    }
}
