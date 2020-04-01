package com.example.myapplication.ui.listings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
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

public class ListingsFragment extends Fragment {

    private ListingsViewModel listingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        RecyclerView allListings;
        ListingAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        listingsViewModel =
                ViewModelProviders.of(this).get(ListingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listings, container, false);
        final ArrayList<ShowListing> showLists = new ArrayList<>();


        showLists.add(new ShowListing("Banana Rays", "The Alcove", "6:00pm"));
        showLists.add(new ShowListing("Gears", "Rounders", "6:30pm"));
        showLists.add(new ShowListing("The Beatles", "High Tide", "7:00pm"));
        showLists.add(new ShowListing("Daft Punk", "House Show", "7:30pm"));
        showLists.add(new ShowListing("Billie Eilish", "The Grammies", "8:00pm"));
        showLists.add(new ShowListing("Justin Bieber", "The Ferguson Center", "8:30pm"));
        showLists.add(new ShowListing("Banana Rays", "The Alcove", "9:00pm"));
        showLists.add(new ShowListing("Foo Fighters", "Mellow Mushroom", "9:30pm"));
        showLists.add(new ShowListing("Ariana Grande", "Sigma Chi", "10:00pm"));
        showLists.add(new ShowListing("Jack Stauber", "Red Shed", "10:30pm"));
        showLists.add(new ShowListing("Taylor Swift", "Houndstooth", "11:00pm"));
        showLists.add(new ShowListing("Lil' Nas X", "Rhythm and Brews", "11:30pm"));
        showLists.add(new ShowListing("Drake", "Green Bar", "12:00am"));


;

        allListings = root.findViewById(R.id.listings);
        allListings.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new ListingAdapter(showLists);
        allListings.setLayoutManager(mLayoutManager);
        allListings.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               //showLists.get(position) resolve clicking on a card in that position
            }
        });
        return root;
    }

}