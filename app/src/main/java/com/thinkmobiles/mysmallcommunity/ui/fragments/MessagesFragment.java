package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.TabsPageAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;

/**
 * Created by dreamfire on 25.11.15.
 */
public class MessagesFragment extends BaseFragment {
    private TabLayout tabs;
    private ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_messages);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
        setAdapter();
    }

    private void findUI(View _v) {
        tabs = (TabLayout) _v.findViewById(R.id.tabs);
        viewPager = (ViewPager) _v.findViewById(R.id.viewpager);
    }

    private void setAdapter(){
        viewPager.setAdapter(new TabsPageAdapter(getChildFragmentManager()));
        tabs.post(new Runnable() {
            @Override
            public void run() {
                tabs.setupWithViewPager(viewPager);
            }
        });
    }
}
