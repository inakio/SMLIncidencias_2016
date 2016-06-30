
package sml.tecnalia.login;

public class checkLogin {
    String inputUser;
    String inputPasswd;
    String checkPasswd = "-11";
    public checkLogin(String user,String passwd){
        this.inputUser  = user;
        this.inputPasswd = passwd;
        System.out.println("checkLogin:::in routine");
        checkMySQLDataBase cmsqldb = new checkMySQLDataBase();
        checkPasswd = cmsqldb.getPasswd(user);
        System.out.println("checkPasswd="+checkPasswd);
    }
    public boolean check(){
        boolean out = false;
        if ( checkPasswd.equals(inputPasswd)){
            out =  true;
        }
        return out;
    }
}
