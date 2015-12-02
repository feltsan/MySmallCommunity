package com.thinkmobiles.mysmallcommunity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.models.DrawerMenuItem;
import com.thinkmobiles.mysmallcommunity.ui.custom_views.CustomBadge;

import java.util.ArrayList;

/**
 * Created by feltsan on 30.11.15.
 */
public class DrawerNavigationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DrawerMenuItem> menuItems;

    public DrawerNavigationAdapter(Context context, ArrayList<DrawerMenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public DrawerMenuItem getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_drawer_list, parent, false);
            holder = new MenuHolder(convertView);
        }else {
            holder = (MenuHolder) convertView.getTag();
        }

        holder.updateView(getItem(position));

        return convertView;
    }

    private final class MenuHolder{
        private TextView icon, title;
        private CustomBadge badge;

        public MenuHolder(final View convertView) {
            initHolder(convertView);
            convertView.setTag(this);
        }

        public void initHolder(View view){
            icon     = (TextView) view.findViewById(R.id.tv_icon_IDL);
            title    = (TextView) view.findViewById(R.id.tv_title_IDL);
            badge    = (CustomBadge) view.findViewById(R.id.cb_badge_IDL);
        }

        public final void updateView(final DrawerMenuItem item){
            icon.setBackground(item.getIcon());
            title.setText(item.getTitle());

            if (item.getCountBadge()>0) {
                badge.setBadgeCount(String.valueOf(item.getCountBadge()));
                badge.setVisibility(View.VISIBLE);
            }else{
                badge.setVisibility(View.INVISIBLE);
            }
        }
    }
}
