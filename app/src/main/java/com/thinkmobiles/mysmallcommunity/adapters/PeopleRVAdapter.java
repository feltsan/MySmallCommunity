package com.thinkmobiles.mysmallcommunity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.models.People;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dreamfire on 25.11.15.
 */
public class PeopleRVAdapter extends RecyclerView.Adapter<PeopleRVAdapter.PeopleViewHolder>{
    private List<People> peoples;
    private Context mContext;
    private View.OnClickListener clickListener;

    public PeopleRVAdapter(Context _c, List<People> _list) {
        mContext = _c;
        peoples = _list;
    }

    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.people_list_item, parent, false);

        v.setOnClickListener(clickListener);

        return new PeopleViewHolder(v);
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PeopleViewHolder holder, int position) {
        holder.tvName.setText(peoples.get(position).getName());
        Glide.with(mContext).load(peoples.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return peoples.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView tvName;

        public PeopleViewHolder(View itemView) {
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.iv_peopleAvatar_PLI);
            tvName = (TextView) itemView.findViewById(R.id.tvPeopleName_PLI);
        }
    }
}
