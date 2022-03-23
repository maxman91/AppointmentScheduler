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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointment;
import model.customer;
import model.user;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static databases.appointmentQuery.deleteAppointmentDB;
import static databases.customerQuery.*;
import static model.appointment.deleteAppointment;
import static model.appointment.getAllAppointments;
import static model.customer.deleteCustomer;
import static model.customer.getAllCustomers;
import static model.user.getAllUsers;
import static model.user.getCurrentUser;
import static utilities.JDBC.closeConnection;
import static utilities.JDBC.openConnection;
/**This mainScreen class is a controller that handles the login page.*/
public class mainScreen implements Initializable {
    public static customer selectedCustomer;
    private static appointment selectedAppointment;
    @FXML
    private TableColumn<?, ?> CustomerIDapp;

    @FXML
    private RadioButton all;

    @FXML
    private TableColumn<?, ?> appointmentID;

    @FXML
    private TableColumn<?, ?> phone;

    @FXML
    private Button appointmentsAdd;

    @FXML
    private Button appointmentsDelete;

    @FXML
    private TableView<appointment> appointmentsTable;

    @FXML
    private Button appointmentsUpdate;

    @FXML
    private TableColumn<?, ?> contact;

    @FXML
    private TableColumn<?, ?> createDate;

    @FXML
    private TableColumn<?, ?> createdBy;

    @FXML
    private Button customerAdd;

    @FXML
    private TableColumn<?, ?> customerAddress;

    @FXML
    private Button customerDelete;

    @FXML
    private TableColumn<?, ?> customerID;

    @FXML
    private TableColumn<?, ?> customerName;

    @FXML
    private TableView<customer> customerTable;

    @FXML
    private Button customerUpdate;

    @FXML
    private TableColumn<?, ?> description;

    @FXML
    private TableColumn<?, ?> divisionID;

    @FXML
    private TableColumn<?, ?> endDateAndTime;

    @FXML
    private TableColumn<?, ?> lastUpdate;

    @FXML
    private TableColumn<?, ?> lastUpdateBy;

    @FXML
    private TableColumn<?, ?> location;

    @FXML
    private RadioButton monthly;

    @FXML
    private TableColumn<?, ?> postalCode;

    @FXML
    private TableColumn<?, ?> startDateAndTime;

    @FXML
    private TableColumn<?, ?> title;

    @FXML
    private TableColumn<?, ?> type;

    @FXML
    private TableColumn<?, ?> userID;

    @FXML
    private RadioButton weekly;

    @FXML
    private Button reportsButton;

    /**This is the setselectedCustomer method that selects a customer that the user selects
       @param selectedCustomer The customer user has selected.
     */

    public void setselectedCustomer(customer selectedCustomer)
    {
        mainScreen.selectedCustomer = selectedCustomer;
    }
    /**The getSelectedCustomer method returns the customer user has selected.
      @return the selected customer.
     */
    public static customer getSelectedCustomer()
    {
        return selectedCustomer;
    }
    /** The setSelected Appointment method selects an appointment that the user selects.
      @param selectedAppointment is the appointment the user selects
     */
    public void setselectedAppointment(appointment selectedAppointment)
    {
        mainScreen.selectedAppointment = selectedAppointment;
    }
    /**The getSelectedAppointment returns the selected appointment.
       @return returns the selectedAppointment */
    public static appointment getSelectedAppointment()
    {
        return selectedAppointment;
    }

    /**The on AppointmentsAdd method takes user to the updateAppointments page setting the setselectedAppointment method to null
      This will allow user to add new appointments rather than updating them.
       */
    @FXML
    void onAppointmentsAdd(ActionEvent event) throws IOException {
        setselectedAppointment(null);

        Parent root = FXMLLoader.load(getClass().getResource("../views/updateAppointments.fxml"));
        Stage Window = (Stage) appointmentsAdd.getScene().getWindow();
        Window.setScene(new Scene(root));




    }
    @FXML
    void onAll(ActionEvent event) {
        appointmentsTable.setItems(getAllAppointments());


    }

    @FXML
    void onReportsClick(ActionEvent event) throws IOException {
        appointmentsTable.setItems(getAllAppointments());
        Parent root = FXMLLoader.load(getClass().getResource("../views/reports.fxml"));
        Stage Window = (Stage) reportsButton.getScene().getWindow();
        Window.setScene(new Scene(root));

    }

