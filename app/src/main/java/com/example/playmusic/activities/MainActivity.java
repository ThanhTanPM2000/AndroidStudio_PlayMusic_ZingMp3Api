package com.example.playmusic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.playmusic.R;
import com.example.playmusic.adapters.SongsAdapter;
import com.example.playmusic.databinding.ActivityMainBinding;
import com.example.playmusic.listener.SongsListener;
import com.example.playmusic.models.Data;
import com.example.playmusic.models.ITEM;
import com.example.playmusic.viewmodels.RecommendedSongViewModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class MainActivity extends AppCompatActivity implements SongsListener {

    private ActivityMainBinding activityMainBinding;
    private RecommendedSongViewModel viewModel;
    private List<ITEM> items = new ArrayList<>();
    private SongsAdapter songsAdapter;
    private int currentPage =1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(RecommendedSongViewModel.class);
        //test();
        doInitialization();
    }

    private void doInitialization(){
        activityMainBinding.songsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(RecommendedSongViewModel.class);
        songsAdapter = new SongsAdapter(items, this);
        activityMainBinding.songsRecyclerView.setAdapter(songsAdapter);
        activityMainBinding.imageSearch.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SearchActivity.class)));
        getRecommendedSongs();
    }

    private void getRecommendedSongs(){
        activityMainBinding.setIsLoading(true);
        viewModel.getRecommendedSongs(0, 0, 0, "song", currentPage).observe(this, mostPopularTVShowsResponse -> {
            activityMainBinding.setIsLoading(false);
           /* Songs.addAll(mostPopularTVShowsResponse.getData().getSong());*/
            if(mostPopularTVShowsResponse.getData() != null){
                items.addAll(mostPopularTVShowsResponse.getData().getITEMS());
                songsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void test(){
        viewModel.getRecommendedSongs(0, 0, 0, "song", -1).observe(this, x -> {
            Toast.makeText(getApplicationContext(), "Total: "+ x.getData(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onSongClicked(ITEM song) {
        Intent intent = new Intent(getApplicationContext(), DetailSong.class);
        intent.putExtra("idSong", song.getId().toString());
        startActivity(intent);
    }
}