package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Album;
import com.thinkmobiles.mysmallcommunity.models.Galleryitem;
import com.thinkmobiles.mysmallcommunity.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamfire on 30.11.15.
 */
public class GalleryFragment extends BaseFragment {
    private FloatingActionButton fab;
    private List<Album> albums;
    private User mUser;
    private GridView mGrid;
    private GalleryItemAdapter adapter;
    private String userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);
        mUser = User.newInstance();
        getArgs();
        albums = new ArrayList<>();
    }

    private void getArgs(){
        if(getArguments() != null){
            userId = getArguments().getString("userId");
        } else {
            userId = mUser.getUserId();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
        getUserAlbums();
    }

    private void findUI(View _v) {
        fab = (FloatingActionButton) _v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.getFragmentNavigator().replaceFragment(new CreateAlbumFragment());
            }
        });

        mGrid = (GridView) _v.findViewById(R.id.gridLayout);
        adapter = new GalleryItemAdapter(albums);
        mGrid.setAdapter(adapter);
    }

    private void getUserAlbums(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("albums");
        query.whereEqualTo("userId", userId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for(ParseObject o: list){
                    countPhoto(o);
                }
            }
        });
    }

    private void countPhoto(ParseObject object){
        final Album a = new Album();
        a.setAlbumId(object.getObjectId());
        a.setName(object.getString("nameAlbum"));
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("photo");
        Log.d("DENYSYUK", "objectId = " + object.getObjectId());
        query.whereEqualTo("albumId", object.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    Log.d("DENYSYUK", "List size = " + list.size());
                    a.setCount(list.size());
                    albums.add(a);
                    adapter.notifyDataSetChanged();
                    getAlbumPhoto();
                }
            }
        });
    }

    private void getAlbumPhoto(){
        for(int i=0; i<albums.size(); i++) {
            final int x = i;
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("photo");
            query.whereEqualTo("albumId", albums.get(x).getAlbumId());
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    if(e == null){
                        ParseFile b = parseObject.getParseFile("image");
                        if(b != null) {
                            byte[] data = null;
                            try {
                                data = b.getData();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            albums.get(x).setAlbumIcon(bitmap);
                            Log.d("DENYSYUK", "Album icon = " + (albums.get(x).getAlbumIcon()==null));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    private class GalleryItemAdapter extends ArrayAdapter<Album> {

        public GalleryItemAdapter(List<Album> list) {
            super(getActivity(), 0, list);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = mActivity.getLayoutInflater()
                        .inflate(R.layout.item_gallery_album, parent, false);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlbumPhotosFragment fragment = new AlbumPhotosFragment();
                    Bundle b = new Bundle();
                    b.putString("albumId", albums.get(position).getAlbumId());
                    fragment.setArguments(b);
                    mActivity.getFragmentNavigator().replaceFragment(fragment);
                }
            });

            ImageView image = (ImageView) convertView.findViewById(R.id.iv_albumicon);
            image.setImageBitmap(albums.get(position).getAlbumIcon());
            TextView tvName = (TextView) convertView.findViewById(R.id.tvAlbumName);
            tvName.setText(albums.get(position).getName());
            TextView tvCount = (TextView) convertView.findViewById(R.id.tvCountPhoto);
            tvCount.setText(albums.get(position).getCount()+" "+"Photos");


            return convertView;
        }
    }
}
