package com.indiehood.app.ui.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.indiehood.app.R;

// implements a recycler view using data pulled directly from firestore
public class FavoritesAdapter extends FirestoreRecyclerAdapter<Artist, FavoritesAdapter.FavoritesHolder> {
    private OnFavoriteClickListener listener;
    private TextView emptyList;

    public FavoritesAdapter(@NonNull FirestoreRecyclerOptions<Artist> options,
                            TextView emptyList) {
        super(options);
        this.emptyList = emptyList;
    }

    class FavoritesHolder extends RecyclerView.ViewHolder {
        TextView artistName;
        TextView artistBio;
        ImageView artistIcon;
        CheckBox favorite;

        FavoritesHolder(View itemView) {
            super(itemView);
            // initialize the items to appear in each card in recycler view
            artistIcon = itemView.findViewById(R.id.band_venue_icon);
            artistName = itemView.findViewById(R.id.band_venue_name);
            artistBio = itemView.findViewById(R.id.band_venue_description);
            favorite = itemView.findViewById(R.id.favorite_button);
            // sets on click listener for the favorite button
            // if clicked, pass document snapshot & its position to function in FavoritesFragment
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
    protected void onBindViewHolder(@NonNull FavoritesHolder viewHolder, int position,
                                    @NonNull Artist currArtist) {
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

    @Override
    public void onDataChanged() {
        emptyList.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @NonNull
    @Override
    public FavoritesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View favoriteView = inflater.inflate(R.layout.favorited_band_row, parent, false);

        return new FavoritesHolder(favoriteView);
    }
    /*
    this interface and its public function are callbacks for the on click listener
     */
    public interface OnFavoriteClickListener {
        void onFavoriteClick(DocumentSnapshot snapshot, int position);
    }

    void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.listener = listener;
    }
}
