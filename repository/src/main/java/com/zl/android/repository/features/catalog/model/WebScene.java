package com.zl.android.repository.features.catalog.model;

import com.google.gson.annotations.SerializedName;

public class WebScene {

    @SerializedName("id")
    public String sceneId;

    @SerializedName("name")
    public String name;

    @SerializedName("desc")
    public String desc;

    @SerializedName("tasks")
    public String[] taskIds;

}
