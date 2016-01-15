package common.containers;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by D1m11n on 02.07.2015.
 *
 *
 * https://gist.github.com/devunwired/8cbe094bb7a783e37ad1
 * http://stackoverflow.com/questions/9907748/viewpager-get-a-partial-view-of-the-next-page
 *http://thehayro.blogspot.de/2013/09/infiniteviewpager-infinite-paging.html
 * http://stackoverflow.com/questions/21877816/viewpager-fragmentstatepageradapter-and-infinite-scrolling
 * http://stackoverflow.com/questions/8836323/multiple-pages-at-the-same-time-on-a-viewpager
 * http://speakman.net.nz/blog/2014/02/20/a-bug-in-and-a-fix-for-the-way-fragmentstatepageradapter-handles-fragment-restoration/
 * http://stackoverflow.com/questions/9907748/viewpager-get-a-partial-view-of-the-next-page
 *
 <com.example.pagercontainer.PagerContainer
 android:id="@+id/pager_container"
 android:layout_width="match_parent"
 android:layout_height="wrap_content"
 android:background="#CCC">
 <android.support.v4.view.ViewPager
 android:layout_width="150dp"
 android:layout_height="100dp"
 android:layout_gravity="center_horizontal" />
 </com.example.pagercontainer.PagerContainer>
 *
 *
 *
 *
 */
public class SlidePagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mPager;
    boolean mNeedsRedraw = false;

    public SlidePagerContainer(Context context) {
        super(context);
        init();
    }

    public SlidePagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidePagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //Disable clipping of children so non-selected pages are visible
        setClipChildren(false);

        //Child clipping doesn't work with hardware acceleration in Android 3.x/4.x
        //You need to set this value here if using hardware acceleration in an
        // application targeted at these releases.
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onFinishInflate() {
        try {
            mPager = (ViewPager) getChildAt(0);
            mPager.setOnPageChangeListener(this);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    public ViewPager getViewPager() {
        return mPager;
    }

    private Point mCenter = new Point();
    private Point mInitialTouch = new Point();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenter.x = w / 2;
        mCenter.y = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //We capture any touches not already handled by the ViewPager
        // to implement scrolling from a touch outside the pager bounds.
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouch.x = (int)ev.getX();
                mInitialTouch.y = (int)ev.getY();
            default:
                ev.offsetLocation(mCenter.x - mInitialTouch.x, mCenter.y - mInitialTouch.y);
                break;
        }

        return mPager.dispatchTouchEvent(ev);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Force the container to redraw on scrolling.
        //Without this the outer pages render initially and then stay static
        if (mNeedsRedraw) invalidate();
    }

    @Override
    public void onPageSelected(int position) { }

    @Override
    public void onPageScrollStateChanged(int state) {
        mNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }
}
