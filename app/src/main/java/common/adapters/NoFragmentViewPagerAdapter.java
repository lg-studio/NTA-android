package common.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 15.06.2015.
 */



public abstract class  NoFragmentViewPagerAdapter<T> extends PagerAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    private ArrayList<T> mViewsList;

    public NoFragmentViewPagerAdapter(Context context, ArrayList<T> pagerItems) {
        super();
        this.mViewsList = pagerItems;
  //      Log.e("NO_FRAGMENT_VIEW_PAGER", "views list count = " + mViewsList.size());
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    protected LayoutInflater getInflater(){
        return inflater;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = generateViewFor(position);
        container.addView(view);
        return view;
    }

    public ArrayList<T> getAllItems(){
        return mViewsList;
    }

    public T getItem(int position){
        if (ListsUtils.isEmpty(mViewsList))
            return null;
        if (mViewsList.size()<=position||position<0)
            return null;
        return mViewsList.get(position);
    }

    /**
     * LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_scene_in_pager, null, false);
     TextView text = (TextView) layout.findViewById(R.id.tv_frg_item_scene);
     text.setText(mViewsList.get(position).title);
     return layout;
     *
     * @param position
     * @return
     */

    public abstract View generateViewFor(int position);

    @Override
    public int getCount() {
        return ListsUtils.isEmpty(mViewsList) ? 0 : mViewsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}



/*


 @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout layout = null;
        layout = (LinearLayout) inflater.inflate(R.layout.item_viewpager, null);
        TextView text = (TextView) layout.findViewById(R.id.idtext);
        text.setText(pagerItems.get(position).getText());
        LinearLayout main = (LinearLayout) layout.findViewById(R.id.main);
        main.setBackgroundColor(context.getResources().getColor(pagerItems.get(position).getColor()));
        container.addView(layout);
        return layout;
    }
 */