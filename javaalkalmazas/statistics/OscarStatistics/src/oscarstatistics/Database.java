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
        
        //kapcsolódás az adatbázishoz
        try {
            this.conn = DriverManager.getConnection(this.url+this.databaseName, this.user, this.pass);
        } catch (SQLException ex) {
            System.out.println("Az adatbázis vagy az adattábla nem található...");
        }
        
        if(this.conn != null) System.out.println("Sikeres kapcsolódás");
        
    }
    
    public void showAll(){
        //Készítsen listát az összes filmrõl a címük és elnyert díjaik számának feltüntetésével!
        //A listát rendezze a jelölések száma szerint csökkenõ rendbe, ezen belül cím szerint névsorba!
        if(this.conn != null){
            String sql = "SELECT cim, dij FROM filmek ORDER BY jelol DESC, cim;";
            Statement stmt = null;
            ResultSet rs = null;
            
            try { stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.println(rs.getString("cim")
                                + " | " + rs.getInt("dij"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
    
    public void after1950(){
        //Listázza ki az 1950 után legalább 3 Oscar-díjjal jutalmazott filmek címét, a díjak és jelölések számát! A listát rendezze a filmek címe szerint névsorba!
        if(this.conn != null){
            String sql = "SELECT cim, dij, jelol FROM filmek WHERE dij > 3 ORDER BY cim;";
            Statement stmt = null;
            ResultSet rs = null;
            
            try { stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.println(rs.getString("cim")
                                + " | " + rs.getInt("dij")
                                + " | " + rs.getInt("jelol"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
    
    public void top5Nom(){
        //Készítsen listát az 5 legtöbb jelölést kapott film címérõl, a díjazás évérõl!
        if(this.conn != null){
            String sql = "SELECT cim, ev FROM filmek ORDER BY jelol DESC LIMIT 5;";
            Statement stmt = null;
            ResultSet rs = null;
            
            try { stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.println(rs.getString("cim")
                                + " | " + rs.getInt("ev"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
}
