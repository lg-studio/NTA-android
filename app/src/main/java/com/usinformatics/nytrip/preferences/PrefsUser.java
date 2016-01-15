package com.usinformatics.nytrip.preferences;

import android.content.Context;
import android.text.TextUtils;

import com.securepreferences.SecurePreferences;
import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.models.UserModel;
import com.usinformatics.nytrip.storages.UserDataStorage;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 11.06.2015.
 */
public class PrefsUser implements UserDataStorage {


    private Context context;
    private static PrefsUser mInstance;
    private SecurePreferences mPrefs;
    private  SecurePreferences.Editor mEditor;

    private void initPrefsIfNeeded(Context context){
        if (mPrefs!=null&&mEditor!=null)
            return;
        mPrefs = new SecurePreferences(context);
        mEditor = mPrefs.edit();
    }

    private PrefsUser(Context context){
        this.context=context.getApplicationContext();
        initPrefsIfNeeded(this.context);

    }

    public static PrefsUser getInstance(Context context){
        if (mInstance == null){
            synchronized (PrefsUser.class){
                if (mInstance==null)
                    mInstance= new PrefsUser(context);
            }
        }
        return mInstance;
    }

    @Override
    public void saveUser(UserModel user) {
        mEditor.putString(Key.FIRST_NAME, user.firstName).
                putString(Key.TOKEN, user.token).
                putString(Key.GROUP, generateFromArray(user.getClasses())). //TODO SOMETIMES NULL
                putString(Key.LAST_NAME, user.lastName).
                putString(Key.TEACHER_CODE, user.teacherCode).
                putString(Key.EMAIL, user.email).
                putString(Key.USER_ID, user.id).
                putString(Key.PASSWORD, user.getPassword()).
                commit();
    }

    @Override
    public UserModel getUser() {
        UserModel user= new UserModel();
        user.firstName=mPrefs.getString(Key.FIRST_NAME,"");
        user.token=mPrefs.getString(Key.TOKEN,"");
        user.setClasses(generateFromString(mPrefs.getString(Key.GROUP, ""))); //TODO SOMETIMES NULL
        user.teacherCode=mPrefs.getString(Key.TEACHER_CODE,"");
        user.email=mPrefs.getString(Key.EMAIL,"");
        user.lastName=mPrefs.getString(Key.LAST_NAME,"");
        user.id =mPrefs.getString(Key.USER_ID,"");
        user.setPassword(mPrefs.getString(Key.PASSWORD,""));
        return user;
    }

    @Override
    public void clearUserData() {
        mEditor.remove(Key.USER_ID)
                .remove(Key.FIRST_NAME)
                .remove(Key.LAST_NAME)
                .remove(Key.TEACHER_CODE)
                .remove(Key.TOKEN)
                .remove(Key.GROUP)
                .remove(AppConsts.PROJECT_ID)
                .remove(AppConsts.GCM_INSTANCE_ID)
                .commit();
    }

    @Override
    public void setCurrentCourseId(String currentSemesterId) {
        mEditor.putString(Key.COURSE_ID, currentSemesterId).commit();
    }

    @Override
    public String getCurrentCourseId() {
        return mPrefs.getString(Key.COURSE_ID,"");
    }

    @Override
    public void setCurrentScene(SceneModel model) {
        if(model==null){
            mEditor.putString(Key.SCENE_ID, null)
                    .putString(Key.SCENE_NAME, null).commit();
            return;
        }
        mEditor.putString(Key.SCENE_ID, model.sceneID).putString(Key.SCENE_NAME, model.name).commit();
    }

    @Override
    public SceneModel getCurrentScene() {
        SceneModel sc= new SceneModel();
        sc.sceneID = mPrefs.getString(Key.SCENE_ID,"");
        sc.name = mPrefs.getString(Key.SCENE_NAME, "");
        return sc;
    }

    @Override
    public void setCurrentEpisode(EpisodeModel model) {
        if(model==null){
            mEditor.putString(Key.EPISODE_ID, null)
                    .putString(Key.EPISODE_NAME, null).commit();
            return;
        }
        mEditor.putString(Key.EPISODE_ID, model.episodeID)
                .putString(Key.EPISODE_NAME, model.name).commit();
    }

    @Override
    public EpisodeModel getCurrentEpisode() {
        EpisodeModel ep= new EpisodeModel();
        ep.episodeID = mPrefs.getString(Key.EPISODE_ID,"");
        ep.name = mPrefs.getString(Key.EPISODE_NAME, "");
        return ep;
    }

    @Override
    public boolean needUpdateCurrentCourse() {
        return false;
    }

    @Override
    public String getLastUsedEmail() {
        return mPrefs.getString(Key.EMAIL,"");
    }

    @Override
    public void setCurrentTaskPath(String taskPath) {

        taskPath = taskPath.replaceAll("[-+.^:, ]","_");
        mEditor.putString(Key.CURRENT_TUSK, taskPath).commit();
    }

    @Override
    public String getCurrentTaskPath() {
        return mPrefs.getString(Key.CURRENT_TUSK, "");
    }

    private interface Key{
        String FIRST_NAME="first";

        String LAST_NAME="last";

        String TOKEN="token";

        String PASSWORD="ps";

        String USER_ID="user_id";

        String TEACHER_CODE="code";

        String GROUP="group";

        String EMAIL="mail";

        String EPISODE_ID="ep_id";

        String EPISODE_NAME="ep_name";

        String SCENE_ID="scene_id";

        String COURSE_ID ="course_id";

        String SCENE_NAME="scene_name";

        String SEMESTER_UPDATE ="semester_update";

        String CURRENT_TUSK = "current_task";

    }

    private static final String SEPARATOR=",";

    private static String generateFromArray(String [] array){
        if(ListsUtils.isEmpty(array)) return "";
        StringBuilder builder = new StringBuilder("");
        for (String string : array) {
            if (builder.length() > 0) {
                builder.append(SEPARATOR);
            }
            builder.append(string);
        }
        return builder.toString();
    }

    private static String [] generateFromString(String arrayString){
        if(TextUtils.isEmpty(arrayString)) return new String[]{""};
        return arrayString.split(SEPARATOR);
    }

}
