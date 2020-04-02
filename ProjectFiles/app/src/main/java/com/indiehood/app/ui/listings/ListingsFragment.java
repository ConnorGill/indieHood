package com.indiehood.app.ui.listings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.Random;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.indiehood.app.R;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;

public class ListingsFragment extends Fragment {

    private ListingsViewModel listingsViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListingAdapter mAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listingsViewModel =
                ViewModelProviders.of(this).get(ListingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listings, container, false);
        setupRecycler(root);
        return root;
    }

    private Void setupRecycler(View root) {
    /*
        String[] stuff = {"Banana Rays", "Billie Eilish", "Chance the Rapper",  "Foo Fighters", "Four Tet", "Gears", "Journey", "Nine Inch Nails", "Phish", "The Grateful Dead"};
        String[] venue = {"Alcove", "BR House", "Copper Top",  "Druid City", "Egan's", "Gallettes", "Red Shed", "Wheelhouse"};
        Double[] prices = {0.0, 0.0, 0.0, 10.00, 5.00};
        CollectionReference ShowListing = db.collection("ShowListingCol");
        for (int i = 0;  i < 20; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("bandName", stuff[ThreadLocalRandom.current().nextInt(stuff.length)]);
            data.put("day", "2020-08-12");
            data.put("time", "18:00");
            data.put("venueName", venue[ThreadLocalRandom.current().nextInt(venue.length)]);
            data.put("numberInterested", ThreadLocalRandom.current().nextInt(4));
            data.put("price", prices[ThreadLocalRandom.current().nextInt(prices.length)]);
            data.put("address", "4383 Courtney Dr, Tuscaloosa, AL 35405");
            data.put("description", "NOT canceled due to COVID-19 (yet)");
            data.put("userInterested", false);
            ShowListing.add(data);
        } */
        CollectionReference ShowListing = db.collection("ShowListingCol");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        Query query = ShowListing.whereGreaterThan("day", currentDate);   //change this to a more intelligent retrieval method later

        FirestoreRecyclerOptions<ShowListing> options = new FirestoreRecyclerOptions.Builder<ShowListing>()
                .setQuery(query, ShowListing.class).build();

        mAdapter = new ListingAdapter(options);
        System.out.println(mAdapter.toString());
        RecyclerView allListings = root.findViewById(R.id.listings);
        allListings.setHasFixedSize(true);
        allListings.setLayoutManager(new LinearLayoutManager(this.getContext()));
        allListings.setAdapter(mAdapter);
        /*
        mAdapter.setOnItemClickListener(new ListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //showLists.get(position) resolve clicking on a card in that position
            }
        }); */

        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }




}