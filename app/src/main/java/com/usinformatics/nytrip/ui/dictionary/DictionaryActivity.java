package com.usinformatics.nytrip.ui.dictionary;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;

import java.util.ArrayList;

import common.views.FontTextView;

/**
 * Created by admin on 7/15/15.
 */
public class DictionaryActivity extends BaseActivity {

    private FrameLayout aZTab;
    private FrameLayout thisTab;
    private FontTextView aZText;
    private FontTextView thisText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dictionary);
        initToolbarEngine(true);
        mToolbarEngine.setToolbarTitle("Dictionary");
        initSortByAZ();
        initSortByThis();
        initDictionaryList();
    }

    private void initDictionaryList() {

        ActionSlideExpandableListView list = (ActionSlideExpandableListView)this.findViewById(R.id.dictionary_list);
        ArrayList<WordModel> fakeData = new ArrayList<>();
        WordModel model = new WordModel();
        fakeData.add(model);
        fakeData.add(model);
        fakeData.add(model);

        DictionaryListAdapter adapter = new DictionaryListAdapter(this,fakeData);
        list.setAdapter(adapter);
    }

    private void initSortByAZ() {
        aZTab = ((FrameLayout) findViewById(R.id.sort_by_az_tab));
        aZText = ((FontTextView) findViewById(R.id.dictionary_sort_by_az));

        aZText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideThisTab();
                showAZTab();
            }
        });
    }

    private void hideThisTab() {
        thisTab.setVisibility(View.INVISIBLE);
        thisText.setTextColor(getResources().getColor(R.color.white_54));
    }

    private void showAZTab(){
        aZTab.setVisibility(View.VISIBLE);
        aZText.setTextColor(getResources().getColor(R.color.white));
    }

    private void initSortByThis() {
        thisTab = ((FrameLayout) findViewById(R.id.sort_by_this_tab));
        thisText = ((FontTextView) findViewById(R.id.dictionary_sort_by_this));

        thisText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAZTab();
                showThisTab();
            }
        });
    }

    private void hideAZTab() {
        aZTab.setVisibility(View.INVISIBLE);
        aZText.setTextColor(getResources().getColor(R.color.white_54));
    }

    private void showThisTab() {
        thisTab.setVisibility(View.VISIBLE);
        thisText.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public String getTag() {
        return getString(R.string.dictionary);
    }

    @Override
    public ItemRawPopup getPopupItemOfCurrentActivity() {
        return ItemRawPopup.DICTIONARY;
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

    }

    @Override
    public void onBackPressed() {
        if(dismissPopupIsNeeded())
            return;
        super.onBackPressed();
    }
}
