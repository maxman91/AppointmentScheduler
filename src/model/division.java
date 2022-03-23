package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**The division class is a model that receives division table data from the database.*/
public class division {
    private int countryID;
    private int divisionID;
    private String division;


    private static ObservableList<division> allDivisions = FXCollections.observableArrayList();
    /** Constructor that creates division objects
     * @param divisionID divisions id.
     * @param division division name.
     * @param countryID country Id.
     * */
    public division(int divisionID, String division, int countryID){
        this.countryID = countryID;
        this.division = division;
        this.divisionID = divisionID;

    }
    /**Method that gets all divisions.
     @return allDivisions.
     */
    public static ObservableList<division> getAllDivisions() { return allDivisions; }

    public int getCountryID() {
        return countryID;
    }
    public int getDivisionID() {return divisionID;}
    public String getDivision() {return division;}

    /**Method that adds division to observable list.
     @param newDivision division object to be added.
     */
    public static void addDivision(division newDivision) {
        allDivisions.add(newDivision);
    }

    /**Method that returns division string.
     @return division.
     */
    public String toString() {
        return (division);
    }
}
