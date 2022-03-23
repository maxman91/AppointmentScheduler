package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**The country class is a model that receives country table data from the database.*/
public class country {
    private int countryID;
    private String country;


    private static ObservableList<country> allCountries = FXCollections.observableArrayList();
    /**The country method is a constructor for the country model class.
     @param country the country.
     @param countryID the country ID. */
    public country(int countryID, String country){
        this.countryID = countryID;
        this.country = country;

    }
    /**The getAllCountries is a method that returns a list of all the countries.
      @return allCountries.
     */
    public static ObservableList<country> getAllCountries() { return allCountries; }
    /**The getCountryID is a method that returns the country ID.
     @return countryID.
     */
    public int getCountryID() {
        return countryID;
    }
    /**The getCountry is a method that returns the country.
     @return country.
     */
    public String getCountry() {return country;}

    /**The addCountry is a method that adds country object to allCountries Observable list.
     @param newCountry  the country object that gets added to allCountries Observable List.
     */
    public static void addCountry(country newCountry) {
        allCountries.add(newCountry);
    }

    @Override

    public String toString() {
        return (country);
    }


}
