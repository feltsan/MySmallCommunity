package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.SettingTabPageAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;

/**
 * Created by dreamfire on 26.11.15.
 */
public class SettingFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
        setAdapter();
    }

    private void findUI(View _v) {
        tabLayout = (TabLayout) _v.findViewById(R.id.settingTabs);
        mViewPager = (ViewPager) _v.findViewById(R.id.settingViewpager);
    }

    private void setAdapter(){
        mViewPager.setAdapter(new SettingTabPageAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });
    }
}
