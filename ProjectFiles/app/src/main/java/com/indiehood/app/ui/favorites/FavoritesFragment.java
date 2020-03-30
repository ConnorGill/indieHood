package com.indiehood.app.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indiehood.app.R;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        super.onCreate(savedInstanceState);
        RecyclerView favorites_rv = root.findViewById(R.id.favorites_rv);
        ArrayList<Artist> artists = Artist.createArtistList(20);
        ArrayList<Artist> favorites = Artist.createFavoritesList(artists);
        FavoritesAdapter adapter = new FavoritesAdapter(favorites);
        favorites_rv.setAdapter(adapter);
        favorites_rv.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // test code to write to database and create new artist in ArtistCollection
        Artist test = new Artist("Four Tet", "test", 5, "https://twitter.com/FourTet",
                "http://www.fourtet.net", "apple music", "spotify");
        Artist test2 = new Artist();
        test2.writeNewArtist(test);

        return root;
    }
}