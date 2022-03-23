package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.appointment;
import model.country;
import model.customer;
import model.division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static controllers.mainScreen.getSelectedCustomer;
import static controllers.mainScreen.selectedCustomer;
import static databases.appointmentQuery.selectAppointments;
import static databases.contactQuery.selectAllContacts;
import static databases.countriesQuery.selectAllCountries;
import static databases.customerQuery.*;
import static databases.divisionQuery.selectAllDivisions;
import static databases.usersQuery.selectAllUsers;
import static model.appointment.getAllAppointments;
import static model.country.getAllCountries;
import static model.customer.getAllCustomers;
import static model.division.getAllDivisions;
import static utilities.JDBC.closeConnection;
import static utilities.JDBC.openConnection;
/** The updateCustomers class is a controller that adds and updates customer data in the data base.*/
public class updateCustomers implements Initializable {
    @FXML
    private TextField CustomerAddress;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField createDate;

    @FXML
    private TextField createdBy;

    @FXML
    private TextField customerID;

    @FXML
    private Label customerLabel;

    @FXML
    private TextField customerName;


    @FXML
    private TextField lastUpdate;

    @FXML
    private TextField lastUpdatedBy;

    @FXML
    private TextField phoneNumberS;

    @FXML
    private TextField postalCode;

    @FXML
    void onAdd(ActionEvent event) throws SQLException {

        String ErrorMessage = "Make sure customer name, address, postal code, and phone number is filled. Also make sure country and division is selected.";
        try {
        String name = customerName.getText();

        String address = CustomerAddress.getText();
        String postal = postalCode.getText();
        String phoneNumber = phoneNumberS.getText();
            if (name.isEmpty() || address.isEmpty() || postal.isEmpty() || phoneNumber.isEmpty() || divisionComboBox.getValue() ==null)  {
                throw new NumberFormatException();
            }

            int division = divisionComboBox.getValue().getDivisionID();

        openConnection();
            if (selectedCustomer != null) {
                int customer_ID = Integer.parseInt(customerID.getText());
                updateCustomersDB(customer_ID, name,division,address,postal, phoneNumber);
            }
            else {
        insertCustomer(name, division,address,postal,phoneNumber);
            }
        getAllCustomers().clear();
        selectAllCustomers();
        closeConnection();

        Parent root = FXMLLoader.load(getClass().getResource("../views/mainScreen.fxml"));
        Stage Window = (Stage) addButton.getScene().getWindow();
        Window.setScene(new Scene(root));

        }
        catch (NumberFormatException | IOException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText("Input failed");
            alert.setContentText(ErrorMessage);
            alert.showAndWait();
        }



    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/mainScreen.fxml"));
        Stage Window = (Stage) cancelButton.getScene().getWindow();
        Window.setScene(new Scene(root));


    }

    @FXML
    private ComboBox<country> countryComboBox;

    @FXML
    private ComboBox<division> divisionComboBox;

    @FXML
    void onCountry(ActionEvent event) {
       

        ObservableList<division> filterDivisions = FXCollections.observableArrayList();
        countryComboBox.getSelectionModel().getSelectedItem().getCountryID();
        int count = getAllDivisions().size();
        for (int i = 0; i < count; i++) {
            if (getAllDivisions().get(i).getCountryID() == countryComboBox.getSelectionModel().getSelectedItem().getCountryID()) {
                filterDivisions.add(getAllDivisions().get(i));


            }

        }


        divisionComboBox.setItems(filterDivisions);

    }

    @FXML
    void onDivision(ActionEvent event) {



    }










    /** The initialize method of the updateCustomers class sets the page up depending of if the user is updating or adding to the database.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        countryComboBox.setItems(getAllCountries());
        customer selectedCustomer = getSelectedCustomer();
        if (selectedCustomer != null) {
            customerLabel.setText("Update Customer");
            addButton.setText("Update");

            customerID.setText(String.valueOf(selectedCustomer.getCustomerID()));
            customerName.setText(selectedCustomer.getCustomerName());
            postalCode.setText(selectedCustomer.getPostalCode());
            createDate.setText(String.valueOf(selectedCustomer.getCreateDate()));
            createdBy.setText(selectedCustomer.getCreatedBy());
            CustomerAddress.setText(selectedCustomer.getAddress());
            lastUpdate.setText(String.valueOf(selectedCustomer.getLastUpdate()));
            phoneNumberS.setText(selectedCustomer.getPhone());
            lastUpdatedBy.setText(selectedCustomer.getLastUpdatedBy());



            int c = getAllDivisions().size();
            int divisionID = 0;

            for (int i = 0; i < c; i++) {
                if (getAllDivisions().get(i).getDivisionID() == selectedCustomer.getDivisionID()) {
                    divisionID = i;
                }


            }


            division sel = getAllDivisions().get(divisionID);

            int count = getAllCountries().size();
            int countryID = 0;
            for (int i = 0; i < count; i++) {
                if (getAllCountries().get(i).getCountryID() == sel.getCountryID()) {
                    countryID = i;
                }
            }
            country selC = getAllCountries().get(countryID);


            divisionComboBox.setValue(sel);
            countryComboBox.setValue(selC);

            ObservableList<division> filterDivisions = FXCollections.observableArrayList();

            

            for (int i = 0; i < c; i++) {
                if (getAllDivisions().get(i).getCountryID() == countryComboBox.getSelectionModel().getSelectedItem().getCountryID()) {
                    filterDivisions.add(getAllDivisions().get(i));


                }

            }


            divisionComboBox.setItems(filterDivisions);


        }

    }
}
