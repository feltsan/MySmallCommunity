package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.content.Intent;
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
import com.thinkmobiles.mysmallcommunity.ui.activities.MainActivity;

import org.json.JSONObject;


/**
 * Created by dreamfire on 17.11.15.
 */
public class LoginFragment extends Fragment {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        super.onCreate(savedInstanceState);

        Log.d("DENYSYUK", "OnCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        loginButton = (LoginButton) v.findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile, email");
        loginButton.setFragment(this);

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("DENYSYUK", "onSuccess");
                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                accessToken = AccessToken.getCurrentAccessToken();
                                //LoginManager.getInstance().logOut();
                                Log.d("DENYSYUK", "JSONObject = " + jsonObject.toString());

                                startActivity(new Intent(getActivity(), MainActivity.class));
                                // my result JSONObject = {"birthday":"09\/02\/1991","email":"yarukhaka@meta.ua","id":"1030963233580793"}
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
                Log.d("DENYSYUK", "onError");
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("DENYSYUK", "Result");
    }
}
