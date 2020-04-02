package com.indiehood.app.ui.listings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.indiehood.app.R;

import java.util.ArrayList;
import java.util.List;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

public class ListingAdapter extends FirestoreRecyclerAdapter<ShowListing, ListingAdapter.ListingHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ListingAdapter(@NonNull FirestoreRecyclerOptions<ShowListing> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ListingHolder holder, final int position, @NonNull final ShowListing model) {
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

    class ListingHolder extends RecyclerView.ViewHolder {
        public TextView mTextBandName;
        public TextView mTextVenue;
        public TextView mTextTime;
        public TextView mInterestedText;
        public ImageView mBandFavorited;
        public CheckBox mUserInterested;
        public TextView mPrice;

        public ListingHolder(@NonNull View itemView) {
            super(itemView);
            mTextBandName = itemView.findViewById(R.id.bandName);
            mTextVenue = itemView.findViewById(R.id.venue);
            mTextTime = itemView.findViewById(R.id.time);
            mInterestedText = itemView.findViewById(R.id.interested_text);
            mBandFavorited = itemView.findViewById(R.id.bandFavorited);
            mUserInterested = itemView.findViewById(R.id.interested);
            mPrice = itemView.findViewById(R.id.price);
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
