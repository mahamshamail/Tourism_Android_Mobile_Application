package com.example.tourismapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
/*
"name":
"address":
"phone_no":
"website":
"description":
"pricing":
"photos":
"rating":

"CN Tower",
"290 Bremner Blvd, Toronto, ON M5V 3L9",
"6475295619",
"https://www.cntower.ca/en-ca/home.html",
"The CN Tower is a 553.3 m-high concrete communications and observation tower located in Downtown Toronto, Ontario, Canada. Built on the former Railway Lands, it was completed in 1976. Its name "CN" originally referred to Canadian National, the railway company that built the tower.",
"$$$",

"Toronto Zoo",
"2000 Meadowvale Rd, Toronto, ON M1B 5K7",
"(416) 392-5900",
"https://www.torontozoo.com",
"The Toronto Zoo is a zoo located in Toronto, Ontario, Canada. Encompassing 287 hectares, the Toronto Zoo is the largest zoo in Canada. It is divided into seven zoogeographic regions: Indo-Malaya, Africa, Americas, Tundra Trek, Australasia, Eurasia, and the Canadian Domain.",
"$$",

"Art Gallery of Ontario",
"317 Dundas St W, Toronto, ON M5T 1G4",
"(416) 590-0392",
"https://ago.ca",
"The Art Gallery of Ontario is an art museum in Toronto, Ontario, Canada. The museum is located in the Grange Park neighbourhood of downtown Toronto, on Dundas Street West between McCaul and Beverley streets.",
"$$",

"Royal Ontario Museum",
"100 Queens Park, Toronto, ON M5S 2C6",
"(416) 503-9290",
"https://www.rom.on.ca/en",
"The Royal Ontario Museum is a museum of art, world culture and natural history in Toronto, Ontario, Canada. It is one of the largest museums in North America and the largest in Canada. It attracts more than one million visitors every year, making the ROM the most-visited museum in Canada.",
"$$",

"Ripley's Aquarium of Canada",
"288 Bremner Blvd, Toronto, ON M5V 3L9",
"(416) 5939-200",
"https://www.ripleyaquariums.com/canada/",
"Ripley's Aquarium of Canada is a public aquarium in Toronto, Ontario, Canada. The aquarium is one of three aquariums owned and operated by Ripley Entertainment. It is located in downtown Toronto, just southeast of the CN Tower.",
"$$",
*
* */

public class TourListFragment extends Fragment {

    private String [] names;
    private String [] addresses;
    private int [] images;
    public TourListFragment() {

    }
    public TourListFragment(String title[], String addresses[], int imgs[]) {
        this.names = title;
        this.addresses =addresses;
        this.images = imgs;
   }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_tour_list, container , false);
        ListView list = (ListView) view.findViewById(R.id.tour_list);

        //this here needs to be updated to save name, address and photo.
        MyAdapter adapter = new MyAdapter(getContext(), names, addresses, images);
        list.setAdapter(adapter);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,movies);
//        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                Toast.makeText(getContext(),item, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),Navigation.class);
                intent.putExtra("name","tour"+i);
                startActivity(intent);
            }
        });

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
