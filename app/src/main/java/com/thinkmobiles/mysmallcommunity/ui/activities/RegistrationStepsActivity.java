package com.thinkmobiles.mysmallcommunity.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.thinkmobiles.mysmallcommunity.R;
import com.thinkmobiles.mysmallcommunity.adapters.RegistrationPagerAdapter;
import com.thinkmobiles.mysmallcommunity.base.BaseActivity;
import com.thinkmobiles.mysmallcommunity.ui.custom_views.CirclePageIndicator;
import com.thinkmobiles.mysmallcommunity.ui.fragments.LoginFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.registration_steps.FamilyFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.registration_steps.HomeFragment;
import com.thinkmobiles.mysmallcommunity.ui.fragments.registration_steps.InteresFragment;

import java.util.ArrayList;

/**
 * Created by feltsan on 23.11.15.
 */
public class RegistrationStepsActivity extends BaseActivity implements View.OnClickListener {

    private final static int FB_SCREEN = 0;
    private final static int HOME_SCREEN = 1;
    private final static int FAMILY_SCREEN = 2;
    private final static int INTERES_SCREEN = 3;

    private ViewPager imagePager;
    private CirclePageIndicator circlePageIndicator;
    private RegistrationPagerAdapter imageAdapter;
    private TextView backButton;
    private TextView nextButton;
    private AppCompatRadioButton home, family, interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_steps);
        findIU();
        setListener();
        initImagePager();
        initImagePagerListener();
    }

    private void findIU() {
        imagePager = $(R.id.vpFragmentPager_RS);
        circlePageIndicator = $(R.id.cpiIndicator_RS);
        backButton = $(R.id.tv_back_RS);
        nextButton = $(R.id.tv_next_RS);
        home = $(R.id.region_img);
        family = $(R.id.family_img);
        interest = $(R.id.interes_img);
    }

    private void setListener() {
        backButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        imagePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void initImagePager() {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(FB_SCREEN, new LoginFragment());
        fragments.add(HOME_SCREEN, new HomeFragment());
        fragments.add(FAMILY_SCREEN, new FamilyFragment());
        fragments.add(INTERES_SCREEN, new InteresFragment());

        imageAdapter = new RegistrationPagerAdapter(fragments, getSupportFragmentManager());
        imagePager.setAdapter(imageAdapter);
        imagePager.setCurrentItem(1);
        backButton.setVisibility(View.INVISIBLE);
        circlePageIndicator.setViewPager(imagePager);

    }

    void initImagePagerListener() {
        circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case HOME_SCREEN:
                        backButton.setVisibility(View.GONE);

                        setAllUncheck();
                        home.setChecked(true);
                        break;
                    case FAMILY_SCREEN:
                        backButton.setVisibility(View.VISIBLE);
                        nextButton.setText(getResources().getString(R.string.btn_next));
                        setAllUncheck();
                        family.setChecked(true);
                        break;
                    case INTERES_SCREEN:
                        setAllUncheck();
                        nextButton.setText(getResources().getString(R.string.btn_finish));
                        interest.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }

    @Override
    public void onBackPressed() {
        // do something here and don't write super.onBackPressed()
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back_RS:
                int pos = (imagePager.getCurrentItem() == 1) ? 1 : imagePager.getCurrentItem() - 1;
                imagePager.setCurrentItem(pos);
                break;
            case R.id.tv_next_RS:
                if (imagePager.getCurrentItem() == INTERES_SCREEN)
                    startActivity(new Intent(RegistrationStepsActivity.this, MainActivity.class));
                imagePager.setCurrentItem(imagePager.getCurrentItem() + 1);
                break;
        }

    }

    private void setAllUncheck() {
        home.setChecked(false);
        family.setChecked(false);
        interest.setChecked(false);
    }
}
