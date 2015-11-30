package com.thinkmobiles.mysmallcommunity.managers;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.interfaces.Saveiface;
import com.thinkmobiles.mysmallcommunity.models.Emirates;
import com.thinkmobiles.mysmallcommunity.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dreamfire on 20.11.15.
 */
public class ParseManager {
    private Context mContext;
    private static ParseManager sParseManager;
    private boolean isUserRegister=false;
    private User user;

    public Saveiface iface;

    public static ParseManager newInstance(Context _c){
        if(sParseManager == null) {
            sParseManager = new ParseManager(_c);
        }
        return sParseManager;
    }

    private ParseManager(Context _c){
        mContext = _c;
        user = User.newInstance();
    }

    public void isUserRegister(String _fbId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
        query.whereEqualTo("userFB", _fbId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject == null) {
                    iface.save(false);
                } else {
                    iface.save(true);
                    user.setUserId(parseObject.getObjectId());
                    user.setUserFbId(parseObject.getString("userFB"));
                    user.setFirstName(parseObject.getString("first_name"));
                    user.setLastName(parseObject.getString("last_name"));
                    user.setEmail(parseObject.getString("email"));
                    user.setEmirate(parseObject.getString("idEmirates"));
                    user.setCommunity(parseObject.getString("idCommunity"));
                    user.setArea(parseObject.getString("idDistrict"));
                    user.setFamilyStatus(parseObject.getString("family_status"));
                    Preferences.newInstance(mContext).setId(parseObject.getObjectId());
                }
            }
        });
    }

    public boolean userIsLoginned(){
        return user.getUserFbId() == null;
    }

    public void closeSession(){
        user.setUserFbId(null);
    }

    public void saveUser(User _user){
        Map<String, Object> params = new HashMap<>();
        params.put("first_name", _user.getFirstName());
        params.put("last_name", _user.getLastName());
        params.put("email", _user.getEmail());
        params.put("emirates", _user.getEmirate());
        params.put("area", _user.getArea());
        params.put("community", _user.getCommunity());
        params.put("family_status", _user.getFamilyStatus());
        params.put("lists", _user.getUserInteres());
        params.put("userFB", _user.getUserFbId());

        ParseCloud.callFunctionInBackground("SetUserModel", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object o, ParseException e) {
                if(o != null) {
                    Log.d("DENYSYUK", "User saved = " + o.toString());
                }
                if(e != null)
                    Log.d("DENYSYUK", "User save error: " + e.toString());
            }
        });
    }

    public boolean isUserExists(String _token){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Users");
        query.whereEqualTo("token", _token);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size() > 0){
                    isUserRegister = true;
                }
            }
        });

        return isUserRegister;
    }

    public List<Emirates> choseEmirate(){
        final List<Emirates> emirates = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Emirates");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                Log.d("DENYSYUK", "Done emirates");
                for (ParseObject o : list) {
                    Emirates emir = new Emirates();
                    emir.setId(o.getObjectId());
                    emir.setName(o.getString("name"));
                    emirates.add(emir);
                }
            }
        });

        return emirates;
    }

    public void setIface(Saveiface iface) {
        this.iface = iface;
    }
}
