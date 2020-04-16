package com.indiehood.app.ui.artist_view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.indiehood.app.R;
import com.indiehood.app.databinding.FragmentArtistViewBinding;
import com.indiehood.app.ui.favorites.FavoritesAdapter;

public class ArtistFragment extends Fragment {
    private FragmentArtistViewBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // private ArtistAdapter adapter; TODO implement recycler view

    public ArtistFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_artist_view, container, false);

        //View root = binding.getRoot();
        // TODO set up artist listing recycler view

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
