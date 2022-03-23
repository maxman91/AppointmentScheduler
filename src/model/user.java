package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.util.Objects;
/**The user class is a model that receives user table data from the database.*/
public class user {
    private int id;
    private String name;
    private String password;
    private Timestamp date;
    private String creator;
    private Timestamp update;
    private String updater;
    private static ObservableList<user> allUsers = FXCollections.observableArrayList();
    private static ObservableList<user> currentUser = FXCollections.observableArrayList();





    // constructors and other methods omitted

    /**user constructor
      @param id the id of user.
     @param name the users name.
     @param password the users password.
     */
    public user(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;


    }



    /**Method that gets all users in a observableList.
      @return allUsers
     */
    public static ObservableList<user> getAllUsers() {
        return allUsers;
    }
    /**Method that gets current User.
     @return currentUser.
     */
    public static ObservableList<user> getCurrentUser() {
        return currentUser;
    }

    /**Method that gets username.
     @return name
     */
    public String getUsername() {
        return name;
    }
    /**Method that gets user id.
     @return id
     */
    public int getUserID() {
        return id;
    }
    /**Method that gets user password.
     @return password.
     */
    public String getUserPassword() {
        return password;
    }

    /**Method that adds user to currentUser list.
     @param newCurrentUser the current user.
     */
    public static void addCurrentUser(user newCurrentUser) {currentUser.add(newCurrentUser);}

    /**Method that adds user to allusers observable list.
     @param newUser the user to be added.
     */
    public static void addUser(user newUser) {
        allUsers.add(newUser);
    }

    @Override

    public String toString() {
        return (String.valueOf(id)+". "+name);
    }





}
