package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.appointment;
import model.contact;
import model.customer;
import model.user;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ResourceBundle;

import static controllers.mainScreen.getSelectedAppointment;
import static controllers.mainScreen.getSelectedCustomer;
import static databases.appointmentQuery.*;
import static databases.customerQuery.insertCustomer;
import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;
import static java.time.ZonedDateTime.now;
import static model.appointment.getAllAppointments;
import static model.contact.getAllContacts;
import static model.country.getAllCountries;
import static model.customer.getAllCustomers;
import static model.user.getAllUsers;
import static utilities.JDBC.closeConnection;
import static utilities.JDBC.openConnection;
import static utilities.timeConvert.timeOfDay;
/** The updateAppointments class is a controller that updates and adds appointments to the data base. */
public class updateAppointments implements Initializable  {
    @FXML
    private TextField PreviousAppEnd;

    @FXML
    private TextField PreviousAppStart;
    @FXML
    private Button addbutton;

    @FXML
    private TextField appointmentIDText;

    @FXML
    private Label appointmentLabel;

    @FXML
    private Button cancel;

    @FXML
    private ComboBox<contact> contactComboBox;

    @FXML
    private ComboBox<customer> customerIDcombo;

    @FXML
    private DatePicker date;

    @FXML
    private TextField description;

    @FXML
    private ComboBox<String> endTimeComboBox;

    @FXML
    private TextField location;

    @FXML
    private ComboBox<String> startTime;

    @FXML
    private TextField title;

    @FXML
    private TextField type;

    @FXML
    private ComboBox<user> userIDCombo;

    ObservableList<String> allTimes = FXCollections.observableArrayList();
    ObservableList<String> EndTimes = FXCollections.observableArrayList();

