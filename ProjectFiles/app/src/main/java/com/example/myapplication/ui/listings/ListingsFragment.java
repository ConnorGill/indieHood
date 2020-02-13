package com.example.myapplication.ui.listings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class ListingsFragment extends Fragment {

    private ListingsViewModel listingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listingsViewModel =
                ViewModelProviders.of(this).get(ListingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listings, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        listingsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}