package com.usinformatics.nytrip.ui.profile;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.helpers.PicassoHelper;
import com.usinformatics.nytrip.models.UserModel;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;

import java.util.ArrayList;

import common.containers.slider.SlidingTabLayout;


/**
 * Created by admin on 7/2/15.
 */
public class ProfileActivity extends BaseActivity {


    private CharSequence Titles[] = {};
    private ViewPager mPager;
    private ArrayList<IFragment> fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profile);
        initToolbarEngine(true);
        mToolbarEngine.setToolbarTitle("Profile");
        findAndInitViews();

        initFragments();
        initViewPager();
        initTabs();
    }

    private void findAndInitViews() {
        UserModel u= StorageFactory.getUserStorage(ProfileActivity.this).getUser();
        ((TextView) findViewById(R.id.profile_name)).setText("" + u.firstName );
        ((TextView) findViewById(R.id.profile_data1)).setText(""+u.lastName);
        ((TextView) findViewById(R.id.profile_data2)).setText(""+u.email);
        PicassoHelper.showRoundedImage(ProfileActivity.this,u.imageUrl,R.mipmap.ic_character_empty, (ImageView) findViewById(R.id.profile_photo));
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(initOverviewFragment());
        fragments.add(initStatisticsFragment());
    }

    private IFragment initStatisticsFragment() {
       return ProfileStatisticFragment.newInstance(ProfileActivity.this);
    }

    private IFragment initOverviewFragment() {
        return ProfileOverviewFragment.newInstance(ProfileActivity.this);
    }

    private void initTabs() {
        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.profile_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.orange);
            }
        });
        tabs.setViewPager(mPager);
    }

    private void initViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(),fragments);
        mPager = (ViewPager) findViewById(R.id.profile_view_pager);
        mPager.setAdapter(adapter);
    }

    @Override
    public String getTag() {
        return getString(R.string.profile_activity);
    }

    @Override
    public ItemRawPopup getPopupItemOfCurrentActivity() {
        return ItemRawPopup.PROFILE;
    }

    @Override
    protected ToolbarEngine getInstanceOfToolbar() {
        return null;
    }

    @Override
    protected int getIdResourcesOfToolbar() {
        return R.id.profile_toolbar;
    }

    @Override
    public void actionToolbarCallback(ToolbarActions currentItem) {

    }

    @Override
    public void onBackPressed() {
        if(dismissPopupIsNeeded())
            return;
        super.onBackPressed();
    }
}
