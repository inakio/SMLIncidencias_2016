/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sml.tecnalia.db;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import sml.tecnalia.users.User;

/**
 *
 * @author inaki
 */
public class MySQLConnection {
    static String DB_URL      = "localhost:3306/usuariosAlarmas_2016";
    static String DB_USER     = "root";
    static String DB_PASSWORD = "cTqdEtv3BGl_";
    static Connection connect = null;
    public static Connection getConnection(){
       try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties prop = new Properties();
            InputStream input;
            
            try{

               ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
               input = classLoader.getResourceAsStream("sml/tecnalia/properties/main.properties");
               System.out.println("MySQLConnection.getConnection()::: input="+input.toString());  
               prop.load(input);
               DB_URL      = prop.getProperty("DB_URL");
               DB_USER     = prop.getProperty("DB_USER");
               DB_PASSWORD = prop.getProperty("DB_PASSWORD");
               
               System.out.println( "MySQLConnection.getConnection::::DB_URL      = "+DB_URL);
               System.out.println( "MySQLConnection.getConnection::::DB_USER     = "+DB_USER);
               System.out.println( "MySQLConnection.getConnection::::DB_PASSWORD = "+DB_PASSWORD);
            }catch(Exception ex){
                System.err.println("MySQLConnection.getConnection::: ex="+ex.toString());
            }
       } catch (ClassNotFoundException ex) {
            System.err.println("MySQLConnection.getConnection::: ex="+ex.toString());
       } 
        try {
            connect = DriverManager.getConnection("jdbc:mysql://"+DB_URL+"?"+ "user=" + DB_USER + "&password=" + DB_PASSWORD);
        } catch (SQLException ex) {
            System.err.println("MySQLConnection.getConnection:::: SQL ex="+ex.toString());
           
        }
       return connect;
    }
}
