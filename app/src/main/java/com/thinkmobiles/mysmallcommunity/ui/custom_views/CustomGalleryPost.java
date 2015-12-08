package com.thinkmobiles.mysmallcommunity.ui.custom_views;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.DefaultListAdapter;
import com.thinkmobiles.mysmallcommunity.adapters.DemoAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.DemoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamfire on 07.12.15.
 */
public class CustomGalleryPost extends BaseFragment {
    private AsymmetricGridView listView;
    private DefaultListAdapter adapter;
    private final DemoUtils utils = new DemoUtils();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_custom_gallery);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUI(view);
    }

    private void findUI(View v) {
//        image7.setImageDrawable(getResources().getDrawable(R.drawable.terrance_photo));

        listView = (AsymmetricGridView) v.findViewById(R.id.listView);
        listView.setRequestedColumnWidth(Utils.dpToPx(getContext(), 80));
        List<DemoItem> items = new ArrayList<>();
        items = utils.moarItems(7);
        adapter = new DefaultListAdapter(mActivity, items);
        AsymmetricGridViewAdapter asymmetricGridViewAdapter = new
                AsymmetricGridViewAdapter(mActivity, listView, adapter);
        listView.setAdapter(asymmetricGridViewAdapter);



    }
}
