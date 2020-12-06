package com.example.playmusic.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.playmusic.R;
import com.example.playmusic.adapters.SongsAdapter;
import com.example.playmusic.databinding.ActivitySearchBinding;
import com.example.playmusic.listener.SongsListener;
import com.example.playmusic.models.Data;
import com.example.playmusic.models.ITEM;
import com.example.playmusic.models.Zero;
import com.example.playmusic.viewmodels.SearchViewModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements SongsListener {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel viewModel;
    private List<ITEM> songs = new ArrayList<>();
    private SongsAdapter songsAdapter;
    private int currentPage = 1;
    private int totalAvaiablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        doInitialization();
    }

    private void doInitialization() {
        activitySearchBinding.imageBack.setOnClickListener(v -> onBackPressed());
        activitySearchBinding.songsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        songsAdapter = new SongsAdapter(songs, this);
        activitySearchBinding.songsRecyclerView.setAdapter(songsAdapter);
        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void run() {
                                    currentPage = 1;
                                    totalAvaiablePages = 1;
                                    searchSongs(s.toString());
                                }
                            });
                        }
                    }, 800);
                } else {
                    songs.clear();
                    songsAdapter.notifyDataSetChanged();
                }
            }
        });

        activitySearchBinding.imageSearch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String s = activitySearchBinding.inputSearch.getText().toString();
                if (!s.trim().isEmpty()) {
                    currentPage = 1;
                    totalAvaiablePages = 1;
                    searchSongs(s);
                } else {
                    songs.clear();
                    songsAdapter.notifyDataSetChanged();
                }
            }
        });

        activitySearchBinding.songsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchBinding.songsRecyclerView.canScrollVertically(1)) {
                    if (!activitySearchBinding.inputSearch.getText().toString().isEmpty()) {
                        if (currentPage < totalAvaiablePages) {
                            currentPage += 1;
                            searchSongs(activitySearchBinding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void searchSongs(String query) {
        toggleLoading();
        viewModel.searchSongs("artist,song", 100, query).observe(this, test -> {
                    toggleLoading();
                    if (test.getDatas() != null) {
                        totalAvaiablePages = test.getDatas().size();
                        int oldCount = songs.size();
                        List<Data> test1 = test.getDatas();
                        Data test2 = test.getDatas().get(0);
                        List<ITEM> bb = test.getDatas().get(0).getITEMS();
                        if (test.getDatas().get(0).getITEMS() != null) {
                            songs.addAll(test.getDatas().get(0).getITEMS());

                            songsAdapter.notifyItemRangeInserted(oldCount, songs.size());
                            //songsAdapter.notifyDataSetChanged();
                        }
                    }

                }
        );
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activitySearchBinding.getIsLoading() != null && activitySearchBinding.getIsLoading()) {
                activitySearchBinding.setIsLoading(false);
            } else {
                activitySearchBinding.setIsLoading(true);
            }
        } else {
            if (activitySearchBinding.getIsLoadingMore() != null && activitySearchBinding.getIsLoadingMore()) {
                activitySearchBinding.setIsLoadingMore(false);
            } else {
                activitySearchBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onSongClicked(ITEM song) {
        Intent intent = new Intent(getApplicationContext(), DetailSong.class);
        intent.putExtra("idSong", song.getId().toString());
        startActivity(intent);
    }
}