package com.indiehood.app.ui.listings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.indiehood.app.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.indiehood.app.ui.show.ShowFragment;

public class ListingAdapter extends FirestoreRecyclerAdapter<ShowListing, ListingAdapter.ListingHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> filters;
    private String sort;
    private OnItemClickListener mListener;
    private ListingsFragment fragment;

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
    }

    @Override
    protected void onBindViewHolder(@NonNull final ListingHolder holder, final int position,
                                    @NonNull final ShowListing model) {
        holder.mTextBandName.setText(model.getBandName());
        holder.mTextVenue.setText(model.getVenueName());
        holder.mTextTime.setText(model.getTime());
        holder.mInterestedText.setText(model.getInterestedText());
        holder.mPrice.setText(model.getStringifiedPrice());
        if (model.getBandFavorite()){
            holder.mBandFavorited.setImageResource(R.drawable.favorites_icon);
        }

        if (model.getUserInterested()) {
            holder.mUserInterested.setChecked(true);
        }
        else {
            holder.mUserInterested.setChecked(false);
        }

        holder.mUserInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mUserInterested.isChecked()) {       //chose to be interested
                    System.out.println("CHECKED NOW");

                    //remove this and the associated fields when user created
                    String docID = ListingAdapter.super.getSnapshots().getSnapshot(position).getId();
                    db.collection("ShowListingCol").document(docID).update("userInterested", true);

                    //you should update the current user to show that they are interested too, referencing local var instead
                    //but also do this...
                    int newInterested = model.getNumberInterested() + 1;
                    updateInterested(position, newInterested);
                }
                else {  //chose not to be interested

                    //remove this and the associated fields when user created
                    String docID = ListingAdapter.super.getSnapshots().getSnapshot(position).getId();
                    db.collection("ShowListingCol").document(docID).update("userInterested", false);

                    //you should update the current user to show that they are not interested, referencing local var instead
                    //but also do this...
                    int newInterested = model.getNumberInterested() - 1;
                    updateInterested(position, newInterested);
                    System.out.println("NOT CHECKED");

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
        Boolean priceEquality = false;
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
                result = result.whereLessThan("day", formattedWeek);
            }
            else {
                result = ShowListing.whereLessThan("day", formattedWeek);
            }
        }
        if (this.filters.contains("Date: Within 1 Day")) {
            Calendar d = Calendar.getInstance();
            d.setTime(currentDate);
            d.add(Calendar.DATE, 1);
            Date currentDatePlusDay = d.getTime();
            String formattedDay = dateFormat.format(currentDatePlusDay);
            if (result != null){
                result = result.whereLessThan("day", formattedDay);
            }
            else {
                result = ShowListing.whereLessThan("day", formattedDay);
            }
        }
        if (this.filters.contains("Price: Free")) {
            if (result != null){
                result = result.whereEqualTo("price", 0);
            }
            else {
                result = ShowListing.whereEqualTo("price", 0);
            }
            priceEquality = true;
        }
        if (result == null) {
            if ("Price: Cheapest First".contains(sort) && priceEquality != true) {
                result = ShowListing.whereGreaterThan("price", -1).orderBy("price", Query.Direction.ASCENDING);
            }
            else if ("Date: Soonest First".contains(sort)) {
                result = ShowListing.whereGreaterThan("day", formattedCurrent).orderBy("day", Query.Direction.ASCENDING);
            }
            else {
                result = ShowListing.whereGreaterThan("day", formattedCurrent);
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
        public TextView mTextTime;
        public TextView mInterestedText;
        public ImageView mBandFavorited;
        public CheckBox mUserInterested;
        public TextView mPrice;

        public ListingHolder(@NonNull final View itemView) {
            super(itemView);
            mTextBandName = itemView.findViewById(R.id.bandName);
            mTextVenue = itemView.findViewById(R.id.venue);
            mTextTime = itemView.findViewById(R.id.time);
            mInterestedText = itemView.findViewById(R.id.interested_text);
            mBandFavorited = itemView.findViewById(R.id.bandFavorited);
            mUserInterested = itemView.findViewById(R.id.interested);
            mPrice = itemView.findViewById(R.id.price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ObservableSnapshotArray<ShowListing> listing = ListingAdapter.super.getSnapshots();
                    ShowListing selected = listing.get(position);

                    FragmentTransaction ft = fragment.getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ShowFragment show = new ShowFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selected", selected);
                    show.setArguments(bundle);
                    ft.replace(R.id.listing_fragment, show);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }
    }
}

/*
public class ListingAdapter extends FirestoreRecyclerAdapter<ShowListing, ListingAdapter.ListingViewHolder> {
    private ArrayList<ShowListing> showListings;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ListingViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextBandName;
        public TextView mTextVenue;
        public TextView mTextTime;
        public TextView mInterestedText;
        public ImageView mBandFavorited;
        public ImageView mVenueFavorited;
        public CheckBox mUserInterested;

        public ListingViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            mTextBandName = itemView.findViewById(R.id.bandName);
            mTextVenue = itemView.findViewById(R.id.venue);
            mTextTime = itemView.findViewById(R.id.time);
            mInterestedText = itemView.findViewById(R.id.interested_text);
            mBandFavorited = itemView.findViewById(R.id.bandFavorited);
            mVenueFavorited = itemView.findViewById(R.id.venueFavorited);
            mUserInterested = itemView.findViewById(R.id.interested);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ListingAdapter(FirestoreRecyclerOptions<ShowListing> options) {
        super(options);
    }



    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_listing, parent, false);
        ListingViewHolder evh = new ListingViewHolder(v, mListener);
        return evh;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.showListings.size();
    }

    @Override
    protected void onBindViewHolder(@NonNull ListingViewHolder holder, int position, @NonNull Object model) {

        final ShowListing currentItem = this.showListings.get(position);
        holder.mTextBandName.setText(currentItem.getBandName());
        holder.mTextVenue.setText(currentItem.getVenue());
        holder.mTextTime.setText(currentItem.getTime());
        holder.mInterestedText.setText(currentItem.getNumberInterested());
        if (currentItem.getBandFavorite()){
            holder.mBandFavorited.setImageResource(R.drawable.favorites_icon);
        }
        if (currentItem.getVenueFavorite()){
            holder.mVenueFavorited.setImageResource(R.drawable.favorites_icon);
        }
    }
} */
