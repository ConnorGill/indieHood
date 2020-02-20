package com.example.myapplication.ui.login;

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

import com.example.myapplication.R;

import org.w3c.dom.Text;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        loginViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //Login Code
            final Button btnLogin = (Button) root.findViewById(R.id.btnLogin);
            final TextView txtArtistUsername = (TextView) root.findViewById(R.id.txtArtistUsername);
            final TextView txtPassword = (TextView) root.findViewById(R.id.txtPassword);

            btnLogin.setOnClickListener(new View.OnClickListener(){
                @Override
                        public void onClick(View textView){
                            String password = txtPassword.getText().toString();
                            String username = txtArtistUsername.getText().toString();

                            if(password.equals("pass") && username.equals("band"))
                                Toast.makeText(textView.getContext(), "Go To Account", 2).show();
                            else
                                Toast.makeText(textView.getContext(), "Login Failed", 2).show();

                            txtArtistUsername.setText("");
                            txtPassword.setText("");
            }


                });


        return root;
    }
}