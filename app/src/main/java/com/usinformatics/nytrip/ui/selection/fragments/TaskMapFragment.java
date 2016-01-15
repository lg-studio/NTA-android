package com.usinformatics.nytrip.ui.selection.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;
import com.usinformatics.nytrip.ui.selection.map.MapHelper;
import com.usinformatics.nytrip.ui.selection.map.TaskMarkerModel;

import java.util.ArrayList;

/**
 * Created by D1m11n on 17.06.2015.
 */
public class TaskMapFragment extends MapFragment implements OnMapReadyCallback, IFragment {

    private static final String TAG = TaskMapFragment.class.getSimpleName();
    private MapHelper mMapHelper;
    private ArrayList<TaskMarkerModel> mMarkers;
    private String mIdCentralMarker;


    public static TaskMapFragment newInstance(ArrayList<TaskMarkerModel> markers){
        TaskMapFragment fr= new TaskMapFragment();
        fr.mMarkers=markers;
        return fr;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       mMapHelper = MapHelper.instantWithSettings((TasksSelectionActivity) TaskMapFragment.this.getActivity(),googleMap);
        if (mMarkers !=null){
            mMapHelper.setMarkers(mMarkers); //TODO CHECK ON NULL POINTER
        }
        printMarkersToLog();
    }

//    public void setMarkers(ArrayList<TaskMarkerModel> list, String idCentralMarker){
//       if(mMapHelper!=null) {
//           mMapHelper.setMarkers(list);
//           return;
//       }
//        mIdCentralMarker=idCentralMarker;
//        mMarkers =list;
//    }

    @Override
    public Type getFragmentType() {
        return Type.MAP;
    }

    @Override
    public void updateContent() {

    }

    @Override
    public Fragment getInstance() {
        return TaskMapFragment.this;
    }

    private void printMarkersToLog(){
        if(mMarkers==null){
            Log.e(TAG, "Markers are empty");
            return;
        }
        for(TaskMarkerModel m:mMarkers)
            Log.e(TAG, m.toString());
    }
}
