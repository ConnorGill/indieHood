package com.indiehood.app.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.indiehood.app.R;

import org.w3c.dom.Text;

// TODO COMPLETE
public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        registerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // TODO add register code... all text fields and onClick for "register" button
        final Button btn_submit_registration = (Button) root.findViewById(R.id.btn_submit_registration);
        final TextView register_band_name = (TextView) root.findViewById(R.id.register_name);
        //   final TextView register_password = (TextView) root.findViewById(R.id.);

        btn_submit_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View textView) {
                String ArtistUsername = register_band_name.getText().toString();
                //        String ArtistPassword = register_password.getText().toString();
            }
        });


        return root;
    }
}
