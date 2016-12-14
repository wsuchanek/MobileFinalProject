package io.sars.maps4;


 /*************************************************************************************************
 * File:   Marker.java
 * Author: Nicole Lane, Will Suchanek
 * Purpose: This object holds all information about the marker currently being set.
 *
 *
 *************************************************************************************************/

public class Marker {

    static final String WINE = "Wine";
    static final String BEER = "Beer";
    static final String SHOP = "Shop";
    static final String BAR = "Bar";
    static final String NOTSELECTED = "None Specified";


    double longitude; //Get latitude and longitude from map activity (user's current location)
    double latitude;
    String ptype; //Type of product; either beer or wine
    String ltype; //Location type; either bar or shop
    String pname; //Product name; string entered by the user
    String lname; //Location name; where the product was purchased
    double price; //The price of the beverage; optional
    String additional; //An extra information about the beverage; optional
    static long rowID; //This marker's rowID in the database


     //Initialize all values to default upon creation
    public Marker(double latIn, double longIn, int rowID){
        this.longitude = longIn;
        this.latitude = latIn;
        this.ptype = "";
        this.ltype = "";
        this.pname = "";
        this.lname = "";
        this.price = -1.00;
        this.additional = this.NOTSELECTED;
        this.rowID = rowID;
    }
    //All getters for data members
    public double getLongitude(){
        return longitude;
    }
    public double getLatitude(){
        return latitude;
    }
    public double getPrice(){
        return price;
    }
    public String getPtype(){
        return ptype;
    }
    public String getLtype(){
        return ltype;
    }
    public String getPname(){
        return pname;
    }
    public String getLname(){
        return lname;
    }
    public String getAdditionalInfo(){
        return additional;
    }
    public long getRowID() { return this.rowID;}


     //All setters for data members
    public void setLongitude(double in){
        longitude = in;
    }
    public void setLatitude(double in){

        latitude = in;
    }
    public void setPrice(double in){
        if(in > 0){
            price = in;
        }
    }
    public void setPtype(String in){

        ptype = in;
    }
    public void setLtype(String in){
        ltype = in;
    }
    public void setPname(String in){
        pname = in;
    }
    public void setLname(String in){
        lname= in;
    }
    public void setAdditionalInfo(String in){
        additional = in;
    }

}
