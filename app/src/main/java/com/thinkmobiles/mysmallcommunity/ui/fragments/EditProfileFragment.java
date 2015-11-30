package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.User;

/**
 * Created by dreamfire on 26.11.15.
 */
public class EditProfileFragment extends BaseFragment {
    private User mUser;
    private EditText mFname;
    private EditText mLname;
    private EditText mProffession;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);
        mUser = User.newInstance();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
        setUI();
    }

    private void findUI(View _v) {
        mFname = (EditText) _v.findViewById(R.id.etFname);
        mLname = (EditText) _v.findViewById(R.id.etLname);
        mProffession = (EditText) _v.findViewById(R.id.etProffession);
    }

    private void setUI(){
        mFname.setText(mUser.getFirstName());
        mLname.setText(mUser.getLastName());
        mProffession.setText(mUser.getProffession());
    }
}
