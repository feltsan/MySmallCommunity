package com.thinkmobiles.mysmallcommunity.ui.fragments.registration_steps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Interes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feltsan on 23.11.15.
 */
public class InteresFragment extends BaseFragment implements View.OnClickListener{
    private GridLayout gridLayout;
    private List<Interes> interesList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_interest);
        interesList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        findUI();
        getAllInteres();

        return view;
    }

    private void findUI(){
        gridLayout                   = $(R.id.gridLayout);
    }

    private void addInterest(){
        for(Interes i : interesList) {
            Button tv = new Button(getActivity());
            tv.setTag(i.getId());
            tv.setText(i.getName());
            tv.setPadding(8, 8, 8, 8);
            tv.setBackgroundColor(getResources().getColor(R.color.com_facebook_likeview_text_color));
            gridLayout.addView(tv);

            tv.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }


    private void getAllInteres(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Interes");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject o : list) {
                    Interes i = new Interes();
                    i.setId(o.getObjectId());
                    i.setName(o.get("name").toString());
                    interesList.add(i);
                }
                addInterest();
            }
        });
    }

}
