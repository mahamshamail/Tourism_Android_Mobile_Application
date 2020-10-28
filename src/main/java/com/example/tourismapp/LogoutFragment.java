package com.example.tourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

public class LogoutFragment extends Fragment implements Serializable
{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "Logging out...", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getContext(),MainActivity.class);
        startActivity(i);

        View view= inflater.inflate(R.layout.fragment_logout_button, container , false);
        Button button = (Button) view.findViewById(R.id.logoutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });





        return view;

    }
}
