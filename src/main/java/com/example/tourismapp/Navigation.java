package com.example.tourismapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
public class Navigation extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, Serializable {
    private String TAG ="_________NAV_CLASS_______";
    private String name ;
    private String uName;
    private TextView tv;
    private RatingBar ratingBar;
    private Intent navRecIntent;
    private Attraction attraction;
    private  ArrayList <Attraction> attractions ;
    private final int numOfAttractions =5;
    private String[] names = new String[numOfAttractions];
    private String[] addresses = new String[numOfAttractions];;
    private int[] images = new int[numOfAttractions];;
    private SharedPreferences sharedPreferences ;
    private final String WISH_LIST_PREFERENCE = "WISH_LIST_PREFERENCE";
    private final String RATING_PREFERENCE = "RATING_PREFERENCE";
    private String numberInWishListSharedPreferences;
    private boolean addToWL= true;
    private float ratingFromSharedPreferences;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Log.d(TAG, "In nav class");

        //shared preferences for rating bar
        this.sharedPreferences = this.getSharedPreferences(RATING_PREFERENCE, Context.MODE_PRIVATE);
        ratingBar = (RatingBar) findViewById(R.id.rating) ;
        ratingFromSharedPreferences = this.sharedPreferences.getFloat("rating", 0);
        // ratingBar.setRating(ratingFromSharedPreferences);

        //initialising arryalists of attractions
        attractions = new ArrayList<Attraction>();
        wishListAttractions = new ArrayList<Attraction>();

        //switch preferences for wish list
        this.sharedPreferences = this.getSharedPreferences(WISH_LIST_PREFERENCE, Context.MODE_PRIVATE);
        numberInWishListSharedPreferences = this.sharedPreferences.getString("wish", "nothing"); //gets SP


        String fileData = loadDataFromFile("attractions.json");
        if (fileData == null) {
            Log.d(TAG, "Error reading data from file");
            return;
        }
        JSONArray o = convertToJSON(fileData);
        if (o == null) {
            Log.d(TAG, "Error converting to JSON");
            return;
        }
        parseJSONData(o);

