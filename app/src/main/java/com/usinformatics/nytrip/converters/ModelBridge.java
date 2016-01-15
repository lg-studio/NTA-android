package com.usinformatics.nytrip.converters;

import android.content.Context;

import com.usinformatics.nytrip.databases.model.CourseDBModel;
import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.ui.selection.map.TaskMarkerModel;
import com.usinformatics.nytrip.ui.selection.map.clusters.ClusterItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D1m11n on 18.06.2015.
 */
public class ModelBridge {

    private ModelBridge(){}

    public static TaskMarkerModel taskMarkerFrom(TaskModel model){
        TaskMarkerModel m= new TaskMarkerModel();
        m.title=model.name;
        return m;
    }


    public static ArrayList<ClusterItemModel> getClusterListFrom(final Context context,ArrayList<TaskMarkerModel> markers){
        ArrayList<ClusterItemModel> list= new ArrayList<>();
        int size=markers.size();
        for (int i=0; i<size; i++)
            list.add(new ClusterItemModel(context.getApplicationContext(),markers.get(i)));
        return list;
    }


//    public static NetworkUserModel getNetworkUserModel(UserModel user){
//        NetworkUserModel mm=new NetworkUserModel();
//        mm.code=user.teacherCode;
//        mm.email=user.email;
//        mm.firstName=user.firstName;
//        mm.lastName=user.lastName;
//        mm.password=user.getPassword();
//        return mm;
//    }

//    public static UserModel getAccountUserModel(NetworkUserModel user, String password){
//        UserModel mm=new UserModel();
//        mm.teacherCode =user.code;
//        mm.email=user.email;
//        mm.firstName=user.firstName;
//        mm.lastName=user.lastName;
//        return mm;
//    }


//    public static void updateModel(UserModel target, NetworkUserModel updater){
//        target.firstName=updater.firstName;
//        target.lastName=updater.lastName;
//        target.token=updater.token;
//        target.email=updater.email;
//        target.uuId =updater.uuid;
//        target.setGroups(updater.groupID);
//    }


//    public static void updateModel( NetworkUserModel  from, UserModel to){
//
//    }

    public static CourseDBModel getCoursesDbModelFrom(CourseModel model){
        CourseDBModel m= new CourseDBModel();
        m.setDesc(model.desc);
        m.setName(model.getName());
        m.setUuid(model.id);
        m.setEpisodesArray(model.episodeIDs);
        return m;
    }

    public static List<CourseDBModel> getCoursesDbModelFrom(List<CourseModel> models){
        List<CourseDBModel> list = new ArrayList<>();
        for(int i=0; i<models.size(); i++){
            list.add(ModelBridge.getCoursesDbModelFrom(models.get(i)));
        }
        return list;
    }


    public static ArrayList<CourseModel> getSemestersFrom(List<CourseDBModel> models){
        ArrayList<CourseModel> list = new ArrayList<>();
        for(int i=0; i<models.size(); i++){
            list.add(ModelBridge.getCourseModelFrom(models.get(i)));
        }
        return list;
    }


    public static CourseModel getCourseModelFrom(CourseDBModel model){
        CourseModel m= new CourseModel();
        m.desc=model.getDesc();
        m.setName(model.getName());
        m.id=model.getUuid();
        m.episodeIDs =model.getEpisodesArray();
        return m;
    }

}
