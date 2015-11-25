package com.thinkmobiles.mysmallcommunity.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.api.API;
import com.thinkmobiles.mysmallcommunity.base.BaseActivity;
import com.thinkmobiles.mysmallcommunity.global.Constants;
import com.thinkmobiles.mysmallcommunity.managers.ParseManager;
import com.thinkmobiles.mysmallcommunity.models.User;
import com.thinkmobiles.mysmallcommunity.ui.fragments.ProfileFragment;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar toolbar;
    private ParseManager mManager;
    private RelativeLayout profile;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = User.newInstance();

        findUI();
        setSupportActionBar(toolbar);
        setListener();

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                Constants.mItemMenu));
       getPost();

        mManager = ParseManager.newInstance(this);

    }

    private void findUI(){
        toolbar              = $(R.id.toolbar);
        mDrawerLayout        = $(R.id.drawer_layout);
        mDrawerList          = $(R.id.left_drawer);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View headerView = inflater.inflate(R.layout.item_drawer_header_menu, null);

        findHeader(headerView);

        profile = (RelativeLayout) headerView.findViewById(R.id.profile_header);
        mDrawerList.addHeaderView(headerView);
    }

    private void findHeader(View _v) {
        ImageView ivProfile = (ImageView) _v.findViewById(R.id.iv_profileAvatar_IDHM);
        ivProfile.setImageBitmap(mUser.getProfileImage());
        TextView tvName = (TextView) _v.findViewById(R.id.tv_nameProfile_IDHM);
        tvName.setText(mUser.getFirstName() + " " + mUser.getLastName());
    }
    private void setListener(){
        mDrawerList.setOnItemClickListener(this);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentNavigator().replaceFragment(new ProfileFragment());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawerLayout.closeDrawers();
                    }
                }, 300);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 9:
                LoginManager.getInstance().logOut();
                mManager.closeSession();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    public void getPost(){
        Log.e("MOCK", API.getPosts().toString());

    }
}
