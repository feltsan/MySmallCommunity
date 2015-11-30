package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.User;

/**
 * Created by dreamfire on 25.11.15.
 */
public class CreateMessageFragment extends BaseFragment{
    private EditText mTopic;
    private EditText mText;
    private Button mBtnSend;
    private User mUser;
    private String id;
    private String name;
    private String imageUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_message);
        mUser = User.newInstance();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle b = getArguments();
        id = b.getString("id");
        name = b.getString("name");
        imageUrl = b.getString("image");

        findUI(view);
    }

    private void findUI(View _v) {
        mText = (EditText) _v.findViewById(R.id.etText_FCM);
        mTopic = (EditText) _v.findViewById(R.id.etTopic_FCM);
        mBtnSend = (Button) _v.findViewById(R.id.btnSend_FCM);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject message = new ParseObject("Messages");
                message.put("fromUser", mUser.getUserId());
                message.put("toUser", id);
                message.put("fromUserName", mUser.getFirstName()+" "+mUser.getLastName());
                message.put("toUserName", name);
                message.put("topic", mTopic.getText().toString());
                message.put("text", mText.getText().toString());
                message.put("image_url", imageUrl);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                            closeFragment();
                    }
                });
            }
        });
    }

    private void closeFragment(){
        mActivity.getFragmentNavigator().replaceFragment(new PeopleFragment());
    }
}
