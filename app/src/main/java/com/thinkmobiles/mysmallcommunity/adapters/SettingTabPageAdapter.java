package com.thinkmobiles.mysmallcommunity.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thinkmobiles.mysmallcommunity.models.User;
import com.thinkmobiles.mysmallcommunity.ui.fragments.EditProfileFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.NotificationFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.ViewNotificationFragment;

/**
 * Created by dreamfire on 26.11.15.
 */
public class SettingTabPageAdapter extends FragmentPagerAdapter {
    private String[] tabs = {"ALL NOTIFICATIONS", "VIEW NOTIFICATION"};

    public SettingTabPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NotificationFragment();
            case 1:
                return new ViewNotificationFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
