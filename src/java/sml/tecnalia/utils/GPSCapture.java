
package sml.tecnalia.utils;

public class GPSCapture {
    int time  = 0;
    Double lat = 0.0;
    Double lon = 0.0;
    int a     = 0;
    int s     = 0;
    int o     = 0;
    int ac    = 0;
    int aa    = 0;
    int h     = 0;
    public GPSCapture(int time, Double lat, Double lon, int a, int s, int o, int ac, int aa, int h){
         this.time = time;
         this.lat  = lat;
         this.lon  = lon;
         this.a    = a;
         this.s    = s;
         this.o    = o;
         this.ac   = ac;
         this.aa   = aa;
         this.h    = h;
    }
    public int getTime(){return this.time;}
    public Double getLat(){return this.lat;}
    public Double getLon(){return this.lon;}
    public int getA(){return this.a;}
    public int getS(){return this.s;}
    public int getO(){return this.o;}
    public int getAC(){return this.ac;}
    public int getAA(){return this.aa;}
    public int getH(){return this.h;}
}
