package com.zl.android.repository.features.catalog.model;

import com.google.gson.annotations.SerializedName;

public class WebEpisode {

    @SerializedName("id")
    public String episodeId;

    @SerializedName("name")
    public String name;

    @SerializedName("desc")
    public String desc;

    @SerializedName("scenes")
    public WebScene[] scenes;

}