    @FXML
    void onAdd(ActionEvent event) {
        String ErrorMessage = "Make sure title, description, location, and type is filled. Also make sure user ID, customer ID and Contact is selected along with start, end time and date.";
        try {
        String appointmentTitle = title.getText();
        String appointmentDescription = description.getText();
        String appointmentLocation = location.getText();
        String appointmentType = type.getText();


        if (appointmentTitle.isEmpty() || appointmentDescription.isEmpty() || appointmentLocation.isEmpty() || appointmentType.isEmpty() || endTimeComboBox.getValue() ==null || date.getValue() == null || customerIDcombo.getValue() == null || userIDCombo.getValue() == null || contactComboBox.getValue() == null)  {
                throw new NumberFormatException();
        }
        String startTimeDB = startTime.getValue();
        String endTime = endTimeComboBox.getValue();
        LocalDate dateDB = date.getValue();
        int customerID = customerIDcombo.getValue().getCustomerID();
        int userID = userIDCombo.getValue().getUserID();
        int contactID = contactComboBox.getValue().getContactID();
        appointment selectedAppointment = getSelectedAppointment();
        if (selectedAppointment != null) {


            int appointmentID = selectedAppointment.getAppointmentID();
            openConnection();
            if (updateAppointmentsDB(appointmentID ,appointmentTitle, appointmentDescription,appointmentLocation,appointmentType,startTimeDB,endTime,dateDB,customerID,userID,contactID) == false){
                ErrorMessage = "Time given conflicts with customers scheduled appointments.";
                closeConnection();
                throw new NumberFormatException();
            }


            getAllAppointments().clear();
            selectAppointments();
            closeConnection();

        }
        else {


        openConnection();
        if (insertAppointment(appointmentTitle, appointmentDescription,appointmentLocation,appointmentType,startTimeDB,endTime,dateDB,customerID,userID,contactID) == false){
            ErrorMessage = "Time given conflicts with customers scheduled appointments.";
            closeConnection();
            throw new NumberFormatException();

        }


        getAllAppointments().clear();
        selectAppointments();
        closeConnection();
        }
        Parent root = FXMLLoader.load(getClass().getResource("../views/mainScreen.fxml"));
        Stage Window = (Stage) addbutton.getScene().getWindow();
        Window.setScene(new Scene(root));



        }
        catch (NumberFormatException | IOException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText("Input failed");
            alert.setContentText(ErrorMessage);
            alert.showAndWait();
        }
    }



    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/mainScreen.fxml"));
        Stage Window = (Stage) cancel.getScene().getWindow();
        Window.setScene(new Scene(root));

    }

    @FXML
    void onCombo(ActionEvent event) {

    }

    @FXML
    void onContact(ActionEvent event) {

    }

    @FXML
    void onCustomerID(ActionEvent event) {

    }

    @FXML
    void onDate(ActionEvent event) {

    }

    @FXML
    void onEndDate(ActionEvent event) {

    }

    @FXML
    void onStartTime(ActionEvent event) {

       ZoneOffset offset  = now().getOffset();

        String offsets = String.valueOf(offset);

        String offsetsSplit[] = offsets.split(":");

        int difference = Integer.parseInt(offsetsSplit[0]);





       String startingTime = startTime.getSelectionModel().getSelectedItem();
       EndTimes.clear();
       int indexAllTimes = allTimes.indexOf(startingTime);
       int c = 56;
       String TMPMTIMES = allTimes.get(0);
       String takeapartampm[] = TMPMTIMES.split(" ");

        String timeAMPM = takeapartampm[1];

        if (timeAMPM.equals("AM")) {
            timeAMPM = "PM";
        }
        else {
            timeAMPM = "AM";
        }

        for (int i = indexAllTimes + 1; i < c ; i++) {
            EndTimes.add(allTimes.get(i));

            String getAMPM[] = allTimes.get(i).split(" ");

            timeAMPM = getAMPM[1];
        }
        int h = 10;

        int s = -4 - difference;

        h = h - s;

        if (h>12) {
            h = h -12;
        }



        EndTimes.add(h+":00 "+timeAMPM);

        endTimeComboBox.setItems(EndTimes);


    }

    @Override
    /** The initialize method of the updateAppointments class sets combo boxes and user data depending of if user is adding or updating appointments. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDcombo.setItems(getAllCustomers());
        userIDCombo.setItems(getAllUsers());
        contactComboBox.setItems(getAllContacts());

        ZoneOffset offset  = now().getOffset();

        String offsets = String.valueOf(offset);

        String offsetsSplit[] = offsets.split(":");

        int difference = Integer.parseInt(offsetsSplit[0]);




        int c = 56;
        int h = 8;

        int s = -4 - difference;

        h = h - s;
        int m = 0;
        String min = "00";
        String time = "AM";
        for (int i = 0; i < c ; i++) {
            if (h >= 12 && h != 24){
                if (time.equals("AM")){
                time = "PM";
                } else {
                    time = "AM";
                }
            }

            if (h == 24){
                time = "AM";
            }

            if (h >= 13){
                h = h -12;

            }
            if (m == 0){
                min = "00";
            } else {
                min = Integer.toString(m);
            }
            allTimes.add(h+":"+min+" "+time);

            m = m + 15;
            if (m == 60) {
                m = 0;
                h++;
            }




        }
        startTime.setItems(allTimes);

        appointment selectedAppointment = getSelectedAppointment();
        if (selectedAppointment != null) {
            appointmentLabel.setText("Update Appointments");
            addbutton.setText("Update");
            appointmentIDText.setText(String.valueOf(selectedAppointment.getAppointmentID()));
            title.setText(selectedAppointment.getTitle());
            description.setText(selectedAppointment.getTitle());
            type.setText(selectedAppointment.getType());
            location.setText(selectedAppointment.getLocation());
            PreviousAppEnd.setText(selectedAppointment.getEndAMPM());
            PreviousAppStart.setText(selectedAppointment.getStartAMPM());
            String dateStart = String.valueOf(selectedAppointment.getStart());
            String dateTime[] = dateStart.split(" ");


            date.setValue(LocalDate.parse(dateTime[0]));
            for (contact number: getAllContacts()) {
                if (number.getContactID() == selectedAppointment.getContactID()){
                    contactComboBox.setValue(number);
                }
                
            }
            for (customer number: getAllCustomers()) {
                if (number.getCustomerID() == selectedAppointment.getCustomerID()){
                    customerIDcombo.setValue(number);
                }

            }
            for (user number: getAllUsers()) {
                if (number.getUserID() == selectedAppointment.getUserID()){
                    userIDCombo.setValue(number);
                }

            }



            startTime.setValue(timeOfDay(selectedAppointment.getLocalStart()));
            endTimeComboBox.setValue(timeOfDay(selectedAppointment.getLocalEnd()));
            System.out.println(selectedAppointment.getStart());
            String startingTime = startTime.getSelectionModel().getSelectedItem();
            int indexAllTimes = allTimes.indexOf(startingTime);







            String TMPMTIMES = allTimes.get(0);
            String takeapartampm[] = TMPMTIMES.split(" ");

            String timeAMPM = takeapartampm[1];

            if (timeAMPM.equals("AM")) {
                timeAMPM = "PM";
            }
            else {
                timeAMPM = "AM";
            }
            for (int i = indexAllTimes + 1; i < c ; i++) {
                EndTimes.add(allTimes.get(i));
                String getAMPM[] = allTimes.get(i).split(" ");

                timeAMPM = getAMPM[1];

            }
            h = 10;
            s = -4 - difference;

            h = h - s;

            if (h>12) {
                h = h -12;
            }

            EndTimes.add(h+":00 "+timeAMPM);

            endTimeComboBox.setItems(EndTimes);

        }




    }
}
