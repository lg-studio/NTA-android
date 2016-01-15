package com.usinformatics.nytrip.network;

/**
 * Created by D1m11n on 19.06.2015.
 */
public interface Api {

    public String API_VERSION = "/v1";

    public String BASE_URL = "http://ny-api.herokuapp.com" + API_VERSION;
//
//    public String BASE_URL ="http://192.168.6.50:3000" + API_VERSION;

    public String URI_REGISTER = "/register";

    public String URI_LOGIN = "/login";

    public String URI_PUSH_NOTIFICATIONS = "/push";

    public String URI_CLASS = "/classes";

    public String URI_COURSES = URI_CLASS + "/{classid}/courses";

    public String URI_SCENES = "/episodes/{episodeid}/scenes";

    public String URI_SCENE = "/scene/{sceneid}";

    public String URI_TASKS = "/scenes/{sceneid}/tasksfordevice";

    public String URI_EPISODES_ALL = "/courses/{courseid}/episodesAll";

    public String URI_IMAGE = BASE_URL + "/image/%s";

    public String URI_AUDIO = "/audio/{audioId}";

    public String URI_TASK_IMAGE = BASE_URL + "/image/task/%s";

    public String URI_SCENE_IMAGE = BASE_URL + "/image/scene/%s";

    public String URI_LOCATION_IMAGE="/image/location/%s";

    public String URI_USER_IMAGE="/image/user/%s";

    public String URI_EPISODE_IMAGE = BASE_URL + "/image/episode/%s";

    public String URI_TASK=BASE_URL +"/scenes/{sceneid}/tasks/{taskid}/my";

    public String URI_TASK_AUDIO=BASE_URL +"/audio/%s";

    public String URI_SEND_CHAT_RESULT=BASE_URL  + "/tasklog";

    public String URI_EPISODE ="/episode/{episodeid}";

    public String FEEDBACK = "/feedback";
}
