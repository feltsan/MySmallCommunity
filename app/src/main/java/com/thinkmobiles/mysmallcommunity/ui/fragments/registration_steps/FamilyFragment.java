package com.thinkmobiles.mysmallcommunity.ui.fragments.registration_steps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.User;

/**
 * Created by feltsan on 23.11.15.
 */
public class FamilyFragment extends BaseFragment implements View.OnClickListener{
    private AppCompatRadioButton family, maried, single;
    private User mUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_family_steps);

        mUser = User.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        findUI();
        setListener();
        return view;
    }

    private void findUI(){
        family = $(R.id.rb_family_FFS);
        maried = $(R.id.rb_maried_FFS);
        single = $(R.id.rb_single_FFS);
    }

    private void setListener(){
        family.setOnClickListener(this);
        maried.setOnClickListener(this);
        single.setOnClickListener(this);
    }

    private void setAllUncheck(){
        family.setChecked(false);
        maried.setChecked(false);
        single.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_family_FFS:
                setAllUncheck();
                family.setChecked(true);
                mUser.setFamilyStatus("family");
                break;

            case R.id.rb_maried_FFS:
                setAllUncheck();
                maried.setChecked(true);
                mUser.setFamilyStatus("maried");
                break;

            case R.id.rb_single_FFS:
                setAllUncheck();
                single.setChecked(true);
                mUser.setFamilyStatus("single");
                break;

        }
    }
}
