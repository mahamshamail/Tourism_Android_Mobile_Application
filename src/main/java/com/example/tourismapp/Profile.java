package com.example.tourismapp;

import java.util.ArrayList;

public class Profile {
    private String name;
    private String userName;
    private String password;
    private String wishlist;

    public Profile(String name, String userName, String password, String wishlist) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.wishlist = wishlist;
    }

    public Profile(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
    }
//private ArrayList <String> wishlist

    public Profile(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getWishlist() {
        return wishlist;
    }

    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }


}
