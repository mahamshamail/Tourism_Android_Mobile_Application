package com.example.tourismapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;


public class AttractionDisplay  extends Fragment implements Serializable {

    private int telltale;
    private Intent i ;
    private ArrayList<Attraction>attractions;
    private int index;
    private boolean add = true;
    private RatingBar rating;
    //private SharedPreferences sharedPreferences ;


    //private ArrayList<Attraction> wishListAttractions = new ArrayList<Attraction>();



    public AttractionDisplay(int r, ArrayList<Attraction>attractions, int index){
        telltale = r;
        this.attractions = attractions;
        this.index = index;
    }
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Toast.makeText(getContext(), "Displaying attraction details", Toast.LENGTH_SHORT).show();


        View view= inflater.inflate(R.layout.activity_attraction_display, container , false);
        rating = (RatingBar) view.findViewById(R.id.rating) ;
        rating.setRating(((Navigation)getActivity()).getRatingSP());
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //update share prefernces
                ((Navigation)getActivity()).setRatingSP(v);
            }
        });
        Button dial = (Button) view.findViewById(R.id.dial);
        Button backButton = (Button) view.findViewById(R.id.backButton);
        Button addToWishListButton = (Button) view.findViewById(R.id.add_to_wish_list);
        Button websiteButton = (Button) view.findViewById(R.id.website_button);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView phone = (TextView) view.findViewById(R.id.phone_no);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView address = (TextView) view.findViewById(R.id.address);
        TextView pricing = (TextView) view.findViewById(R.id.pricing);
        ImageView photo1 = (ImageView)view.findViewById(R.id.image_of_attraction) ;
        ImageView photo2 = (ImageView)view.findViewById(R.id.image_of_attraction1) ;
        ImageView photo3 = (ImageView)view.findViewById(R.id.image_of_attraction2) ;
        RatingBar rating = (RatingBar) view.findViewById(R.id.rating);

        name.setText(attractions.get(index).getName());
        phone.setText(attractions.get(index).getPhone_no());
        description.setText(attractions.get(index).getDescription());
        address.setText(attractions.get(index).getAddress());
        pricing.setText(attractions.get(index).getPricing());
        photo1.setImageResource(attractions.get(index).getPhoto1());
        photo2.setImageResource(attractions.get(index).getPhoto2());
        photo3.setImageResource(attractions.get(index).getPhoto3());

        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String w = attractions.get(index).getWebsite();
                Intent intent = new Intent(getContext(),WebView.class);
                intent.putExtra("link",w);
                startActivity(intent);
            }
        });

        final String n = phone.getText().toString();

        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+n));
                startActivity(intent);
            }
        });

        addToWishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((Navigation)getActivity()).getWLSize()>0){
                    for (int j=0; j<((Navigation)getActivity()).getWLSize(); j++ ){
                        if(((Navigation)getActivity()).getWishListAttractions().get(j).getName().equals(attractions.get(index).getName())){
                            Toast.makeText(getContext(), "Already in wish-list!", Toast.LENGTH_SHORT).show();

                            add = false;
                            break;
                        }
                    }
                }
                if(add) {
                    //add to wishlist
                    ((Navigation) getActivity()).getAttractions().get(index).setInWishList(true);
                    //wishListAttractions.add(attractions.get(index));
                    Toast.makeText(getContext(), attractions.get(index).getName() + " added to wish-list!", Toast.LENGTH_SHORT).show();
                    ((Navigation) getActivity()).addToWishList(attractions.get(index));

                    String sp = "" + index;
                    ((Navigation) getActivity()).addToSP(sp);
                    Log.d("______Attraction details__________", "SP:" + sp);
                    Log.d("______Attraction details__________", attractions.get(index).toString());
                }
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("______APP DISPLAY_______","telltale "+telltale);
                if(telltale==0 || telltale==1) {

                    Intent i = new Intent(getContext(), Navigation.class);
                    i.putExtra("name","return to t");
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(getContext(), Navigation.class);
                    i.putExtra("name","return to w");
                    startActivity(i);
                }

            }
        });



        return view;

    }

}
