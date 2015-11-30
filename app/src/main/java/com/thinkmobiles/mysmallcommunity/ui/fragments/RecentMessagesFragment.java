package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.MessageRVAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Message;
import com.thinkmobiles.mysmallcommunity.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamfire on 25.11.15.
 */
public class RecentMessagesFragment extends BaseFragment{
    private RecyclerView recyclerView;
    private MessageRVAdapter adapter;
    private List<Message> messageList;
    private User mUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stories);

        mUser = User.newInstance();
        getRecentMessage();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        messageList = new ArrayList<>();
        findUI(view);
    }

    private void findUI(View _v) {
        recyclerView = (RecyclerView) _v.findViewById(R.id.storiesRV);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        adapter = new MessageRVAdapter(getActivity(), messageList);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(onItemClick);
    }

    private View.OnClickListener onItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    private void getRecentMessage(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Messages");
        query.whereContains("toUser", mUser.getUserId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    for(ParseObject o: list) {
                        Message message = new Message();
                        message.setId(o.getObjectId());
                        message.setName(o.getString("fromUserName"));
                        message.setDate(o.getCreatedAt());
                        message.setText(o.getString("text"));
                        message.setPhoto(o.getString("image_url"));
                        messageList.add(message);
                    }
                }
                if(adapter != null)
                    adapter.notifyDataSetChanged();

            }
        });
    }
}
