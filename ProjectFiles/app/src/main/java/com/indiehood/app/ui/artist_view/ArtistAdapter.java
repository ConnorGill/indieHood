package com.indiehood.app.ui.artist_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.indiehood.app.R;
import com.indiehood.app.ui.listings.ListingAdapter;
import com.indiehood.app.ui.listings.ShowListing;

// implements a recycler view using data pulled directly from firestore
public class ArtistAdapter extends FirestoreRecyclerAdapter<ShowListing, ArtistAdapter.ShowHolder> {
    private ArtistFragment fragment;

    ArtistAdapter(@NonNull FirestoreRecyclerOptions<ShowListing> options, ArtistFragment artistFragment) {
        super(options);
    }

    class ShowHolder extends RecyclerView.ViewHolder {
        TextView mTextBandName;
        TextView mTextVenue;
        TextView mTextDay;
        TextView mTextMonth;
        TextView mTimeStart;
        TextView mInterestedText;
        ImageView mBandFavorited;
        CheckBox mUserInterested;

        ShowHolder(View itemView) {
            super(itemView);
            mTextBandName = itemView.findViewById(R.id.bandName);
            mTextVenue = itemView.findViewById(R.id.venue);
            mTextDay = itemView.findViewById(R.id.day);
            mTextMonth = itemView.findViewById(R.id.month);
            mBandFavorited = itemView.findViewById(R.id.bandFavorited);
            mUserInterested = itemView.findViewById(R.id.interested);
            mInterestedText = itemView.findViewById(R.id.interested_text);
            mTimeStart = itemView.findViewById(R.id.time);
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull final ShowHolder viewHolder, int position,
                                    @NonNull ShowListing model) {
        model.formatValues();
        viewHolder.mTextBandName.setText(model.getBandName());
        viewHolder.mTextVenue.setText(model.getVenueName());
        viewHolder.mTextMonth.setText(model.dateMonth);
        viewHolder.mTextDay.setText(model.dateDay);
        viewHolder.mTimeStart.setText(model.startTimeFormatted);
        viewHolder.mInterestedText.setText(model.getInterestedText());
        if (model.getBandFavorite()) {
            viewHolder.mBandFavorited.setImageResource(R.drawable.favorites_icon);
        }

        if (model.getUserInterested()) {
            viewHolder.mUserInterested.setChecked(true);
        }
        else {
            viewHolder.mUserInterested.setChecked(false);
        }

        /*viewHolder.mUserInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.mUserInterested.isChecked()) {       //chose to be interested
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

            void updateInterested(int position, int newInterested) {
                String docID = ListingAdapter.super.getSnapshots().getSnapshot(position).getId();
                db.collection("ShowListingCol").document(docID).update("numberInterested", newInterested);
                ListingAdapter.super.bindViewHolder(viewHolder, position);
            }
        });*/
    }

    @NonNull
    @Override
    public ShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View artistView = inflater.inflate(R.layout.show_listing, parent, false);

        return new ShowHolder(artistView);
    }

    /*@Override
    public void onDataChanged() {
        emptyList.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }*/
}
