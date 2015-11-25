package com.thinkmobiles.mysmallcommunity.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseActivity;
import com.thinkmobiles.mysmallcommunity.models.Post;

import java.util.List;

/**
 * Created by dreamfire on 24.11.15.
 */
public class PostAdapter extends BaseAdapter {
    private Context mContext;
    private List<Post> mPosts;

    public PostAdapter(Context _context, List<Post> _lists){
        mContext = _context;
        mPosts = _lists;
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
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View v = inflater.inflate(R.layout.post_list_item, parent, false);
        TextView name = (TextView) v.findViewById(R.id.tvUserName_PLI);
        TextView date = (TextView) v.findViewById(R.id.tvDate_PLI);
        TextView text = (TextView) v.findViewById(R.id.tvText_PLI);
        ImageView image = (ImageView) v.findViewById(R.id.iv_profileAvatar_PLI);

        name.setText(mPosts.get(position).getUserName());
        date.setText(mPosts.get(position).getDate());
        text.setText(mPosts.get(position).getText());
        image.setImageBitmap(mPosts.get(position).getPhoto());

//        title.setText(getItem(position).getName());
//        title.setTextColor(Color.BLACK);
        return v;
    }
}
