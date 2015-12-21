package com.thinkmobiles.mysmallcommunity.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by dreamfire on 21.12.15.
 */
public class FacebookSocialClient {
    private Activity mActivity;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private Context mContext;

    public FacebookSocialClient(Context _context, Activity _activity){
        mContext = _context;
        mActivity = _activity;

        fbInitial();
    }

    private void fbInitial(){
        FacebookSdk.sdkInitialize(mContext);
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginResult.getAccessToken();

                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                accessToken = AccessToken.getCurrentAccessToken();
                                LoginManager.getInstance().logOut();
                            }
                        }
                );

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

    }

    public void login(){
        LoginManager.getInstance().logInWithReadPermissions(mActivity,
                Arrays.asList("public_profile", "user_friends"));
    }

    public void faceBookResult(int requestCode, int resultCode, Intent data){
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}