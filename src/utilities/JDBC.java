package utilities;


import java.sql.Connection;
import java.sql.DriverManager;

public abstract class JDBC {

    private static final String Url = "jdbc" + ":mysql:" + "//localhost/" + "client_schedule";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";
    public static Connection connection;
    private static String Drv = "com.mysql.cj.jdbc.Driver";

    public static void openConnection()
    {
        try {
            Class.forName(Drv);
            connection = DriverManager.getConnection(Url, userName, password);

        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            connection.close();

        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
