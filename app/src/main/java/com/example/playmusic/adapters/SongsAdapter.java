package com.example.playmusic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playmusic.R;
import com.example.playmusic.databinding.ItemContainerSongsBinding;
import com.example.playmusic.listener.SongsListener;
import com.example.playmusic.models.ITEM;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder>{

    private List<ITEM> Items;
    private LayoutInflater layoutInflater;
    private static SongsListener songsListener;

    public SongsAdapter(List<ITEM> Items, SongsListener songsListener) {
        this.Items = Items;
        SongsAdapter.songsListener = songsListener;
    }

    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSongsBinding songsBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_songs, parent, false
        );
        return new SongsViewHolder(songsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder holder, int position) {
        holder.bindSong(Items.get(position));
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    static class SongsViewHolder extends RecyclerView.ViewHolder{

        private ItemContainerSongsBinding itemContainerSongsBinding;

        public SongsViewHolder(ItemContainerSongsBinding itemContainerSongsBinding){
            super(itemContainerSongsBinding.getRoot());
            this.itemContainerSongsBinding = itemContainerSongsBinding;
        }

        public void bindSong(ITEM song){
            itemContainerSongsBinding.setITEM(song);
            itemContainerSongsBinding.executePendingBindings();
            itemContainerSongsBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    songsListener.onSongClicked(song);
                }
            });
        }
    }
}
