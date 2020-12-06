package com.example.playmusic.network;

import com.example.playmusic.responses.songsRecommendedResponses;
import com.example.playmusic.responses.songsSearchResponses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("chart-realtime")
    Call<songsRecommendedResponses> getItems(@Query("songId") int songId, @Query("videoId") int videoId, @Query("albumId") int albumId, @Query("chart") String chart, @Query("time") int time);

    @GET("complete")
    Call<songsSearchResponses> getItems(@Query("type") String type , @Query("num") int num, @Query("query") String nameSong);
}
