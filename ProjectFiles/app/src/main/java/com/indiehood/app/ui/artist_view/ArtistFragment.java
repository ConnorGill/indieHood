package com.indiehood.app.ui.artist_view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;
import com.indiehood.app.R;
import com.indiehood.app.ui.SharedArtistViewModel;
import com.indiehood.app.ui.listings.ShowListing;

public class ArtistFragment extends Fragment {
    // to communicate with FavoritesFragment
    private SharedArtistViewModel viewModel;
    private Observer<String> artistObserver;
    // elements of the artist profile
    private ImageView coverPhoto; // TODO implement firebase storage
    private ImageView proPic; // TODO implement firebase storage
    private TextView bandName;
    private TextView bandBio;
    private CheckBox favorited;
    private ImageButton twitter;
    private ImageButton instagram;
    private ImageButton appleMusic;
    private ImageButton spotify;
    // for Firestore read/writes
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ShowListings = db.collection("ShowListingCol");
    private DocumentReference artistRef;
    private Artist artist;
    private ArtistAdapter adapter;

    class FavoriteButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            favoriteButtonClicked();
        }
    }

    private void favoriteButtonClicked() {
        final String TAG = "favButtonClicked";
        artist.setFavorited(!artist.getFavorited());
        if (artist.getFavorited()) {
            Toast.makeText(getContext(), "Artist favorited", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Artist unfavorited", Toast.LENGTH_SHORT).show();
        }
        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) {
                transaction.update(artistRef, "favorited", artist.getFavorited());
                return null;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });
    }

    class TwitterButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            twitterButtonClicked();
        }
    }

    private void twitterButtonClicked() {
        Uri link = Uri.parse(artist.getSocial1());
        if (!artist.getSocial1().equals("")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, link);
            startActivity(intent);
        }
        else {
            Toast.makeText(getContext(), "Error: artist does not have a link provided", Toast.LENGTH_SHORT).show();
        }
    }

    class InstaButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            instaButtonClicked();
        }
    }

    private void instaButtonClicked() {
        Uri link = Uri.parse(artist.getSocial2());
        if (!artist.getSocial2().equals("")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, link);
            startActivity(intent);
        }
        else {
            Toast.makeText(getContext(), "Error: artist does not have a link provided", Toast.LENGTH_SHORT).show();
        }
    }

    class AMButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            appleMusicButtonClicked();
        }
    }

    private void appleMusicButtonClicked() {
        Uri link = Uri.parse(artist.getMedia1());
        if (!artist.getMedia1().equals("")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, link);
            startActivity(intent);
        }
        else {
            Toast.makeText(getContext(), "Error: artist does not have a link provided", Toast.LENGTH_SHORT).show();
        }
    }

    class SpotifyButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            spotifyButtonClicked();
        }
    }

    private void spotifyButtonClicked() {
        Uri link = Uri.parse(artist.getMedia2());
        if (!artist.getMedia2().equals("")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, link);
            startActivity(intent);
        }
        else {
            Toast.makeText(getContext(), "Error: artist does not have a link provided", Toast.LENGTH_SHORT).show();
        }
    }

    public ArtistFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_artist_view, container, false);
        bandName = root.findViewById(R.id.band_name);
        bandBio = root.findViewById(R.id.band_bio);
        favorited = root.findViewById(R.id.favorite_button);
        twitter = root.findViewById(R.id.twitter);
        instagram = root.findViewById(R.id.instagram);
        appleMusic = root.findViewById(R.id.appleMusic);
        spotify = root.findViewById(R.id.spotify);
        favorited.setOnClickListener(new FavoriteButtonClick());
        twitter.setOnClickListener(new TwitterButtonClick());
        instagram.setOnClickListener(new InstaButtonClick());
        appleMusic.setOnClickListener(new AMButtonClick());
        spotify.setOnClickListener(new SpotifyButtonClick());
        // TODO set up artist listing recycler view
        setUpRecyclerView(root);

        return root;
    }

    private void setUpRecyclerView(View r) {
        final Query query = ShowListings.whereEqualTo("bandName", artist.getArtistName())
                .orderBy("startDay", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ShowListing> options = new FirestoreRecyclerOptions.Builder<ShowListing>()
                .setQuery(query, ShowListing.class)
                .build();
        adapter = new ArtistAdapter(options, this);
        final RecyclerView artist_shows_rv = r.findViewById(R.id.artist_shows);
        artist_shows_rv.setHasFixedSize(true);
        artist_shows_rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        artist_shows_rv.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // to communicate with favorites view
        viewModel = new ViewModelProvider(requireActivity()).get(SharedArtistViewModel.class);
        artist = new Artist();
        artistObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String s) {
                assert s != null;
                System.out.println(s);
                artistRef = db.document(s);
                // get data from artist document reference
                artistRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                assert documentSnapshot != null;
                                artist = documentSnapshot.toObject(Artist.class);
                                assert artist != null;
                                System.out.println(artist.getArtistName());
                                bandName.setText(artist.getArtistName());
                                bandBio.setText(artist.getBio());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String TAG = "artistRef.get()";
                                Log.d(TAG, "Transaction failure", e);
                            }
                        });
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getArtistPath().observe(getViewLifecycleOwner(), artistObserver);
    }
}
