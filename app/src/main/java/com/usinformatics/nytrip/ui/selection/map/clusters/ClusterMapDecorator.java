package com.usinformatics.nytrip.ui.selection.map.clusters;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.usinformatics.nytrip.converters.ModelBridge;
import com.usinformatics.nytrip.ui.selection.map.MapMarkerClicker;
import com.usinformatics.nytrip.ui.selection.map.TaskMarkerModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by D1m11n on 19.06.2015.
 */
public class ClusterMapDecorator extends ClusterManager<ClusterItemModel> {


    private GoogleMap mGoogleMap;
    private Context mContext;

    private HashMap<Marker, String> mMarkers;

    public ClusterMapDecorator(Context context, GoogleMap map) {
        super(context, map);
        mContext=context;
        mGoogleMap=map;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("CLUSTERMAPDECORATOR", "marker click");
        MapMarkerClicker.displayInfo(marker);
        return super.onMarkerClick(marker);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        super.onCameraChange(cameraPosition);
    }

    public void setMarkers(ArrayList<TaskMarkerModel> list) {
        addItems(ModelBridge.getClusterListFrom(mContext, list));
    }

    public void setMapMarkersTasks(HashMap<Marker, String> list) {
        //addItems(ModelBridge.getClusterListFrom(mContext, list));
        //mMarkers=list;
    }



    /*
    https://developers.google.com/maps/documentation/android/utility/marker-clustering
    https://developers.google.com/maps/documentation/android/marker

    private void addItems() {

    // Set some lat/lng coordinates to start with.
    double lat = 51.5145160;
    double lng = -0.1270060;

    // Add ten cluster items in close proximity, for purposes of this example.
    for (int i = 0; i < 10; i++) {
        double offset = i / 60d;
        lat = lat + offset;
        lng = lng + offset;
        MyItem offsetItem = new MyItem(lat, lng);
        mClusterManager.addItem(offsetItem);
    }
     */
}
