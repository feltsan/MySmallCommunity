package com.thinkmobiles.mysmallcommunity.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thinkmobiles.mysmallcommunity.ui.fragments.LoginFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.PeopleFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.RecentMessagesFragment;

/**
 * Created by dreamfire on 25.11.15.
 */
public class TabsPageAdapter extends FragmentPagerAdapter {
    private String[] tabs = {"RECENT", "ALL MEMBERS"};

    public TabsPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RecentMessagesFragment();
            case 1:
                return new PeopleFragment();
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
