package com.thinkmobiles.mysmallcommunity.ui.fragments.registration_steps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Community;
import com.thinkmobiles.mysmallcommunity.models.Emirates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feltsan on 23.11.15.
 */
public class HomeFragment extends BaseFragment {
    private Spinner spinnerEmirate, spinnerCommunity;
    private List<Emirates> emirList;
    private List<Community> comList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_steps);

        comList = new ArrayList<>();
        emirList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        findUI();
        setSpinner();
        return view;
    }

    private void findUI() {
        spinnerEmirate = $(R.id.spinnerEmirate);
        spinnerCommunity = $(R.id.spinnerCommunity);
    }

    private void setSpinner() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Emirates");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                Log.d("DENYSYUK", "" + list.size());
                for (ParseObject s : list) {
                    Emirates emirates = new Emirates();
                    emirates.setId(s.getObjectId());
                    emirates.setName(s.get("name").toString());

                    emirList.add(emirates);

                }
                spinnerEmirate.setAdapter(new ArrayAdapter<Emirates>(getActivity(),
                        android.R.layout.simple_list_item_1, emirList));

                spinnerEmirate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("DENYSYUK", emirList.get(position).getName());
                        selectEmirate(emirList.get(position).getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Community");
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject o : list) {
                    Community community = new Community();
                    community.setId(o.getObjectId());
                    community.setIdEmirates(o.get("idEmirates").toString());
                    community.setName(o.get("name").toString());

                    comList.add(community);
                }
                setCommunitySpinner(comList);
            }
        });
    }

    private void selectEmirate(String id) {
        List<Community> list = new ArrayList<>();
        for (Community c : comList) {
            if (id.equals(c.getIdEmirates())) {
                list.add(c);
            }
        }
        setCommunitySpinner(list);
    }


    private void setCommunitySpinner(List<Community> list) {
        spinnerCommunity.setAdapter(new ArrayAdapter<Community>(getActivity(),
                android.R.layout.simple_list_item_1, list));
    }
}
