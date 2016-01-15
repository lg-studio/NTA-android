package com.usinformatics.nytrip.storages;

/**
 * Created by D1m11n on 11.06.2015.
 */
public interface AppDataStorage {

    public boolean isFirstRun();

    public void setWasFirstRun();

    public void setDisplayIntro(boolean value);

    public boolean canDisplayIntro();

    public void saveProjectId(String gcmID);

    public void saveInstanceId(String instanceId);

}
