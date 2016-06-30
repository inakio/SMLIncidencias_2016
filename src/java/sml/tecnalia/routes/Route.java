/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sml.tecnalia.routes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import sml.tecnalia.db.MySQLConnection;


public class Route {
    String name;
    long trayectoriaId;
    String routeName;
    ArrayList<ArrayList<Double>> route;
    String Sroute;
    pkList allPKs   = new pkList();
    pkList routePKs = new pkList();
    segmentList routeSegments = new segmentList();
    int findPksType = 1;

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int iniTime;
    int endTime;
    int diasAviso;
    String puntos;
    String dbUser = "root";
    String dbPass = "mysql";

   public Route(NewRoute nRoute){
        this.name      = nRoute.getName();
        this.route     = new ArrayList<ArrayList<Double>>();
        this.Sroute    = nRoute.getPath();
        this.routeName = nRoute.getRoute_name();
        this.iniTime   = nRoute.getIni_time();
        this.endTime   = nRoute.getEnd_time();
        this.diasAviso = nRoute.getDays();
        this.puntos    = nRoute.getConf();
        
        System.out.println("Route.Route():::iniTime="+iniTime+", endTime="+endTime+", diasAviso=" + diasAviso);
        
        loadAllPks();
        parseRouteFromOSRM(Sroute);
        try {
            saveRoute();
        } catch (SQLException ex) {
            System.err.println("Route.Route()::: problems saving route ex="+ex.toString());
            //Logger.getLogger(Route.class.getName()).log(Level.SEVERE, null, ex);
        }
        findPks();
        findSegments();
        try {
            saveSegments();
        } catch (SQLException ex) {
            System.err.println("Route.Route()::: segments not saved ex="+ex.toString());
           // Logger.getLogger(Route.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   }
    
   private void saveRoute() throws SQLException{
            Connection connect = MySQLConnection.getConnection();
            Statement statement = connect.createStatement();   
            String sqlupdate = "insert into trayectorias"+
                        "( route_name, name,  coords, ini_time, end_time, days)"+
                        " values('"+routeName+"',"+name+",'"+Sroute+"',"+iniTime+","+endTime+"," + diasAviso +")";
            System.out.println(sqlupdate);
            statement.executeUpdate(sqlupdate, Statement.RETURN_GENERATED_KEYS);
            resultSet  = statement.getGeneratedKeys();
            System.out.println("resultSet==="+resultSet.toString());
            if ( resultSet.next() ){
               this.trayectoriaId = (long) Long.parseLong(resultSet.getString(1));
            }
            
   
        System.out.println("trayectoriaId="+this.trayectoriaId);
   } 
   private void saveSegments() throws SQLException{

            Connection connect = MySQLConnection.getConnection();
            Statement statement; 
            for (int s=0;s<routeSegments.segments.size();s++){
                segment seg = routeSegments.segments.get(s);
                statement = connect.createStatement();   
                String sqlupdate = "insert into segmentos"+
                        "( name, carretera, iniKm, endKm, ini_time, end_time, trayectoriaId)"+
                        " values("+this.name+",'"+seg.getRoad()+"',"+seg.getIniKm()+","+
                                 seg.getEndKm()+","+this.iniTime+","+this.endTime+","+this.trayectoriaId+")";
                System.out.println(sqlupdate);
                statement.executeUpdate(sqlupdate);
            }
   } 
   private void parseRouteFromOSRM(String  osrmRoute){
       System.out.println("Route.parseRouteFromOSRM():::In parseRouteFromOSRM...");
       String[] s = osrmRoute.split("\\),");
       String slat;
       String slon;
       System.out.println("Route.parseRouteFromOSRM():::s.length="+s.length);
       for (int i=0;i<s.length;i++){
           System.out.println("Route.parseRouteFromOSRM()::: s["+i+"]="+s[i]);
            String o = s[i].replace("LatLng(", "").replace(")", "");
            String[] oo = o.split(",");
            slat = oo[0];
            slon = oo[1];
            System.out.println("i="+i+" <"+slat+","+slon+">");
            ArrayList<Double> node = new ArrayList<Double>();
            node.add(Double.parseDouble(slat));
            node.add(Double.parseDouble(slon));
            route.add(node);
       }
       System.out.println("Leaving parseRouteFromOSRM...");
   }
   
   private void findPks(){

      System.out.println("In findPks...");
      if ( this.findPksType == 0 ){
          for (int p=0;p<route.size();p++){
              ArrayList<Double> node = route.get(p);
              pk lpk =allPKs.checkIfPkInList(node);
              if (lpk != null){
                 routePKs.addPk(lpk);
              }
          }
      }
      else if (this.findPksType == 1){
          for (int p=1;p<route.size();p++){
              ArrayList<Double> nodeIni = route.get(p-1);
              ArrayList<Double> nodeEnd = route.get(p);
              ArrayList<pk> lpk = allPKs.checkIfPkInListType1(nodeIni,nodeEnd);
              if (!lpk.isEmpty()){
                 for (int k=0;k<lpk.size();k++){
                     pk kpk = lpk.get(k);
                     routePKs.addPk(kpk);
                 }
              }
          }                                
      }
      System.out.println("routePKs:::"+routePKs.printOut());
   }

   
   private void findSegments(){
       System.out.println("In findSegments...");
       for (int i=0;i<this.routePKs.pks.size();i++){
           System.out.print("...  i="+i+" ");
           pk lpk = this.routePKs.pks.get(i);
           System.out.println(" lpk.road="+lpk.road);
           routeSegments.addPk(lpk);
           routeSegments.clean();
       }
       System.out.println("segmentos:::\n"+routeSegments.printOut());
   
   }
   private void loadAllPks(){
    String csvFile = "euskadiPks.csv";
    System.out.println("In loadAllPks...");
    BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	try {
            URL url = this.getClass().getResource(csvFile);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
	    while ((line = br.readLine()) != null) {
		String[] readPk = line.split(cvsSplitBy);
		//System.out.println("road="+readPk[3]+", km="+readPk[0]+" <"+ readPk[2]+","+readPk[1]+">");
	        String road = readPk[3];
                int km = -1;
                try{
                   km = (int) Integer.parseInt(readPk[0]);}
                catch(Exception e){}
                Double lat = 0.0;
                try{
                    lat = Double.parseDouble(readPk[2]);}
                catch(Exception e){}
                Double lon = 0.0;
                try{
                    lon = Double.parseDouble(readPk[1]);}
                catch(Exception e){}
                pk lpk = new pk(road, km, lat, lon);
                allPKs.addPk(lpk); 
            }
 
	} catch (FileNotFoundException e) {
            System.out.println("File Not Found");
	} catch (IOException e) {
            System.out.println("IO Exception");
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}
        System.out.println("Leaving loadAllPks...");
    }

}
