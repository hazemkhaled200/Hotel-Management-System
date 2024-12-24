package hellofx;

import java.sql.DriverManager;
//import com.mysql.*;
import java.sql.Connection;

public class DatabaseConnection {

    private static Connection databaseLink;
    private static DatabaseConnection obj;

    // Singlton.
    private DatabaseConnection(){}

    public static DatabaseConnection getObject(){
        if(obj ==  null){
            return obj = new DatabaseConnection();
        }
        else{
            return obj;
        }
    }

    public Connection getConnection(){
        String databaseName = "hotel";
        String databaseUser = "root";
        String databasePassword = "hazem";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
    
}
