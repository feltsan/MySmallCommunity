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
import com.thinkmobiles.mysmallcommunity.adapters.SpinnerHintAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Community;
import com.thinkmobiles.mysmallcommunity.models.Emirates;
import com.thinkmobiles.mysmallcommunity.models.SpinnerItem;
import com.thinkmobiles.mysmallcommunity.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feltsan on 23.11.15.
 */
public class HomeFragment extends BaseFragment {
    private Spinner spinnerEmirate, spinnerCommunity, spinnerArea;
    private List<SpinnerItem> emirList;
    private List<SpinnerItem> comList;
    private List<SpinnerItem> areaList;
    private User mUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_steps);

        comList = new ArrayList<>();
        emirList = new ArrayList<>();
        areaList = new ArrayList<>();
        mUser = User.newInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        findUI();
        setEmirateSpinner();
        return view;
    }

    private void findUI() {
        spinnerEmirate = $(R.id.spinnerEmirate);
        spinnerCommunity = $(R.id.spinnerCommunity);
        spinnerArea = $(R.id.spinnerArea);
    }


    private void setEmirateSpinner(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Emirates");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list != null) {
                    for (ParseObject o : list) {
                        SpinnerItem item = new SpinnerItem();
                        item.setId(o.getObjectId());
                        item.setName(o.getString("name"));

                        emirList.add(item);
                    }

                    SpinnerItem item = new SpinnerItem();
                    item.setName("Choose Emirates");
                    emirList.add(item);

                    SpinnerHintAdapter adapter = new SpinnerHintAdapter(getActivity(), emirList);
                    spinnerEmirate.setAdapter(adapter);
                    spinnerEmirate.setSelection(emirList.size() - 1);
                }
            }
        });

        spinnerEmirate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemId = emirList.get(position).getId();
                if (itemId != null) {
                    setCommunitySpinner(itemId);
                    mUser.setEmirate(itemId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCommunitySpinner(String _id) {
        comList = new ArrayList<>();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Community");
        query.whereEqualTo("idEmirates", _id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject o : list) {
                    SpinnerItem item = new SpinnerItem();
                    item.setId(o.getObjectId());
                    item.setName(o.getString("name"));

                    comList.add(item);
                }
                SpinnerItem item = new SpinnerItem();
                item.setName("Choose community");
                comList.add(item);

                SpinnerHintAdapter commAdapter = new SpinnerHintAdapter(getActivity(), comList);
                spinnerCommunity.setAdapter(commAdapter);
                spinnerCommunity.setSelection(comList.size() - 1);
            }
        });

        spinnerCommunity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemId = comList.get(position).getId();
                if(itemId != null) {
                    setAreaSpinner(itemId);
                    mUser.setCommunity(itemId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAreaSpinner(String _id){
        areaList = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("District");
        query.whereEqualTo("idCommunity", _id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject o : list) {
                    SpinnerItem item = new SpinnerItem();
                    item.setId(o.getObjectId());
                    item.setName(o.getString("name"));

                    areaList.add(item);
                }

                SpinnerItem item = new SpinnerItem();
                item.setName("Choose area");
                areaList.add(item);

                SpinnerHintAdapter areaAdapter = new SpinnerHintAdapter(getActivity(), areaList);
                spinnerArea.setAdapter(areaAdapter);
                spinnerArea.setSelection(areaList.size() - 1);
            }
        });

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemId = areaList.get(position).getId();
                if(itemId != null){
                    mUser.setArea(itemId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
