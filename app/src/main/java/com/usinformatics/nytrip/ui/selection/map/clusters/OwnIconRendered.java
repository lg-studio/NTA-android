package com.usinformatics.nytrip.ui.selection.map.clusters;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by D1m11n on 29.07.2015.
 */

//http://stackoverflow.com/questions/23658561/how-to-set-my-own-icon-for-markers-in-clusterer-in-google-maps
public class OwnIconRendered extends DefaultClusterRenderer<ClusterItemModel> {


    public OwnIconRendered(Context context, GoogleMap map, ClusterManager<ClusterItemModel> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(ClusterItemModel item,
                                               MarkerOptions markerOptions) {
        //markerOptions.icon(item.getMarker().getIcon());
    }
}
