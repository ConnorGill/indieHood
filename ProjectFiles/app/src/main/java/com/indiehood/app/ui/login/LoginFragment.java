package com.indiehood.app.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.indiehood.app.R;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        loginViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //Login Code
            final Button btnLogin = (Button) root.findViewById(R.id.btnLogin);
            // Chance added this and created register view and registerFragment. not sure how to auth
            final Button btnRegister = (Button) root.findViewById(R.id.btnRegister);
            final TextView txtArtistUsername = (TextView) root.findViewById(R.id.txtArtistUsername);
            final TextView txtPassword = (TextView) root.findViewById(R.id.txtPassword);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View textView){
                    String password = txtPassword.getText().toString();
                    String username = txtArtistUsername.getText().toString();

                    //implement Firebase here to check dummy data when created
                    if(username.equals("band") && password.equals("pass")) {
                        Toast.makeText(textView.getContext(), "Go To Account", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(textView).navigate(R.id.nav_listings);
                    }
                    //change destination to account profile from listings after created
                    else
                        Toast.makeText(textView.getContext(), "Login Failed", Toast.LENGTH_LONG).show();

                    txtArtistUsername.setText("");
                    txtPassword.setText("");
                }
            });

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(textView.getContext(), "Go to registration page", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(textView).navigate(R.id.nav_register);
                }
            });


        return root;
    }
}