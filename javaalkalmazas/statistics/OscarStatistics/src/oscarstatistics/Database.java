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
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-5s\n", "C�m", "D�jak");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement l�trehoz�s�ban! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-10d\n", rs.getString("cim"), rs.getInt("dij"));
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
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-5s | %-2s\n", "C�m", "D�jak", "Jel�l�sek");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement l�trehoz�s�ban! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-5d | %-2d\n", rs.getString("cim"), rs.getInt("dij"), rs.getInt("jelol"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtat�s�n�l! " + ex); }
            } 
        }
    }
    
    public void top5Nom(){
        //K�sz�tsen list�t az 5 legt�bb jel�l�st kapott film c�m�r�l, a d�jaz�s �v�r�l!
        if(this.conn != null){
            String sql = "SELECT cim, ev FROM filmek ORDER BY jelol DESC LIMIT 5;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-4s\n", "C�m", "�v");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement l�trehoz�s�ban! " + ex); }

            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-4d\n", rs.getString("cim"), rs.getInt("ev"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtat�s�n�l! " + ex); }
            } 
        }
    }
    
    public void likeKing(){
        //List�zza ki az �sszes olyan film adat�t, amely c�m�ben szerepel a King sz� vagy sz�r�szlet!
        //Jelen�tse meg a film c�m�t, a jel�l�sek �s az elnyert d�jak sz�m�t!
        //Rendezze a list�t a jel�l�sek, ezen bel�l a d�jak sz�ma szerint cs�kken� sorrendbe!
        if(this.conn != null){
            String sql = "SELECT cim, jelol, dij FROM filmek WHERE cim LIKE '%King%' ORDER BY jelol DESC, dij DESC;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-9s | %-2s\n", "C�m", "Jel�l�sek", "D�jak");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement l�trehoz�s�ban! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-9d | %-2d\n", rs.getString("cim"), rs.getInt("jelol"), rs.getInt("dij"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtat�s�n�l! " + ex); }
            } 
        }
    }
    
    public void winner(){
        //List�zza ki azoknak a filmeknek minden adat�t, amelyek minden jel�l�st megnyertek!
        //A list�t rendezze �v szerint, ezen bel�l c�m szerint n�vekv� sorrendbe!
        if(this.conn != null){
            String sql = "SELECT * FROM filmek WHERE dij=jelol ORDER BY ev, cim;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%9s | %-50s | %-4s | %-5s | %-8s\n", "Azonos�t�","C�m","�v", "D�jak", "Jel�l�sek");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement l�trehoz�s�ban! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%9s | %-50s | %-4d | %-5d | %-8d\n", rs.getString("azon"),rs.getString("cim"),
                                rs.getInt("ev"), rs.getInt("dij"), rs.getInt("jelol"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtat�s�n�l! " + ex); }
            } 
        }
    }
    
    public void worldWarII(){
        //List�zza ki a II. vil�gh�bor� alatt legal�bb 3 Oscar-d�jat kapott filmek c�m�t �s a d�jaz�s �v�t!
        //A list�t rendezze a d�jaz�s �ve, ezen bel�l a film c�me szerint n�vekv� sorrendbe!
        if(this.conn != null){
            String sql = "SELECT cim, ev FROM filmek WHERE (ev BETWEEN 1939 AND 1945) AND dij >=3 ORDER BY ev, cim;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%50s | %-4s\n", "C�m","�v");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement l�trehoz�s�ban! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%50s | %-4d\n",rs.getString("cim"), rs.getInt("ev"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtat�s�n�l! " + ex); }
            } 
        }
    }
    
    public void likeThe(){
        //K�sz�tsen list�t azokr�l a filmc�mekr�l, melyek a The sz�val kezd�dnek! Rendezze a list�t n�vsorba!
        if(this.conn != null){
            String sql = "SELECT cim FROM filmek WHERE cim LIKE 'The%' ORDER BY cim;";
            Statement stmt = null;
            ResultSet rs;
            
            try {
                stmt = conn.createStatement();
                System.out.printf("%-50s\n", "C�m");
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement l�trehoz�s�ban! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        System.out.printf("%-50s\n",rs.getString("cim"));
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtat�s�n�l! " + ex); }
            } 
        }
    }
}
