package com.thinkmobiles.mysmallcommunity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.models.Settings;

import java.util.List;

/**
 * Created by dreamfire on 30.11.15.
 */
public class SettingRVAdapter extends RecyclerView.Adapter<SettingRVAdapter.SettingViewHolder> {
    private Context mContext;
    private List<Settings> lists;

    public SettingRVAdapter(Context _c, List<Settings> _lists) {
        mContext = _c;
        lists = _lists;
    }

    @Override
    public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_list_settings, parent, false);

        return new SettingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SettingViewHolder holder, int position) {
        holder.tvName.setText(lists.get(position).getName());
        holder.ivIcon.setImageDrawable(mContext.getResources()
                .getDrawable(lists.get(position).getIcon()));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class SettingViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView ivIcon;

        public SettingViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName_ILS);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon_ILS);
        }
    }
}
