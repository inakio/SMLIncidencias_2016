package sml.tecnalia.users;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.annotation.XmlRootElement;
import sml.tecnalia.db.MySQLConnection;


@XmlRootElement
public class User {
    private String name;
    private String email;
    private String host;
    private String passwd;
    private String gcm;
    private static boolean ltrace = false;
    
    public User(){
      this.name    ="";
      this.email   ="";
      this.host="";
      this.passwd  ="";
      this.gcm     ="";
    }
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getGcm() {
        return gcm;
    }

    public void setGcm(String gcm) {
        this.gcm = gcm;
    }
    
    public static void saveTypes(String name,  String types, int levels) throws SQLException{
         System.out.println("User.saveTypes()::: name="+name+", types="+types+", levels="+levels);
         Connection connect =null;
         Statement statement;
         String sqlupdate;
         try{
            connect = MySQLConnection.getConnection();
            statement = connect.createStatement();  
            sqlupdate = "update usuarios "+
                               "set types=\""+types+"\" "+
                               "where name='"+name+"'";
            System.out.println(sqlupdate);
            statement.executeUpdate(sqlupdate);
         }
         catch(SQLException ex){
            System.err.println("User.saveTypes()::: ex1="+ex.toString());
         }
         try{
            statement = connect.createStatement(); 
            sqlupdate = "update usuarios "+
                              "set levels=\""+levels+"\" "+
                              "where name='"+name+"'";
            System.out.println("Users.saveTypes():::"+sqlupdate);
            statement.executeUpdate(sqlupdate);
            connect.close();  
         }
         catch(SQLException ex){
             System.err.println("User.saveTypes()::: ex2="+ex.toString());
         }
     }   
    
     public static void savePasswd(String name,  String passwd) {      
         System.out.println("User.savePasswd()::: name="+name+", newPasswd="+passwd);
         try{
            Connection connect = MySQLConnection.getConnection();
            Statement statement = connect.createStatement();  
            String sqlupdate = "update usuarios "+
                                  "set passwd=\""+passwd+"\" "+
                                  "where name='"+name+"'";
            System.out.println("User.savePasswd():::"+sqlupdate);
            int ret = statement.executeUpdate(sqlupdate);
            System.out.println("User.savePasswd():::result="+ret);
            connect.close();
         }
         catch(SQLException ex){
            System.err.println("User.savePasswd()::: ex="+ex.toString());
         }     
     }  
     
     public static void saveEmail(String name,  String email) {      
         System.out.println("User.saveEmail()::: name="+name+", newEmail="+email);
         try{
            Connection connect = MySQLConnection.getConnection();
            Statement statement = connect.createStatement();  
            String sqlupdate = "update usuarios "+
                                  "set email=\""+email+"\" "+
                                  "where name='"+name+"'";
            System.out.println("User.saveEmail():::"+sqlupdate);
            statement.executeUpdate(sqlupdate);
            connect.close();
         }
         catch(SQLException ex){
            System.err.println("Users.saveEmail()::: ex="+ex.toString());
         }
    } 
     
    public static String altaUsuario(User newUser){
        
        String status = "{\"status\":\"error1\"}";
        if ( newUser != null ){
            
            
            String name     = newUser.getName();
            String psswd    = newUser.getPasswd();
            String email    = newUser.getEmail();
            String gcm      = newUser.getGcm();
            String host     = newUser.getHost(); 
            if ( ltrace ){
                System.out.println("User.altaUsuario:::name     ="+name);
                System.out.println("User.altaUsuario:::psswd    ="+psswd);
                System.out.println("User.altaUsuario:::email    ="+email);
                System.out.println("User.altaUsuario:::host     ="+host);
                System.out.println("User.altaUsuario:::gcm      ="+gcm);   
            }         
            Connection connect = MySQLConnection.getConnection();
            try {   
                Statement statement = connect.createStatement();
                String sqlupdate = "insert into usuarios"+
                        "( name,  passwd, email, gcm, host)"+
                        " values('"+name+"','"+psswd+"','"+email+"','"+gcm+"','"+host+"')";
                statement.executeUpdate(sqlupdate, Statement.RETURN_GENERATED_KEYS);
                status = "{\"status\":\"ok\"}";
            
            } catch (SQLException ex) {
                System.err.println("User.altaUsuario:::ex="+ex.toString());
                status = "{\"status\":\"error3\"}";
            }
        }
        return status;
    }

}
