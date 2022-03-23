package databases;

import model.appointment;
import model.customer;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static model.appointment.addAppointment;
import static model.appointment.getAllAppointments;
import static model.contact.getAllContacts;
import static model.customer.addCustomer;
import static model.customer.getAllCustomers;
import static model.user.getCurrentUser;
import static utilities.timeCheck.checkScheduleConflict;
import static utilities.timeCheck.checkScheduleConflictUpdate;
import static utilities.timeConvert.*;

/** The appointmentQuery class updates, inserts, deletes data from the appointments table.*/
public class appointmentQuery {
    /**The selectAppointments method selects every entry from the appointments table creates appointment objects. */
    public static void selectAppointments() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {


            String contactName = "null";
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");

            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            int count = getAllContacts().size();

            for (int i = 0; i < count; i++) {
                if (getAllContacts().get(i).getContactID() == contactID) {
                    contactName = getAllContacts().get(i).getContactName();
                    break;
                }

            }

            //System.out.println(toLocalTime(start));

            String localStart = toLocalTime(start);

            String localEnd = toLocalTime(end);

            String startAMPM = toLocalTimeAMPM(localStart);

            String endAMPM = toLocalTimeAMPM(localEnd);

            addAppointment(new appointment(appointmentID,  title,  description,  location, type,  start,  end,  createDate,
                     createdBy,  lastUpdate, lastUpdatedBy , customerID, userID, contactID, contactName,localStart,localEnd,startAMPM,endAMPM));

        }
    }
    /** The insertAppointment method inserts appointments into the appointment database while making sure data is in the right format, and their is no schedule conflict.
       @param contactID The contact ID.
       @param date  The date of appointment.
       @param description The description of appointment.
       @param customerID The customer ID.
       @param end The end time of appointment.
       @param location The location of the appointment.
       @param start Start time of appointment.
       @param title Title of the appointment.
       @param type Type of appointment.
       @param userID The userID.
        @return Returns true if their is no conflict and data is successfully entered into the database.
       */

    public static boolean insertAppointment(String title, String description, String location, String type, String start,String end,
                                            LocalDate date, int customerID,int userID, int contactID) throws SQLException {
        int appointmentID = getAllAppointments().size() + 1;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startTime = stringToLocalTime(start).atDate(date);

        LocalDateTime  StartTimeUTC = startTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        LocalDateTime endTime = stringToLocalTime(end).atDate(date);

        LocalDateTime  endTimeUTC = endTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        String currentUser = getCurrentUser().get(0).getUsername();

        if (checkScheduleConflict(StartTimeUTC,endTimeUTC,customerID) == true){
            return false;
        }
        else {



        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(? , ?, ?, ?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,appointmentID);
        ps.setString(2,title);
        ps.setString(3,description);
        ps.setString(4,location);
        ps.setString(5,type);
        ps.setString(6,StartTimeUTC.format(format));
        ps.setString(7,endTimeUTC.format(format));
        ps.setString(8,ZonedDateTime.now(ZoneOffset.UTC).format(format));
        ps.setString(9,currentUser);
        ps.setString(10,ZonedDateTime.now(ZoneOffset.UTC).format(format));
        ps.setString(11,currentUser);
        ps.setInt(12,customerID);
        ps.setInt(13,userID);
        ps.setInt(14,contactID);
        ps.executeUpdate();


        return true;
        }
    }
    /**The deleteAppointmentDB deletes a appointment from the database.
      @param AppointmentID The ID of the appointment to be deleted.*/
    public static void deleteAppointmentDB(int AppointmentID) throws SQLException {




        String sql = "Delete from appointments where Appointment_ID = ? ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,AppointmentID);



        ps.executeUpdate();

    }
    /** the updateAppointmentDB updates appointments while also checking for time conflicts.
     @param contactID The contact ID.
     @param dateDB  The date of appointment.
     @param appointmentDescription The description of appointment.
     @param customerID The customer ID.
     @param endTime The end time of appointment.
    @param appointmentLocation The location of the appointment.
    @param startTimeDB Start time of appointment.
    @param appointmentTitle Title of the appointment.
    @param appointmentType Type of appointment.
    @param userID The userID.
     @param appointmentID The id of the appoint to be deleted.
     @return returns true if the their is no conflict with another customer appointment.
     */

    public static boolean updateAppointmentsDB(int appointmentID ,String appointmentTitle,String appointmentDescription,String appointmentLocation,
                                               String appointmentType,String startTimeDB,String endTime,LocalDate dateDB,int customerID,int userID,int contactID) throws SQLException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startTime = stringToLocalTime(startTimeDB).atDate(dateDB);

        LocalDateTime StartTimeUTC = startTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        LocalDateTime endTimes = stringToLocalTime(endTime).atDate(dateDB);

        LocalDateTime endTimeUTC = endTimes.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        String currentUser = getCurrentUser().get(0).getUsername();

        if (checkScheduleConflictUpdate(appointmentID, StartTimeUTC, endTimeUTC, customerID) == true) {
            return false;
        } else {

            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                    "End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?,User_ID = ?, Contact_ID=? WHERE Appointment_ID = ? ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, appointmentType);
            ps.setString(5, StartTimeUTC.format(format));
            ps.setString(6, endTimeUTC.format(format));
            ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(format));
            ps.setString(8, currentUser);
            ps.setInt(9, customerID);
            ps.setInt(10, userID);
            ps.setInt(11, contactID);
            ps.setInt(12, appointmentID);
            ps.executeUpdate();

            return true;
        }

    }


}
