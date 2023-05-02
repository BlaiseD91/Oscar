package oscarstatistics;

import java.sql.*;

public class Database {
    private String url;
    private String databaseName;
    private String user;
    private String pass;
    private Connection conn;

    public Database(String url, String databaseName, String user, String pass) {
        this.url = url;
        this.databaseName = databaseName;
        this.user = user;
        this.pass = pass;
        this.conn = null;
        
        //Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Baj van! Nincs meg a driver! " +ex);
        }
        
        //kapcsol�d�s az adatb�zishoz
        try {
            this.conn = DriverManager.getConnection(this.url+this.databaseName, this.user, this.pass);
        } catch (SQLException ex) {
            System.out.println("Az adatb�zis vagy az adatt�bla nem tal�lhat�...");
        }
        
        if(this.conn != null) System.out.println("Sikeres kapcsol�d�s");
        
    }
    
    public void showAll(){
        //K�sz�tsen list�t az �sszes filmr�l a c�m�k �s elnyert d�jaik sz�m�nak felt�ntet�s�vel!
        //A list�t rendezze a jel�l�sek sz�ma szerint cs�kken� rendbe, ezen bel�l c�m szerint n�vsorba!
        if(this.conn != null){
            String sql = "SELECT cim, dij FROM filmek ORDER BY jelol DESC, cim;";
            Statement stmt = null;
            ResultSet rs = null;
            
            try { stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement l�trehoz�s�ban! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.println(rs.getString("cim")
                                + " | " + rs.getInt("dij"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtat�s�n�l! " + ex); }
            } 
        }
    }
    
    public void after1950(){
        //List�zza ki az 1950 ut�n legal�bb 3 Oscar-d�jjal jutalmazott filmek c�m�t, a d�jak �s jel�l�sek sz�m�t! A list�t rendezze a filmek c�me szerint n�vsorba!
        if(this.conn != null){
            String sql = "SELECT cim, dij, jelol FROM filmek WHERE dij > 3 ORDER BY cim;";
            Statement stmt = null;
            ResultSet rs = null;
            
            try { stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement l�trehoz�s�ban! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.println(rs.getString("cim")
                                + " | " + rs.getInt("dij")
                                + " | " + rs.getInt("jelol"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtat�s�n�l! " + ex); }
            } 
        }
    }
}
