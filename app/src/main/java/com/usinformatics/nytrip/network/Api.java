package com.usinformatics.nytrip.network;

/**
 * Created by D1m11n on 19.06.2015.
 */
public interface Api {


    public String API_VERSION = "/v1";

    public String BASE_URL = "http://ny-api.herokuapp.com" + API_VERSION;

    //public String BASE_URL ="http://192.168.6.58:3000" + API_VERSION;

    public String URI_REGISTER = "/register";

    public String URI_LOGIN = "/login";

    public String URI_PUSH_NOTIFICATIONS = "/push";

    public String URI_CLASS = "/classes";

    public String URI_COURSES = URI_CLASS + "/{classid}/courses";


    public String URI_SCENES = "/episodes/{episodeid}/scenes";

    public String URI_TASKS = "/scenes/{sceneid}/tasks";

    public String URI_EPISODES_ALL = "/courses/{courseid}/episodes/all";

    public String URI_IMAGE = BASE_URL + "/image/%s";

    public String URI_AUDIO = "/audio/{audioId}";
}
