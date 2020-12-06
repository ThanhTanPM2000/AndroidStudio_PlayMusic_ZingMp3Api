package com.example.playmusic.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.playmusic.network.ApiClient;
import com.example.playmusic.network.ApiService;
import com.example.playmusic.responses.songsRecommendedResponses;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendSongsRepository {

    private ApiService apiService;

    public RecommendSongsRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<songsRecommendedResponses> getSongRecommended(int songId, int videoId, int albumId, String chart, int time){
        MutableLiveData<songsRecommendedResponses> dt = new MutableLiveData<>();
        apiService.getItems(songId, videoId, albumId, chart, time).enqueue(new Callback<songsRecommendedResponses>() {
            @Override
            public void onResponse(Call<songsRecommendedResponses> call, Response<songsRecommendedResponses> response) {
                dt.setValue(response.body());
            }

            @Override
            public void onFailure(Call<songsRecommendedResponses> call, Throwable t) {
                dt.setValue(null);
            }
        });
        return dt;
    }
}
