package com.indiehood.app.ui.listings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.indiehood.app.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListingsFragment extends Fragment {

    private ListingsViewModel listingsViewModel;
    private View root;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListingAdapter mAdapter;
    private MultiSpinner filter_options;
    private Spinner sort_options;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listingsViewModel = new ViewModelProvider(this).get(ListingsViewModel.class);
        root = inflater.inflate(R.layout.fragment_listings, container, false);
        setupRecycler();
        setUpMultiSpinner();
        setupSingleSpinner();
        return root;
    }

    private Void setupRecycler() {
        CollectionReference ShowListing = db.collection("ShowListingCol");
        /*
       String[] stuff = {"Banana Rays", "Bonnie Prince Billy", "Four Tet",  "Gears", "Hollow Hand", "Julia Jacklin", "Mapache", "Prince Fatty"};
        String[] venue = {"Alcove", "BR House", "Copper Top",  "Druid City", "Egan's", "Gallettes", "Red Shed", "Wheelhouse"};
        Double[] prices = {0.0, 6.50, 9.0, 10.00, 5.00};
        String[][] times = { {"18:00", "20:00"}, {"18:30", "20:30"}, {"22:15", "1:30"}};
        String[][] dates = { {"2020-08-12", "2020-08-13"}, {"2020-10-02", "2020-10-03"}, {"2020-07-01", "2020-07-02"}};
        //CollectionReference ShowListing = db.collection("ShowListingCol");
        for (int i = 0;  i < 20; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("bandName", stuff[ThreadLocalRandom.current().nextInt(stuff.length)]);
            String[] time = times[ThreadLocalRandom.current().nextInt(times.length)];
            data.put("startTime", time[0]);
            data.put("endTime", time[1]);
            String[] date = dates[ThreadLocalRandom.current().nextInt(dates.length)];
            if (Integer.parseInt(time[1].split(":")[0]) < Integer.parseInt(time[0].split(":")[0])) {
                data.put("startDay", date[0]);
                data.put("endDay", date[1]);
             }
             else {
                data.put("startDay", date[0]);
                data.put("endDay", date[0]);
             }
            data.put("venueName", venue[ThreadLocalRandom.current().nextInt(venue.length)]);
            data.put("numberInterested", ThreadLocalRandom.current().nextInt(4));
            data.put("price", prices[ThreadLocalRandom.current().nextInt(prices.length)]);
            data.put("address1", "4383 Courtney Dr");
            data.put("address2", "Tuscaloosa, AL 35405");
            data.put("addressLat", 33.166504);
            data.put("addressLong", -87.548277);
            data.put("description", "NOT canceled due to COVID-19 (yet)");
            data.put("userInterested", false);
            ShowListing.add(data);
        } */

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        Query query = ShowListing.whereGreaterThan("startDay", currentDate).orderBy("startDay", Query.Direction.ASCENDING);   //change this to a more intelligent retrieval method later
        final FirestoreRecyclerOptions<ShowListing> options = new FirestoreRecyclerOptions.Builder<ShowListing>()
                .setQuery(query, ShowListing.class).build();
        mAdapter = new ListingAdapter(options, this);
        final RecyclerView allListings = root.findViewById(R.id.listings);
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

    private void setUpMultiSpinner() {
        filter_options = root.findViewById(R.id.filter_options);
        filter_options.addAdapter(mAdapter);
    }

    private void setupSingleSpinner() {
        sort_options = root.findViewById(R.id.sort_options);
        sort_options.setOnItemSelectedListener(new SortingSelection(mAdapter));
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

