package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        mUser = User.newInstance();

        posts = new ArrayList<>();

        for(int i=0; i<10; i++){
            Post post = new Post();
            post.setUserName(mUser.getFirstName() + " " + mUser.getLastName());
            post.setDate("12-Nov at 12:00");
            post.setText("Hello it my post number " + i*100);
            post.setPhoto(mUser.getProfileImage());

            posts.add(post);
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
    }

    private void findUI(View _v) {
        mListView = (ListView) _v.findViewById(R.id.postListView);
        PostAdapter adapter = new PostAdapter(getActivity(), posts);
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(mActivity.LAYOUT_INFLATER_SERVICE);
        View header = inflater.inflate(R.layout.item_user_list_header, null);

        TextView t = (TextView) header.findViewById(R.id.tvName_IULH);
        t.setText(mUser.getFirstName() + " " + mUser.getLastName());

        ImageView iv = (ImageView) header.findViewById(R.id.ivUserPhoto);
        Drawable drawable = new BitmapDrawable(getResources(), mUser.getProfileImage());
        iv.setImageDrawable(drawable);

        mListView.addHeaderView(header);
        mListView.setAdapter(adapter);
    }


}
