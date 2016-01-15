package common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import com.usinformatics.nytrip.R;

import java.lang.reflect.Field;

/**
 * Created by D1m11n on 12.06.2015.
 */
public class SmoothScrollViewPager extends ViewPager {

    private static final  String TAG="SMOOTH_SCROLL_PAGER";

    private int mDuration_ms =1000;

    public SmoothScrollViewPager(Context context){
            super(context);
    }

    public SmoothScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScrollDuration(context,attrs);
        replaceNativeScroller();
    }

    private void setScrollDuration(Context context, AttributeSet attrs){
        TypedArray t = context.obtainStyledAttributes(attrs, new int[]{R.styleable.SmoothScrollViewPager_scrollDurationms});
        mDuration_ms = t.getInt(0,1000);
        t.recycle();

    }

    private void replaceNativeScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            Log.e(TAG,"setScroller error = " + e.toString());
        }
    }

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration_ms);
            Log.e(TAG,"duration is = " + duration);
        }
    }
}