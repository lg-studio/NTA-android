package com.usinformatics.nytrip.ui.additional.popup;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.R;

import java.util.ArrayList;

import common.utis.ScreenUtils;

/**
 * Created by D1m11n on 16.06.2015.
 */
public class PopupWindowBuilder {


    public static final int ID_MENU_SETTING =1;

    private static final int WIDTH=200;
    private static final String TAG = "POPUP_WINDOW_BUILDER";


    private static PopupWindow popupWindow;
    private PopupListAdapter mAdapter;
    private final Context context;
    private static ItemRawPopup mCurrentPopup;
    private static PopupWindowBuilder mBuilder;
    //private ArrayList<ItemRawPopup> list= FakeData.getPopupWindowAkaSettingsData();

    private PopupWindowBuilder(final Activity activity){
        //registerNotificationBroadcastReceiver(activity);
        this.context=activity.getApplicationContext();
    }

    public static PopupWindowBuilder getInstance(final Activity activity){
//        if(mBuilder==null)
          mBuilder = new PopupWindowBuilder(activity);
        return mBuilder;
    }

    public void setCurrentPopup(ItemRawPopup popup){
        Log.e(TAG,"set current popup is " + popup);
        mCurrentPopup=popup;
    }

   //https://www.codeofaninja.com/2013/04/show-listview-as-drop-down-android.html
    private PopupWindowBuilder getWindowFor( final OnItemPopupClick callback){
        Log.e(TAG,"current popup is " + mCurrentPopup);
        if(popupWindow!=null) return this;
        View view =((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.popup_window, null, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        mAdapter= new PopupListAdapter(context,getListWithout(mCurrentPopup));
        listView.setAdapter(mAdapter);
        popupWindow = new PopupWindow(view,(int) ScreenUtils.dpToPixels(context, WIDTH),WindowManager.LayoutParams.WRAP_CONTENT, true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClickHandler(parent,position,callback);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
               onPopupDismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        return this;
    }

    private void onItemClickHandler(final AdapterView<?> parent, int position,final OnItemPopupClick callback){
        Log.e("POPUP", "item click");
        ItemRawPopup p=(ItemRawPopup) parent.getItemAtPosition(position);
        if(callback!=null&&p!=mCurrentPopup)
            callback.onItemPopupClick((ItemRawPopup) parent.getItemAtPosition(position));
        Log.e(TAG,"DISMISS");
        if(p!=ItemRawPopup.LOGOUT)
            mCurrentPopup=p;
        popupWindow.dismiss();
    }

    private void onPopupDismiss(){
        clearData(context);
        popupWindow=null;
    }


    public void showAsDropDown (View anchor,OnItemPopupClick callback ){
        if (anchor==null) return;
        if(popupWindow==null){
            getWindowFor(callback);
        }
        if(popupWindow.isShowing())
            return;
        popupWindow.showAsDropDown(anchor);
    }


    //TODO ADD
    public boolean isShowing(){
        return popupWindow==null?false:popupWindow.isShowing();
    }

    public void dismiss(){
        if(!isShowing()) return;
          popupWindow.dismiss();
    }


    private void clearData(Context context) {
        if(mAdapter!=null)
          mAdapter.clear();
        mAdapter=null;
    }


    private void registerNotificationBroadcastReceiver(Activity activity){
            activity.registerReceiver(NotifReceiver, new IntentFilter(AppConsts.BROADCAST_NOTIFICATION));
    }

    private void unregisterNotificationBroadcastReceiver(Context context){
        try {
            context.unregisterReceiver(NotifReceiver);
        }catch (IllegalArgumentException e){
            Log.e(TAG,"unregister receiver = " + e.toString());
        }
    }

    private BroadcastReceiver NotifReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mAdapter!=null) {
                mAdapter.updateNotifications(intent.getIntExtra(AppConsts.EXTRA_NOTIFICATION, -1));
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    //TODO UPDATE HAS LEAKED ERROR
    public void release(){
        //unregisterNotificationBroadcastReceiver(context);
        popupWindow=null;
    }

    private ArrayList<ItemRawPopup> getList(){
        ArrayList<ItemRawPopup> list = new ArrayList<>();
        for (int i=0; i< ItemRawPopup.values().length; i++){
            list.add(ItemRawPopup.values()[i]);
        }
        return list;
    }

    private ArrayList<ItemRawPopup> getListWithout(ItemRawPopup popup){
        if(popup==null)
            return getList();
        ArrayList<ItemRawPopup> list = new ArrayList<>();
        for (int i=0; i< ItemRawPopup.values().length; i++){
            if(ItemRawPopup.values()[i].getModel()==popup.getModel())
                continue;
            list.add(ItemRawPopup.values()[i]);
        }
        return list;
    }
}
