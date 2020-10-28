package com.example.tourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

public class MapFragment extends Fragment {
    private android.webkit.WebView wv;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_map, container , false);

        wv = (android.webkit.WebView) view.findViewById(R.id.webview2);
        // Configure related browser settings
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        wv.setWebViewClient(new WebViewClient());
        // Load the initial URL
        wv.loadUrl("https://www.bing.com/maps?cp=43.6426929841045~-79.388287&sty=r&lvl=18&FORM=MBEDLD");

        return view;


    }



}
