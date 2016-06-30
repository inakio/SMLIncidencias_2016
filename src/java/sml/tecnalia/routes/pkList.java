/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sml.tecnalia.routes;

import java.util.ArrayList;

public class pkList {
    ArrayList<pk> pks = new ArrayList<pk>();
  
    public pkList(){
    }
    public void addPk(pk lpk){
        boolean notInList = true;
        for (int p=0;p<this.pks.size();p++){
            pk clpk = this.pks.get(p);
            if ( clpk.equals(lpk) ){
               notInList = false;
               break;
            }
        }
        if ( notInList ){
            this.pks.add(lpk);
        }
    }
/*
private Double calcDistance(Double lat1, Double lat2, Double lon1, Double lon2){
      lat1 = lat1*Math.PI/180.0;
      lat2 = lat2*Math.PI/180.0;
      lon1 = lon1*Math.PI/180.0;
      lon2 = lon2*Math.PI/180.0;
      Double distance = 0.0;
      if   ( (Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1-lon2) > 1.0 ) &&
             (Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1-lon2) - 1.0 ) > 1.0e-15){
            distance = 1.0;
            System.out.println("calcDistance::: ERROR!!!! distance="+distance);}
      else if ( (Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1-lon2) > 1.0 ) &&
                (Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1-lon2) - 1.0 ) < 1.0e-15){
            distance = 0.0;}
      else if ( Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1-lon2) > 0.999999999999999 ){
            distance = 0.0;}
      else{
            distance = Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1-lon2))*6371.0;}
        //System.out.println("calcDistance::: distance="+distance);
        return distance;
}*/    
static private Double calcDistance(Double lat1, Double lat2, Double lon1, Double lon2){
          lat1 = Math.toRadians(lat1);
          lat2 = Math.toRadians(lat2);
          lon1 = Math.toRadians(lon1);
          lon2 = Math.toRadians(lon2);
          Double distance = 0.0;
          distance = Math.sqrt((lat1-lat2)*(lat1-lat2) + (lon1-lon2)*(lon1-lon2)*Math.cos(lat1)*Math.cos(lat2))*6371.0;
          return distance;
}


