package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.PeopleRVAdapter;
import com.thinkmobiles.mysmallcommunity.adapters.PostAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.managers.Preferences;
import com.thinkmobiles.mysmallcommunity.models.People;
import com.thinkmobiles.mysmallcommunity.models.Post;
import com.thinkmobiles.mysmallcommunity.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dreamfire on 25.11.15.
 */
public class PeopleFragment extends BaseFragment implements View.OnClickListener{
    private RecyclerView mRecycler;
    private List<People> peoples;
    private User mUser;
    private PeopleRVAdapter adapter;
    private TextView mCommunityName;
    private TextView mMembers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_people);
        peoples = new ArrayList<>();
        mUser = User.newInstance();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAllUserCommunity();
        findUI(view);
    }

    private void findUI(View _v) {
        mCommunityName = (TextView) _v.findViewById(R.id.tvComName);
        getCommunity(mCommunityName);

        mMembers = (TextView) _v.findViewById(R.id.tvMembers);

        mRecycler = (RecyclerView) _v.findViewById(R.id.rvPeople);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(llm);
        adapter = new PeopleRVAdapter(getActivity(), peoples);
        mRecycler.setAdapter(adapter);
        adapter.setClickListener(onItemClick);
    }

    private View.OnClickListener onItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = mRecycler.getChildAdapterPosition(v);
            PeopleProfileFragment fragment = new PeopleProfileFragment();
            Bundle b = new Bundle();
            b.putString("human_id", peoples.get(pos).getId());
            b.putString("human_image", peoples.get(pos).getImage());
            fragment.setArguments(b);
            mActivity.getFragmentNavigator().replaceFragment(fragment);
        }
    };

    private void getFriendPhoto(String _id, final People _p){
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                "/"+_id+"/picture",
                null,
                HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Log.d("Photo Profile", response.getJSONObject().toString());
                JSONObject jsonObject = response.getJSONObject();
                try {

                    JSONObject data = jsonObject.getJSONObject("data");
                    String url = data.getString("url");
                    _p.setImage(url);
                    adapter.notifyDataSetChanged();
                    Log.d("DENYSYUK", "URL = " + url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters =new Bundle();
        parameters.putString("type","large");
        parameters.putBoolean("redirect",false);
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getCommunity(final TextView _tv){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Community");
        query.whereEqualTo("objectId", mUser.getCommunity());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                _tv.setText("Community " + parseObject.getString("name"));
            }
        });
    }

    private void getAllUserCommunity(){
        Map<String, Object> params = new HashMap<>();
        params.put("communityId", mUser.getCommunity());
        params.put("meId", mUser.getUserId());
        ParseCloud.callFunctionInBackground("GetAllUserCommunity", params,
                new FunctionCallback<List<ParseObject>>() {
                    @Override
                    public void done(List<ParseObject> o, ParseException e) {
//
                        for(ParseObject object: o){
                            People p = new People();
                            p.setId(object.getObjectId());
                            p.setName(object.getString("first_name")+ " " + object.getString("last_name"));
                            getFriendPhoto(object.getString("userFB"), p);
                            peoples.add(p);
                        }

                        if(adapter != null) {
                            adapter.notifyDataSetChanged();
                            if(peoples.size() != 0) {
                                mMembers.setText(peoples.size() + " " + "Members");
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }
}
