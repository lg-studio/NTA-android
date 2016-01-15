package com.usinformatics.nytrip.ui;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.helpers.ToastHelper;
import com.usinformatics.nytrip.network.Api;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.OnActionToolbarCallback;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;

/**
 * Created by D1m11n on 17.06.2015.
 */
public abstract class BaseActivity extends AppCompatActivity implements OnActionToolbarCallback {


    //TODO ADD TOOLBAR ENGINE MODE

    public abstract String getTag();

    protected ToolbarEngine mToolbarEngine;

    protected void log(String message){
        Log.e(getTag(), message);
    }

    protected void initToolbarEngine(boolean showNavigationButton){
        mToolbarEngine=getInstanceOfToolbar();
        if (mToolbarEngine==null&&getIdResourcesOfToolbar()>0)
            mToolbarEngine=new ToolbarEngine(this, getIdResourcesOfToolbar());
//        if(mToolbarEngine==null)
//            return;
        mToolbarEngine.setToolbarClick(this);
        mToolbarEngine.setNavigationButton(showNavigationButton);
        mToolbarEngine.setActivePopupItemThatWillDisabled(getPopupItemOfCurrentActivity());
    }

    public ToolbarEngine getToolbar(){
        return mToolbarEngine;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("BASE_ACTIVITY", "optionsItemSel" + item );
        Log.e("BASE_ACTIVITY", "toolbarEngine" + mToolbarEngine );
        if(mToolbarEngine==null)
           return super.onOptionsItemSelected(item);
        else
            return mToolbarEngine.onMenuItemClick(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Api.BASE_URL.contains("herok")){
            ToastHelper.showSimple(BaseActivity.this, "LOCAL IP");
        }
    }

    public abstract ItemRawPopup getPopupItemOfCurrentActivity();

    /**
     * If you don't create custom toolbar you can return null
     * @return null or custom toolbarengine
     */
    protected abstract ToolbarEngine getInstanceOfToolbar();

    /**
     * If you don't create custom toolbar you NEED set this parameter
     * @return null or custom toolbarengine
     */
    protected abstract int getIdResourcesOfToolbar();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mToolbarEngine==null)
            return true;
        return mToolbarEngine.onCreateOptionsMenu(menu, R.menu.menu_task_selection, true);
    }

    /**
     *
     * @return true if popup was displayed and dismissed now
     * if popup null or was disissed erlier -return false
     */
    protected boolean dismissPopupIsNeeded(){
        if(mToolbarEngine!=null&&mToolbarEngine.dismissPopupWindow())
            return true;
        return false;
    }



}