package com.usinformatics.nytrip.ui.selection.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;

import java.util.ArrayList;
import java.util.HashMap;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 17.06.2015.
 */
public class MapHelper {


    //private ClusterMapDecorator mMapDecorator;
    private GoogleMap mGoogleMap;
    private ArrayList<TaskMarkerModel> mTaskMarkersList;
    private HashMap<Marker, String> mMarkers= new HashMap<>();
    private TasksSelectionActivity mActivity;
    private String mCentralMarkerId;


    public static MapHelper instantWithSettings(TasksSelectionActivity  activity, GoogleMap map) {
        MapHelper helper = new MapHelper();
        helper.mGoogleMap = map;
        helper.mActivity = activity;
        helper.setDefaultSettings();
        helper.fillMap();
        return helper;
    }

    private void fillMap() {
        //mMapDecorator = new ClusterMapDecorator(mActivity, mGoogleMap);
        //mGoogleMap.setOnCameraChangeListener(mMapDecorator);
//        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                MapMarkerClicker.displayInfo(marker);
//                //marker.showInfoWindow();
//                return false;
//            }
//        });
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                    mActivity.displayScenesTasks(mMarkers.get(marker));
            }
        });
        setCustomRendererAndAlgorithm();
    }


    //TODO SET CLUSTERERS
    private void setCustomRendererAndAlgorithm() {
        //mMapDecorator.setRenderer(new );
    }

    private void setDefaultSettings() {
        MapSettingsBuilder.setDefault(mGoogleMap);
    }


    public void setMarkers(ArrayList<TaskMarkerModel> list, String centralMarkerId) {
        //mMapDecorator.setMarkers(list);
        addMarkersToMap(list);
        mTaskMarkersList = list;
        mCentralMarkerId=centralMarkerId;
    }

    public void setMarkers(ArrayList<TaskMarkerModel> list) {
        setMarkers(list, null);
    }

    private void addMarkersToMap(ArrayList<TaskMarkerModel> list) {
        if (ListsUtils.isEmpty(list))
            return;
        int size =list.size();
        for (int i=0; i<size; i++)
            addItemMarker(list.get(i));
        //mMapDecorator.setMapMarkersTasks(mMarkers);

    }

    private void addItemMarker(TaskMarkerModel taskMarkerModel) {
        Marker m=mGoogleMap.addMarker(generateFor(taskMarkerModel));
        mMarkers.put(m,taskMarkerModel.taskId);
        //m.showInfoWindow();
    }

    private MarkerOptions generateFor(TaskMarkerModel taskMarkerModel){
        return new MarkerOptions().
                position(taskMarkerModel.getLatLng()).
                title("Name: " + taskMarkerModel.title).
               // snippet("SNIPPET =" + taskMarkerModel.snippet).
                icon(BitmapDescriptorFactory.fromResource(taskMarkerModel.idIcon));
    }


}
