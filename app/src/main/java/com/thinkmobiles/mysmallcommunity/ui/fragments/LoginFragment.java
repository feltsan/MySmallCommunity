package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.global.Constants;
import com.thinkmobiles.mysmallcommunity.ui.activities.LoginActivity;
import com.thinkmobiles.mysmallcommunity.ui.activities.MainActivity;
import com.thinkmobiles.mysmallcommunity.ui.activities.RegistrationStepsActivity;

import org.json.JSONObject;


/**
 * Created by dreamfire on 17.11.15.
 */
public class LoginFragment extends BaseFragment {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private SharedPreferences mPrefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);

        mPrefs = getActivity().getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        findUI();
        setLoginButton();

        return view;
    }

    private void findUI(){
        loginButton           = $(R.id.login_button);
    }

    private void setLoginButton(){
        loginButton.setReadPermissions("public_profile, email");
        loginButton.setFragment(this);

        callbackManager = CallbackManager.Factory.create();

        if(false){
            mActivity.getFragmentNavigator().replaceFragment(new RegistrationStepsFragment());
        }
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                accessToken = AccessToken.getCurrentAccessToken();

                                Log.d("DENYSYUK", "JSONObject = " + jsonObject.toString());

                                startActivity(new Intent(getActivity(), RegistrationStepsActivity.class));
                            }
                        }
                );

                Bundle parameters = new Bundle();
                parameters.putString("fields", "name, birthday, email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d("DENYSYUK", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("DENYSYUK", "onError " + error.toString());
            }
        });

    }

    private void setToken(AccessToken _token) {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString(Constants.TOKEN, _token.toString());
        edit.apply();
    }

    private boolean isLoggined(){
        return mPrefs.contains(Constants.TOKEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("DENYSYUK", "Result");
    }
}
