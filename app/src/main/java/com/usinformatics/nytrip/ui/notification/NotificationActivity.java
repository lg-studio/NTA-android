package com.usinformatics.nytrip.ui.notification;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;

import common.views.FontTextView;

/**
 * Created by admin on 7/24/15.
 */
public class NotificationActivity extends BaseActivity {

    private FrameLayout feedbackTab;
    private FrameLayout messageTab;
    private FontTextView feedbackText;
    private FontTextView messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_notification);
        initToolbarEngine(true);
        mToolbarEngine.setToolbarTitle("Notifications");
        initFeedbackTab();
        initMessageTab();
    }

    private void initFeedbackTab() {
        feedbackTab = ((FrameLayout) findViewById(R.id.first_tab_line));
        feedbackText = ((FontTextView) findViewById(R.id.first_tab_text));
        feedbackText.setText(getString(R.string.feedback));

        feedbackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMessageTab();
                showFeedbackTab();
                showFeedbackFragment();
            }
        });

        showFeedbackFragment();
    }

    private void showFeedbackFragment() {
        NotificationFeedbackFragment feedbackFragment = new NotificationFeedbackFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, feedbackFragment)
                .commit();
    }

    private void initMessageTab() {
        messageTab = ((FrameLayout) findViewById(R.id.second_tab_line));
        messageText = ((FontTextView) findViewById(R.id.second_tab_text));
        messageText.setText(getString(R.string.message));
        messageText.setTextColor(getResources().getColor(R.color.white_54));

        messageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFeedbackTab();
                showMessageTab();
                showMessageTab();
            }
        });
    }

    private void hideFeedbackTab() {
        feedbackTab.setVisibility(View.INVISIBLE);
        feedbackText.setTextColor(getResources().getColor(R.color.white_54));
    }

    private void hideMessageTab() {
        messageTab.setVisibility(View.INVISIBLE);
        messageText.setTextColor(getResources().getColor(R.color.white_54));
    }

    private void showFeedbackTab(){
        feedbackTab.setVisibility(View.VISIBLE);
        feedbackText.setTextColor(getResources().getColor(R.color.white));
    }

    private void showMessageTab() {
        messageTab.setVisibility(View.VISIBLE);
        messageText.setTextColor(getResources().getColor(R.color.white));
        showMessageFragment();
    }

    private void showMessageFragment() {
        NotificationMessageFragment messageFragment = new NotificationMessageFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, messageFragment)
                .commit();
    }

    @Override
    public String getTag() {
        return null;
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
        return R.id.profile_toolbar;
    }

    @Override
    public void actionToolbarCallback(ToolbarActions currentItem) {
        if(currentItem==ToolbarActions.BACK)
            this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(dismissPopupIsNeeded())
            return;
        super.onBackPressed();
    }
}
