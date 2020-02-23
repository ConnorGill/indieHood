package com.example.myapplication.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        super.onCreate(savedInstanceState);
        RecyclerView favorites_rv = root.findViewById(R.id.favorites_rv);
        ArrayList<Band> bands = Band.createBandList(20);
        ArrayList<Band> favorites = Band.createFavoritesList(bands);
        FavoritesAdapter adapter = new FavoritesAdapter(favorites);
        favorites_rv.setAdapter(adapter);
        favorites_rv.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return root;
    }
}