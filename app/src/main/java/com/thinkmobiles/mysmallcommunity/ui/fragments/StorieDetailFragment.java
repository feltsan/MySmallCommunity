package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Comment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dreamfire on 02.12.15.
 */
public class StorieDetailFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private List<Comment> comments;
    private TextView userName;
    private CircleImageView mUserIcon;
    private TextView mDateCreate;
    private TextView mText;
    private ImageView postImage;
    private EditText writeComment;
    private Button btnWriteCommnet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_storie_detail);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
    }

    private void findUI(View _v){
        mRecycler = (RecyclerView) _v.findViewById(R.id.commentRV);
        mUserIcon = (CircleImageView) _v.findViewById(R.id.iv_profileAvatar_FSD);
        userName = (TextView) _v.findViewById(R.id.tvUserName_FSD);
        mDateCreate = (TextView) _v.findViewById(R.id.tvDate_FDS);
        mText = (TextView) _v.findViewById(R.id.tvText_FDS);
        postImage = (ImageView) _v.findViewById(R.id.iv_profileAvatar_FSD);
        writeComment = (EditText) _v.findViewById(R.id.etWriteComment);
        btnWriteCommnet = (Button) _v.findViewById(R.id.btnWriteComment);
        btnWriteCommnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeComment();
            }
        });

    }

    private void writeComment(){
        if(writeComment.getText() != null){

        }
    }
}
