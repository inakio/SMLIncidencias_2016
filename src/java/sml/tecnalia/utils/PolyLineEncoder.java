
package sml.tecnalia.utils;

import java.util.ArrayList;

public class PolyLineEncoder {
private static Double precision = 1.0E5;
private static StringBuffer encodeSignedNumber(int num) {
        int sgn_num = num << 1;
        if (num < 0) {
            sgn_num = ~(sgn_num);
        }
        return(encodeNumber(sgn_num));
    }

    private static StringBuffer encodeNumber(int num) {
        StringBuffer encodeString = new StringBuffer();
        while (num >= 0x20) {
                int nextValue = (0x20 | (num & 0x1f)) + 63;
                encodeString.append((char)(nextValue));
            num >>= 5;
        }
        num += 63;
        encodeString.append((char)(num));
        return encodeString;
    }
   
    /**
     * Encode a polyline with Google polyline encoding method
     * @param polyline the polyline
     * @param precision 1 for a 6 digits encoding, 10 for a 5 digits encoding.
     * @return the encoded polyline, as a String
     */
    public static String encode(ArrayList<ArrayList<Double>> polyline) {
                StringBuffer encodedPoints = new StringBuffer();
                int prev_lat = 0, prev_lng = 0;
                for (ArrayList<Double> trackpoint:polyline) {
                        int lat = (int) Math.round(trackpoint.get(0)*precision);
                        int lng = (int) Math.round(trackpoint.get(1)*precision);
                        encodedPoints.append(encodeSignedNumber(lat - prev_lat));
                        encodedPoints.append(encodeSignedNumber(lng - prev_lng));                      
                        prev_lat = lat;
                        prev_lng = lng;
                }
                return encodedPoints.toString();
        }
   
    /**
     * Decode a "Google-encoded" polyline
     * @param encodedString
     * @param precision 1 for a 6 digits encoding, 10 for a 5 digits encoding.
     * @return the polyline.
     */
    public static ArrayList<ArrayList<Double>> decode(String encodedString) {
        ArrayList<ArrayList<Double>> polyline = new ArrayList<ArrayList<Double>>();
        int index = 0;
        int len = encodedString.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            ArrayList<Double> pos = new ArrayList<Double>();
            pos.add((Double) (lat/precision));
            pos.add((Double) (lng/precision));
            polyline.add(pos);
        }

        return polyline;
    }

    public static ArrayList<GPSCapture> decodeExtPolyline(String encodedString) {
        ArrayList<GPSCapture> extPolyline = new ArrayList<GPSCapture>();
        int index = 0;
        int time  = 0;
        int a     = 0;
        int s     = 0;
        int o     = 0;
        int ac    = 0;
        int aa    = 0;
        int h     = 0;
        int len = encodedString.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dtime = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            time += dtime;
            //////////////////////////////////////////////////////////////////
            shift  = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            //////////////////////////////////////////////////////////////////
            shift  = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            //////////////////////////////////////////////////////////////////
            shift  = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int da = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            a += da;
            //////////////////////////////////////////////////////////////////
            shift  = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int ds = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            s += ds;            
            //////////////////////////////////////////////////////////////////
            shift  = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int d_o = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            o += d_o;                 
            //////////////////////////////////////////////////////////////////
            shift  = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dac = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            ac += dac;
            //////////////////////////////////////////////////////////////////
            shift  = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int daa = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            aa += daa;
            //////////////////////////////////////////////////////////////////
            shift  = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dh = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            h += dh; 
            ArrayList<GPSCapture> pos = new ArrayList<GPSCapture>();
            GPSCapture cap = new GPSCapture(time,lat/precision,lng/precision,a,s,o,ac,aa,h);

            extPolyline.add(cap);
        }
        return extPolyline;
    }

    /* ESTAS FUNCIONES NO LAS NECESITARIAMOS PARA NUESTRAS APLICACIONES 
    private static StringBuffer encodePoint(double plat, double plng, double lat, double lng) 
    {
	int late5  = (int) Math.round(lat  * precision);
        int plate5 = (int) Math.round(plat * precision);
	int lnge5  = (int) Math.round(lng  * precision);
        int plnge5 = (int) Math.round(plng * precision);
	int dlng = lnge5 - plnge5;
	int dlat = late5 - plate5;
        return encodeSignedNumber(dlat).append(encodeSignedNumber(dlng));
    }
    
    private static StringBuffer encodepNumNum(int pnum, int num){
         int dnum = num - pnum;
        return encodeSignedNumber(dnum);
    }
    
    public static String encodeExtPolyline(ArrayList<GPSCapture> vals) {
	
	int pt  = 0;
	double pla = 0;
	double plo = 0;
	int pa  = 0;
	int ps  = 0;
	int po  = 0;
	int pac = 0;
	int paa = 0;
	int ph  = 0;
	String encoded_points = "";
        
	for(int i = 0; i < vals.size(); ++i) {
            GPSCapture gpsCap = vals.get(i);
            
            int t   =  gpsCap.getTime();
            double la  = gpsCap.getLat();
            double lo  = gpsCap.getLon();
            int a   = gpsCap.getA();
            int s   = gpsCap.getS();
            int o   = gpsCap.getO();
            int ac  = gpsCap.getAC();
            int aa  = gpsCap.getAA();
            int h   = gpsCap.getH();

            encoded_points += encodepNumNum(pt,t);
	    encoded_points += encodePoint(pla, plo, la, lo);
            encoded_points += encodepNumNum(pa,a);
            encoded_points += encodepNumNum(ps,s);
            encoded_points += encodepNumNum(po,o);
            encoded_points += encodepNumNum(pac,ac);
            encoded_points += encodepNumNum(paa,aa);
            encoded_points += encodepNumNum(ph,h);
            pt  = t;
	    pla = la;
	    plo = lo;
            pa  = a;
            ps  = s;
            po  = o;
            pac = ac;
            paa = aa;
            ph  = h;
	}
	return encoded_points;
    }
    */
}
