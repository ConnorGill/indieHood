package com.indiehood.app.ui.artist_view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.indiehood.app.R;
import com.indiehood.app.ui.SharedArtistViewModel;

public class ArtistFragment extends Fragment {
    private SharedArtistViewModel viewModel;
    private Observer<String> artistObserver;
    // elements of the artist profile
    private ImageView coverPhoto; // TODO implement firebase storage
    private ImageView proPic; // TODO implement firebase storage
    private TextView bandName;
    private TextView bandBio;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference artistRef;
    private Artist artist;
    // private ArtistAdapter adapter;

    public ArtistFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_artist_view, container, false);
        bandName = root.findViewById(R.id.band_name);
        bandBio = root.findViewById(R.id.band_bio);
        // TODO set up artist listing recycler view

        return root;
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
                artistRef = db.document(s);
                // get data from artist document reference
                artistRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                assert documentSnapshot != null;
                                artist = documentSnapshot.toObject(Artist.class);
                                assert artist != null;
                                Log.d("onSuccess", artist.getArtistName());
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
