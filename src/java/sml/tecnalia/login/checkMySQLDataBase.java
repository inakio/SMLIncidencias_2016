/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sml.tecnalia.login;


import sml.tecnalia.routes.Route;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sml.tecnalia.db.MySQLConnection;
/**
 *
 * @author inaki
 */
public class checkMySQLDataBase {
     //
    // database usuariosAlarmas
    // user:   root
    // passwd: mysql
    //
    // tablas: usuarios, segmentos
    // create table usuarios(usuarioId int not NULL primary key, nombre char(25), apellidos char(50), email char(50) );
    // create table segmentos(segmentoId int not NULL primary key auto_increment, usuarioId int not NULL, carretera char(50), iniKm int, endKm int); 
    // 
     
     PreparedStatement preparedStatement = null;
     ResultSet resultSet = null;
     Statement statement2 = null;
     ResultSet resultSet2 = null;
     String     dbUser = "root";
     String     dbPass = "mysql";
     
     public checkMySQLDataBase(){
     }
     public ArrayList<Usuario> getAllUsers(){
         System.out.println("checkMySQLDataBase.getAllUsers():::preparado para conectar a la base de datos");
         ArrayList<Usuario> usrs = new ArrayList<Usuario>();
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from usuarios");
             while (resultSet.next()) {

                 String name         = resultSet.getString("name");
                 Usuario usr         = new Usuario(name);
                 String email        = resultSet.getString("email");
                 String passwd       = resultSet.getString("passwd");
                 statement2 = connect.createStatement();
                 resultSet2 = statement2.executeQuery("select * from segmentos where name="+name);
                 usr.setName(name);
                 usr.setPasswd(passwd);
                 ArrayList<segment> segs = new ArrayList<segment>();
                 while (resultSet2.next()) {
                     String carretera = resultSet2.getString("carretera");
                     int    iniKm     = resultSet2.getInt("iniKm");
                     int    endKm     = resultSet2.getInt("endKm");
                     segment seg = new segment(carretera,iniKm,endKm);
                     segs.add(seg);
                 }
                 usr.setSegments(segs);
                 usrs.add(usr);
             }
             connect.close();
         }
         catch(Exception e){System.out.println("e="+e.toString());}
         return usrs;  
     
     }
     
     public ResultSet getUserInformation(String userId){
         System.out.println("preparado para conectar a la base de datos");
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from usuarios where usuarioId="+userId);
             //writeUserInfo(resultSet);
             connect.close();
         }
         catch(Exception e){System.out.println("e="+e.toString());}
         return resultSet;
     }
     
     public String getPasswd(String userId){
         System.out.println("checkMySQLDataBase.getPasswd()::: preparado para conectar a la base de datos");
         String passwd = "";
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from usuarios where usuarioId="+userId);
             System.out.println("checkMySQLDataBase.getPasswd():::resultSet:::"+resultSet.toString());
             resultSet.next();
             passwd = resultSet.getString("passwd");
             connect.close();
         }
         catch(Exception e){System.err.println("checkMySQLDataBase.getPasswd():::e="+e.toString());}
         return passwd;
     }
     public String getEmail(String userId){
         System.out.println("checkMySQLDataBase.getEmail():::preparado para conectar a la base de datos");
         String email = "";
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from usuarios where usuarioId="+userId);
             System.out.println("checkMySQLDataBase.getEmail():::resultSet:::"+resultSet.toString());
             resultSet.next();
             email = resultSet.getString("email");
             connect.close();
         }
         catch(Exception e){System.err.println("checkMySQLDataBase.getEmail():::e="+e.toString());}
         return email;
     }       

     public String getTypes(String name){
         System.out.println("checkMySQLDataBase.getTypes():::preparado para conectar a la base de datos in getName");
         String type = "011100100";
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from usuarios where name="+name);
             System.out.println("resultSet:::"+resultSet.toString());
             resultSet.next();
             type = resultSet.getString("tipoIncidencias");
             connect.close();
         }
         catch(Exception e){System.out.println("e="+e.toString());}

         return type;
    } 
    public int getIncsLevel(String userId){
         System.out.println("preparado para conectar a la base de datos in getName");
         int level= 0;
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from usuarios where usuarioId="+userId);
             System.out.println("resultSet:::"+resultSet.toString());
             resultSet.next();
             level = resultSet.getInt("nivelIncidencias");
             connect.close();
         }
         catch(Exception e){System.out.println("e="+e.toString());}

         return level;
    }  
    public ArrayList<String> getRouteNames(String userId){
         System.out.println("preparado para conectar a la base de datos in getRouteNames");
         ArrayList<String> routeNames = new ArrayList<String>();
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from trayectorias where usuarioId="+userId);
             System.out.println("resultSet:::"+resultSet.toString());
             while (resultSet.next()){
                String s = resultSet.getString("nombre");
                routeNames.add(s);
                //System.out.println("nombre:::"+s);
             }
             connect.close();
         }
         catch(Exception e){System.out.println("e="+e.toString());}
         return routeNames;
    }   
 
     public ArrayList<String> getRouteIds(String userId){
         System.out.println("preparado para conectar a la base de datos in getRouteIds");
         ArrayList<String> routeIds = new ArrayList<String>();
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from trayectorias where usuarioId="+userId);
             //System.out.println("resultSet:::"+resultSet.toString());
             while (resultSet.next()){
                String s = resultSet.getString("trayectoriaId");
                routeIds.add(s);
                //System.out.println("nombre:::"+s);
             }
             connect.close();
         }
         catch(Exception e){System.out.println("e="+e.toString());}
         return routeIds;
    } 
     
     public ArrayList<String> getRoutes(String userId){
         System.out.println("preparado para conectar a la base de datos in getRoutes");
         ArrayList<String> routes = new ArrayList<String>();
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from trayectorias where usuarioId="+userId);
             System.out.println("resultSet:::"+resultSet.toString());
             while (resultSet.next()){
                routes.add(resultSet.getString("coords"));
             }
             connect.close();
         }
         catch(Exception e){System.out.println("e="+e.toString());}
         return routes;
    }   
      public ArrayList<ArrayList<Integer>> getRoutesTimes(String userId){
         System.out.println("preparado para conectar a la base de datos in getRoutesTimes");
         ArrayList<ArrayList<Integer>> times= new ArrayList<ArrayList<Integer>>();
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from trayectorias where usuarioId="+userId);
             System.out.println("resultSet:::"+resultSet.toString());
             while (resultSet.next()){
                int iniTime = 0;
                if ( resultSet.getString("iniTime") != null){
                   iniTime = Integer.parseInt(resultSet.getString("iniTime"));
                }
                int endTime = 1440;
                if ( resultSet.getString("endTime") != null){
                   endTime = Integer.parseInt(resultSet.getString("endTime"));
                }
                ArrayList<Integer> ltimes = new ArrayList<Integer>();
                ltimes.add(iniTime);
                ltimes.add(endTime);
                times.add(ltimes);
             }
             connect.close();
         }
         catch(Exception e){System.out.println("e="+e.toString());}
         return times;
    }    
      
      public ArrayList<String> getRouteDiasAviso(String userId){
         System.out.println("preparado para conectar a la base de datos in getRouteDiasAviso");
         ArrayList<String> routeIds = new ArrayList<String>();
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from trayectorias where usuarioId="+userId);
             //System.out.println("resultSet:::"+resultSet.toString());
             while (resultSet.next()){
                String s = resultSet.getString("diasAviso");
                routeIds.add(s);
                //System.out.println("nombre:::"+s);
             }
             connect.close();
         }
         catch(Exception e){System.out.println("e="+e.toString());}
         return routeIds;
    } 
      
    public ArrayList<String> getRouteMarkers(String userId){
         System.out.println("preparado para conectar a la base de datos in getRouteDiasAviso");
         ArrayList<String> routeMarkers = new ArrayList<String>();
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from trayectorias where usuarioId="+userId);
             //System.out.println("resultSet:::"+resultSet.toString());
             while (resultSet.next()){
                String s = resultSet.getString("markers");
                routeMarkers.add(s);
                //System.out.println("nombre:::"+s);
             }
             connect.close();
         }
         catch(Exception e){System.out.println("e="+e.toString());}
         return routeMarkers;
    }   
      
     public void eraseRoute(String name, String trajectory_id) throws SQLException{       
            Connection connect = MySQLConnection.getConnection();
            Statement statement = connect.createStatement();  
   
            String sqlupdate = "delete from trayectorias "+
                        "where trajectory_id="+trajectory_id;
            System.out.println(sqlupdate);
            statement.executeUpdate(sqlupdate);
            statement = connect.createStatement(); 
            sqlupdate = "delete from segmentos "+
                        "where trajectory_id="+trajectory_id;
            System.out.println(sqlupdate);
            statement.executeUpdate(sqlupdate);
            connect.close();     
     }
     
     public void renameRoute(String userId, String routeIndx, String newName) throws SQLException{
         
        System.out.println("##### renameRoute::: userId="+userId+", routeIndx="+routeIndx+", newName="+newName);
        Connection connect = MySQLConnection.getConnection();
        Statement statement = connect.createStatement();  
        String sqlupdate = "update trayectorias "+
                           "set nombre=\""+newName+"\" "+
                           "where trayectoriaId="+routeIndx;
        System.out.println(sqlupdate);
        statement.executeUpdate(sqlupdate);
        connect.close();      
     }     

     public ArrayList<segment> getSegments(String userId){
         System.out.println("preparado para conectar a la base de datos");
         ArrayList<segment> segs = new ArrayList<segment>();
         try {
             Connection connect = MySQLConnection.getConnection();
             Statement statement = connect.createStatement();  
             resultSet = statement.executeQuery("select * from segmentos where usuarioId="+userId);
             while (resultSet.next()) {
                String road = resultSet.getString("carretera");
                int iniKm = resultSet.getInt("iniKm");
                int endKm = resultSet.getInt("endKm");
                segment seg = new segment(road,iniKm,endKm);
                segs.add(seg);
             }  
         }
         catch(Exception e){System.out.println("e="+e.toString());} 
         return segs;
    }
    
    public void writeSegmentInfoToTable(ArrayList<segment> segs, JTable table) throws SQLException {
     // ResultSet is initially before the first data set
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while(((DefaultTableModel) table.getModel()).getRowCount()>0){
              ((DefaultTableModel) table.getModel()).removeRow(((DefaultTableModel)table.getModel()).getRowCount()-1);
        }
        for (int s=0;s<segs.size();s++){
             model.addRow(new Object[]{segs.get(s).getRoad(),segs.get(s).getIniKm(),segs.get(s).getEndKm()});
        }
    }
}
