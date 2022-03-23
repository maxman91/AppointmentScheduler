package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.temporal.Temporal;
/**The appointment class is a model that receives appointment table data from the database.*/
public class appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    private String localStart;
    private String localEnd;

    private String startAMPM;
    private String endAMPM;


    private String contactName;

    private static ObservableList<appointment> allAppointments = FXCollections.observableArrayList();
    /**The appointment constructor creates appointment objects
       @param customerID The customers ID.
        @param userID  The user ID.
     @param type The type of appointment.
     @param title The title of appointment.
     @param start The start time of appointment.
     @param location The location of appointment.
     @param end The end time of appointment.
     @param description The description of appointment.
     @param contactID The contact ID.
     @param appointmentID The appointment ID.
     @param contactName The contact name.
     @param createDate The date appointment was created.
     @param createdBy  The user that created appointment.
     @param lastUpdate The last date appointment was updated.
     @param lastUpdatedBy The last user to update the appointment.
     @param localEnd The local end time of appointment.
     @param localStart The local start time of the appointment.
     */
    public appointment(int appointmentID, String title, String description, String location,String type, Timestamp start, Timestamp end, Timestamp createDate,
                       String createdBy, Timestamp lastUpdate,String lastUpdatedBy, int customerID,int userID,int contactID, String contactName, String localStart,String localEnd, String startAMPM, String endAMPM){
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate= createDate;
        this.createdBy= createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
        this.localEnd = localEnd;
        this.localStart = localStart;
        this.startAMPM = startAMPM;
        this.endAMPM = endAMPM;
    }
/** The getAllAppointments method returns and observableList of every appointment object.
    @return returns a observable list of every appointment.
 */
    public static ObservableList<appointment> getAllAppointments() {
        return allAppointments;
    }




    /** The getAppointmentID method returns the appointmentID.
     @return returns appointmentID.
     */
    public int getAppointmentID() {
        return appointmentID;
    }
    /** The getTitle method returns the title.
     @return returns title.
     */
    public String getTitle() {
        return title;
    }
    /** The getDescription method returns the description.
     @return returns description.
     */
    public String getDescription() {
        return description;
    }
    /** The getLocation method returns the location.
     @return returns location.
     */
    public String getLocation() {
        return location;
    }
    /** The getType method returns the type.
     @return returns type.
     */
    public String getType() { return type; }
    /** The getStart method returns the time meeting will start.
     @return returns the start time.
     */
    public Timestamp getStart() {
        return start;
    }
    /** The getEnd method returns the time meeting will end.
     @return returns the end time.
     */
    public Timestamp getEnd() {
        return end;
    }
    /** The getCreateDate method returns the time meeting was first set up.
     @return returns the date meeting was created.
     */
    public Timestamp getCreateDate() {
        return createDate;
    }
    /** The getCreatedBy method returns the user that created the meeting.
     @return returns user that created meeting.
     */
    public String getCreatedBy() {
        return createdBy;
    }
    /** The getLastUpdate method returns the time that appointment was last updated.
     @return The last time appointment was updated.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    /** The getLastUpdateBy method returns the user that last updated meeting.
     @return The last user that updated the meeting.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /** The getCustomedID method returns the customer id associated with meeting.
     @return customers ID.
     */
    public int getCustomerID() { return customerID; }
    /** The getUserID method returns the userid.
     @return UserID.
     */
    public int getUserID() { return userID; }
    /** The getContactID method returns the contactID.
     @return ContactID.
     */
    public int getContactID() { return contactID; }

    /** The addAppointment method adds appointment objects to a list.
     @param newAppointment is an appointment object that will be added to a list.
     */
    public static void addAppointment(appointment newAppointment) {
        allAppointments.add(newAppointment);
    }



    /** The getContactName method returns the contact name.
     @return contacts name.
     */
    public String getContactName() { return contactName; }
    /** The getLocalStart method returns the local start time of appointment.
     @return localStart.
     */
    public String getLocalStart() { return localStart; }
    /** The getLocalEnd method returns the local end time of appointment.
     @return localEnd.
     */
    public String getLocalEnd() { return localEnd; }
    /** The getStart method returns the local am or pm start time of appointment.
     @return startAMPM.
     */
    public String getStartAMPM() { return startAMPM; }
    /** The getStart method returns the local am or pm end time of appointment.
     @return endAMPM.
     */
    public String getEndAMPM() { return endAMPM; }
    /** The deleteAppointment method deletes an object within the allAppointments observable list.
     @param selectProduct is the appointment object that will be removed from the list.
     @return returns true if appointment object has been removed.
     */
    public static boolean deleteAppointment(appointment selectProduct){
        if (allAppointments.contains(selectProduct)){
            allAppointments.remove(selectProduct);
            return true;
        }
        else {
            return false;
        }
    }

}
