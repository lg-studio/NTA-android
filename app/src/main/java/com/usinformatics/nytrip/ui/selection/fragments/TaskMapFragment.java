package com.usinformatics.nytrip.ui.selection.fragments;

import android.app.Activity;
import android.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.usinformatics.nytrip.ui.selection.map.MapHelper;
import com.usinformatics.nytrip.ui.selection.map.TaskMarkerModel;

import java.util.ArrayList;

/**
 * Created by D1m11n on 17.06.2015.
 */
public class TaskMapFragment extends MapFragment implements OnMapReadyCallback, IFragment {

    private MapHelper mMapHelper;
    private ArrayList<TaskMarkerModel> mTempMarkers;
    private int mIdCentralMarker;


    public static TaskMapFragment newInstance(){
        return new TaskMapFragment();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       mMapHelper = MapHelper.instantWithSettings(TaskMapFragment.this.getActivity(),googleMap);
        if (mTempMarkers!=null){
            mMapHelper.setMarkers(mTempMarkers,mIdCentralMarker); //TODO CHECK ON NULL POINTER
            mTempMarkers=null;
        }

    }

    public void setMarkers(ArrayList<TaskMarkerModel> list, int idCentralMarker){
       if(mMapHelper!=null) {
           mMapHelper.setMarkers(list, 0);
           return;
       }
        mIdCentralMarker=idCentralMarker;
        mTempMarkers=list;
    }



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
}
