package databases;

import model.country;
import model.division;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static model.country.addCountry;
import static model.division.addDivision;
/** The divisionQuery class selects all first level divisions from the database.*/
public class divisionQuery {
    /** The selectAllDivisions creates division objects of every first level division in the database.*/
    public static void selectAllDivisions() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");


            addDivision(new division(divisionID, division, countryID));

        }
    }
}
