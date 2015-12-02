package com.thinkmobiles.mysmallcommunity.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseActivity;
import com.thinkmobiles.mysmallcommunity.models.Post;
import com.thinkmobiles.mysmallcommunity.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dreamfire on 24.11.15.
 */
public class PostAdapter extends BaseAdapter {
    private Context mContext;
    private List<Post> mPosts;
    private User mUser;

    public PostAdapter(Context _context, List<Post> _lists){
        mContext = _context;
        mPosts = _lists;
        mUser = User.newInstance();
    }

    @Override
    public int getCount() {
        return mPosts.size();
    }

    @Override
    public Object getItem(int position) {
        return mPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int a = position;
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View v = inflater.inflate(R.layout.post_carview_list_item, parent, false);
        TextView name = (TextView) v.findViewById(R.id.tvUserName_PLI);
        TextView date = (TextView) v.findViewById(R.id.tvDate_PLI);
        TextView text = (TextView) v.findViewById(R.id.tvText_PLI);
        CircleImageView image = (CircleImageView) v.findViewById(R.id.iv_profileAvatar_PLI);
        ImageView postImage = (ImageView) v.findViewById(R.id.ivPostImage_PLI);
        final TextView tvLike = (TextView) v.findViewById(R.id.tvLike);

        name.setText(mPosts.get(position).getUserName());
        date.setText(formatDate(mPosts.get(position).getDate()));
        text.setText(mPosts.get(position).getText());
        if(mPosts.get(position).getImage() != null)
        postImage.setImageBitmap(mPosts.get(position).getImage());
        Glide.with(mContext).load(mPosts.get(position).getPhoto()).fitCenter().into(image);

        ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("Likes");
        q.whereEqualTo("postId", mPosts.get(a).getId());
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                Log.d("DENYSYUK", "List = " + list.size());
                tvLike.setText(list.size()+"");
                mPosts.get(a).setLikeCount(list.size());
            }
        });
        tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userId", mUser.getUserId());
                params.put("postId", mPosts.get(a).getId());
                ParseCloud.callFunctionInBackground("LikePost", params, new FunctionCallback<Object>() {
                    @Override
                    public void done(Object o, ParseException e) {
                        if(e == null)
                            Log.d("DENYSYUK", "Count like = " + o.toString());
                        notifyDataSetChanged();
                        if(e != null)
                            Log.d("DENYSYUK", "Error = " + e.toString());
                    }
                });
            }
        });

//        title.setText(getItem(position).getName());
//        title.setTextColor(Color.BLACK);
        return v;
    }


    private String formatDate(Date _date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String d = sdf.format(_date);

        return d;
    }
}
