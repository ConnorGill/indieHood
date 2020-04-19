package com.indiehood.app.ui.show;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SymbolTable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.indiehood.app.R;
import com.indiehood.app.ui.listings.ListingAdapter;
import com.indiehood.app.ui.listings.ShowListing;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class ShowFragment extends Fragment implements OnMapReadyCallback {
    private ShowListing show;
    private GoogleMap mMap;
    private ShowAdapter mAdapter;
    private View root;
    private String docID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        show = (ShowListing) bundle.getSerializable("selected");
        docID = (String) bundle.get("docID");
        root = inflater.inflate(R.layout.fragment_full_show, container, false);
        setupAdapter();
        injectData();
        setupCalendar();
        setupInterested();
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
        double lat = show.getAddressLat();
        double log = show.getAddressLong();
        LatLng venue = new LatLng(lat, log);
        mMap.addMarker(new MarkerOptions().position(venue).title(show.getVenueName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venue, 15));
    }

    @SuppressLint("SetTextI18n")
    private void injectData() {
        TextView bandName = root.findViewById(R.id.bandName);
        TextView interested = root.findViewById(R.id.full_interested_count);
        TextView price = root.findViewById(R.id.full_show_price);
        TextView date = root.findViewById(R.id.full_date);
        TextView time = root.findViewById(R.id.time);
        TextView address1 = root.findViewById(R.id.address1);
        TextView address2 = root.findViewById(R.id.address2);
        TextView description = root.findViewById(R.id.show_description);
        time.setText(show.startTimeFormatted + " to " + show.endTimeFormatted);
        bandName.setText(show.getBandName());
        interested.setText(Integer.toString(show.getNumberInterested()));
        price.setText(show.priceFormatted);
        date.setText(show.dateMonth + " " + show.dateDay + " " + show.dateYear);
        address1.setText(show.getAddress1());
        address2.setText(show.getAddress2());
        description.setText(show.getDescription());
    }

    private void setupCalendar() {
        Button addToCalendar = root.findViewById(R.id.addToCalendar);
        addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar beginTime = Calendar.getInstance();
                String[] start = show.startTimeFormatted.split(":");
                String starthour = start[0];
                String startminute = start[1].split(" ")[0];
                String month = show.getDay().split("-")[1];
                String day = show.getDay().split("-")[2];
                beginTime.set(Integer.parseInt(show.dateYear), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(starthour), Integer.parseInt(startminute));
                String[] end = show.endTimeFormatted.split(":");
                String endhour = end[0];
                String endminute = end[1].split(" ")[0];
                Calendar endTime = Calendar.getInstance();
                endTime.set(Integer.parseInt(show.dateYear), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(endhour), Integer.parseInt(endminute));
                Button interested = root.findViewById(R.id.interested);
                if (!"I'm Interested".contains(interested.getText())) {
                    interested.performClick();
                }
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE,  show.getBandName() + " @ " + show.getVenueName())
                        .putExtra(CalendarContract.Events.DESCRIPTION, show.getDescription() + "\n\n" + "Discovered through indieHood")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, show.getAddress1() + " " + show.getAddress2())
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                startActivity(intent);
            }
        });
    }

    private void setupInterested() {
        final Button interested = root.findViewById(R.id.interested);
        if (show.getUserInterested()) {
            interested.setText("Not Interested");
        }
        else {
            interested.setText("I'm Interested");
        }
        interested.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                System.out.println(interested.getText().length());
                System.out.println("I'm Interested".length());
                if ("I'm Interested".contains(interested.getText())) {       //chose to be interested
                    System.out.println("CHECKED NOW");

                    //remove this and the associated fields when user created
                    show.setUserInterested(true);
                    db.collection("ShowListingCol").document(docID).update("userInterested", true);
                    interested.setText("Not Interested");
                    //you should update the current user to show that they are interested too, referencing local var instead
                    //but also do this...
                    int newInterested = show.getNumberInterested() + 1;
                    updateInterested(newInterested);
                }
                else {  //chose not to be interested
                    System.out.println("NOT CHECKED");

                    //remove this and the associated fields when user created
                    show.setUserInterested(false);
                    db.collection("ShowListingCol").document(docID).update("userInterested", false);

                    interested.setText("I'm Interested");
                    //you should update the current user to show that they are not interested, referencing local var instead
                    //but also do this...
                    int newInterested = show.getNumberInterested() - 1;
                    updateInterested(newInterested);

                }
            }

        });

    }

    public void updateInterested(int newInterested) {
        db.collection("ShowListingCol").document(docID).update("numberInterested", newInterested);
        TextView interested = root.findViewById(R.id.full_interested_count);
        show.setNumberInterested(newInterested);
        interested.setText(Integer.toString(newInterested));
    }
}
