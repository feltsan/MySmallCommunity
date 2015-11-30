package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.PostAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Post;
import com.thinkmobiles.mysmallcommunity.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamfire on 24.11.15.
 */
public class ProfileFragment extends BaseFragment {
    private List<Post> posts;
    private ListView mListView;
    private User mUser;
    private PostAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        mUser = User.newInstance();

        posts = new ArrayList<>();
        getMePosts();
    }

    private void getMePosts(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Posts");
        query.whereEqualTo("userId", mUser.getUserId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    for(ParseObject o: list){
                        Post post = new Post();
                        post.setId(o.getObjectId());
                        post.setText(o.getString("text"));
                        post.setDate(o.getCreatedAt());
                        post.setPhoto(mUser.getPhotoUrl());
                        ParseFile b = o.getParseFile("image");
                        if(b != null) {
                            byte[] data = null;
                            try {
                                data = b.getData();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            post.setImage(bitmap);
                        }

                        posts.add(post);
                    }
                    if(adapter != null)
                        adapter.notifyDataSetChanged();
                }
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
        adapter = new PostAdapter(getActivity(), posts);
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(mActivity.LAYOUT_INFLATER_SERVICE);
        View header = inflater.inflate(R.layout.item_user_list_header, null);
        addHeader(header);

        mListView.addHeaderView(header);
        mListView.setAdapter(adapter);
    }

    private void addHeader(View header){
        TextView t = (TextView) header.findViewById(R.id.tvName_IULH);
        t.setText(mUser.getFirstName() + " " + mUser.getLastName());

        TextView tvLiving = (TextView) header.findViewById(R.id.tvEmirates_IULH);
        setProfileEmirates(tvLiving);

        Button btnNewPost = (Button) header.findViewById(R.id.btnNewPost);
        btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.getFragmentNavigator().replaceFragment(new CreatePostFragment());
            }
        });

        ImageView iv = (ImageView) header.findViewById(R.id.ivUserPhoto);
        Glide.with(mActivity).load(mUser.getPhotoUrl()).into(iv);
    }

    private void setProfileEmirates(final TextView _tv){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Emirates");
        query.whereEqualTo("objectId", mUser.getEmirate());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                _tv.setText(parseObject.getString("name"));
                mUser.setEmirateName(parseObject.getString("name"));
            }
        });
    }


}
