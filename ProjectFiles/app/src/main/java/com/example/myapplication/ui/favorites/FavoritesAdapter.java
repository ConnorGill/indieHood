package com.example.myapplication.ui.favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bandName;
        public ImageView bandIcon;
        public ImageButton favorite;

        public ViewHolder(View itemView) {
            super(itemView);
            bandIcon = itemView.findViewById(R.id.band_venue_icon);
            bandName = itemView.findViewById(R.id.band_venue_name);
            favorite = itemView.findViewById(R.id.favorite_button);
        }
    }

    private List<Band> myBands;
    public FavoritesAdapter(List<Band> favorites) { myBands = favorites; }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View favoriteView = inflater.inflate(R.layout.favorited_band_row, parent, false);
        return new ViewHolder(favoriteView);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder viewHolder, int position) {
        Band band = myBands.get(position);

        TextView name = viewHolder.bandName;
        ImageView icon = viewHolder.bandIcon;
        ImageButton fav = viewHolder.favorite;

        name.setText(band.getBandName());
        // icon.setImageIcon(); TODO how to implement? Right now they are hardcoded
        fav.setEnabled(band.isFavorited());
    }

    @Override
    public int getItemCount() {
        return myBands.size();
    }
}