    @FXML
    void onMonthly(ActionEvent event) {
        LocalDate current = LocalDate.now();
        LocalDate currentMonth = current.plusMonths(1);

        FilteredList<appointment> weekAppointments = new FilteredList<>(getAllAppointments());

        weekAppointments.setPredicate(row -> {

            LocalDate rowDate = row.getStart().toLocalDateTime().toLocalDate();

            return rowDate.isAfter(current.minusDays(1)) && rowDate.isBefore(currentMonth);
        });

        appointmentsTable.setItems(weekAppointments);

    }
    @FXML
    void onWeekly(ActionEvent event) {
        LocalDate current = LocalDate.now();

        LocalDate currentWeek = current.plusWeeks(1);

        FilteredList<appointment> monthAppointments = new FilteredList<>(getAllAppointments());
        monthAppointments.setPredicate(row -> {

            LocalDate rowDate = row.getStart().toLocalDateTime().toLocalDate();
            return rowDate.isAfter(current.minusDays(1)) && rowDate.isBefore(currentWeek);
        });

        appointmentsTable.setItems(monthAppointments);


    }

    @FXML
    void onappointmentsDelete(ActionEvent event) throws SQLException {
        appointment removeAppointment = appointmentsTable.getSelectionModel().getSelectedItem();







        if (removeAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this selected Appointment?\nAppointmentID: " + removeAppointment.getAppointmentID() +"\nType: " +removeAppointment.getType(), ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    deleteAppointment(removeAppointment);
                    appointmentsTable.setItems(getAllAppointments());
                    all.setSelected(true);
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "AppointmentID number "+removeAppointment.getAppointmentID() +", " + removeAppointment.getType() + " has been deleted");
                    alert1.showAndWait();
                    openConnection();


                    deleteAppointmentDB(removeAppointment.getAppointmentID());
                    closeConnection();

                    removeAppointment = null;


                }
            }
        else {Alert alert = new Alert(Alert.AlertType.INFORMATION, "Select appointment you want to delete.");
            alert.showAndWait(); }

    }

    @FXML
    void onappointmentsUpdate(ActionEvent event) throws IOException {
        appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        setselectedAppointment(selectedAppointment);

        if (selectedAppointment != null) {
            Parent root = FXMLLoader.load(getClass().getResource("../views/updateAppointments.fxml"));
            Stage Window = (Stage) customerUpdate.getScene().getWindow();
            Window.setScene(new Scene(root));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Select appointment you want to update.");
            alert.showAndWait();

        }

    }

    @FXML
    void oncustomerAdd(ActionEvent event) throws IOException {
       setselectedCustomer(null);
        Parent root = FXMLLoader.load(getClass().getResource("../views/updateCustomers.fxml"));
        Stage Window = (Stage) customerAdd.getScene().getWindow();
        Window.setScene(new Scene(root));

    }

    @FXML
    void oncustomerDelete(ActionEvent event) throws SQLException {
        customer removeCustomer = customerTable.getSelectionModel().getSelectedItem();
        boolean hasAppointments = false;







        if (removeCustomer != null) {
            int count = getAllAppointments().size();
            for (int i = 0; i < count; i++) {
                if (getAllAppointments().get(i).getCustomerID() == removeCustomer.getCustomerID() ) {
                    hasAppointments = true;


                }

            }
            if (hasAppointments == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Can not delete customers with appointments scheduled. Delete appointments associated with this customer first.");
                alert.showAndWait();

            }
            else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected customer?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                int deleteID = removeCustomer.getCustomerID();
                openConnection();


                deleteCustomerDB(deleteID);
                closeConnection();

                customerTable.setItems(getAllCustomers());

                deleteCustomer(removeCustomer);


                removeCustomer = null;


            }
        }}
        else {Alert alert = new Alert(Alert.AlertType.INFORMATION, "Select appointment you want to delete.");
            alert.showAndWait(); }


    }

    @FXML
    void oncustomerUpdate(ActionEvent event) throws IOException {
        customer selectedCustromer = customerTable.getSelectionModel().getSelectedItem();
        setselectedCustomer(selectedCustromer);

        if (selectedCustromer != null) {
            Parent root = FXMLLoader.load(getClass().getResource("../views/updateCustomers.fxml"));
            Stage Window = (Stage) customerUpdate.getScene().getWindow();
            Window.setScene(new Scene(root));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Select customer you want to update.");
            alert.showAndWait();

        }

    }


    @Override
    /** The initialize method of the mainScreen class sets the customer and appointments tables.*/
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(getAllCustomers());
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDate.setCellValueFactory(new PropertyValueFactory<>("localCreateDate"));
        createdBy.setCellValueFactory(new PropertyValueFactory<>("CreatedBy"));
        lastUpdate.setCellValueFactory(new PropertyValueFactory<>("localUpdate"));
        lastUpdateBy.setCellValueFactory(new PropertyValueFactory<>("LastUpdatedBy"));
        divisionID.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        appointmentsTable.setItems(getAllAppointments());
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

        ToggleGroup group = new ToggleGroup();
        weekly.setToggleGroup(group);
        monthly.setToggleGroup(group);
        all.setToggleGroup(group);
        all.setSelected(true);


    }
}
