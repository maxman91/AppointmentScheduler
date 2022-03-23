package databases;

import model.contact;
import model.customer;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static model.contact.addContact;
import static model.customer.addCustomer;
/** The contactQuery class selects all contacts from the database.*/
public class contactQuery {
    /**The selectAllContacts method gets all contacts from database and creates contact objects out of each row.*/
    public static void selectAllContacts() throws SQLException {
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");

            addContact(new contact(contactID, contactName, contactEmail));

        }
    }
}
