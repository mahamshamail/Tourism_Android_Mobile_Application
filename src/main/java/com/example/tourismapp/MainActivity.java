package com.example.tourismapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {
    private ArrayList<Profile> profiles ;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private Switch rememberMeSwitch;
    private Intent mainIntent;
    private Intent recIntent;
    private Button loginButton;
    private final String TAG= "(________(TOURISM_PROJECT________))>>>>>><___MAIN__ACTIVITY___> ";
    private SharedPreferences sharedPreferences ; //reference for shared preferences
    private final String SWITCH_PREFERENCE = "SWITCH_PREFERENCE"; //name of the shared preference
    private boolean switchFromSharedPreferences;
    private String  userNameSharedPreference;
    private String  passwordSharedPreference;
    private String name ="";
   // private TextView accountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recIntent= getIntent();
        Log.d(TAG,"At login Screen");
        profiles = new ArrayList<Profile>();
        profiles.add(new Profile("Thanos","thanos@gmail.com","1234"));
        profiles.add(new Profile("Wonder Woman","wonderwoman@yahoo.com","abc00021"));
        profiles.add(new Profile("Jon Snow","jonsnow@winteriscoming.com","gameofthrones2"));
        profiles.add(new Profile("Super Man","superman@kyptom.com","kk11iii"));
        saveProfiles();

        //initialising my reference to shared preferences
        this.sharedPreferences = this.getSharedPreferences(SWITCH_PREFERENCE, Context.MODE_PRIVATE);

        //initialing views
        loginButton = (Button) findViewById(R.id.LoginButton) ;
        userNameEditText = (EditText) findViewById(R.id.EnterUsernameEditText);
        passwordEditText = (EditText)findViewById(R.id.EnterPasswordEditText);
        rememberMeSwitch = (Switch) findViewById(R.id.RememberMeSwitch);
        switchFromSharedPreferences = this.sharedPreferences.getBoolean("Switch", false);
        userNameSharedPreference = this.sharedPreferences.getString("username", "");
        passwordSharedPreference = this.sharedPreferences.getString("password", "");

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordEditText.getText().toString().equals(String.valueOf(s))) {
                    Log.d(TAG, "Text is being updated.!");
                    SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
                    boolean switchChecked = ((Switch) rememberMeSwitch).isChecked();
                    if(switchChecked){
                        Log.d(TAG, "Remember Me Has Been Checked as: "+true+" By The User. Saving as shared preference.");
                        String pass = passwordEditText.getText().toString();
                        preferencesEditor.putString("password", pass);
                        Log.d(TAG, "password set: "+pass);
                    }else{
                        Log.d(TAG, "Remember Me Has Been Unchecked as: "+false+" By The User. Saving as shared preference.");
                        preferencesEditor.putBoolean("Switch",false); //create a key-value pair to store your name
                    }
                    preferencesEditor.apply();
                    Log.d(TAG, "Switch shared preference saved!");
                }else{
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(userNameEditText.getText().toString().equals(String.valueOf(s))) {
                    Log.d(TAG, "Text is being updated.!");

                    //do something
                    SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
                    boolean switchChecked = ((Switch) rememberMeSwitch).isChecked();
                    if(switchChecked){
                        Log.d(TAG, "Remember Me Has Been Checked as: "+true+" By The User. Saving as shared preference.");
                        //save switch and password shared preference for the user profile
                        //saveProfiles();
                        String userName = userNameEditText.getText().toString();
                        //String pass = passwordEditText.getText().toString();
                        //preferencesEditor.putBoolean("Switch",true); //create a key-value pair to store your name
                        preferencesEditor.putString("username", userName);
                        // preferencesEditor.putString("password", pass);
                        //Toast t = Toast.makeText(this, "knjk", Toast.LENGTH_SHORT);
                        Log.d(TAG, "username set: "+userName);
                    }else{
                        Log.d(TAG, "Remember Me Has Been Unchecked as: "+false+" By The User. Saving as shared preference.");
                        //save switch shared preference for the user profile
                        preferencesEditor.putBoolean("Switch",false); //create a key-value pair to store your name
                    }
                    //commit the changes (commit = actually do the save = press the save button)
                    preferencesEditor.apply();

                    Log.d(TAG, "Switch shared preference saved!");
                }else{
                }
            }
        });





        if(switchFromSharedPreferences ){// need to save this in shared preferences
            //load profile
            Log.d(TAG, "Switch is checked onCreate."); //saved but here we move to the user profile
            rememberMeSwitch.setChecked(true);
            //login and go to Tourist attraction list
            if(userNameSharedPreference.length()>0 && passwordSharedPreference.length()>0){
                userNameEditText.setText(userNameSharedPreference);
                passwordEditText.setText(passwordSharedPreference);
                Log.d(TAG, "username loaded: "+userNameSharedPreference+" pass loaded: "+passwordSharedPreference);
                //login

            }
        }else{
            //load login page
            Log.d(TAG, "Switch is unchecked onCreate.");
            rememberMeSwitch.setChecked(false);

        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            //SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Login Button Pressed.");
                //get edit texts and get data from JSON file, match the two and then sign in
                // test the file loading & json conversion functions
                String fileData = loadDataFromFile("test.json");
                if (fileData == null) {
                    Log.d(TAG, "Error loading file");
                    return;
                }
                JSONArray jsonData = convertToJSON(fileData);
                if (jsonData == null) {
                    Log.d(TAG, "Error converting file string data to JSON");
                    return;
                }
                parseJSONData(jsonData);
                //shift to next screen
                mainIntent = new Intent(MainActivity.this, Navigation.class);
                mainIntent.putExtra("name",name);
                startActivity(mainIntent);
            }
        });

        rememberMeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
                boolean switchChecked = ((Switch) rememberMeSwitch).isChecked();
                if(switchChecked){
                    Log.d(TAG, "Remember Me Has Been Checked as: "+true+" By The User. Saving as shared preference.");
                    //save switch and password shared preference for the user profile
                    //saveProfiles();
                    String userName = userNameEditText.getText().toString();
                    String pass = passwordEditText.getText().toString();
                    preferencesEditor.putBoolean("Switch",true); //create a key-value pair to store your name
                    preferencesEditor.putString("username", userName);
                    preferencesEditor.putString("password", pass);
                    //Toast t = Toast.makeText(this, "knjk", Toast.LENGTH_SHORT);
                    Log.d(TAG, "username set: "+userName+" pass set: "+pass);
                }else{
                    Log.d(TAG, "Remember Me Has Been Unchecked as: "+false+" By The User. Saving as shared preference.");
                    //save switch shared preference for the user profile
                    preferencesEditor.putBoolean("Switch",false); //create a key-value pair to store your name
                }
                //commit the changes (commit = actually do the save = press the save button)
                preferencesEditor.apply();
                Log.d(TAG, "Switch shared preference saved!");
            }
        });


    }


    public String loadDataFromFile(String filename) {
        // get the JSON file from the
        // String FILENAME = "person.json";
        String jsonString;
        try {
            // open the file
            // InputStream fileData = this.getAssets().open(filename);
            InputStream fileData = this.openFileInput(filename);
            // get information about the file
            int fileSize = fileData.available();
            // set up a array to store each piece of data in the file
            // the size of the array is the same size as the file
            byte[] buffer = new byte[fileSize];
            // get all the data from the file
            fileData.read(buffer);
            // close the file
            fileData.close();
            // convert the data to json
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
            // 1. try to convert the string to the JSONArray data type
            jsonData = new JSONArray(fileData);
            // 2. if successful return it
            return jsonData;

        }catch (JSONException e) {
            // 2. if conversion fails, then print error message and return
            e.printStackTrace();
            return null;
        }
    }
    public void parseJSONData(JSONArray jsonArray) {
        String username ="";
        String password="";
        boolean c = false;
        Log.d(TAG, "Parsing json: " + jsonArray.toString());
        try {
            // 0. Loop through each object in the array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currObject = jsonArray.getJSONObject(i);
                // 1. Parse out the id, pt, en data from the JSON file
//                int id = currObject.getInt("id");
                name = currObject.getString("name");
                username = currObject.getString("username");
                password = currObject.getString("password");
//
                Log.d(TAG, "Parsed username" + username);
                Log.d(TAG, "Parsed password" + password);
                c = loginCheck(username, password);
                if(c){
                    break;
                }

            }
            if(!c){
                Toast t = Toast.makeText(this, "Login NOT successful", Toast.LENGTH_SHORT);
                t.show();
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

    public void matchProfile(){
        //
    }
    public void saveProfiles() {
        // @TODO: SAVING TO A FILE
        Log.d(TAG, "In save profiles func");
        JSONArray arr = new JSONArray();
        for (int i = 0; i < this.profiles.size(); i++) {
            Profile profile = profiles.get(i);
            JSONObject object = null;
            try {
                object = new JSONObject();
                object.put("name", profile.getName());
                object.put("username", profile.getUserName());
                object.put("password", profile.getPassword());
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

        this.writeToFile("test.json", data);
    }

    public boolean loginCheck(String username, String password){
        boolean login = false;
        if (username.equals(userNameEditText.getText().toString()) && password.equals(passwordEditText.getText().toString())) {
            login= true;
            Toast t = Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT);
            t.show();
            Log.d(TAG, "logged into username: " + username);
            return login;
        }
        return login;
    }

}