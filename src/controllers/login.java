package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.user;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import static model.appointment.getAllAppointments;
import static model.user.*;

/**This login class is a controller that handles the login page. enterUserName method checks if username and password is correct.
 a lambda expression is used to loop through all the appointments to see if user has any upcoming appointment.*/
public class login implements Initializable {
    @FXML
    private Label mainLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label userLabel;

    @FXML
    private TextField userName;
    @FXML
    private Button enterButton;

    @FXML
    private Label countryLabel;



    @FXML
    private TextField userPassword;

    boolean check;

    String Error;
    String ErrorMessage;


    @FXML
    void enterUsername(ActionEvent event) throws SQLException, IOException {
        String users = userName.getText();
        String pass = userPassword.getText();
        boolean check = false;
        int count = getAllUsers().size();
        for (int i = 0; i < count; i++) {
           if (getAllUsers().get(i).getUsername().equals(users) && getAllUsers().get(i).getUserPassword().equals(pass)) {
                int currentUserID = getAllUsers().get(i).getUserID();
                String currentUserName = getAllUsers().get(i).getUsername();
                String currentPassword = getAllUsers().get(i).getUserPassword();
               addCurrentUser(new user(currentUserID, currentUserName, currentPassword));
               int UserId = getCurrentUser().get(0).getUserID();
               System.out.println(assertNotNull(getCurrentUser()));
               AtomicReference<String> Message = new AtomicReference<>("You have no appointments coming up in the next 15 minutes.");
               getAllAppointments().forEach((n)-> {          //lambda expression
                   if (n.getUserID() == UserId){
                       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                       LocalDateTime dateTime = LocalDateTime.parse(n.getLocalStart(), formatter);
                       int minutes = Math.toIntExact(ChronoUnit.MINUTES.between(LocalDateTime.now(), dateTime));
                       if (minutes >= 0 && minutes < 15){
                           Message.set("Appointment number "+ n.getAppointmentID()+", scheduled to start on "+n.getLocalStart()+" is coming up in less than 15 minutes.");
                       }

                        }
               });
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setHeaderText("Upcoming Appointments");
               alert.setContentText(Message.get());
               alert.showAndWait();

               String Path = "login_activity.txt";
               DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

               BufferedWriter log = new BufferedWriter(new FileWriter(Path, true));
               log.append("Login Attempt by USER: '" + users + "' was successful at " +ZonedDateTime.now(ZoneOffset.UTC).format(format)+ " UTC time.\n");

               log.flush();
               log.close();



               Parent root = FXMLLoader.load(getClass().getResource("../views/mainScreen.fxml"));
               Stage Window = (Stage) enterButton.getScene().getWindow();
               Window.setScene(new Scene(root));
               check = true;


           }

        }

        if (check == false) {
            String Path = "login_activity.txt";
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            BufferedWriter log = new BufferedWriter(new FileWriter(Path, true));
            log.append("Login Attempt by USER: '" + users + "' failed at " +ZonedDateTime.now(ZoneOffset.UTC).format(format)+ " UTC time.\n");

            log.flush();
            log.close();



            Alert alert = new Alert(Alert.AlertType.INFORMATION,  ErrorMessage);
        alert.setHeaderText(Error);
        alert.showAndWait();}

    }

    private boolean assertNotNull(ObservableList<user> userNow) {
        if (userNow.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    /**This is the initialize method for the login class.
       is sets the labels, button and error messages text from the Language properties files. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryLabel.setText(ZoneId.systemDefault().toString());
        ResourceBundle rb = ResourceBundle.getBundle("LanguageProperties/languages", Locale.getDefault());
        mainLabel.setText(rb.getString("login"));
        passwordLabel.setText(rb.getString("password"));
        userLabel.setText(rb.getString("username"));
        enterButton.setText(rb.getString("enter"));
        Error = rb.getString("error");
        ErrorMessage = rb.getString("errorMessage");
        userName.setPromptText(rb.getString("username"));
        userPassword.setPromptText(rb.getString("password"));



    }
}
