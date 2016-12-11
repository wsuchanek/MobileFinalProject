package io.sars.maps4;

/**
 * Created by willsuchanek on 12/10/16.
 */
public class Marker {
    double longitude;
    double latitude;
    String ptype;
    String ltype;
    String pname;
    String lname;
    double price;
    String additional;

    public Marker(double lonIn, double latIn, String ptypeIn, String ltypeIn, String pnameIn, String lnameIn, double priceIn,String addIn){
        this.longitude = lonIn;
        this.latitude = latIn;
        this.ptype = ptypeIn;
        this.ltype = ltypeIn;
        this.pname = pnameIn;
        this.lname = lnameIn;
        this.price = priceIn;
        this.additional = addIn;
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

}
