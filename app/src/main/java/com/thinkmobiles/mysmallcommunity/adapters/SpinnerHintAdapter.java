package com.thinkmobiles.mysmallcommunity.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.models.SpinnerItem;

import java.util.List;
import java.util.Objects;

/**
 * Created by dreamfire on 20.11.15.
 */
public class SpinnerHintAdapter extends BaseAdapter {
    private List<SpinnerItem> objects;
    private Context context;

    public SpinnerHintAdapter(Context context, List<SpinnerItem> objects) {
        this.objects = objects;
        this.context = context;
    }

    @Override
    public SpinnerItem getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_filter_spinner, parent, false);
        TextView title = (TextView) v.findViewById(R.id.spinnerTarget);
        title.setText(getItem(position).getName());
        title.setTextColor(Color.BLACK);
        return v;
    }

    @Override
    public int getCount() {
// don't display last item. It is used as hint.
        int count = objects.size();
        return count > 1 ? count  : 1;
    }
}
