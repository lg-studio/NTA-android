package com.usinformatics.nytrip.ui.selection.map;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.usinformatics.nytrip.ui.selection.map.clusters.ClusterMapDecorator;

import java.util.ArrayList;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 17.06.2015.
 */
public class MapHelper {


    private ClusterMapDecorator mMapDecorator;
    private GoogleMap mGoogleMap;
    private ArrayList<TaskMarkerModel> mMarkersList;
    private Activity mActivity;


    public static MapHelper instantWithSettings(Activity activity, GoogleMap map) {
        MapHelper helper = new MapHelper();
        helper.mGoogleMap = map;
        helper.mActivity = activity;
        helper.setDefaultSettings();
        helper.prepareCluster();
        return helper;
    }

    private void prepareCluster() {
        mMapDecorator = new ClusterMapDecorator(mActivity, mGoogleMap);
        mGoogleMap.setOnCameraChangeListener(mMapDecorator);
        mGoogleMap.setOnMarkerClickListener(mMapDecorator);
        setCustomRendererAndAlgorithm();
    }


    //TODO SET CLUSTERERS
    private void setCustomRendererAndAlgorithm() {

    }

    private void setDefaultSettings() {
        MapSettingsBuilder.setDefault(mGoogleMap);
    }


    public void setMarkers(ArrayList<TaskMarkerModel> list, int centralMarkerId) {
        mMapDecorator.setMarkers(list);
        addMarkersToMap(list);
        mMarkersList = list; //TODO ME BE DELETE
    }

    private void addMarkersToMap(ArrayList<TaskMarkerModel> list) {
        if (ListsUtils.isEmpty(list))
            return;
        int size =list.size();
        for (int i=0; i<size; i++)
            addItemMarker(list.get(i));
    }

    private void addItemMarker(TaskMarkerModel taskMarkerModel) {
        mGoogleMap.addMarker(generateFor(taskMarkerModel));
    }

    private MarkerOptions generateFor(TaskMarkerModel taskMarkerModel){
        return new MarkerOptions().position(taskMarkerModel.getLatLng()).snippet(taskMarkerModel.snippet).title(taskMarkerModel.title);
    }


}
