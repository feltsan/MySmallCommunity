package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.User;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by dreamfire on 26.11.15.
 */
public class CreatePostFragment extends BaseFragment {
    private static final int SELECT_PHOTO = 100;

    private EditText mPostEdit;
    private Button mBtnPost;
    private Button mBtnAdd;
    private User mUser;
    private Bitmap postImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_post_create);
        mUser = User.newInstance();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
    }

    private void findUI(View _v){
        mPostEdit = (EditText) _v.findViewById(R.id.etPostText_FPC);
        mBtnPost = (Button) _v.findViewById(R.id.btnPost_FPC);
        mBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPostEdit.getText() != null)
                savePost();
            }
        });
        mBtnAdd = (Button) _v.findViewById(R.id.btnAddPhoto);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PHOTO);
            }
        });
    }

    ParseFile file = null;
    private void savePost(){
        if(postImage != null){
        Bitmap bitmap = postImage;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();
        file = new ParseFile("terrance.png", data);
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.d("DENYSYUK", "done image");
                createNewPost(file);
            }
        });} else {
            createNewPost(null);
        }
    }
    private void createNewPost(ParseFile _file){
        ParseObject post = new ParseObject("Posts");
        post.put("userId", mUser.getUserId());
        post.put("communityId", mUser.getCommunity());
        post.put("text", mPostEdit.getText().toString());
        post.put("photo", mUser.getPhotoUrl());
        post.put("userName", mUser.getFirstName()+" "+mUser.getLastName());
        if(_file != null)
        post.put("image", file);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null)
                    Log.d("DENYSYUK", "error " + e.toString());
                if(e == null)
                    replaceFragment();
            }
        });
    }

    private void replaceFragment(){
        mActivity.getFragmentNavigator().replaceFragment(new ProfileFragment());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if(resultCode == getActivity().RESULT_OK){
                    Uri selectedImage = data.getData();
                    try {
                        InputStream is = getActivity().getContentResolver()
                                .openInputStream(selectedImage);
                        postImage = BitmapFactory.decodeStream(is);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}
