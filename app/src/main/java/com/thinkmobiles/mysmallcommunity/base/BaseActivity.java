package com.thinkmobiles.mysmallcommunity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.managers.FragmentNavigator;

/**
 * Created by feltsan on 17.11.15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private FragmentNavigator mFragmentNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mFragmentNavigator = new FragmentNavigator();
        mFragmentNavigator.register(getSupportFragmentManager(), R.id.container);

    }

    /**
     * The method finding view in activity
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int resId){
        return (T) findViewById(resId);
    }

    /**
     * The method opening new activity
     */
    public void handleToActivity(Class<? extends BaseActivity> activityClass){
        final Intent activityTransactionIntent = new Intent(this, activityClass);
        startActivity(activityTransactionIntent);
    }

    /**
     * The method opening new activity with request code
     */
    public void handleToActivity(Class<? extends BaseActivity> activityClass, int requestCode){
        final Intent activityTransactionIntent = new Intent(this, activityClass);
        startActivityForResult(activityTransactionIntent, requestCode);
    }

    public FragmentNavigator getFragmentNavigator() {
        return mFragmentNavigator;
    }
}
