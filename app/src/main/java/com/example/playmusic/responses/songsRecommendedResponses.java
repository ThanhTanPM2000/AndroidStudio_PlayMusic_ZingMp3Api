package com.example.playmusic.responses;

import com.example.playmusic.models.Data;
import com.example.playmusic.models.ITEM;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class songsRecommendedResponses {

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }
}