        //intents and data passing
        navRecIntent = getIntent();
        name = navRecIntent.getStringExtra("name");
         if(name==null ){
             tv = (TextView) findViewById(R.id.WelcomeMessage);
             tv.setText("Attraction Details");
             getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new AttractionDisplay(0, attractions, 0)).commit();
         }
         else if (name.contains("tour")){
             int in = Integer.parseInt(name.substring(4,5));
             Log.d("__________INTEGER PARSED:__________",""+in);
             tv = (TextView) findViewById(R.id.WelcomeMessage);
             tv.setText("Attraction Details");
             uName = tv.getText().toString();
             getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new AttractionDisplay(1, attractions, in)).commit();
         }
         else if (name.contains("wish")){
             int in = Integer.parseInt(name.substring(4,5));
             Log.d("__________INTEGER PARSED:__________",""+in);
             tv = (TextView) findViewById(R.id.WelcomeMessage);
             tv.setText("      Attraction Details\n  (Item is in your wish-list)");
             tv.setTextSize(15);
             uName = tv.getText().toString();
             getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new AttractionDisplay(2, attractions, in)).commit();
         }else if (name.equals("return to t")){
             Log.d(TAG,"in tour 1");
             tv = (TextView) findViewById(R.id.WelcomeMessage);
             tv.setText("Tourist Attraction List");
             uName = tv.getText().toString();
             getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new TourListFragment(names,addresses,images)).commit();

         }else if (name.equals("return to w")){
             Log.d(TAG,"in wish 1");
             tv = (TextView) findViewById(R.id.WelcomeMessage);
             uName = tv.getText().toString();
             if(wishListAttractions.size()==0 && numberInWishListSharedPreferences.equals("nothing")){
                 tv.setText("Your Wish Is Empty!");
                 getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new WishListFragment(wishListAttractions)).commit();
             }
             else{
                 tv.setText("Your Wish List");
                 getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new WishListFragment(wishListAttractions)).commit();
             }
         }
         else{
             tv = (TextView) findViewById(R.id.WelcomeMessage);
             tv.setText("      Welcome " + name + "!\nExplore Attractions in Toronto:");
             uName = tv.getText().toString();
             getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new TourListFragment(names,addresses,images)).commit();
         }

        //Navigation code
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();
        NavigationView navigationView = (NavigationView)
                findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        }


        //loading wish list
        if(!numberInWishListSharedPreferences.equals("nothing") ){
            if(wishListAttractions.size()>0) {
                int j =0 ;
                for (int k = 0; k < wishListAttractions.size(); k++) {
                     j = Integer.parseInt(numberInWishListSharedPreferences.substring(k,k+1));
                        if(wishListAttractions.get(k).getName().equals(attractions.get(j).getName())) {
                            addToWL = false;
                            break;
                        }
                }
                if(addToWL){
                    wishListAttractions.add(attractions.get(j));
                }
            }
            else {
                if(addToWL) {
                    for (int i = 0; i < numberInWishListSharedPreferences.length(); i++) {
                        int j = Integer.parseInt(numberInWishListSharedPreferences.substring(i, i + 1));
                        wishListAttractions.add(attractions.get(j));
//                        Log.d("_____ADD 2 WISH_______", "numberInWishListSharedPreferences.substring(i,i+1)" + numberInWishListSharedPreferences.substring(i, i + 1));
//                        Log.d("_____ADD 2 WISH_______", "I: " + i);
//                        Log.d("______ADD 2 WISH___Attraction details__________", attractions.get(i).toString());
//                        Log.d("______ADD 2 WISH___Wish details__________", wishListAttractions.get(wishListAttractions.size() - 1).toString());
                        //Toast.makeText(getApplicationContext(),attractions.get(j).getName()+" added to wish-list!", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_tour_list) {
            tv = (TextView) findViewById(R.id.WelcomeMessage);
            tv.setText("Tourist Attraction List\n");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new TourListFragment(names,addresses,images)).commit();
        }
        else if(id==R.id.nav_Map) {
            tv = (TextView) findViewById(R.id.WelcomeMessage);
            tv.setText("Navigate to your destination:\n");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MapFragment()).commit();
        }
        else if(id==R.id.nav_logout_button) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new LogoutFragment()).commit();
        }
        else if(id==R.id.nav_wish_list) {
            tv = (TextView) findViewById(R.id.WelcomeMessage);
            if(wishListAttractions.size()==0){
                tv.setText("Your Wish Is Empty!\n");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new WishListFragment(wishListAttractions)).commit();
            }
            else {
                tv.setText("Your Wish List\n");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new WishListFragment(wishListAttractions)).commit();
            }
        }
        return false;
    }

    public String loadDataFromFile(String filename) {
        String jsonString;
        try {
             InputStream fileData = this.getAssets().open(filename);
            int fileSize = fileData.available();
            byte[] buffer = new byte[fileSize];
            fileData.read(buffer);
            fileData.close();
            jsonString = new String(buffer, "UTF-8");
            return jsonString;
        } catch (IOException e) {
            Log.d(TAG,"Error opening file.");
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray convertToJSON(String fileData) {
        JSONArray jsonData;
        try {
            jsonData = new JSONArray(fileData);
            return jsonData;

        }catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void parseJSONData(JSONArray jsonArray) {

        boolean c = false;
        Log.d(TAG, "Parsing json: " + jsonArray.toString());
        try {
            // 0. Loop through each object in the array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currObject = jsonArray.getJSONObject(i);
                // 1. Parse out the id, pt, en data from the JSON file
                String name1 = currObject.getString("name");
                String address= currObject.getString("address");
                String phone_no= currObject.getString("phone_no");
                String website=currObject.getString("website");
                String description=currObject.getString("description");
                String pricing=currObject.getString("pricing");
                int photo1 =currObject.getInt("photo1");
                int photo2 =currObject.getInt("photo2");
                int photo3 =currObject.getInt("photo3");
                names[i]=name1;
                images[i]=photo1;
                addresses[i]=address;
                Log.d("names[i]: ", names[i]);
                Log.d("images[i]: ",""+images[i]);
                Log.d("addresses[i]: ",addresses[i]);
                Attraction a = new Attraction(
                        name1,
                        address,
                        phone_no,
                        website,
                        description,
                        pricing,
                        photo1,
                        photo2,
                        photo3);
                attractions.add(a);
                Log.d(TAG, "Parsed: " +
                        name1+" "+
                        address+" "+
                        phone_no+" "+
                        website+" "+
                        description+" "+
                        pricing+" "+
                        photo1+" "+
                        photo2+" "+
                        photo3);

            }


        }
        catch(JSONException e) {
            e.printStackTrace();
            return;
        }

    }

    // 0. Give the function a file name & the information you want to save to that file
    private void writeToFile(String filename, String data) {
        Log.d(TAG, "In writeToFile profiles func");
        try {
            OutputStreamWriter outputStreamWriter
                    = new OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE));

            // DEBUG: If you wnat to see the path where the file is stored, you can do it
            Log.d(TAG, "File is saved: " + this.getFilesDir().getAbsolutePath());

            // 2. Put the data into the file
            outputStreamWriter.write(data);

            // 3. Save the file
            outputStreamWriter.close();

            // 4. Output a success message
            Log.d(TAG, "File written");
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void saveAttractions() {
        // @TODO: SAVING TO A FILE
        Log.d(TAG, "In save profiles func");
        JSONArray arr = new JSONArray();
        for (int i = 0; i < 5; i++) {
            Attraction attraction = this.attractions.get(i);
            JSONObject object = null;
            try {

                object = new JSONObject();
                object.put("name", attraction.getName());
                object.put("address", attraction.getAddress());
                object.put("phone_no", attraction.getPhone_no());
                object.put("website", attraction.getWebsite());
                object.put("description", attraction.getDescription());
                object.put("pricing", attraction.getPricing());
                object.put("photo1", attraction.getPhoto1());
                object.put("photo2", attraction.getPhoto2());
                object.put("photo3", attraction.getPhoto3());
                // 5d. Add that object to the JSON array
                arr.put(i, object);
            }
            catch (Exception e) {
                Log.d(TAG, "Failed to create JSON object");
                e.printStackTrace();
            }

        }
        //        // 6. Write the array to the file
//
        String data = arr.toString();
        Log.d(TAG, "Data: " + data);

        this.writeToFile("attractions.json", data);
    }

    public float getRatingSP(){
        ratingFromSharedPreferences = this.sharedPreferences.getFloat("rating", 0);
        return ratingFromSharedPreferences;

    }
    public void setRatingSP(float rating){
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putFloat("rating",rating);
        preferencesEditor.apply();
    }

    public ArrayList<Attraction> getAttractions() {
        return attractions;
    }

    private static ArrayList<Attraction> wishListAttractions ;

    public ArrayList<Attraction> getWishListAttractions() {
        return wishListAttractions;
    }

    public int getWLSize(){
        return wishListAttractions.size();
    }
    public void setWishListAttractions(ArrayList<Attraction> wishListAttractions) {
        this.wishListAttractions = wishListAttractions;
    }

    public void setSP(String n){
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putString("wish", n );
        preferencesEditor.apply();
        String SP3 = this.sharedPreferences.getString("wish", "nothing");
        Log.d(TAG,"SP3: "+SP3);

    }
    public void addToSP(String sp){
        String SP2 =  this.sharedPreferences.getString("wish", "nothing");;
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();

        if(!SP2.equals("nothing")) {
            if(SP2.contains(sp)){
                addToWL = false;
                Toast.makeText(getApplicationContext(),"Item is already\nin your wish list!",Toast.LENGTH_LONG).show();
            }
            else {
                preferencesEditor.putString("wish", SP2 + sp); //create a key-value pair to store your name
                preferencesEditor.apply();
                String SP3 = this.sharedPreferences.getString("wish", "nothing");
                Log.d(TAG, "SP3: " + SP3);

            }
        }
        else {
            if(SP2.contains(sp)){
                addToWL = false;
                Toast.makeText(getApplicationContext(),"Item is already\nin your wish list!",Toast.LENGTH_LONG).show();
            }
            else {
                preferencesEditor.putString("wish", sp);
                preferencesEditor.apply();
                String SP3 = this.sharedPreferences.getString("wish", "nothing");
                Log.d(TAG,"SP3: "+SP3);
            }

        }


    }
    public void addToWishList(Attraction a) {
        wishListAttractions.add(a);
    }


}