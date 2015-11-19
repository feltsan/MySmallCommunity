package com.thinkmobiles.mysmallcommunity;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by feltsan on 17.11.15.
 */
public class App extends Application {
    public static final String PARSE_ID = "njNebBBm6isbJ7zTwaW2tVL1QFuF7jp5gBxzUeDt";
    public static final String PARSE_KEY = "dxSkAZBOBhd72k6NTNFfnV5ySjFPKRVsQcfcvjfF";

    @Override
    public void onCreate() {
        super.onCreate();
        initParse();
    }

    private void initParse(){
        Parse.initialize(this, App.PARSE_ID, App.PARSE_KEY);
    }
}
