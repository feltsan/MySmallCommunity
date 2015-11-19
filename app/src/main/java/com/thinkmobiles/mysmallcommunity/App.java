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
        ParseFacebookUtils.initialize(this);
//        ParseUser user = new ParseUser();
//        user.setUsername("njoy");
//        user.setPassword("123");
//        user.setEmail("email@example.com");
//
//// other fields can be set just like with ParseObject
//        user.put("phone", "650-555-0000");
//
//        user.signUpInBackground(new SignUpCallback() {
//            public void done(ParseException e) {
//                if (e == null) {
//                    // Hooray! Let them use the app now.
//                } else {
//                    // Sign up didn't succeed. Look at the ParseException
//                    // to figure out what went wrong
//                }
//            }
//        });
//
    }
}
