package oscardatahandler;


public class OscarDataHandler {
   
    public static void main(String[] args) {
        String flag = "";
        String file = "";
        try{
            flag = args[1];
            file = args[0];
        }
        catch (ArrayIndexOutOfBoundsException ex){
        }
        String url = "jdbc:mysql://localhost:3306/";
        String databaseName = "oscar";
        String user = "root";
        String pass = "";
        
        Database db = new Database(url, databaseName, user, pass, flag, file);
    }
    
}
