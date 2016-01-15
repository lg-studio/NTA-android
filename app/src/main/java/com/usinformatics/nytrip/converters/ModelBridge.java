package com.usinformatics.nytrip.converters;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.usinformatics.nytrip.FakeData;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.databases.model.CourseDBModel;
import com.usinformatics.nytrip.databases.model.EpisodeDBModel;
import com.usinformatics.nytrip.databases.model.TaskDbModel;
import com.usinformatics.nytrip.models.AudioFileIdModel;
import com.usinformatics.nytrip.models.CharacterModel;
import com.usinformatics.nytrip.models.ChatModel;
import com.usinformatics.nytrip.models.ClassModel;
import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.ExecutedAnswerModel;
import com.usinformatics.nytrip.models.ExecutedChatModel;
import com.usinformatics.nytrip.models.GroupModel;
import com.usinformatics.nytrip.models.LocationModel;
import com.usinformatics.nytrip.models.QuestionModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.models.UserModel;
import com.usinformatics.nytrip.models.types.QuestionType;
import com.usinformatics.nytrip.network.models.NetAudioFileIdModel;
import com.usinformatics.nytrip.network.models.NetCharacterModel;
import com.usinformatics.nytrip.network.models.NetChatModel;
import com.usinformatics.nytrip.network.models.NetClassModel;
import com.usinformatics.nytrip.network.models.NetCourseModel;
import com.usinformatics.nytrip.network.models.NetEpisodeModel;
import com.usinformatics.nytrip.network.models.NetExecutedAnswerModel;
import com.usinformatics.nytrip.network.models.NetExecutedChatModel;
import com.usinformatics.nytrip.network.models.NetGroupModel;
import com.usinformatics.nytrip.network.models.NetLocationModel;
import com.usinformatics.nytrip.network.models.NetQuestionModel;
import com.usinformatics.nytrip.network.models.NetSceneModel;
import com.usinformatics.nytrip.network.models.NetTaskModel;
import com.usinformatics.nytrip.network.models.NetUserModel;
import com.usinformatics.nytrip.ui.selection.map.TaskMarkerModel;
import com.usinformatics.nytrip.ui.selection.map.clusters.ClusterItemModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 18.06.2015.
 */

//TODO SPLIT ON REPOSITORY AND INNER APP
public class ModelBridge {

    private static final String TAG = ModelBridge.class.getSimpleName();

    private ModelBridge() {
    }

    public static TaskMarkerModel taskMarkerFrom(TaskModel model) {
        TaskMarkerModel m = new TaskMarkerModel();
        m.title =  model.name;
        m.latlng = model.location.latLng;
        return m;
    }


    public static ArrayList<ClusterItemModel> getClusterListFrom(final Context context, ArrayList<TaskMarkerModel> markers) {
        ArrayList<ClusterItemModel> list = new ArrayList<>();
        int size = markers.size();
        for (int i = 0; i < size; i++)
            list.add(new ClusterItemModel(context.getApplicationContext(), markers.get(i)));
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
//        target.uuId =updater.id;
//        target.setGroups(updater.groupID);
//    }


//    public static void updateModel( NetworkUserModel  from, UserModel to){
//
//    }

    public static CourseDBModel getCoursesDbModelFrom(CourseModel model) {
        CourseDBModel m = new CourseDBModel();
        m.setDesc(model.desc);
        m.setName(model.getName());
        m.setUuid(model.id);
        m.setEpisodesArray(model.episodeIDs);
        return m;
    }

    public static List<CourseDBModel> getCoursesDbModelFrom(List<CourseModel> models) {
        List<CourseDBModel> list = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            list.add(ModelBridge.getCoursesDbModelFrom(models.get(i)));
        }
        return list;
    }


