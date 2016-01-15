package com.usinformatics.nytrip.ui.selection.models;

import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by D1m11n on 30.06.2015.
 */
public class ScenesWithEpisodeDescriptionModel {

    public ArrayList<SceneModel> mScenesList;

    public EpisodeModel episode;


    public ScenesWithEpisodeDescriptionModel(SceneModel [] scenes, EpisodeModel episode ){
        mScenesList= new ArrayList<>(Arrays.asList(scenes));
        this.episode=episode;
    }


}
