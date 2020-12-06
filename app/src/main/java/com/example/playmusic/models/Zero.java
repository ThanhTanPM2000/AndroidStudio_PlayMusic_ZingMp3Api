package com.example.playmusic.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Zero {

    @SerializedName("song")
    private List<ITEM> ITEMS;

    public List<ITEM> getSongs() {
        return ITEMS;
    }
}

