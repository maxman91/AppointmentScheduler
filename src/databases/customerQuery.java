package databases;

import model.customer;
import model.user;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static model.customer.addCustomer;
import static model.customer.getAllCustomers;
import static model.user.getCurrentUser;
import static utilities.timeConvert.toLocalTime;

/** customerQuery class selects, updates, deletes all customers from database.*/
public abstract class customerQuery {
    /**The selectAllCustomers method selects all customers from database and places them in a customer object.*/
    public static void selectAllCustomers() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionID = rs.getInt("Division_ID");

            String localCreateDate = toLocalTime(createDate);
            String localUpdate = toLocalTime(lastUpdate);
            addCustomer(new customer(customerID,customerName,address,postalCode,phone,createDate,createdBy,lastUpdate,lastUpdatedBy,divisionID, localCreateDate, localUpdate));

        }
    }
    /**The insertCustomer method inserts data into the customer table.
     @param name the name of customer.
     @param address the address of customer.
     @param division the division of customer.
     @param phoneNumber the phone number of customer.
     @param postal Postal data of customer.
     */
    public static void insertCustomer(String name, int division,String address,String postal,String phoneNumber) throws SQLException {
        int customerID = getAllCustomers().size() + 1;
        String currentUser = getCurrentUser().get(0).getUsername();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");




        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID)VALUES(" + customerID+ ",'" + name+"','"+ address +"','"+ postal+"','"+ phoneNumber+"','"+ ZonedDateTime.now(ZoneOffset.UTC).format(format)+"', '"+ currentUser+"','"+ ZonedDateTime.now(ZoneOffset.UTC).format(format)+"','"+ currentUser+"','"+ division +"')";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        System.out.println(sql);


        ps.executeUpdate();

    }
    /**This method deletes customers from the database.
      @param customerID is the customer id of the row that will be deletes.*/
    public static void deleteCustomerDB(int customerID) throws SQLException {




        String sql = "Delete from customers where Customer_ID = "+customerID+" ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        System.out.println(sql);


        ps.executeUpdate();

    }
    /**updateCustomersDB is the method that will update customers in the database.
     @param postal is the postal information of the customer.
     @param phoneNumber is the phone number of the customer.
     @param division is the division information of the customer.
     @param address is the address of the customer.
     @param customerID is the customerID of the row that will be updated.
     @param name is the name of the customer.
      */

    public static void updateCustomersDB(int customerID,String name,int division,String address,String postal,String phoneNumber) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentUser = getCurrentUser().get(0).getUsername();
        ps.setString(1,name);
        ps.setString(2,address);
        ps.setString(3,postal);
        ps.setString(4,phoneNumber);
        ps.setString(5,ZonedDateTime.now(ZoneOffset.UTC).format(format));
        ps.setString(6,currentUser);
        ps.setInt(7,division);
        ps.setInt(8,customerID);



        ps.executeUpdate();

    }






}
