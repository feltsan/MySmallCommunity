package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Album;
import com.thinkmobiles.mysmallcommunity.models.Galleryitem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamfire on 02.12.15.
 */
public class AlbumPhotosFragment extends BaseFragment {
    private GridView mGridView;
    private List<Bitmap> photos;
    private GalleryItemAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_album_photos);
        photos = new ArrayList<>();
        getArgs();
    }

    private void getArgs(){
        Bundle b = new Bundle();
        b = getArguments();
        if(b != null){
            getPhotos(b.getString("albumId"));
        }
    }

    private void getPhotos(String _id){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("photo");
        query.whereEqualTo("albumId", _id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                Log.d("DENYSYUK", "Photo list size = " + list.size());
                for(ParseObject o: list){
                    ParseFile b = o.getParseFile("image");
                    if(b != null) {
                        byte[] data = null;
                        try {
                            data = b.getData();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        photos.add(bitmap);
                        adapter.notifyDataSetChanged();
                    }
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
        mGridView = (GridView) _v.findViewById(R.id.gridLayoutPhotos);
        adapter = new GalleryItemAdapter(photos);
        mGridView.setAdapter(adapter);
    }

    private class GalleryItemAdapter extends ArrayAdapter<Bitmap> {

        public GalleryItemAdapter(List<Bitmap> list) {
            super(getActivity(), 0, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = mActivity.getLayoutInflater()
                        .inflate(R.layout.item_album_photo, parent, false);
            }

            ImageView image = (ImageView) convertView.findViewById(R.id.ivphoto_IAP);
            image.setImageBitmap(photos.get(position));

            return convertView;
        }
    }
}
