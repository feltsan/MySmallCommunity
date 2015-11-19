package com.thinkmobiles.mysmallcommunity.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseActivity;
import com.thinkmobiles.mysmallcommunity.ui.fragments.LoginFragment;

/**
 * Created by dreamfire on 17.11.15.
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentNavigator().replaceFragment(new LoginFragment());

    }
}
