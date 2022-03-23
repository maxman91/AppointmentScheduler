package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import static databases.appointmentQuery.selectAppointments;
import static databases.contactQuery.selectAllContacts;
import static databases.countriesQuery.selectAllCountries;
import static databases.customerQuery.selectAllCustomers;
import static databases.divisionQuery.selectAllDivisions;
import static databases.usersQuery.selectAllUsers;
import static java.time.ZonedDateTime.now;
import static utilities.JDBC.closeConnection;
import static utilities.JDBC.openConnection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        openConnection();
        selectAllUsers();
        selectAllContacts();
        selectAllCustomers();
        selectAppointments();
        selectAllCountries();
        selectAllDivisions();
        closeConnection();

        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        //primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
