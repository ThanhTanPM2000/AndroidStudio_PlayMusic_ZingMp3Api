package com.example.playmusic.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("song")
    private List<ITEM> ITEMS;

    public List<ITEM> getITEMS() {
        return ITEMS;
    }

}

