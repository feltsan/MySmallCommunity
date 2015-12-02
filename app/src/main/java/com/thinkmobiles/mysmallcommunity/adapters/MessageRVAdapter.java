package com.thinkmobiles.mysmallcommunity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.models.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dreamfire on 25.11.15.
 */
public class MessageRVAdapter extends RecyclerView.Adapter<MessageRVAdapter.MessageViewHolder>{
    private Context mContext;
    private List<Message> messages;
    private View.OnClickListener clickListener;

    public MessageRVAdapter(Context _c, List<Message> _list){
        mContext = _c;
        messages = _list;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.message_list_item, parent, false);

        v.setOnClickListener(clickListener);

        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.name.setText(messages.get(position).getName());
        holder.date.setText(formatDate(messages.get(position).getDate()));
        holder.text.setText(messages.get(position).getText());
        if(messages.get(position).getPhoto() != null) {
            Glide.with(mContext).load(messages.get(position).getPhoto()).fitCenter()
                    .into(holder.image);
        }
    }

    private String formatDate(Date _date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String d = sdf.format(_date);

        return d;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name;
        TextView date;
        TextView text;

        public MessageViewHolder(View itemView) {
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.iv_peopleAvatar_MLI);
            name = (TextView) itemView.findViewById(R.id.tvName_MLI);
            date = (TextView) itemView.findViewById(R.id.tvDate_MLI);
            text = (TextView) itemView.findViewById(R.id.tvText_MLI);
        }
    }
}