    public static ArrayList<CourseModel> getSemestersFrom(List<CourseDBModel> models) {
        ArrayList<CourseModel> list = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            list.add(ModelBridge.getCourseModelFrom(models.get(i)));
        }
        return list;
    }


    public static CourseModel getCourseModelFrom(CourseDBModel model) {
        CourseModel m = new CourseModel();
        m.desc = model.getDesc();
        m.setName(model.getName());
        m.id = model.getUuid();
        m.episodeIDs = model.getEpisodesArray();
        return m;
    }

    public static TaskMarkerModel getMarkerFrom(TaskModel task) {
        TaskMarkerModel marker = new TaskMarkerModel();
        Random m= new Random();
        marker.latlng = FakeData.generateRandomLatLng(50, 50, 50000); //task.location.latLng;
        marker.snippet = "";
        marker.iconUrl = "";
        marker.idIcon = R.mipmap.ic_pin;
        marker.taskId = task.id;
        marker.title = task.getName();
        return marker;
    }

    public static ArrayList<TaskMarkerModel> getMarkersFrom(List<TaskModel> tasks) {
        if (ListsUtils.isEmpty(tasks)) return null;
        ArrayList<TaskMarkerModel> markers = new ArrayList<>();
        for (TaskModel t : tasks) {
            if(t.location!=null)
            markers.add(getMarkerFrom(t));
        }
        return markers;
    }

    public static ArrayList<TaskMarkerModel> getMarkersFrom(TaskModel[] tasks) {
        if (ListsUtils.isEmpty(tasks)) return null;
        return getMarkersFrom(Arrays.asList(tasks));
    }

    public static TaskModel getTaskFrom(TaskDbModel db) {
        if (db == null) return null;
        TaskModel t = new TaskModel();
        t.id = db.taskId;
        t.sceneId = db.sceneId;
        t.name = db.name;
        Gson g = new Gson();
        t.character = g.fromJson(db.characterObjJson, CharacterModel.class);
        t.location = g.fromJson(db.locationObjJson, LocationModel.class);
        t.setChat(g.fromJson(db.chatObjJson, ChatModel.class));
        t.imageUrl = db.imageURL;
        t.desc = db.desc;
        t.rating = db.rating;
        t.goal = db.goal;
        t.dueDate = db.dueDate;
        t.status = db.status;
        t.timesCompleted = db.timesCompleted;
        t.timesStarted = db.timesStarted;
        return t;
    }

    public static TaskDbModel getTaskDbFrom(TaskModel task) {
        if (task == null) return null;
        TaskDbModel db = new TaskDbModel();
        db.taskId = task.id;
        db.sceneId = task.sceneId;
        db.name = task.name;
        Gson g = new Gson();
        db.characterObjJson = g.toJson(task.character);
        db.locationObjJson = g.toJson(task.location);
        db.chatObjJson = g.toJson(task.getChat());
        db.imageURL = task.imageUrl;
        db.desc = task.desc;
        db.rating = task.rating;
        db.goal = task.goal;
        db.dueDate = task.dueDate;
        db.status = task.status;
        db.timesCompleted = task.timesCompleted;
        db.timesStarted = task.timesStarted;
        return db;
    }

    public static ArrayList<TaskDbModel> getTasksDbFrom(List<TaskModel> tasks) {
        if (ListsUtils.isEmpty(tasks)) return null;
        ArrayList<TaskDbModel> list = new ArrayList<>();
        for (TaskModel t : tasks)
            list.add(getTaskDbFrom(t));
        return list;
    }

    public static ArrayList<TaskModel> getTasksFrom(List<TaskDbModel> tasks) {
        if (ListsUtils.isEmpty(tasks)) return null;
        ArrayList<TaskModel> list = new ArrayList<>();
        for (TaskDbModel db : tasks)
            list.add(getTaskFrom(db));
        return list;
    }

    public static EpisodeModel getEpisodeFrom(EpisodeDBModel db) {
        EpisodeModel m = new EpisodeModel();
        m.name = db.name;
        m.episodeID = db.id;
        m.desc = db.desc;
        m.courseID = db.courseID;
        m.scenes = new Gson().fromJson(db.scenesJsonArr, SceneModel[].class);
        return m;
    }

    public static EpisodeDBModel getEpisodeDBFrom(EpisodeModel m) {
        EpisodeDBModel db = new EpisodeDBModel();
        db.desc = m.desc;
        db.name = m.name;
        db.id = m.episodeID;
        db.courseID = m.courseID;
        db.scenesJsonArr = new Gson().toJson(m.scenes);
        return db;
    }

    public static ArrayList<EpisodeModel> getEpisodesFrom(List<EpisodeDBModel> list) {
        if (ListsUtils.isEmpty(list)) return null;
        ArrayList<EpisodeModel> lst = new ArrayList<>();
        for (EpisodeDBModel db : list)
            lst.add(getEpisodeFrom(db));
        return lst;
    }

    public static ArrayList<EpisodeDBModel> getEpisodesDBFrom(List<EpisodeModel> list) {
        if (ListsUtils.isEmpty(list)) return null;
        ArrayList<EpisodeDBModel> lst = new ArrayList<>();
        for (EpisodeModel m : list)
            lst.add(getEpisodeDBFrom(m));
        return lst;
    }

    public static LocationModel getLocationFrom(NetLocationModel net) {
        if (net == null) return null;
        LocationModel lm = new LocationModel();
        lm.imgUrl = net.getImageUrl();
        lm.latLng = new LatLng(net.lat, net.lon);
        return lm;
    }

    public static CharacterModel getCharacterFrom(NetCharacterModel net) {
        if (net == null) return null;
        CharacterModel ch = new CharacterModel();
        ch.latLng = new LatLng(net.lat, net.lon);
        ch.imgUrl = net.getImageUrl();
        ch.name = net.name;
        ch.desc = net.desc;
        return ch;
    }

    public static QuestionModel getQuestionFrom(NetQuestionModel net) {
        if (net == null) return null;
        QuestionModel q = new QuestionModel();
        q.urlAudioFromTeacher = net.getUrlAudioFromTeacher();
        q.text = net.getText();
        q.id = net.id;
        q.variants = net.mVariants;
        q.audioId = net.audioResponseId;
        try {
            q.type = QuestionType.valueOf(net.type);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "getQuestionfrom_ error = " + e.toString());
            q.type = QuestionType.TEXT;
        }
        return q;
    }

    public static QuestionModel[] getItemQuestions(NetQuestionModel[] net) {
        if (net == null) return null;
        QuestionModel[] items = new QuestionModel[net.length];
        for (int i = 0; i < net.length; i++)
            items[i] = getQuestionFrom(net[i]);
        return items;
    }

    public static ChatModel getChatFrom(NetChatModel net) {
        if (net == null) return null;
        ChatModel chat = new ChatModel();
        chat.questions = getItemQuestions(net.questions);
        return chat;
    }

    public static SceneModel getSceneFrom(NetSceneModel net) {
        if (net == null) return null;
        SceneModel sc = new SceneModel();
        sc.sceneID = net.sceneID;
        sc.name = net.name;
        sc.desc = net.desc;
        sc.imageUrl = net.getImage();
        sc.taskIDs = net.taskIDs;
        return sc;
    }

    public static SceneModel[] getScenesFrom(NetSceneModel[] net) {
        if (net == null) return null;
        SceneModel[] sc = new SceneModel[net.length];
        for (int i = 0; i < net.length; i++)
            sc[i] = getSceneFrom(net[i]);
        return sc;
    }


    /**
     * NOT SAVE COURSE ID;
     * @param net
     * @return
     */
    public static EpisodeModel getEpisodeFrom(NetEpisodeModel net) {
        if (net == null) return null;
        EpisodeModel ep = new EpisodeModel();
        ep.episodeID = net.episodeID;
        ep.scenes = getScenesFrom(net.scenes);
        ep.desc = net.desc;
        ep.name = net.name;
        ep.imageUrl = net.getImageUrl();
        return ep;
    }

    public static CourseModel getCourseFrom(NetCourseModel net) {
        if (net == null) return null;
        CourseModel cm = new CourseModel();
        cm.setName(net.getName());
        cm.id = net.id;
        cm.desc = net.desc;
        cm.episodeIDs = net.episodeIDs;
        return cm;
    }


    public static CourseModel [] getCoursesFrom(NetCourseModel [] net) {
        if (net == null) return null;
        CourseModel [] cm = new CourseModel[net.length];
        for (int i = 0; i < cm.length; i++) {
            cm[i] = getCourseFrom(net[i]);
        }
        return cm;
    }

    public static ClassModel getClassFrom(NetClassModel net) {
        if (net == null) return null;
        ClassModel cl = new ClassModel();
        cl.code = net.code;
        cl.currentCourse = net.currentCourse;
        cl.coursesIDs = net.coursesIDs;
        cl.desc = net.desc;
        cl.id = net.id;
        cl.name = net.name;
        return cl;
    }

    /**
     *
     *
     * @param net
     * @return last, first name, classes, id, role, token, email
     */
    public static UserModel getUserFrom(NetUserModel net) {
        if (net == null) return null;
        UserModel u = new UserModel();
        u.lastName = net.lastName;
        u.firstName = net.firstName;
        u.setClasses(net.getClasses());
        u.email = net.email;
        u.id = net.id;
        u.role = net.role;
        u.token = net.token;
        u.imageUrl = net.getImageUrl();
        u.code = net.code;
        return u;
    }

    public static TaskModel getTaskFrom(NetTaskModel net) {
        if (net == null) return null;
        Log.e(TAG, "NET TASK = " + net.toString());
        TaskModel tm = new TaskModel();
        tm.setChat(getChatFrom(net.getChat()));
        tm.imageUrl = net.getImageUrl();
        tm.id = net.id;
        tm.desc = net.desc;
        tm.timesCompleted = net.timesCompleted;
        tm.bestThreeFriendsScores = net.bestThreeFriendsScores;
        tm.location = getLocationFrom(net.location);
        tm.character = getCharacterFrom(net.character);
        tm.dueDate = net.dueDate;
        tm.excercisesIds = net.excercisesIds;
        tm.goal = net.goal;
        tm.name = net.name;
        tm.rating = net.rating;
        tm.status = net.status;
        tm.timesStarted = net.timesStarted;
        //Log.e(TAG, "REAL TASK = " + tm.toString());
        Log.e(TAG, "REAL TASK CHAT = " + tm.getChat().toString());
        return tm;
    }

    public static TaskModel[] getTasksFrom(NetTaskModel[] net) {
        if (net == null) return null;
        TaskModel tasks[] = new TaskModel[net.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = getTaskFrom(net[i]);
        }
        return tasks;
    }

    public static NetUserModel getNetUserFrom(UserModel user) {
        NetUserModel net = new NetUserModel();
        net.firstName = user.firstName;
        net.lastName = user.lastName;
        net.email = user.email;
        net.setPassword(user.getPassword());
        net.code = user.code;
        return net;
    }

    public static GroupModel getGroupFrom(NetGroupModel net) {
        if (net == null) return null;
        GroupModel m = new GroupModel();
        m.coursesIDs = net.coursesIDs;
        m.currentCourse = net.currentCourse;
        m.desc = net.desc;
        m.id = net.id;
        m.name = net.name;
        return m;
    }

    public static GroupModel[] getGroupsFrom(NetGroupModel[] net) {
        if (net == null) return null;
        GroupModel[] m = new GroupModel[net.length];
        for (int i = 0; i < m.length; i++)
            m[i] = getGroupFrom(net[i]);
        return m;
    }

    public static ExecutedChatModel getExecutedChatFrom(TaskModel task){
        if (task == null) return null;
        ExecutedChatModel e= new ExecutedChatModel();
        e.taskId=task.id;
        e.imageUrl=task.getImageUrl();
        e.type=task.chatType;
        return e;
    }

    public static NetExecutedChatModel getNetExecutedChatFrom(ExecutedChatModel m){
        if(m==null) return null;
        NetExecutedChatModel net= new NetExecutedChatModel();
        net.completedMarkPercent=m.getCompletedMarkPercent();
        net.endTime=m.endTime;
        net.episodeSceneName=m.episodeSceneName;
        net.taskId=m.taskId;
        net.type=m.type;
        net.itemResults=getNetExcecutedAnswersFrom(m.itemResults);
        return net;
    }

    public static NetExecutedAnswerModel getNetExecutedAnswerFrom(ExecutedAnswerModel ans){
        if(ans==null) return null;
        NetExecutedAnswerModel net= new NetExecutedAnswerModel();
        net.markInPrecent=ans.getMarkInPercent();
        net.selectedAnswer=ans.selectedAnswer;
        net.selectedQuestion=ans.selectedQuestion;
        net.triesCount=ans.triesCount;
        return net;
    }

    public static NetExecutedAnswerModel [] getNetExcecutedAnswersFrom(List<ExecutedAnswerModel> anss){
        if(ListsUtils.isEmpty(anss)) return null;
        int size=anss.size();
        NetExecutedAnswerModel [] nets= new NetExecutedAnswerModel[size];
        for(int i=0; i<size; i++)
            nets[i]=getNetExecutedAnswerFrom(anss.get(i));
        return nets;
    }

    public static  AudioFileIdModel getAudioFileIdFrom(NetAudioFileIdModel net){
        if(net==null) return null;
        AudioFileIdModel a= new AudioFileIdModel();
        a.taskId=net.taskId;
        a.audioIds=net.audioIds;
        return a;
    }

}
