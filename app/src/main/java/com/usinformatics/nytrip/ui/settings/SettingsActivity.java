package com.usinformatics.nytrip.ui.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;

/**
 * Created by admin on 7/9/15.
 */
public class SettingsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_settings);
        initToolbarEngine(false);
        mToolbarEngine.setToolbarTitle("Settings");
        initNotificationsSettings();
        initSendFeedback();

    }

    private void initSendFeedback() {
        findViewById(R.id.send_feedback_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
    }
        //TODO  need implement feedback
    private void sendFeedback() {

    }

    //TODO need add behavior for turn on / off notifications
    private void initNotificationsSettings() {
        ((CheckBox) findViewById(R.id.settings_enable_notification)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });
    }

    @Override
    public String getTag() {
        return null;
    }

    @Override
    public ItemRawPopup getPopupItemOfCurrentActivity() {
        return ItemRawPopup.SETTINGS;
    }

    @Override
    protected ToolbarEngine getInstanceOfToolbar() {
        return null;
    }

    @Override
    protected int getIdResourcesOfToolbar() {
        return R.id.settings_toolbar;
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
