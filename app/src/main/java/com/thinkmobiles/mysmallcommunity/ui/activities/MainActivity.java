package com.thinkmobiles.mysmallcommunity.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.login.LoginManager;
import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.DrawerNavigationAdapter;
import com.thinkmobiles.mysmallcommunity.api.API;
import com.thinkmobiles.mysmallcommunity.base.BaseActivity;
import com.thinkmobiles.mysmallcommunity.base.BaseFragment;
import com.thinkmobiles.mysmallcommunity.global.Constants;
import com.thinkmobiles.mysmallcommunity.managers.ParseManager;
import com.thinkmobiles.mysmallcommunity.managers.Preferences;
import com.thinkmobiles.mysmallcommunity.models.User;
import com.thinkmobiles.mysmallcommunity.ui.fragments.GalleryFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.MessagesFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.PeopleFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.ProfileFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.SettingFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.SettingsFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.StoriesFragment;
import com.thinkmobiles.mysmallcommunity.utility.DrawerMenu;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
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

        mDrawerList.setAdapter(new DrawerNavigationAdapter(this, new DrawerMenu().createDrawerNawiItem(this)));
        getPost();

        mManager = ParseManager.newInstance(this);

        getFragmentNavigator().replaceFragment(new StoriesFragment());

    }

    private void findUI(){
        toolbar              = $(R.id.toolbar);
        mDrawerLayout        = $(R.id.drawer_layout);
        mDrawerList          = $(R.id.left_drawer);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View headerView = inflater.inflate(R.layout.item_drawer_header_menu, null);
        profile = (RelativeLayout) headerView.findViewById(R.id.profile_header);
        findHeader(headerView);

        setupDrawer();
    }

    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                mDrawerToggle.syncState();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                mDrawerToggle.syncState();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void findHeader(View _v) {
        ImageView ivProfile = (ImageView) _v.findViewById(R.id.iv_profileAvatar_IDHM);
        Glide.with(this).load(mUser.getPhotoUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter().into(ivProfile);

        TextView tvName = (TextView) _v.findViewById(R.id.tv_nameProfile_IDHM);
        tvName.setText(mUser.getFirstName() + " " + mUser.getLastName());

        mDrawerList.addHeaderView(_v);
    }
    private void setListener(){
        mDrawerList.setOnItemClickListener(this);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentNavigator().replaceFragment(new ProfileFragment());
              closeDrawer();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void closeDrawer(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawers();
            }
        }, 300);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(mDrawerToggle != null)
        mDrawerToggle.syncState();
    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if(mDrawerToggle.onOptionsItemSelected(item)){
//            return true;
//        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 1:
                changeFragment(new StoriesFragment());
                break;
            case 2:
                changeFragment(new MessagesFragment());
                break;
            case 3:
                changeFragment(new GalleryFragment());
                break;
            case 5:
                changeFragment(new PeopleFragment());
                break;
            case 9:
                changeFragment(new SettingsFragment());
                break;
            case 10:
                LoginManager.getInstance().logOut();
                mManager.closeSession();
                Preferences.newInstance(this).clearId();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void changeFragment(BaseFragment fragment){
        getFragmentNavigator().replaceFragment(fragment);
        closeDrawer();
    }

    public void getPost(){
        Log.e("MOCK", API.getPosts().toString());
    }
}