    public ArrayList<Double> findCoords(String road, int km){
        ArrayList<Double> coords = new ArrayList<Double>();
        for(int p=0;p<pks.size();p++){
           if ( pks.get(p).getRoad().equals(road) &&
                pks.get(p).getKm() == km ){
                coords.add(pks.get(p).getLat());
                coords.add(pks.get(p).getLon());
                break;
           }
        }
        return coords;
    }
    
    
    public String printOut(){
        String out = "";
        for (int i=0;i<this.pks.size();i++){
            pk lpk = this.pks.get(i);
            out = out + i+":"+lpk.getRoad()+" km="+lpk.getKm()+"\n";
        }
        return out;
    }
                   
            
    public pk checkIfPkInList(ArrayList<Double> node){
        Double thres = 100.0; //metros
        Double lat = node.get(0);
        Double lon = node.get(1);
        pk rpk   = null;
        pk minPk = null;
        Double minDist=9999999.9;
        
        boolean itIsApk = false;
        for (int p=0;p<this.pks.size();p++){
            pk cpk = this.pks.get(p);
            Double dist = calcDistance(cpk.getLat(), lat, cpk.getLon(), lon);
            if ( dist < minDist ){
               minDist = dist;
               minPk   = cpk;
            }
        }
        System.out.println("minDist="+minDist);
        if ( minDist < thres ){
            itIsApk = true;
            rpk = minPk;
        }
        return rpk;
    }
    /*
    public ArrayList<pk> checkIfPkInListType1(ArrayList<Double> nodeIni, ArrayList<Double> nodeEnd){
        Double thres = 0.03; //kilometros
        ArrayList<pk> alpk = new ArrayList<pk>();

        pk minPk = null;
        Double minDist=9999999.9;
        Double latIni = nodeIni.get(0);
        Double lonIni = nodeIni.get(1);
        Double latEnd = nodeEnd.get(0);
        Double lonEnd = nodeEnd.get(1);
        Double TDist = calcDistance(latIni,latEnd,lonIni,lonEnd);
        int N = 2 * (int) Math.round(TDist/thres);
        
        Double lat, lon, delta, dist;
        
        if ( Math.abs(latIni-latEnd) < 1.0e-14 ){
            delta = (lonEnd-lonIni) / (N-1);
            lat   = latIni;
            for (int i=0;i<N;i++){
                lon = lonIni + i*delta;
                minPk   = null;
                minDist = 9999999.9;
                for (int p=0;p<this.pks.size();p++){
                    pk cpk = this.pks.get(p);
                    dist = calcDistance(lat,cpk.getLat(),lon,cpk.getLon());
                    if ( dist < minDist ){
                       minDist = dist;
                       minPk   = cpk;
                    }
                }
                if (minDist < thres){
                    alpk.add(minPk);
                }
            }
        }
        else{
            delta = (latEnd-latIni) / (N-1);
            for (int i=0;i<N;i++){
                lat = latIni + i*delta;
                lon = lonIni + i*delta*(lonEnd-lonIni)/(latEnd-latIni);
                minPk   = null;
                minDist = 9999999.9;
                for (int p=0;p<this.pks.size();p++){
                    pk cpk = this.pks.get(p);
                    dist = calcDistance(lat,cpk.getLat(),lon,cpk.getLon());
                    if ( dist < minDist ){
                       minDist = dist;
                       minPk   = cpk;
                    }
                }
                if (minDist < thres){
                    alpk.add(minPk);
                }
            }       
        }
        return alpk;
    }*/
 public ArrayList<pk> checkIfPkInListType1(ArrayList<Double> nodeIni, ArrayList<Double> nodeEnd){
        Double thres = 0.03; //kilometros
        ArrayList<pk> alpk = new ArrayList<pk>();
        Double latThres = 0.0005;
        Double lonThres = 0.0005;
        pk minPk = null;
        Double minDist=9999999.9;
        Double latIni = nodeIni.get(0);
        Double lonIni = nodeIni.get(1);
        Double latEnd = nodeEnd.get(0);
        Double lonEnd = nodeEnd.get(1);
        Double TDist = calcDistance(latIni,latEnd,lonIni,lonEnd);
        //System.out.println(calcDistance(latIni,latIni,lonIni,lonIni+latThres)+"");
        int N = 2 * (int) Math.round(TDist/thres);
       
        Double lat, lon, delta, dist;
       
        if ( Math.abs(latIni-latEnd) < 1.0e-14 ){
            delta = (lonEnd-lonIni) / (N-1);
            lat   = latIni;
            for (int i=0;i<N;i++){
                lon = lonIni + i*delta;
                minPk   = null;
                minDist = 9999999.9;
                for (int p=0;p<this.pks.size();p++){
                    pk cpk = this.pks.get(p);
                    if (Math.abs(lat -cpk.getLat()) > latThres || Math.abs(lon -cpk.getLon()) > lonThres){continue;}
                    dist = calcDistance(lat,cpk.getLat(),lon,cpk.getLon());
                    if ( dist < minDist ){
                       minDist = dist;
                       minPk   = cpk;
                    }
                }
                if (minDist < thres){
                    alpk.add(minPk);
                }
            }
        }
        else{
            delta = (latEnd-latIni) / (N-1);
            for (int i=0;i<N;i++){
                lat = latIni + i*delta;
                lon = lonIni + i*delta*(lonEnd-lonIni)/(latEnd-latIni);
                minPk   = null;
                minDist = 9999999.9;
                for (int p=0;p<this.pks.size();p++){
                    pk cpk = this.pks.get(p);
                    if (Math.abs(lat - cpk.getLat()) > latThres || Math.abs(lon - cpk.getLon()) > lonThres){ continue; }
                    dist = calcDistance(lat,cpk.getLat(),lon,cpk.getLon());
                    if ( dist < minDist ){
                       minDist = dist;
                       minPk   = cpk;
                    }
                }
                if (minDist < thres){
                    alpk.add(minPk);
                }
            }      
        }
        return alpk;
    }    
    
    
    
    
    private Double distanceToLine(ArrayList<Double> node,ArrayList<Double> nodeIni, ArrayList<Double> nodeEnd){
       Double distance;
       Double mlon, mlat;
       Double lat = node.get(0);
       Double lon = node.get(1);
       Double latIni = nodeIni.get(0);
       Double lonIni = nodeIni.get(1);
       Double latEnd = nodeEnd.get(0);
       Double lonEnd = nodeEnd.get(1);
       Double minlon = Math.min(lonIni, lonEnd);
       Double maxlon = Math.max(lonIni, lonEnd);
       if ( Math.abs(lonEnd-lonIni) < 1.0d-14){
           mlon = lonIni;
           mlat = lat;
       }
       else{
           mlon = calc_aprox(lat,lon,latIni,lonIni,latEnd,lonEnd);
           if      ( mlon < minlon){mlon = minlon;}
           else if ( mlon > maxlon){mlon = maxlon;}
           mlat = calc_gc_lat(mlon,latIni,lonIni,latEnd,lonEnd);
       }
       distance = calcDistance(lat,mlat,lon,mlon);
       //System.out.println("D[<"+lat+","+lon+">  -- < "+mlat+","+mlon+">] = "+distance);

       return distance;
   }
   private Double calc_gc_lat(Double lon, Double lat1, Double lon1, Double lat2, Double lon2){
     Double lpi   = Math.PI;
     Double lat;
     Double llat;
     Double llon  = lon *lpi/180.0e0;
     Double llat1 = lat1*lpi/180.0e0;
     Double llat2 = lat2*lpi/180.0e0;
     Double llon1 = lon1*lpi/180.0e0;
     Double llon2 = lon2*lpi/180.0e0;
     if (lon1 != lon2){
        llat = llat1 + (llon-llon1)*(llat2-llat1)/(llon2-llon1);
        lat = llat*180.0e0/lpi;
        //System.out.println("calc_gc_lat:::lat="+lat);
     }
     else lat = -9999.9999e09;
     return lat;
}
   private Double calc_aprox(Double lat, Double lon, Double lat1, Double lon1, Double lat2, Double lon2){
       Double mlon = 0.0;
       Double cienoch = 180.0;
       Double lpi   = Math.PI;
       Double llat  = lat  * lpi / cienoch;
       Double llon  = lon  * lpi / cienoch;
       Double llat1 = lat1 * lpi / cienoch;
       Double llon1 = lon1 * lpi / cienoch;
       Double llat2 = lat2 * lpi / cienoch;
       Double llon2 = lon2 * lpi / cienoch;
       Double Dlat  = llat2-llat1;
       Double Dlon  = llon2-llon1;
       Double mllon;
       if ( Math.abs(Dlon) < 1.0e-14 ){
          System.out.println("calc_aprox:::Dlon is TOO SMALL");
          mllon = llon1;
       }
       else{
         Double x     = Dlat/Dlon;
         //System.out.println("calc_aprox:::x="+x); 
         Double coslat = Math.cos(llat);
         Double div = 1.0e0/(Math.cos(llat)*Math.cos(llat)+x*x);
         Double num = (llon*Math.cos(llat)*Math.cos(llat)+x*(llat-llat1)+llon1*x*x);
         Double num1 = llon*Math.cos(llat)*Math.cos(llat);
         Double num2 = x*(llat-llat1);
         Double num3 = llon1*x*x; 
         mllon = num*div;
       }
       mlon = mllon*cienoch/lpi;
       return mlon;
   }
    
    
    
}
