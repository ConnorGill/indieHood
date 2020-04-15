package com.indiehood.app.ui.show;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.indiehood.app.R;
import com.indiehood.app.ui.listings.ShowListing;

public class ShowFragment extends Fragment {
    private ShowListing show;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        show = (ShowListing) bundle.getSerializable("selected");
        View root = inflater.inflate(R.layout.fragment_full_show, container, false);
        return root;
    }
}
