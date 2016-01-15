package com.usinformatics.nytrip.ui.selection.map.clusters;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.usinformatics.nytrip.ui.selection.map.TaskMarkerModel;

/**
 * Created by D1m11n on 19.06.2015.
 */
public class ClusterItemModel implements ClusterItem{


    private TaskMarkerModel mMarker;
    private Context mContext;


    public ClusterItemModel (Context context, TaskMarkerModel markerModel){
        mMarker=markerModel;
        mContext=context;
    }

    @Override
    public LatLng getPosition() {
        return mMarker.getLatLng();
    }
}
