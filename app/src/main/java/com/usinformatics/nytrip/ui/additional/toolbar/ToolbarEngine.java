package com.usinformatics.nytrip.ui.additional.toolbar;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.popup.OnItemPopupClick;
import com.usinformatics.nytrip.ui.additional.popup.PopupWindowBuilder;

/**
 * Created by D1m11n on 10.07.2015.
 */
public class ToolbarEngine implements OnItemPopupClick, MenuItem.OnMenuItemClickListener {


    private static final String TAG = "TOOLBAR_ENGINE";
    protected final AppCompatActivity mActivity;
    protected MenuItem mMenu;
    private boolean isMenuVisible=true;
    protected boolean isEnabled = true;
    private static PopupWindowBuilder mPopupWindow;
    protected OnActionToolbarCallback mToolbarClickCallback;

    protected Toolbar mToolbar;

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public ToolbarEngine(AppCompatActivity activity, int instanceID) {
        mActivity = activity;
        mToolbar = (Toolbar) mActivity.findViewById(instanceID);
        prepareActionBarWithToolbar();
    }

    public ToolbarEngine(AppCompatActivity activity, Toolbar toolbar) {
        mActivity = activity;
        mToolbar = toolbar;
        prepareActionBarWithToolbar();
    }

    private void prepareActionBarWithToolbar() {
        mActivity.setSupportActionBar(mToolbar);
    }

    public boolean onCreateOptionsMenu(final Menu menu, int idMenuToInflate, boolean showSettings) {
        Log.e("TOOLBAR_ENGINE", "onCreateOptionsMenu");
        if (menu == null || idMenuToInflate <= 0) return false;
        buildExtMenu(menu, idMenuToInflate);
        if (showSettings)
            addSettingItem(menu);
        return true;
    }

    private void addSettingItem(Menu menu) {
        mMenu = menu.add(Menu.NONE, PopupWindowBuilder.ID_MENU_SETTING, 100, "");
        mMenu.setIcon(R.mipmap.ic_menu_more);
        mMenu.setOnMenuItemClickListener(this);
        //mMenu.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        MenuItemCompat.setShowAsAction(mMenu,MenuItem.SHOW_AS_ACTION_ALWAYS);
        mMenu.setVisible(isMenuVisible);
    }

    @Override
    public void onItemPopupClick(final ItemRawPopup popup) {
        PopupClickHelper.onClick(mActivity, popup);
    }


    protected void buildExtMenu(final Menu menu, int idMenuToInflate) {}

    public void setVisibilityMenu(boolean isVisible){
        if(mMenu!=null)
            mMenu.setVisible(isVisible);
        else isMenuVisible=isVisible;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (!isEnabled) return true;
        if (item.getItemId() == mMenu.getItemId()) {
            onMenuClick();
            return true;
        }
        return onToolbarItemClick(item);
    }

    protected boolean onToolbarItemClick(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            if(mToolbarClickCallback!=null)
                mToolbarClickCallback.actionToolbarCallback(ToolbarActions.BACK);
        }
        return true;
    }

    private void onMenuClick() {
        Log.e(TAG, "onMenuClick");
        if (mPopupWindow == null) {
            mPopupWindow = PopupWindowBuilder.getInstance(mActivity);
            mPopupWindow.showAsDropDown(mActivity.findViewById(mMenu.getItemId()), this);
            Log.e(TAG, "onMenuClick new");
            return;
        }
        if (mPopupWindow.isShowing())
            return;
        mPopupWindow.showAsDropDown(mActivity.findViewById(mMenu.getItemId()), this);
    }

    public void setActivePopupItemThatWillDisabled(ItemRawPopup popup) {
        PopupWindowBuilder.getInstance(mActivity).setCurrentPopup(popup);
    }

    public boolean isPopupOpened() {
        return mPopupWindow != null ? mPopupWindow.isShowing() : false;
    }

    public boolean dismissPopupWindow() {
        if (isPopupOpened()) {
            mPopupWindow.dismiss();
            return true;
        }
        return false;
    }

    protected void release() {
        if (mPopupWindow != null)
            mPopupWindow.release();
    }

    public void setToolbarClick(OnActionToolbarCallback toolbarClick) {
        this.mToolbarClickCallback = toolbarClick;
    }

    public void setToolbarTitle(String title) {
        mActivity.getSupportActionBar().setTitle("  " + String.valueOf(title));
        mToolbar.setTitleTextColor(mActivity.getResources().getColor(android.R.color.white));
    }

    public void setToolbarTitle(int idTitleString) {
        setToolbarTitle(mActivity.getString(idTitleString));
    }

    //TODO
    public void setNavigationButton(boolean isArrow) {
        if (isArrow) {
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(false);
            mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        } else {
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true); //SHOW LOGO ICON
            mToolbar.setNavigationIcon(R.mipmap.ic_launcher);

        }
    }
}

