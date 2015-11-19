package com.thinkmobiles.mysmallcommunity.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.models.Community;
import com.thinkmobiles.mysmallcommunity.models.Emirates;
import com.thinkmobiles.mysmallcommunity.models.Interes;
import com.thinkmobiles.mysmallcommunity.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamfire on 18.11.15.
 */
public class RegistrationStepsFragment extends BaseFragment implements View.OnClickListener {

    private Spinner spinnerEmirate, spinnerCommunity, mFamilySpinner;
    private List<Emirates> emirList;
    private List<Community> comList;
    private List<Interes> interesList;
    private Button mNextBtn;
    private LinearLayout mRegionLayout,mInterestlayout;
    private int steps=0;
    private GridLayout gridLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_steps_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);

        findUI();
        setSpinner();
        setListener();
        getAllInteres();

        comList = new ArrayList<>();
        emirList = new ArrayList<>();
        interesList = new ArrayList<>();

        return view;
    }

    private void findUI(){
        spinnerEmirate               = $(R.id.spinnerEmirate);
        spinnerCommunity             = $(R.id.spinnerCommunity);
        mFamilySpinner               = $(R.id.familySpinner);
        mRegionLayout                = $(R.id.regionLayout);
        mNextBtn                     = $(R.id.nextbtn);
        mInterestlayout              = $(R.id.interestLayout);
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

    private void setListener(){
        steps = 1;
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
    }

    private void nextStep(){
        steps++;
        switch (steps){
            case 2:
                mRegionLayout.setVisibility(View.GONE);
                mFamilySpinner.setVisibility(View.VISIBLE);
                break;
            case 3:
                mFamilySpinner.setVisibility(View.GONE);
                addInterest();
                break;
            case 4:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            default:
                break;
        }
    }

    private void setSpinner(){
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
                for(ParseObject o: list){
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

    private void getAllInteres(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Interes");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject o: list){
                    Interes i = new Interes();
                    i.setId(o.getObjectId());
                    i.setName(o.get("name").toString());

                    interesList.add(i);
                }
            }
        });
    }

    private void selectEmirate(String id){
        List<Community> list = new ArrayList<>();
        for(Community c : comList){
            if(id.equals(c.getIdEmirates())){
                list.add(c);
            }
        }
        setCommunitySpinner(list);
    }


    private void setCommunitySpinner(List<Community> list){
        spinnerCommunity.setAdapter(new ArrayAdapter<Community>(getActivity(),
                android.R.layout.simple_list_item_1, list));
    }

    @Override
    public void onClick(View v) {
            v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }
}
