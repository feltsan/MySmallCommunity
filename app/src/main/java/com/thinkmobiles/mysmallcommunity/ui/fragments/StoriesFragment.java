package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.PostAdapter;
import com.thinkmobiles.mysmallcommunity.adapters.PostRVAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Post;
import com.thinkmobiles.mysmallcommunity.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamfire on 26.11.15.
 */
public class StoriesFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private PostRVAdapter adapter;
    private List<Post> posts;
    private User mUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stories);

        posts = new ArrayList<>();
        mUser = User.newInstance();
        getAllPosts();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
    }

    private void findUI(View _v){
        mRecycler = (RecyclerView) _v.findViewById(R.id.storiesRV);
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(llm);
        adapter = new PostRVAdapter(getActivity(), posts);
        mRecycler.setAdapter(adapter);
        adapter.setClickListener(onItemClick);
    }

    private View.OnClickListener onItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    private void getAllPosts(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Posts");
        query.whereEqualTo("communityId", mUser.getCommunity());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    for(ParseObject o: list){
                        final Post post = new Post();
                        post.setId(o.getObjectId());
                        post.setUserName(o.getString("userName"));
                        post.setUserId(o.getString("userId"));
                        post.setDate(o.getCreatedAt());
                        post.setPhoto(o.getString("photo"));
                        post.setText(o.getString("text"));
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
                        } else {
                            Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(),
                                    R.drawable.ic_favorite_black_24dp);
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
}
