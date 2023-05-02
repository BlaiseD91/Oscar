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
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-5s\n", "Cím", "Díjak");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-10d\n", rs.getString("cim"), rs.getInt("dij"));
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
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-5s | %-2s\n", "Cím", "Díjak", "Jelölések");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-5d | %-2d\n", rs.getString("cim"), rs.getInt("dij"), rs.getInt("jelol"));
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
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-4s\n", "Cím", "Év");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }

            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-4d\n", rs.getString("cim"), rs.getInt("ev"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
    
    public void likeKing(){
        //Listázza ki az összes olyan film adatát, amely címében szerepel a King szó vagy szórészlet!
        //Jelenítse meg a film címét, a jelölések és az elnyert díjak számát!
        //Rendezze a listát a jelölések, ezen belül a díjak száma szerint csökkenõ sorrendbe!
        if(this.conn != null){
            String sql = "SELECT cim, jelol, dij FROM filmek WHERE cim LIKE '%King%' ORDER BY jelol DESC, dij DESC;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-9s | %-2s\n", "Cím", "Jelölések", "Díjak");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-9d | %-2d\n", rs.getString("cim"), rs.getInt("jelol"), rs.getInt("dij"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
    
    public void birthYearTops(){
        //Listázza ki azokat a filmeket, amelyek az Ön vagy édesanyja születési évében kaptak több, mint 4 jelölést!
        //Jelenítse meg a filmek címét névsorban! Kezelje az esetleges holtversenyt!
        if(this.conn != null){
            String sql = "SELECT cim, jelol FROM filmek WHERE (ev=1991 OR ev=1973) AND jelol>4 ORDER BY cim;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-9s\n", "Cím", "Jelölések");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-9d\n", rs.getString("cim"), rs.getInt("jelol"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
    
    public void winner(){
        //Listázza ki azoknak a filmeknek minden adatát, amelyek minden jelölést megnyertek!
        //A listát rendezze év szerint, ezen belül cím szerint növekvõ sorrendbe!
        if(this.conn != null){
            String sql = "SELECT * FROM filmek WHERE dij=jelol ORDER BY ev, cim;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%9s | %-50s | %-4s | %-5s | %-8s\n", "Azonosító","Cím","Év", "Díjak", "Jelölések");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%9s | %-50s | %-4d | %-5d | %-8d\n", rs.getString("azon"),rs.getString("cim"),
                                rs.getInt("ev"), rs.getInt("dij"), rs.getInt("jelol"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
    
    public void worldWarII(){
        //Listázza ki a II. világháború alatt legalább 3 Oscar-díjat kapott filmek címét és a díjazás évét!
        //A listát rendezze a díjazás éve, ezen belül a film címe szerint növekvõ sorrendbe!
        if(this.conn != null){
            String sql = "SELECT cim, ev FROM filmek WHERE (ev BETWEEN 1939 AND 1945) AND dij >=3 ORDER BY ev, cim;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-4s\n", "Cím","Év");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-4d\n",rs.getString("cim"), rs.getInt("ev"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
    
    public void likeThe(){
        //Készítsen listát azokról a filmcímekrõl, melyek a The szóval kezdõdnek! Rendezze a listát névsorba!
        if(this.conn != null){
            String sql = "SELECT cim FROM filmek WHERE cim LIKE 'The%' ORDER BY cim;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%-50s\n", "Cím");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%-50s\n",rs.getString("cim"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
    
    public void winnerAfter1960(){
        //Listázza ki azoknak a filmeknek minden adatát, melyek 1960 után készültek és minden jelölést megnyertek!
        //Rendezze a listát a jelölés és ev szerint csökkenõ sorrendbe!
        if(this.conn != null){
            String sql = "SELECT * FROM filmek WHERE ev > 1960 AND dij=jelol ORDER BY ev DESC;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%9s | %-50s | %-4s | %-5s | %-8s\n", "Azonosító","Cím","Év", "Díjak", "Jelölések");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%9s | %-50s | %-4d | %-5d | %-8d\n", rs.getString("azon"),rs.getString("cim"),
                                rs.getInt("ev"), rs.getInt("dij"), rs.getInt("jelol"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
    
    public void top5After2000(){
        //Készítsen listát az 2000 után 5 legtöbb díjat kapott film címérõl és a díjak számáról!
        //Kezelje az esetleges holtversenyt!
        if(this.conn != null){
            String sql = "SELECT cim, dij FROM filmek WHERE ev > 2000 ORDER BY dij DESC;";
            Statement stmt = null;
            ResultSet rs;
            int awards = 0;
            int movies = 0;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-5s\n", "Cím", "Díjak");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        if(movies >=5 && awards != rs.getInt("dij")) break;
                        System.out.printf("%50s | %-5d\n", rs.getString("cim"), rs.getInt("dij"));
                        movies++;
                        awards = rs.getInt("dij");
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
    }
}
