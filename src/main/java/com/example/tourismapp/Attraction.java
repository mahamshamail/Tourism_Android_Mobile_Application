package com.example.tourismapp;

import android.util.Log;

import java.io.Serializable;

public class Attraction implements Serializable {
   private String name,
           address, phone_no, website , description, pricing;
   private  int  photo1, photo2, photo3 ;
   private boolean inWishList =false;

   public boolean getisInWishList() {
      return inWishList;
   }

   public void setInWishList(boolean inWishList) {
      this.inWishList = inWishList;
   }

   public Attraction(String name, String address, String phone_no, String website, String description, String pricing, int photo1, int photo2, int photo3) {
      this.name = name;
      this.address = address;
      this.phone_no = phone_no;
      this.website = website;
      this.description = description;
      this.pricing = pricing;
      this.photo1 = photo1;
      this.photo2 = photo2;
      this.photo3 = photo3;
      Log.d("______ATTRACTION SAVED__________",toString());
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getPhone_no() {
      return phone_no;
   }

   public void setPhone_no(String phone_no) {
      this.phone_no = phone_no;
   }

   public String getWebsite() {
      return website;
   }

   public void setWebsite(String website) {
      this.website = website;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getPricing() {
      return pricing;
   }

   public void setPricing(String pricing) {
      this.pricing = pricing;
   }

   public int getPhoto1() {
      return photo1;
   }

   public void setPhoto1(int photo1) {
      this.photo1 = photo1;
   }

   public int getPhoto2() {
      return photo2;
   }

   public void setPhoto2(int photo2) {
      this.photo2 = photo2;
   }

   public int getPhoto3() {
      return photo3;
   }

   public void setPhoto3(int photo3) {
      this.photo3 = photo3;
   }

   @Override
   public String toString() {
      return "Attraction{" +
              "name='" + name + '\'' +
              ", address='" + address + '\'' +
              ", phone_no='" + phone_no + '\'' +
              ", website='" + website + '\'' +
              ", description='" + description + '\'' +
              ", pricing='" + pricing + '\'' +
              ", photo1=" + photo1 +
              ", photo2=" + photo2 +
              ", photo3=" + photo3 +
              '}';
   }
}
