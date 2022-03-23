package databases;

import model.contact;
import model.country;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static model.contact.addContact;
import static model.country.addCountry;
/** The countriesQuery selects all countries from countries table.*/
public class countriesQuery {
    /** The selectAllCountries method selects all countries from the database and places each of them in a object.*/
    public static void selectAllCountries() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");


            addCountry(new country(countryID, country));

        }
    }
}
