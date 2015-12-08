package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.SettingRVAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamfire on 28.11.15.
 */
public class SettingsFragment extends BaseFragment {
    private SettingRVAdapter adapter;
    private RecyclerView mRecycler;
    private List<Settings> lists;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_container);
        lists = new ArrayList<>();
        setListSettings();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);

        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setTitle(R.string.settings);
    }

    private void findUI(View _v){
        mRecycler = (RecyclerView) _v.findViewById(R.id.rvContainer);
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(llm);
        adapter = new SettingRVAdapter(getActivity(), lists);
        mRecycler.setAdapter(adapter);
        adapter.setClickListener(onItemClick);
    }

    private View.OnClickListener onItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (mRecycler.getChildAdapterPosition(v)){
                case 0:
                    SettingFragment fragment = new SettingFragment();
                    mActivity.getFragmentNavigator().replaceFragment(fragment);
                    break;
            }
        }
    };

    private void setListSettings(){
        Settings s = new Settings();
        s.setIcon(R.drawable.ic_apps_black_24dp);
        s.setName("Notifications");
        lists.add(s);

        s = new Settings();
        s.setIcon(R.drawable.ic_arrow_back_black_24dp);
        s.setName("About Us");
        lists.add(s);

        s = new Settings();
        s.setIcon(R.drawable.ic_cancel_black_24dp);
        s.setName("Terms & Conditions");
        lists.add(s);

        s = new Settings();
        s.setIcon(R.drawable.ic_check_black_24dp);
        s.setName("Contact admin");
        lists.add(s);

        s = new Settings();
        s.setIcon(R.drawable.ic_cancel_black_24dp);
        s.setName("Help / FAQ");
        lists.add(s);
    }
}
