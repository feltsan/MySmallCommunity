package com.thinkmobiles.mysmallcommunity.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.AccessToken;

/**
 * Created by dreamfire on 25.11.15.
 */
public class Preferences {
    private static final String PREFERENCE_APP = "preferences";
    private static final String PREFS_FBID = "facebook_id";
    private static final String PREF_TOKEN = "token";

    private static Preferences sPrefs;
    private Context mContext;
    private SharedPreferences mPref;
    private String id = null;

    public static Preferences newInstance(Context _c){
        if(sPrefs == null){
            sPrefs = new Preferences(_c.getApplicationContext());
        }

        return sPrefs;
    }

    private Preferences(Context _c){
        mContext = _c;
        mPref = mContext.getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE);
    }

    public void saveToken(AccessToken _token){
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(PREF_TOKEN, _token.toString());
        editor.apply();
    }

    public Object getToken(){
        if(mPref.contains(PREF_TOKEN)){
            return mPref.getString(PREF_TOKEN, null);
        }
        return null;
    }

    public void setId(String _id) {
        SharedPreferences.Editor edit = mPref.edit();
        edit.putString(PREFS_FBID, _id);
        edit.apply();
    }

    public String getId(){
        if(mPref.contains(PREFS_FBID)){
            id = mPref.getString(PREFS_FBID, null);
        }

        return id;
    }

    public void clearId(){
        mPref.edit().remove(PREFS_FBID);
    }
}
