package oscardatahandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private String url;
    private String databaseName;
    private String user;
    private String pass;
    private Connection conn;
    private String flag;
    private String file;

    public Database(String url, String databaseName, String user, String pass, String flag, String file) {
        this.url = url;
        this.databaseName = databaseName;
        this.user = user;
        this.pass = pass;
        this.conn = null;
        this.flag = flag;
        this.file = file;
        
        //Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Baj van! Nincs meg a driver! " +ex);
        }
        
        //kapcsolódás az adatbázishoz
        try {
            this.conn = DriverManager.getConnection(this.url+this.databaseName, this.user, this.pass);
            createTable();
        } catch (SQLException ex) {
            System.out.println("Az adatbázis vagy az adattábla nem található...");
        }
        
        if(this.conn==null){
            try {
                this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
                Statement stmt = this.conn.createStatement();
                String sql = "CREATE DATABASE IF NOT EXISTS `" + databaseName + "` DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci";
                stmt.executeUpdate(sql);
                createTable();
                System.out.println("...de létrehoztam! ");
                this.conn = DriverManager.getConnection(this.url+this.databaseName, this.user, this.pass);
                uploadDatas();
                
            } catch(SQLException ex) { System.out.println("Hiba! Nem is tudom létrehozni! " + ex);}
        }
        if(this.conn != null){
            System.out.println("Sikeres kapcsolódás");
            if(flag.equalsIgnoreCase("-f") || flag.equalsIgnoreCase("--froce")){
                System.out.println("Mivel megadtál egy force flaget, törlöm a film tábla tartalmát és feltöltöm újra a megadott fájlból");
                dropDatas();
                uploadDatas();
            }
        }
        
    }
    
    private void createTable(){
        if(conn != null){
            Statement stmt = null;
            String sql = "CREATE TABLE IF NOT EXISTS `filmek` (" +
                            "  `azon` varchar(15) COLLATE utf8_hungarian_ci NOT NULL," +
                            "  `cim` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL," +
                            "  `ev` int(4) DEFAULT NULL," +
                            "  `dij` int(2) DEFAULT NULL," +
                            "  `jelol` int(2) DEFAULT NULL" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;";
            
            try { stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    stmt.execute(sql);
                } catch(SQLException ex){
                    System.out.println("Baj van! Hiba a CreateTable-nél! " + ex);
                }
            }
        }
    }
    
    private void dropDatas(){
        if(this.conn != null){
            Statement stmt = null;
            String sql = "DELETE FROM filmek;";

            try {    
                stmt = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Baj van! Hiba a statement létrehozásában! " + ex);
            }
            
            if(stmt != null){
                try {    
                    stmt.execute(sql);
                } catch(SQLException ex){
                    System.out.println("Baj van! Hiba a törlésnél " + ex);
                }
            }
        }
    }
    private void uploadDatas(){
        if(this.conn != null){
            
            try {
                BufferedReader br = new BufferedReader(new FileReader(this.file));
                String row = br.readLine();
                row = br.readLine();
                //azon, cim, ev, dij, jelol
                while(row != null){
                    String[] datas = row.split(";");
                    Statement stmt = null;
                    String sql = "INSERT INTO `filmek` VALUES ("+ datas[0] //azon
                            + ", " + datas[1] //cim
                            + ", " + datas[2] // ev
                            + ", " + datas[3] // dij
                            + ", " + datas[4] + ");"; //jelol
                    System.out.println(sql);
                    try { stmt = conn.createStatement();
                    } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
                    if(stmt != null){
                        try { stmt.execute(sql);
                        } catch(SQLException ex){ System.out.println("Baj van! Hiba az adatok létrehozásánál! " + ex); }
                    }
                    row = br.readLine();
                }
                br.close();
                System.out.println("Az adatokat sikeresen feltöltöttük a filmek táblába");
            } catch (IOException ex) {
                System.out.println("Hiba a file beolvasásánál! " +ex);
            }
        }
    }
}
