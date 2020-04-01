package com.indiehood.app.ui.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.indiehood.app.R;

public class FavoritesAdapter extends FirestoreRecyclerAdapter<Artist, FavoritesAdapter.FavoritesHolder> {
    private OnFavoriteClickListener listener;

    class FavoritesHolder extends RecyclerView.ViewHolder {
        public TextView artistName;
        public TextView artistBio;
        public ImageView artistIcon;
        public CheckBox favorite;

        public FavoritesHolder(View itemView) {
            super(itemView);
            artistIcon = itemView.findViewById(R.id.band_venue_icon);
            artistName = itemView.findViewById(R.id.band_venue_name);
            artistBio = itemView.findViewById(R.id.band_venue_description);
            favorite = itemView.findViewById(R.id.favorite_button);

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onFavoriteClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull FavoritesHolder viewHolder, int position, @NonNull Artist currArtist) {

        if (currArtist.getArtistName() != null) {
            viewHolder.artistName.setText(currArtist.getArtistName());
        }

        if (currArtist.getBio() != null) {
            viewHolder.artistBio.setText(currArtist.getBio());
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

    public interface OnFavoriteClickListener {
        void onFavoriteClick(DocumentSnapshot snapshot, int position);
    }

    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.listener = listener;
    }

    public FavoritesAdapter(@NonNull FirestoreRecyclerOptions<Artist> options) {
        super(options);
    }

    @Override
    public void onDataChanged() { // TODO implement empty data view
        // Called each time there is a new query snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
    }
}
