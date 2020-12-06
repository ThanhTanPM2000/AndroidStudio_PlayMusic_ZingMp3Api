package com.example.playmusic.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.playmusic.repositories.SearchSongRepository;
import com.example.playmusic.responses.songsSearchResponses;

public class SearchViewModel extends ViewModel {

    private SearchSongRepository searchSongRepository;

    public SearchViewModel(){
        searchSongRepository = new SearchSongRepository();
    }

    public LiveData<songsSearchResponses> searchSongs(String type, int num, String name){
        return searchSongRepository.SearchSong(type, num, name);
    }
}
