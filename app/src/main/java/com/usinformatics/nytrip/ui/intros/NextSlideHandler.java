package com.usinformatics.nytrip.ui.intros;

import android.os.Handler;

import com.usinformatics.nytrip.AppConsts;

/**
 * Created by D1m11n on 22.06.2015.
 */
public class NextSlideHandler {


    private Handler mHandler;

//    private NextSlideHandler(){
//       mHandler= new Handler();
//
//        final  Runnable rr=new Runnable() {
//            @Override
//            public void run() {
//                int next = viewPager.getCurrentItem() + 1;
//                if (next < adapter.getCount()) {
//                    viewPager.setCurrentItem(next);
//                    hh.postDelayed(this, AppConsts.DELAY_INTRO_TRANSITION);
//                } else {
//                    moveToNextWindow();
//                }
//            }};
//        hh.postDelayed(rr, AppConsts.DELAY_INTRO_TRANSITION);
//    }


    public static NextSlideHandler newInstance() {
        NextSlideHandler slider = new NextSlideHandler();
        return slider;
    }

}
