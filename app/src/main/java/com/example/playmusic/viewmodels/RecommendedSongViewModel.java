package com.example.playmusic.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.playmusic.repositories.RecommendSongsRepository;
import com.example.playmusic.responses.songsRecommendedResponses;

public class RecommendedSongViewModel extends ViewModel {

    private RecommendSongsRepository recommendSongsRepository;

    public RecommendedSongViewModel(){
        recommendSongsRepository = new RecommendSongsRepository();
    }

    public LiveData<songsRecommendedResponses> getRecommendedSongs(int songId, int videoId, int albumId, String chart, int time){
        return recommendSongsRepository.getSongRecommended(songId, videoId, albumId, chart, time);
    }


}
