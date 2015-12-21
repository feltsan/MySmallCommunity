package com.thinkmobiles.mysmallcommunity.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.thinkmobiles.mysmallcommunity.base.BaseActivity;
import com.thinkmobiles.mysmallcommunity.managers.ParseManager;
import com.thinkmobiles.mysmallcommunity.managers.Preferences;
import com.thinkmobiles.mysmallcommunity.models.User;
import com.thinkmobiles.mysmallcommunity.ui.custom_views.CustomGalleryPost;
import com.thinkmobiles.mysmallcommunity.ui.fragments.LoginFragment;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by dreamfire on 17.11.15.
 */
public class LoginActivity extends BaseActivity {
    private ParseManager mManager;
    private Preferences mPreference;
    CallbackManager callbackManager;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = User.newInstance();
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        createCallFB();

        mManager = ParseManager.newInstance(this);
        mPreference = Preferences.newInstance(this);

        //if(mPreference.getId() == null) {
        getFragmentNavigator().replaceFragment(new CustomGalleryPost());
//        } else {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
    }

    void createCallFB(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                String name = null;
                                String[] names = null;
                                try{
                                    name = jsonObject.getString("name");
                                    names = name.split(" ");

                                    mUser.setUserFbId(jsonObject.getString("id"));
                                    mUser.setFirstName(names[0]);
                                    mUser.setLastName(names[1]);
                                    mUser.setEmail(jsonObject.getString("email"));

//                                    getImageUrl(loginResult.getAccessToken());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mManager.isUserRegister(mUser.getUserFbId());
                               // startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        Log.d("DENYSYUK", "Activity onActivityResult " + data.toString());

    }
}

