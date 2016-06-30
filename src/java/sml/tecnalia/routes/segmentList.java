/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sml.tecnalia.routes;

import java.util.ArrayList;

/**
 *
 * @author inaki
 */
public class segmentList {
    ArrayList<segment> segments = new ArrayList<segment>();
    public segmentList(){
    }
    
   public void addPk(pk lpk){
       String lroad = lpk.getRoad();
       int lkm = lpk.getKm();
       boolean newSegment = true;
       System.out.println("adding pk="+lpk.getRoad()+" km="+lpk.getKm());
       for (int s=0;s<this.segments.size();s++){
           segment cseg = this.segments.get(s);
           if ( lroad.equals(cseg.getRoad()) ){
               if ( lpk.getKm() <= cseg.getEndKm() &&  
                    lpk.getKm() >= cseg.getIniKm() )
               {
                   System.out.println(" --> should not add anything");
                   newSegment = false;
                   break;
               }
               if ( cseg.getEndKm()+1 == lpk.getKm() ||
                    cseg.getEndKm()+2 == lpk.getKm() )
               {
                   System.out.println(" --> should change segment endKim");
                   cseg.setEndKm(lpk.getKm());
                   newSegment = false;
                   break;
               }
               else if ( cseg.getIniKm()-1 == lpk.getKm() ||
                         cseg.getIniKm()-2 == lpk.getKm() )
               {
                   System.out.println(" --> should change segment iniKim");
                   cseg.setIniKm(lpk.getKm());
                   newSegment = false;
                   break;               
               }
           }
       }
       if ( newSegment ){
           System.out.println("should add a new segment");
           segment aseg = new segment(lroad, lkm, lkm);
           this.segments.add(aseg);
       }
       System.out.println(". . . . . . . . . . . . ");
       System.out.println(this.printOut());
       System.out.println(". . . . . . . . . . . . ");
    }
    public void segment(segment lseg){
        this.segments.add(lseg);
    }
    public void clean(){
        ArrayList<ArrayList<Integer>> combinarSegs = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> borrarSegsIndxs = new ArrayList<Integer>();
        for (int s1=0;s1<this.segments.size();s1++){
            segment seg1 = segments.get(s1);
            for (int s2=0;s2<s1;s2++){
                segment seg2 = segments.get(s2);
                if ( seg1.getRoad().equals(seg2.getRoad()) ){
                   if ( seg2.getIniKm() > seg1.getIniKm() ){
                     if ( seg2.getEndKm() < seg1.getEndKm()){
                         borrarSegsIndxs.add(s2);
                     }
                     else if ( seg2.getIniKm() <= seg1.getEndKm() ||
                               seg2.getIniKm() == seg1.getEndKm()+1 ){
                         ArrayList<Integer> segs = new ArrayList<Integer>();
                         segs.add(s1);
                         segs.add(s2);
                         combinarSegs.add(segs);
                     }

                   }
                   else if ( seg1.getIniKm() > seg2.getIniKm() ){
                     if ( seg1.getEndKm() < seg2.getEndKm()){
                         borrarSegsIndxs.add(s1);
                     }
                     else if ( seg1.getIniKm() <= seg2.getEndKm() ||
                               seg1.getIniKm() == seg2.getEndKm()+1 ){
                         ArrayList<Integer> segs = new ArrayList<Integer>();
                         segs.add(s2);
                         segs.add(s1);
                         combinarSegs.add(segs);
                     }

                   }
                   else if ( seg1.getIniKm() == seg2.getIniKm() ){
                      if ( seg2.getEndKm() <= seg1.getEndKm()){
                         borrarSegsIndxs.add(s2);
                      }
                      else{
                         ArrayList<Integer> segs = new ArrayList<Integer>();
                         segs.add(s1);
                         segs.add(s2);
                         combinarSegs.add(segs);
                      }
                   }
                }   
            }
        }
        for(int i=0; i<combinarSegs.size();i++){
           ArrayList<Integer> ss = combinarSegs.get(i);
           int indxSeg1 = ss.get(0);
           int indxSeg2 = ss.get(1);
           this.segments.get(indxSeg1).setEndKm(this.segments.get(indxSeg2).getEndKm());
           borrarSegsIndxs.add(indxSeg2);
        }
        borrarSegments(borrarSegsIndxs);
    }
    
    public void borrarSegments(ArrayList<Integer> borrarSegsIndxs ){
        ArrayList<segment> newSegments = new ArrayList<segment>();
        for(int i=0; i<this.segments.size();i++){
           if ( borrarSegsIndxs.contains(i)){}
           else{
              newSegments.add(this.segments.get(i));
           }
        }
        System.out.println("segmentos borrados="+(this.segments.size()-newSegments.size()));
        this.segments = newSegments;     
    }
    
    public String printOut(){
        String out = "";
        for (int i=0;i<this.segments.size();i++){
            segment lseg = this.segments.get(i);
            out = out + i+":"+lseg.getRoad()+" ["+lseg.getIniKm()+"-"+lseg.getEndKm()+"]\n";
        }
        return out;  
    }
    
}
