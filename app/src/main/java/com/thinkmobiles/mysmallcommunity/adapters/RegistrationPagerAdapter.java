package com.thinkmobiles.mysmallcommunity.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class RegistrationPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    public RegistrationPagerAdapter(final ArrayList<Fragment> _fragments,
                                    final FragmentManager _fragmentManager) {
        super(_fragmentManager);
        mFragments = _fragments;
    }

    @Override
    public final Fragment getItem(final int _position) {
        return mFragments.get(_position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


}

