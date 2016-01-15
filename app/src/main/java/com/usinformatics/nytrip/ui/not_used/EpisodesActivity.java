package com.usinformatics.nytrip.ui.not_used;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;
import com.usinformatics.nytrip.audio.SpeechRecognizerEngine;

/**
 * Created by D1m11n on 12.06.2015.
 */
public class EpisodesActivity extends BaseActivity {



    @Override
    public String getTag() {
        return "SCENE_ACTIVITY";
    }

    @Override
    public ItemRawPopup getPopupItemOfCurrentActivity() {
        return ItemRawPopup.USER_ACTIVITY;
    }

    @Override
    protected ToolbarEngine getInstanceOfToolbar() {
        return null;
    }

    @Override
    protected int getIdResourcesOfToolbar() {
        return 0;
    }


    private SpeechRecognizerEngine mSpeechEngine;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_episodes);
        //initSimpleToolbar(R.id.toolbar, getString(R.string.Scene_selection));
        //setActionBar((Toolbar)findViewById(R.id.toolbar));
        //getFragmentManager().beginTransaction().replace(R.id.content, EpisodesWithScenesFragment.newInstance()).commit();
    }



//    @Override
//    public void actionToolbarCallback(Toolbar toolbar, ExtToolbarEngine.ItemToolbarClick lastItem, ExtToolbarEngine.ItemToolbarClick currentItem) {
//        log("actionToolbarCallback" + currentItem.toString());
//        if (currentItem.equals(ExtToolbarEngine.ItemToolbarClick.PREFS))
//            return;
//        new TextSpeechEngine(EpisodesActivity.this).startTextSpeecher(new TextSpeechEngine.OnTextSpeechCallback() {
//            @Override
//            public void onError(String message) {
//                log("textSpeecher error = " + message);
//            }
//
//            @Override
//            public void onReadyToSpeech(TextToSpeech textSpeech) {
//                textSpeech.speak("Helloo my firends; Glad to see you", TextToSpeech.QUEUE_FLUSH, null);
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSpeechEngine.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void actionToolbarCallback(ToolbarActions currentItem) {

    }

}

