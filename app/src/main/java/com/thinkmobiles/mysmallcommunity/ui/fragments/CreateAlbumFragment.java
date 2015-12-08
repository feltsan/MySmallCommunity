package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Galleryitem;
import com.thinkmobiles.mysmallcommunity.models.User;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by dreamfire on 30.11.15.
 */
public class CreateAlbumFragment extends BaseFragment {
    private static final int SELECT_PHOTO = 100;
    private FloatingActionButton fab;
    private GridView mGrid;
    private ArrayList<Galleryitem> lists = new ArrayList<>();
    private GalleryItemAdapter adapter;
    private ImageView closeImage;
    private User mUser;
    private EditText albumName;
    private Button btnCreate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_album);
        mUser = User.newInstance();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
    }

    private void findUI(View _v) {
        btnCreate = (Button) _v.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlbum();
            }
        });

        albumName = (EditText) _v.findViewById(R.id.etNameAlbum);
        fab = (FloatingActionButton) _v.findViewById(R.id.fab_FCA);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PHOTO);
            }
        });

        mGrid = (GridView) _v.findViewById(R.id.gridLayout_FCA);
        adapter = new GalleryItemAdapter(lists);
        mGrid.setAdapter(adapter);
    }

    private void closeImage(String _id){
        for(Galleryitem item: lists){
            if(item.getId().equals(_id)){
                lists.remove(item);
            }
        }
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
                        Bitmap postImage = BitmapFactory.decodeStream(is);
                        Galleryitem item = new Galleryitem();
                        item.setBitmap(postImage);
                        item.setId(selectedImage.toString());
                        lists.add(item);
                        adapter.notifyDataSetChanged();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    private void createAlbum(){
        if(albumName.getText() != null) {
            ParseObject album = new ParseObject("albums");
            album.put("userId", mUser.getUserId());
            album.put("nameAlbum", albumName.getText().toString());
            album.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("albums");
                        query.whereEqualTo("userId", mUser.getUserId());
                        query.whereEqualTo("nameAlbum", albumName.getText().toString());
                        query.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                savePhoto(parseObject.getObjectId());
                            }
                        });
                    }
                }
            });
        }
    }

    private void savePhoto(String _id){
        final String id = _id;
        for(Galleryitem g: lists){
            Bitmap bitmap = g.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] data = stream.toByteArray();
            final ParseFile file = new ParseFile(System.currentTimeMillis()+".png", data);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null)
                        savePhotoObject(file, id);
                    Log.d("DENYSYUK", "done image");
                }
            });}
        mActivity.getFragmentNavigator().replaceFragment(new GalleryFragment());
    }

    private void savePhotoObject(ParseFile _file, String _id){
        ParseObject photo = new ParseObject("photo");
        photo.put("albumId", _id);
        photo.put("image", _file);
        photo.saveInBackground();
    }

    private class GalleryItemAdapter extends ArrayAdapter<Galleryitem> {

        public GalleryItemAdapter(ArrayList<Galleryitem> list) {
            super(getActivity(), 0, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = mActivity.getLayoutInflater()
                        .inflate(R.layout.item_gallery, parent, false);
            }

            final Galleryitem item = lists.get(position);

            ImageView image = (ImageView) convertView.findViewById(R.id.gallery_item_imageView);
            image.setImageBitmap(lists.get(position).getBitmap());
            image.setTag(lists.get(position).getId());

            ImageView imageClose = (ImageView) convertView.findViewById(R.id.imageClose);
            imageClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lists.remove(item);
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }
}
