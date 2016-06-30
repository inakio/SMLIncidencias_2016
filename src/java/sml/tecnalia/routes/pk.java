/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sml.tecnalia.routes;
public class pk {
    String road;
    int km;
    double lat;
    double lon;
    public pk(String road, int km, double lat, double lon){
        if ( road.equals("N-I")) road = "N-1";
        this.road = road;
        this.km = km;
        this.lat = lat;
        this.lon = lon;
    }
    public String getRoad(){return this.road;}
    public int getKm(){return this.km;}
    public double getLat(){return this.lat;}
    public double getLon(){return this.lon;}
    public void setRoad(String road){this.road = road;}
    public void setKm(int km){this.km = km;}
    public void setLat(double lat){this.lat = lat;}
    public void setLon(double lon){this.lon = lon;}
}
