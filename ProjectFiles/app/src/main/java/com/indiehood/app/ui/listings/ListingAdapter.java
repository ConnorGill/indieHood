package com.indiehood.app.ui.listings;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.indiehood.app.MainActivity;
import com.indiehood.app.R;
import com.indiehood.app.User;
import com.indiehood.app.ui.show.ShowFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListingAdapter extends FirestoreRecyclerAdapter<ShowListing, ListingAdapter.ListingHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> filters;
    private String sort;
    private OnItemClickListener mListener;
    private ListingsFragment fragment;
    private User currentUser;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ListingAdapter(@NonNull FirestoreRecyclerOptions<ShowListing> options, ListingsFragment fragment) {
        super(options);
        this.filters = new ArrayList<>();
        this.sort = "Date: Soonest First";
        this.fragment = fragment;
        this.currentUser = ((MainActivity) fragment.requireActivity()).currentUser;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final ListingHolder holder, final int position, @NonNull final ShowListing model) {
        model.formatValues();
        holder.mTextBandName.setText(model.getBandName());
        holder.mTextVenue.setText("@ " + model.getVenueName());
        holder.mTextMonth.setText(model.dateMonth);
        holder.mTextDay.setText(model.dateDay);
        holder.mTimeStart.setText(model.startTimeFormatted);
        holder.mInterestedText.setText(model.getInterestedText());
        if (currentUser.getFavoritedBands().contains(model.getBandName())) {
            holder.mBandFavorited.setVisibility(View.VISIBLE);
        }
        else {
            holder.mBandFavorited.setVisibility(View.GONE);
        }
        if (currentUser.getInterestedShows().contains(model.getShowID())) {
            holder.mUserInterested.setChecked(true);
        }
        else {
            holder.mUserInterested.setChecked(false);
        }

        holder.mUserInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mUserInterested.isChecked()) {       //chose to be interested
                    holder.mUserInterested.setClickable(false);     //this prevents the logic from manipulation during processing
                    System.out.println("CHECKED NOW");
                    String docID = currentUser.getUID();
                    currentUser.getInterestedShows().add(model.getShowID());
                    db.collection("UserCol").document(docID).update("interestedShows", currentUser.getInterestedShows());
                    int newInterested = model.getNumberInterested() + 1;
                    updateInterested(position, newInterested);
                    holder.mUserInterested.setClickable(true);
                }
                else {  //chose not to be interested
                    holder.mUserInterested.setClickable(false);
                    String docID = currentUser.getUID();
                    currentUser.getInterestedShows().remove(model.getShowID());
                    db.collection("UserCol").document(docID).update("interestedShows", currentUser.getInterestedShows());
                    int newInterested = model.getNumberInterested() - 1;
                    updateInterested(position, newInterested);
                    System.out.println("NOT CHECKED");
                    holder.mUserInterested.setClickable(true);
                }
            }
            public void updateInterested(int position, int newInterested) {
                String docID = ListingAdapter.super.getSnapshots().getSnapshot(position).getId();
                db.collection("ShowListingCol").document(docID).update("numberInterested", newInterested);
                ListingAdapter.super.bindViewHolder(holder, position);
            }
        });


    }

    @NonNull
    @Override
    public ListingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_listing, parent, false);
        return new ListingHolder(v);
    }
    private void UpdateListings() {
        System.out.println("Starting Reload");
        CollectionReference ShowListing = db.collection("ShowListingCol");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String formattedCurrent = dateFormat.format(currentDate);
        Query result = null;
        if (this.filters.contains("Favorited: Bands")) {
            if (result != null) {
                result = result.whereEqualTo("userInterested", true);
            }
            else {
                result = ShowListing.whereEqualTo("userInterested", true);
            }
        }
        if (this.filters.contains("Date: Within 1 Week")) {
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DATE, 7);
            Date currentDatePlusWeek = c.getTime();
            String formattedWeek = dateFormat.format(currentDatePlusWeek);
            if (result != null){
                result = result.whereLessThan("startDay", formattedWeek);
            }
            else {
                result = ShowListing.whereLessThan("startDay", formattedWeek);
            }
        }
        if (this.filters.contains("Date: Within 1 Day")) {
            Calendar d = Calendar.getInstance();
            d.setTime(currentDate);
            d.add(Calendar.DATE, 1);
            Date currentDatePlusDay = d.getTime();
            String formattedDay = dateFormat.format(currentDatePlusDay);
            if (result != null){
                result = result.whereLessThan("startDay", formattedDay);
            }
            else {
                result = ShowListing.whereLessThan("startDay", formattedDay);
            }
        }
        if (this.filters.contains("Price: Free")) {
            if (result != null){
                result = result.whereEqualTo("price", 0);
            }
            else {
                result = ShowListing.whereEqualTo("price", 0);
            }
        }
        if (result == null) {
            if ("Interested: Most First".contains(sort)) {
                result = ShowListing.whereGreaterThan("numberInterested", -1).orderBy("numberInterested", Query.Direction.DESCENDING);
            }
            else if ("Price: Cheapest First".contains(sort)) {
                result = ShowListing.whereGreaterThan("price", -1).orderBy("price", Query.Direction.ASCENDING);
            }
            else if ("Date: Soonest First".contains(sort)) {
                result = ShowListing.whereGreaterThan("startDay", formattedCurrent).orderBy("startDay", Query.Direction.ASCENDING);
            }
            else {
                result = ShowListing.whereGreaterThan("startDay", formattedCurrent);
            }
        }
        FirestoreRecyclerOptions<ShowListing> options = new FirestoreRecyclerOptions.Builder<ShowListing>()
                .setQuery(result, ShowListing.class).build();
        ListingAdapter.super.updateOptions(options);
        System.out.println("Done Reloading");
    }

    public void FilterListing(ArrayList<String> chosenFilters) {
        this.filters = chosenFilters;
        this.UpdateListings();
    }

    public void SortListing(String sortingChoice) {
        System.out.println(sortingChoice);
        this.sort = sortingChoice;
        this.UpdateListings();
    }

    class ListingHolder extends RecyclerView.ViewHolder {
        public TextView mTextBandName;
        public TextView mTextVenue;
        public TextView mTextDay;
        public TextView mTextMonth;
        public TextView mTimeStart;
        public TextView mInterestedText;
        public ImageView mBandFavorited;
        public CheckBox mUserInterested;

        public ListingHolder(@NonNull final View itemView) {
            super(itemView);
            mTextBandName = itemView.findViewById(R.id.bandName);
            mTextVenue = itemView.findViewById(R.id.venue);
            mTextDay = itemView.findViewById(R.id.day);
            mTextMonth = itemView.findViewById(R.id.month);
            mBandFavorited = itemView.findViewById(R.id.bandFavorited);
            mUserInterested = itemView.findViewById(R.id.interested);
            mInterestedText = itemView.findViewById(R.id.interested_text);
            mTimeStart = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ObservableSnapshotArray<ShowListing> listing = ListingAdapter.super.getSnapshots();
                    ShowListing selected = listing.get(position);
                    String docID = listing.getSnapshot(position).getId();
                    ShowFragment show = new ShowFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selected", selected);
                    bundle.putString("docID", docID);
                    Navigation.findNavController(v).navigate(R.id.nav_full_show, bundle);
                }
            });
        }
    }
}

