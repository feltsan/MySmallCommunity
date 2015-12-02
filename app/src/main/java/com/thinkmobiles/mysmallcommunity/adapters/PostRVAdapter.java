package com.thinkmobiles.mysmallcommunity.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.thinkmobiles.mysmallcommunity.models.Post;
import com.thinkmobiles.mysmallcommunity.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dreamfire on 26.11.15.
 */
public class PostRVAdapter extends RecyclerView.Adapter<PostRVAdapter.PostViewHolder> {
    private Context mContext;
    private List<Post> posts;
    private View.OnClickListener clickListener;
    private User mUser;

    public PostRVAdapter(Context _c, List<Post> _list){
        mContext = _c;
        posts = _list;
        mUser = User.newInstance();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.post_carview_list_item, parent, false);
            v.setOnClickListener(clickListener);
        return new PostViewHolder(v);
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {
        if(posts.get(position).getPhoto() != null)
            Glide.with(mContext).load(posts.get(position).getPhoto()).fitCenter().into(holder.photo);
        holder.name.setText(posts.get(position).getUserName());
        holder.date.setText(formatDate(posts.get(position).getDate()));
        holder.text.setText(posts.get(position).getText());
        if(posts.get(position).getImage() != null) {
            holder.image.setImageBitmap(posts.get(position).getImage());
        }
        ParseQuery<ParseObject> like = new ParseQuery<ParseObject>("Likes");
        like.whereEqualTo("postId", posts.get(position).getId());
        like.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                holder.tvLike.setText(list.size()+"");
                posts.get(position).setLikeCount(list.size());
            }
        });
        ParseQuery<ParseObject> dlike = new ParseQuery<ParseObject>("Dislike");
        dlike.whereEqualTo("postId", posts.get(position).getId());
        dlike.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                holder.tvDisLike.setText(list.size()+"");
                posts.get(position).setDislikeCount(list.size());
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userId", mUser.getUserId());
                params.put("postId", posts.get(position).getId());
                ParseCloud.callFunctionInBackground("LikePost", params, new FunctionCallback<Object>() {
                    @Override
                    public void done(Object o, ParseException e) {
                        notifyDataSetChanged();
                    }
                });
            }
        });
        holder.disLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userId", mUser.getUserId());
                params.put("postId", posts.get(position).getId());
                ParseCloud.callFunctionInBackground("DisLikePost", params, new FunctionCallback<Object>() {
                    @Override
                    public void done(Object o, ParseException e) {
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        CircleImageView photo;
        TextView name;
        TextView date;
        TextView text;
        ImageView image;
        ImageView like;
        ImageView disLike;
        ImageView comment;
        TextView tvLike;
        TextView tvDisLike;
        TextView tvComment;


        public PostViewHolder(View itemView) {
            super(itemView);
            photo = (CircleImageView) itemView.findViewById(R.id.iv_profileAvatar_PLI);
            name = (TextView) itemView.findViewById(R.id.tvUserName_PLI);
            date = (TextView) itemView.findViewById(R.id.tvDate_PLI);
            text = (TextView) itemView.findViewById(R.id.tvText_PLI);
            image = (ImageView) itemView.findViewById(R.id.ivPostImage_PLI);
            like = (ImageView) itemView.findViewById(R.id.ivLike);
            disLike = (ImageView) itemView.findViewById(R.id.ivDisLike);
            comment = (ImageView) itemView.findViewById(R.id.ivComment);

            tvLike = (TextView) itemView.findViewById(R.id.tvLike);
            tvDisLike = (TextView) itemView.findViewById(R.id.tvDisLike);
            tvComment = (TextView) itemView.findViewById(R.id.tvComment);
        }
    }

    private String formatDate(Date _date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String d = sdf.format(_date);

        return d;
    }

}
