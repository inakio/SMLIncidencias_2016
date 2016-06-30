/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sml.tecnalia.login;

/**
 *
 * @author inaki
 */
public class segment {
    private String road;
    private int iniKm;
    private int endKm;
    public segment(String road, int iniKm, int endKm){
        this.road = road;
        this.iniKm = iniKm;
        this.endKm = endKm;
    }
    public void   setRoad  (String road) { this.road = road;   }
    public void   setIniKm (int iniKm)   { this.iniKm = iniKm; }
    public void   setEndKm (int endKm)   { this.endKm = endKm; }
    
    public String getRoad()  { return this.road;  }
    public int    getIniKm() { return this.iniKm; }
    public int    getEndKm() { return this.endKm; }
    
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
