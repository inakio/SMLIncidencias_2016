/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sml.tecnalia.routes;

/**
 *
 * @author inaki
 */
public class segment {
    private String road;
    private int iniKm;
    private int endKm;
    private int iniTime;
    private int endTime;
    public segment(String road, int iniKm, int endKm){
        this.road = road;
        this.iniKm = iniKm;
        this.endKm = endKm;
    }
    public segment(String road, int iniKm, int endKm, int iniTime, int endTime){
        this.road = road;
        this.iniKm = iniKm;
        this.endKm = endKm;
        this.iniTime = iniTime;
        this.endTime = endTime;
    }
    
    public void   setRoad  (String road) { this.road = road;   }
    public void   setIniKm (int iniKm)   { this.iniKm = iniKm; }
    public void   setEndKm (int endKm)   { this.endKm = endKm; }
    public void   iniTime  (int iniTime) { this.iniTime = iniTime; }
    public void   endTime  (int endTime) { this.endTime = endTime; }
    
    public String getRoad()  { return this.road;  }
    public int    getIniKm() { return this.iniKm; }
    public int    getEndKm() { return this.endKm; }
    public int    getIniTime() { return this.iniTime; }
    public int    getEndTime() { return this.endTime; }

    
    public boolean checkSegment(String iRoad, int iKm){
        boolean check = false;  
        if ( iRoad.equals(this.road)  &&
             (iKm >= this.iniKm)      &&
             (iKm <= this.endKm)
           ){
            check = true;
        }
        return check;
    }
}
