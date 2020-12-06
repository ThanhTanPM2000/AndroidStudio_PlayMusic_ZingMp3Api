package com.example.playmusic.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.playmusic.network.ApiClient;
import com.example.playmusic.network.ApiService;
import com.example.playmusic.responses.songsRecommendedResponses;
import com.example.playmusic.responses.songsSearchResponses;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchSongRepository {

    private ApiService apiService;

    public SearchSongRepository(){
        apiService = ApiClient.getRetrofit_Search().create(ApiService.class);
    }

    public LiveData<songsSearchResponses> SearchSong(String type, int num, String name){
        MutableLiveData<songsSearchResponses> dt = new MutableLiveData<>();
        apiService.getItems(type, num, name).enqueue(new Callback<songsSearchResponses>() {
            @Override
            public void onResponse(@NonNull Call<songsSearchResponses> call,@NonNull Response<songsSearchResponses> response) {
                dt.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<songsSearchResponses> call, @NonNull Throwable t) {
                dt.setValue(null);
            }
        });
        return dt;
    }
}
