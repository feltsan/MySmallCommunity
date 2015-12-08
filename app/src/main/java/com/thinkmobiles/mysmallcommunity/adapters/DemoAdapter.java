package com.thinkmobiles.mysmallcommunity.adapters;

import android.widget.ListAdapter;

import com.thinkmobiles.mysmallcommunity.models.DemoItem;

import java.util.List;

/**
 * Created by dreamfire on 07.12.15.
 */
public interface DemoAdapter extends ListAdapter {
    void appendItems(List<DemoItem> newItems);

    void setItems(List<DemoItem> moreItems);
}
