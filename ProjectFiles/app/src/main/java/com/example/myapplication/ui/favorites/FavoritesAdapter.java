package com.example.myapplication.ui.favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView artistName;
        public ImageView artistIcon;
        public ImageButton favorite;

        public ViewHolder(View itemView) {
            super(itemView);
            artistIcon = itemView.findViewById(R.id.band_venue_icon);
            artistName = itemView.findViewById(R.id.band_venue_name);
            favorite = itemView.findViewById(R.id.favorite_button);
        }
    }

    private List<Artist> myArtists;
    public FavoritesAdapter(List<Artist> favorites) {
        myArtists = favorites;
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View favoriteView = inflater.inflate(R.layout.favorited_band_row, parent, false);
        return new ViewHolder(favoriteView);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder viewHolder, int position) {
        Artist artist = myArtists.get(position);

        TextView name = viewHolder.artistName;
        ImageView icon = viewHolder.artistIcon;
        ImageButton fav = viewHolder.favorite;

        if (artist.getName() != null) {
            name.setText(artist.getName());
        }

        // icon.setImageIcon(); TODO how to implement? Right now they are hardcoded
        fav.setEnabled(artist.getFavorited());
    }

    @Override
    public int getItemCount() {
        return myArtists.size();
    }
}
