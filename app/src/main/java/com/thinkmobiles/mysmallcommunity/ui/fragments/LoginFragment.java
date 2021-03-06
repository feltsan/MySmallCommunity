package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.interfaces.Saveiface;
import com.thinkmobiles.mysmallcommunity.managers.FacebookSocialClient;
import com.thinkmobiles.mysmallcommunity.managers.ParseManager;
import com.thinkmobiles.mysmallcommunity.models.User;
import com.thinkmobiles.mysmallcommunity.ui.activities.MainActivity;
import com.thinkmobiles.mysmallcommunity.ui.activities.RegistrationStepsActivity;
import com.thinkmobiles.mysmallcommunity.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;


/**
 * Created by dreamfire on 17.11.15.
 */
public class LoginFragment extends BaseFragment implements Saveiface {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ParseManager mManager;
    private User mUser;

    private Button btnFacebook;
    private CallbackManager mCallbackManager;

    private FacebookSocialClient mFacebookSocialClient;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);

        mUser = User.newInstance();
        mManager = ParseManager.newInstance(getActivity());
        mManager.setIface(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        findUI();

        return view;
    }

    private void findUI(){
//        loginButton           = $(R.id.login_button);
        btnFacebook           = $(R.id.btnFacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void setLoginButton(){
//        loginButton.setReadPermissions("public_profile, email");
//        loginButton.setFragment(this);




    }

    private void getImageUrl(AccessToken _token){
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover, picture.height(300).width(300)");
        params.putBoolean("redirect", false);
        new GraphRequest(_token,
                "me",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            Log.d("DENYSYUK", response.toString());
                            try {
                                JSONObject data = response.getJSONObject();
                                if (data.has("picture")) {
                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    Log.d("DENYSYUK", "Picture = " + profilePicUrl);
                                    mUser.setPhotoUrl(profilePicUrl);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();
    }

    private void getImageProfile(final String _url) {
                            AsyncTask<Void, Void, Bitmap> t = new AsyncTask<Void, Void, Bitmap>(){
                                protected Bitmap doInBackground(Void... p) {
                                    Bitmap bm = null;
                                    try {
                                        URL aURL = new URL(_url);
                                        URLConnection conn = aURL.openConnection();
                                        conn.setUseCaches(true);
                                        conn.connect();
                                        InputStream is = conn.getInputStream();
                                        BufferedInputStream bis = new BufferedInputStream(is);
                                        bm = BitmapFactory.decodeStream(bis);
                                        bis.close();
                                        is.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return bm;
                                }

                                protected void onPostExecute(Bitmap bm){
                                    mUser.setProfileImage(bm);
                                    Drawable drawable = new BitmapDrawable(getResources(), bm);
                                    Log.d("DENYSYUK", "Drawable = " + (drawable==null));

                                }
                            };
                            t.execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("DENYSYUK", "Fragment onActivityResult");
    }

    public void login(){
        LoginManager.getInstance().logInWithReadPermissions(mActivity,
                Arrays.asList("public_profile", "user_friends"));
    }

    @Override
    public void save(boolean b) {
        if(true) {
            startActivity(new Intent(getActivity(), RegistrationStepsActivity.class));
        } else {
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
        getActivity().finish();
    }
}
