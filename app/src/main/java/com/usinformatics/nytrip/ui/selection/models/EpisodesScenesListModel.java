package com.usinformatics.nytrip.ui.selection.models;

import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by D1m11n on 15.06.2015.
 */
public class EpisodesScenesListModel {


    private final HashMap<EpisodeModel,SceneModel[]> childArray;

    private final EpisodeModel[] groupArray;


//    private SparseArray<ArrayList<ItemChildContentModel>> childItems;
//
//
//    private ArrayList<ItemGroupContentModel> groupItems;
//
//    public EpisodesScenesListModel(ArrayList<ItemGroupContentModel> groups, SparseArray<ArrayList<ItemChildContentModel>> childs){
//        this.groupItems =groups;
//        this.childItems=childs;
//
//    }
//
//
    public HashMap<EpisodeModel,SceneModel[]> getChildItems() {
        return childArray;
    }
//
    public EpisodeModel[] getGroupItems(){
        return groupArray;
    }

    public EpisodesScenesListModel(EpisodeModel[] groupArray, HashMap<EpisodeModel,SceneModel[]> childArray){
        this.groupArray=groupArray;
        this.childArray=childArray;
    }

    public EpisodesScenesListModel(List<EpisodeModel> groupArray, HashMap<EpisodeModel,SceneModel[]> childArray){
        this.groupArray= groupArray.toArray(new EpisodeModel[groupArray.size()]);
        this.childArray=childArray;
    }

}
