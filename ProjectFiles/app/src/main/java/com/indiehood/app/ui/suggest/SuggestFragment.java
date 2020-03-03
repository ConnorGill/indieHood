package com.indiehood.app.ui.suggest;

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

import com.indiehood.app.R;

public class SuggestFragment extends Fragment {

    private SuggestViewModel suggestViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        suggestViewModel =
                ViewModelProviders.of(this).get(SuggestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_suggest, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        suggestViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}