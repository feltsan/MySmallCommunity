package com.thinkmobiles.mysmallcommunity.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseActivity;
import com.thinkmobiles.mysmallcommunity.managers.ParseManager;
import com.thinkmobiles.mysmallcommunity.ui.fragments.LoginFragment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by dreamfire on 17.11.15.
 */
public class LoginActivity extends BaseActivity {
    private ParseManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mManager = ParseManager.newInstance(this);

        if(mManager.userIsLoginned()) {
            getFragmentNavigator().replaceFragment(new LoginFragment());
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }
}
