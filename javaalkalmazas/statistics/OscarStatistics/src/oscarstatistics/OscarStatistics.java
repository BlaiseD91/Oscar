package oscarstatistics;


public class OscarStatistics {


    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String databaseName = "oscar";
        String user = "root";
        String pass = "";
        
        Database db = new Database(url, databaseName, user, pass);
        
        
        //System.out.println("1. FELADAT: "); db.showAll();
        //System.out.println("2. FELADAT: "); db.after1950();
        //System.out.println("3. FELADAT: "); db.top5Nom();
        System.out.println("4. FELADAT: ");
        System.out.println("5. FELADAT: ");
        System.out.println("6. FELADAT: ");
        System.out.println("7. FELADAT: ");
        System.out.println("8. FELADAT: ");
        System.out.println("9. FELADAT: ");
        System.out.println("10. FELADAT: ");
    }
    
}
