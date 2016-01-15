package com.usinformatics.nytrip.ui.additional.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.usinformatics.nytrip.R;

/**
 * Created by D1m11n on 16.06.2015.
 */
public class ExtToolbarEngine extends ToolbarEngine  {

    private static final String TAG = "CUSTOM_TOOLBAR_ENGINE";


    private MenuItem mMap;
    private MenuItem mScenesTasks;
    private MenuItem mInfo;
    private ToolbarMode mMode;


    public ExtToolbarEngine(AppCompatActivity activity, int idToolbar) {
        super(activity, idToolbar);
    }

    public ExtToolbarEngine(AppCompatActivity activity, Toolbar toolbar) {
        super(activity, toolbar);
    }


    private void findAndInitMenuItems(final Menu menu) {
        mInfo=menu.findItem(R.id.action_info);
        mMap=menu.findItem(R.id.action_map);
        mScenesTasks=menu.findItem(R.id.action_scenes_tasks);
        mInfo.setOnMenuItemClickListener(this);
        mMap.setOnMenuItemClickListener(this);
        mScenesTasks.setOnMenuItemClickListener(this);
        if(mMode!=null)
            showViews(mMode);
    }

    /*
http://stackoverflow.com/questions/10397613/how-can-i-get-the-current-location-of-an-actionbar-menuitem/13300185#13300185
I suggest adding if(viewTreeObserver.isAlive()) check before removing the listener because sometimes an exception is thrown if it's not alive. –  M.Sameer Dec 16 '13 at 16:41
*/

    public void setToolbarTitle(String title,ToolbarMode mode) {
        super.setToolbarTitle(title);
        if(mScenesTasks == null)
            mMode=mode;
        else
            showViews(mode);
    }

    public void setToolbarTitle(int idTitleString,ToolbarMode mode) {
        setToolbarTitle(mActivity.getString(idTitleString), mode);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public boolean onToolbarBackClick(MenuItem item) {
        if (!isEnabled) return true;
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.e(TAG, "BACK BUTTON CLICK");
                mToolbarClickCallback.actionToolbarCallback(ToolbarActions.BACK);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected boolean onToolbarItemClick(MenuItem item) {
        ToolbarActions action=null;
        switch(item.getItemId()){
            case R.id.action_info: action= ToolbarActions.INFO; break;
            case R.id.action_map: action= ToolbarActions.MAP; break;
            case R.id.action_scenes_tasks: action= ToolbarActions.SCENES_WTH_TASKS; break;
        }
        mToolbarClickCallback.actionToolbarCallback(action);
        return true;
    }

    private void showViews(ToolbarMode mode) {
        mMode=null;
        mMap.setVisible(false);
        mScenesTasks.setVisible(false);
        mInfo.setVisible(false);
        switch (mode) {
            case MAP:
                mScenesTasks.setVisible(true);
                break;
            case SIMPLE_HINT:
                break;
            case SIMPLE:
                mInfo.setVisible(true);
                break;
            case TASKS:
                mMap.setVisible(true);
                break;
            default:
                break;
        }
    }
@Override
public void release(){
    super.release();
}

    @Override
    protected void buildExtMenu(Menu menu, int idMenuToInflate) {
        MenuInflater inflater = mActivity.getMenuInflater();
        inflater.inflate(idMenuToInflate, menu);
        findAndInitMenuItems(menu);
    }

//    private void updateMarginLeftLayoutFromToolbarInset() {
//        int left = mToolbar.getContentInsetLeft() > 0 ? mToolbar.getContentInsetLeft() : mToolbar.getContentInsetStart();
//        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        lp.setMargins(left - 20, 0, 0, 0);
//        View v = mToolbar.findViewById(R.id.include_map_toolbar);
//        ((LinearLayout) v).setLayoutParams(lp);
//    }
}
