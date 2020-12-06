package com.example.playmusic.models;

import com.google.gson.annotations.SerializedName;

public class ITEM {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    //@SerializedName("artists_names")
    @SerializedName("artists_names")
    private String nghesi;

    @SerializedName("performer")
    private String performer;

    @SerializedName("type")
    private String type;

    @SerializedName(value = "thumbnail", alternate = "thumb")
    private String thumbnail;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNghesi() {
        return nghesi;
    }

    public String getPerformer() {
        return performer;
    }

    public String getType() {
        return type;
    }

    public String getThumbnail() {
        if(thumbnail.contains("https")){
            return thumbnail = this.thumbnail.substring(this.thumbnail.indexOf("cover"));
        }
        else {
            return  thumbnail;
        }
    }
}
