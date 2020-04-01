package com.indiehood.app.ui.listings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiehood.app.R;

import java.util.ArrayList;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingViewHolder> {
    private ArrayList<ShowListing> showListings;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public static class ListingViewHolder extends RecyclerView.ViewHolder {
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

    public ListingAdapter(ArrayList<ShowListing> showList) {
        this.showListings = showList;
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
    public void onBindViewHolder(final @NonNull ListingViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return this.showListings.size();
    }
}
