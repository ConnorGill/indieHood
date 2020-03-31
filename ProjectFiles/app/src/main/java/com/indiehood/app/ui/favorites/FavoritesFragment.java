package com.indiehood.app.ui.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.indiehood.app.R;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    // TODO implement later? conserves resources is all
    /*private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ArtistCollection = db.collection("ArtistCollection");
    private Artist testArtist;
    private ArrayList<Artist> artists;
    private ArrayList<Artist> favorites;

    @Override
    public void onStart() {
        super.onStart();
        ArtistCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) return;
                testArtist = new Artist();
                artists = testArtist.readArtists(ArtistCollection);
                favorites = testArtist.createFavoritesList(artists);
            }
        });

    } */

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        super.onCreate(savedInstanceState);
        RecyclerView favorites_rv = root.findViewById(R.id.favorites_rv);
        Artist testArtist = new Artist();
        ArrayList<Artist> artists = testArtist.readArtists();

        for (int i = 0; i < artists.size(); i++) {
            System.out.println(artists.get(i).getArtistName());
        }

        ArrayList<Artist> favorites = Artist.createFavoritesList(artists);
        FavoritesAdapter adapter = new FavoritesAdapter(favorites);
        favorites_rv.setAdapter(adapter);
        favorites_rv.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // TODO remove test code, put elsewhere
        /*Artist test = new Artist("Foo Fighters", "Love hurts, ask Courtney, she killed Kurt", false, 2, "nil",
                "nil", "apple music", "spotify");
        testArtist.writeNewArtist(test);*/

        return root;
    }
}