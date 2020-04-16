package com.indiehood.app.ui.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.indiehood.app.MainActivity;
import com.indiehood.app.R;
import com.indiehood.app.databinding.FragmentFavoritesBinding;
import com.indiehood.app.ui.artist_view.Artist;

public class FavoritesFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ArtistCollection = db.collection("ArtistCollection");
    private FavoritesAdapter adapter;
    private TextView emptyList;
    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        emptyList = root.findViewById(R.id.empty_rv);
        setUpRecyclerView(root);

        return root;
    }

    private void setUpRecyclerView(View r) {
        final Query query = ArtistCollection.whereEqualTo("favorited", true);
        // add listener to see if there are no favorites to show
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null && queryDocumentSnapshots.isEmpty()) {
                    emptyList.setVisibility(View.VISIBLE);
                    // no favorites, so return
                    return;
                }
            }
        });

        FirestoreRecyclerOptions<Artist> options = new FirestoreRecyclerOptions.Builder<Artist>()
                .setQuery(query, Artist.class)
                .build();
        // pass empty list for OnDataChanged() method to use if no favorites populated
        adapter = new FavoritesAdapter(options, emptyList);

        final RecyclerView favorites_rv = r.findViewById(R.id.favorites_rv);
        favorites_rv.setHasFixedSize(true);
        favorites_rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        favorites_rv.setAdapter(adapter);

        // for when the favorite button is clicked to unfavorite artist
        adapter.setOnFavoriteClickListener(new FavoritesAdapter.OnFavoriteClickListener() {
            final String TAG = "onFavClick";
            @Override
            public void onFavoriteClick(DocumentSnapshot snapshot, int position) {
                final DocumentReference artistRef = snapshot.getReference();
                Artist artist = snapshot.toObject(Artist.class);
                assert artist != null;
                artist.setFavorited(false);
                db.runTransaction(new Transaction.Function<Void>() {
                    @Nullable
                    @Override
                    public Void apply(@NonNull Transaction transaction) {
                        transaction.update(artistRef, "favorited", false);
                        return null;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                    }
                });
            }
        });

        // for when the user clicks an artist entirely
        adapter.setOnArtistClickListener(new FavoritesAdapter.OnArtistClickListener() {
            final String TAG = "onArtistClick";
            @Override
            public void onArtistClick(DocumentSnapshot snapshot, int position) {
                String id = snapshot.getId();
                Toast.makeText(getContext(), "Position: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();
                // TODO implement sending user to correct artist view
            }
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}