package com.indiehood.app.ui.show;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.indiehood.app.R;
import com.indiehood.app.ui.listings.ShowListing;

import java.io.IOException;
import java.util.List;

public class ShowFragment extends Fragment implements OnMapReadyCallback {
    private ShowListing show;
    private GoogleMap mMap;
    private ShowAdapter mAdapter;
    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        show = (ShowListing) bundle.getSerializable("selected");
        root = inflater.inflate(R.layout.fragment_full_show, container, false);
        setupAdapter();
        injectData();
        setupMapIntegration();
        return root;
    }


    private void setupMapIntegration() {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        System.out.println("DONE");
        return;
    }

    private void setupAdapter() {
        mAdapter = new ShowAdapter(this.getContext(), show);
        return;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double lat = 33.166504;
        double log = -87.548277;
        LatLng venue = new LatLng(lat, log);
        mMap.addMarker(new MarkerOptions().position(venue).title(show.getVenueName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venue, 15));
    }

    @SuppressLint("SetTextI18n")
    private void injectData() {
        TextView bandName = root.findViewById(R.id.bandName);
        TextView interestedCount = root.findViewById(R.id.full_interested_count);
        TextView price = root.findViewById(R.id.full_show_price);
        TextView date = root.findViewById(R.id.full_date);
        TextView address1 = root.findViewById(R.id.address1);
        TextView address2 = root.findViewById(R.id.address2);
        TextView description = root.findViewById(R.id.show_description);

        bandName.setText(show.getBandName());
        interestedCount.setText(show.getNumberInterested() + " interested");
        price.setText(show.priceFormatted);
        date.setText(show.dateMonth + " " + show.dateDay + " " + show.dateYear);
        address1.setText(show.getAddress1());
        address2.setText(show.getAddress2());
        description.setText(show.getDescription());
    }
}
