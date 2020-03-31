package com.indiehood.app.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.indiehood.app.R;


public class FavoritesFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ArtistCollection = db.collection("ArtistCollection");
    private FavoritesAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        super.onCreate(savedInstanceState);
        setUpRecyclerView(root);

        return root;
    }

    private void setUpRecyclerView(View r) {
        Query query = ArtistCollection.whereEqualTo("favorited", true);

        FirestoreRecyclerOptions<Artist> options = new FirestoreRecyclerOptions.Builder<Artist>()
                .setQuery(query, Artist.class)
                .build();

        adapter = new FavoritesAdapter(options);

        RecyclerView favorites_rv = r.findViewById(R.id.favorites_rv);
        favorites_rv.setHasFixedSize(true);
        favorites_rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        favorites_rv.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}