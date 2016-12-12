package io.sars.maps4;

/**
 * Created by willsuchanek on 12/10/16.
 */
public class Marker {

    static final String WINE = "Wine";
    static final String BEER = "Beer";
    static final String SHOP = "Shop";
    static final String BAR = "Bar";


    double longitude;
    double latitude;
    String ptype;
    String ltype;
    String pname;
    String lname;
    double price;
    String additional;

    public Marker(double latIn, double longIn){
        this.longitude = latIn;
        this.latitude = longIn;
        this.ptype = "";
        this.ltype = "";
        this.pname = "";
        this.lname = "";
        this.price = 0;
        this.additional = "";
    }

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

    public void setLongitude(double in){
        longitude = in;
    }
    public void setLatitude(double in){

        latitude = in;
    }
    public void setPrice(double in){
        price = in;
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
