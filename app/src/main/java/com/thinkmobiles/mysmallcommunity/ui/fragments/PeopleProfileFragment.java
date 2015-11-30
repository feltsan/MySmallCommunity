package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.PostAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.People;
import com.thinkmobiles.mysmallcommunity.models.Post;
import com.thinkmobiles.mysmallcommunity.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamfire on 27.11.15.
 */
public class PeopleProfileFragment extends BaseFragment {
    private ListView mListView;
    private List<Post> posts;
    private PostAdapter adapter;
    private String id;
    private String url;
    private ParseObject mUser;

    private TextView mName;
    private TextView mCommunity;
    private ImageView imageView;
    private View header;
    private Button btnNewMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        posts = new ArrayList<>();

        Bundle b = getArguments();
        if(b != null){
            id = b.getString("human_id");
            url = b.getString("human_image");
            getPeoplePosts();
            getPeopleInfo();
        }

    }

    private void getPeoplePosts(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Posts");
        query.whereEqualTo("userId", id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for(ParseObject o: list){
                    Post p = new Post();
                    p.setId(o.getObjectId());
                    p.setUserId(o.getString("userId"));
                    p.setDate(o.getCreatedAt());
                    p.setText(o.getString("text"));
                    p.setPhoto(o.getString("photo"));
                    p.setUserName(o.getString("userName"));
                    ParseFile b = o.getParseFile("image");
                    if(b != null) {
                        byte[] data = null;
                        try {
                            data = b.getData();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        p.setImage(bitmap);
                    }

                    posts.add(p);
                }
            }
        });
    }

    private void getPeopleInfo(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Users");
        query.whereEqualTo("objectId", id);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                mUser = parseObject;
                mName.setText(mUser.getString("first_name")+" "+mUser.getString("last_name"));
                setCommunityName();
            }
        });
    }

    private void setCommunityName(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Community");
        query.whereEqualTo("objectId", mUser.getString("idCommunity"));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                mCommunity.setText(parseObject.getString("name"));
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
    }

    private void findUI(View _v) {
        mListView = (ListView) _v.findViewById(R.id.postListView);
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(mActivity.LAYOUT_INFLATER_SERVICE);
        header = inflater.inflate(R.layout.item_list_people_header, null);
        addHeader(header);

        adapter = new PostAdapter(mActivity, posts);
        mListView.addHeaderView(header);
        mListView.setAdapter(adapter);
    }

    private void addHeader(View _header){
        mName = (TextView) _header.findViewById(R.id.tvName_ILPH);
        mCommunity = (TextView) _header.findViewById(R.id.tvEmirates_ILPH);
        imageView = (ImageView) _header.findViewById(R.id.ivUserPhoto_ILPH);
        btnNewMessage = (Button) _header.findViewById(R.id.btnNewMessage);
        btnNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateMessageFragment fragment = new CreateMessageFragment();
                Bundle b = new Bundle();
                b.putString("id", id);
                b.putString("name", mUser.getString("first_name")+" "+mUser.getString("last_name"));
                b.putString("image", url);
                fragment.setArguments(b);
                mActivity.getFragmentNavigator().replaceFragment(fragment);
            }
        });
        Glide.with(mActivity).load(url).into(imageView);
    }

}
