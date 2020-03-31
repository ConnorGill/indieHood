package com.indiehood.app.ui.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.indiehood.app.R;

public class FavoritesAdapter extends FirestoreRecyclerAdapter<Artist, FavoritesAdapter.FavoritesHolder> {

    public class FavoritesHolder extends RecyclerView.ViewHolder {
        public TextView artistName;
        public ImageView artistIcon;
        public ImageButton favorite;

        public FavoritesHolder(View itemView) {
            super(itemView);
            artistIcon = itemView.findViewById(R.id.band_venue_icon);
            artistName = itemView.findViewById(R.id.band_venue_name);
            favorite = itemView.findViewById(R.id.favorite_button);
        }

    }

    public FavoritesAdapter(@NonNull FirestoreRecyclerOptions<Artist> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FavoritesHolder viewHolder, int position, @NonNull Artist currArtist) {

        if (currArtist.getArtistName() != null) {
            viewHolder.artistName.setText(currArtist.getArtistName());
        }

        // icon.setImageIcon(); TODO how to implement? Right now they are hardcoded

        if (currArtist.getFavorited()) {
            viewHolder.favorite.setEnabled(currArtist.getFavorited());
        }
    }

    @NonNull
    @Override
    public FavoritesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View favoriteView = inflater.inflate(R.layout.favorited_band_row, parent, false);

        return new FavoritesHolder(favoriteView);
    }

}
