package com.example.playmusic.responses;

import com.example.playmusic.models.Data;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class songsSearchResponses {

    @SerializedName("data")
    private List<Data> Datas;

    public List<Data> getDatas() {
        return this.Datas;
    }
}
