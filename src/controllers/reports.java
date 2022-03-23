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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointment;
import model.contact;
import model.customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.appointment.getAllAppointments;
import static model.contact.getAllContacts;
import static model.customer.getAllCustomers;
/** The reports class is a controller that show appointment reports.*/

public class reports implements Initializable {
    @FXML
    private TableColumn<?, ?> CustomerIDapp;

    @FXML
    private TableColumn<?, ?> appointmentID;

    @FXML
    private TableView<appointment> appointmentsTable;

    @FXML
    private Button buttonReturn;

    @FXML
    private TableColumn<?, ?> contact;

    @FXML
    private ComboBox<model.contact> contactComboBox;

    @FXML
    private ComboBox<customer> customerComboBox;

    @FXML
    private TableColumn<?, ?> description;

    @FXML
    private TableColumn<?, ?> endDateAndTime;

    @FXML
    private TableColumn<?, ?> location;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private TableColumn<?, ?> startDateAndTime;

    @FXML
    private TableColumn<?, ?> title;

    @FXML
    private Label totalByCustomer;

    private static ObservableList<appointment> filteredAppointments = FXCollections.observableArrayList();

    private static ObservableList<String> filteredType = FXCollections.observableArrayList();

    private static ObservableList<String> months = FXCollections.observableArrayList();


    @FXML
    private Label totalBymonth;

    @FXML
    private TableColumn<?, ?> type;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TableColumn<?, ?> userID;

    @FXML
    void onContactComboBox(ActionEvent event) {
        filteredAppointments.clear();
         int id = contactComboBox.getSelectionModel().getSelectedItem().getContactID();
        for (appointment object:
                getAllAppointments()) {
            if (object.getContactID() == id){
                filteredAppointments.add(object);

            }

        }
        appointmentsTable.setItems(filteredAppointments);

    }

    @FXML
    void onCustomerComboBox(ActionEvent event) {
        int id = customerComboBox.getSelectionModel().getSelectedItem().getCustomerID();
        int count = 0;
        for (appointment object:
             getAllAppointments()) {
            if (object.getCustomerID() == id){
                count = count + 1;
            }

        }
        totalByCustomer.setText(String.valueOf(count));
    }

    @FXML
    void onMonthComboBox(ActionEvent event) {
        String stringMonths = monthComboBox.getSelectionModel().getSelectedItem();
        String arrayMonths[] = stringMonths.split(" ");
        int IntMonths = Integer.parseInt(arrayMonths[0]);
        int countType = 0;

        if (typeComboBox.getSelectionModel().getSelectedItem() != null) {
            for (appointment object:
                 getAllAppointments()) {
             String stringStart = String.valueOf(object.getStart());
             String arrayStart[] = stringStart.split("-");
             int intStart = Integer.parseInt(arrayStart[1]);

             if (intStart == IntMonths && typeComboBox.getSelectionModel().getSelectedItem().equals(object.getType())){
                 countType = countType + 1;
             }

             System.out.println(monthComboBox.getSelectionModel().getSelectedItem());
             System.out.println(object.getType());


            }
        }
        totalBymonth.setText(String.valueOf(countType));

    }

    @FXML
    void onReturn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/mainScreen.fxml"));
        Stage Window = (Stage) buttonReturn.getScene().getWindow();
        Window.setScene(new Scene(root));

    }

    @FXML
    void onTypeComboBox(ActionEvent event) {
        int countType = 0;
        if (monthComboBox.getSelectionModel().getSelectedItem() != null){
        String stringMonths = monthComboBox.getSelectionModel().getSelectedItem();
        String arrayMonths[] = stringMonths.split(" ");
        int IntMonths = Integer.parseInt(arrayMonths[0]);


        if (typeComboBox.getSelectionModel().getSelectedItem() != null) {
            for (appointment object:
                    getAllAppointments()) {
                String stringStart = String.valueOf(object.getStart());
                String arrayStart[] = stringStart.split("-");
                int intStart = Integer.parseInt(arrayStart[1]);

                if (intStart == IntMonths && typeComboBox.getSelectionModel().getSelectedItem().equals(object.getType())){
                    countType = countType + 1;
                }

                System.out.println(monthComboBox.getSelectionModel().getSelectedItem());
                System.out.println(object.getType());


            }
        }


    }
        totalBymonth.setText(String.valueOf(countType));
    }
















    /** The initialize method for the reports controller sets combo boxes and the appointment table.
       A lambda foreach is used to more efficiently filter appointment Types.*/




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateAndTime.setCellValueFactory(new PropertyValueFactory<>("startAMPM"));
        endDateAndTime.setCellValueFactory(new PropertyValueFactory<>("endAMPM"));
        CustomerIDapp.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));

    contactComboBox.setItems(getAllContacts());

    customerComboBox.setItems(getAllCustomers());
    months.add("1 January");months.add("2 February");months.add("3 March");months.add("4 April");months.add("5 May");months.add("6 June");
    months.add("7 July");months.add("8 August");months.add("9 September");months.add("10 October");months.add("11 November");months.add("12 December");
    monthComboBox.setItems(months);

    getAllAppointments().forEach(object ->{if (!filteredType.contains(object.getType())){
        filteredType.add(object.getType()); } } );   //lambda



    typeComboBox.setItems(filteredType);
    }
}
