/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sml.tecnalia.login;

import java.util.ArrayList;

public class usuarioTecnalia {
    long   id;
    String email;
    int    sendEmail;
    String passwd;
    String name;
    String familyName;
    ArrayList<segment> segments;
    int numSegments = 0;
    
    public usuarioTecnalia(long id){
        this.id = id;
    }
    public void setId            (Long s)         { this.id         = s; }
    public void setName           (String s)      { this.name       = s; }
    public void setFamilyName    (String s)      { this.familyName = s; }
    public void setEmail          (String s)      { this.email = s;  }
    public void setSendEmail       (int s)     { this.sendEmail = s;}
    public void setPasswd         (String s)      { this.passwd     = s; }
    public void setSegments      (ArrayList<segment> s) {
        this.segments = s;
        numSegments=this.segments.size();
    }
    public void addSegment (segment s){
        this.segments.add(s);
        this.numSegments = this.segments.size();
    }
    public void addSegments (ArrayList<segment> segs){
        for (int s=0;s<segs.size();s++){
           this.segments.add(segs.get(s));
        }
        numSegments = this.segments.size();
    }
    public void eraseSegments(){
        this.segments = new ArrayList<segment>();
        numSegments = this.segments.size();
    }
    public long   getId()           { return this.id;   }
    public String getName()         { return this.name; }
    public String getFamilyName()   { return this.familyName; }
    public String getEmail()          { return this.email; }
    public int    getSendEmail()       { return this.sendEmail;}
    public String getPasswd()        { return this.passwd; }
    public boolean checkUser(String iRoad, int iKm){
        boolean check = false;
        for (int s=0;s<this.segments.size();s++){
            segment seg = segments.get(s);
            if (seg.checkSegment(iRoad, iKm)){ check=true; break;}     
        }
        return check;
    }
    
}
