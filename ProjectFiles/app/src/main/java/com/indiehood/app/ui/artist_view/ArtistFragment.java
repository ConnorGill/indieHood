package com.indiehood.app.ui.artist_view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.indiehood.app.R;
import com.indiehood.app.databinding.FragmentArtistViewBinding;
import com.indiehood.app.ui.SharedArtistViewModel;
import com.indiehood.app.ui.favorites.FavoritesAdapter;

public class ArtistFragment extends Fragment {
    private FragmentArtistViewBinding binding;
    // to communicate with favorites view
    private SharedArtistViewModel viewModel;
    // elements of the artist profile
    private ImageView coverPhoto; // TODO implement firebase storage
    private ImageView proPic; // TODO implement firebase storage
    private TextView bandName;
    private TextView bandBio;

    /* TODO maybe we don't need these
        we can hardcode in two options for social, two options for streaming
    private ImageButton social1;
    private ImageButton social2;
    private ImageButton media1;
    private ImageButton media2;
    */

    // TODO implement recycler view
    // private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // private ArtistAdapter adapter;

    public ArtistFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_artist_view, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedArtistViewModel.class);
        bandName = root.findViewById(R.id.band_name);
        bandBio = root.findViewById(R.id.band_bio);
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
