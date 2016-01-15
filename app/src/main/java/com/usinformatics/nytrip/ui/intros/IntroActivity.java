package com.usinformatics.nytrip.ui.intros;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;

import java.util.ArrayList;

import common.views.SmoothScrollViewPager;

/**
 * Created by D1m11n on 12.06.2015.
 */
public class IntroActivity extends BaseActivity {


    private SmoothScrollViewPager mViewPager;
    private IntroPagerAdapter mIntroPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_intro);
        mViewPager= (SmoothScrollViewPager) findViewById(R.id.sm_view_pager);
        initViewPager(mViewPager);
        initLetsStartBtn();
        startSlidingPager(mViewPager, mIntroPagerAdapter);
    }

    private void initLetsStartBtn() {
        findViewById(R.id.lets_start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNextWindow();
            }
        });
    }

    private void initViewPager(final SmoothScrollViewPager viewPager) {
        mIntroPagerAdapter= new IntroPagerAdapter(getSupportFragmentManager(), initIntroFragments());
        viewPager.setAdapter(mIntroPagerAdapter);
        viewPager.setCurrentItem(0);
    }

    private ArrayList<IntroFragment> initIntroFragments() {

        ArrayList<IntroFragment> introFragments = new ArrayList<>();
        IntroModel model = new IntroModel();

        model.imageResource = R.mipmap.intro_image_1;
        introFragments.add(IntroFragment.newInstance(model));

        model.imageResource = R.mipmap.intro_image_2;
        introFragments.add(IntroFragment.newInstance(model));

        model.imageResource = R.mipmap.intro_image_3;
        introFragments.add(IntroFragment.newInstance(model));

        return introFragments;
    }

    private void startSlidingPager(final SmoothScrollViewPager viewPager, final IntroPagerAdapter adapter) {
      final Handler hh= new Handler();

      final  Runnable rr=new Runnable() {
            @Override
            public void run() {
                int next = viewPager.getCurrentItem() + 1;
                if (next < adapter.getCount()) {
                    viewPager.setCurrentItem(next);
                    hh.postDelayed(this, AppConsts.DELAY_INTRO_TRANSITION);
                }
            }};
        hh.postDelayed(rr, AppConsts.DELAY_INTRO_TRANSITION);
    }

    private void moveToNextWindow() {
        startActivity(new Intent(IntroActivity.this, TasksSelectionActivity.class));
        finish();
    }

    @Override
    public void actionToolbarCallback(ToolbarActions currentItem) {}



    @Override
    public String getTag(){
        return getString(R.string.intro_activity);
    }

    @Override
    public ItemRawPopup getPopupItemOfCurrentActivity() {
        return null;
    }

    @Override
    protected ToolbarEngine getInstanceOfToolbar() {
        return null;
    }

    @Override
    protected int getIdResourcesOfToolbar() {
        return 0;
    }

}
