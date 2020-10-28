package com.example.tourismapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class WishListFragment extends Fragment {
    private ArrayList<Attraction> wishlist;
    private String [] names;
    private String [] addresses;
    private int [] images;
    private Button clear;
    public WishListFragment(ArrayList<Attraction>wishlist) {
        Log.d("IN WISHLISH FRAGMENT","WISH LIST SIZE 1: "+wishlist.size());
       this.wishlist = wishlist;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_wish_list, container , false);
        clear = (Button) view.findViewById(R.id.clearButton) ;
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Navigation)getActivity()).setSP("nothing");
                Intent intent = new Intent(getContext(),Navigation.class);
                intent.putExtra("name","return to w");
                startActivity(intent);
            }
        });
        if(wishlist.size()>0){
            Log.d("______WISHLIST SIZE_______",""+wishlist.size());
            names = new String[this.wishlist.size()];
            images=  new int[wishlist.size()];
             addresses = new String[wishlist.size()];
             for(int i =0; i<wishlist.size();i++){
                 Log.d("IN WISHLISH FRAGMENT","WISH LIST SIZE 2: "+wishlist.size());
               names[i]=wishlist.get(i).getName();
               addresses[i] = wishlist.get(i).getAddress();
               images[i]= wishlist.get(i).getPhoto2();
               //view= inflater.inflate(R.layout.content_main, container , false);

                 ListView list = (ListView) view.findViewById(R.id.wish_list);

                 //this here needs to be updated to save name, address and photo.
                 MyAdapter adapter = new MyAdapter(getContext(), names, addresses, images);
                 list.setAdapter(adapter);

                 list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                         String item = adapterView.getItemAtPosition(i).toString();

                         Toast.makeText(getContext(),item, Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(getContext(),Navigation.class);
                         intent.putExtra("name","wish"+i);
                         startActivity(intent);
                     }
                 });
            }
        }else {

        }


        return view;
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.list_row, R.id.main_title, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View row = layoutInflater.inflate(R.layout.list_row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.main_title);
            TextView myDescription = row.findViewById(R.id.sub_title);

            // now set our resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);




            return row;
        }
    }


}
