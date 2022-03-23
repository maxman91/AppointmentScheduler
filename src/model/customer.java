package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
/**The customer class is a model that receives customer table data from the database.*/

public class customer {
    private int customerID;
    private String customerName;
    private String Address;
    private String postalCode;
    private String phone;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    private String localCreateDate;
    private String localUpdate;

    private static ObservableList<customer> allCustomers = FXCollections.observableArrayList();
    /** The customer method is constructor that creates customer objects.
    @param lastUpdatedBy user that updated customer.
     @param lastUpdate date that customers was last updated.
     @param createdBy user that created the customer entry.
     @param createDate date that customer was created.
     @param customerID the customer ID.
     @param Address the address of the customer.
     @param customerName customer name
     @param divisionID the divisionID of customer.
     @param localCreateDate the local time of createdate.
     @param localUpdate the local update time of user viewing the local update.
     @param phone the customer phone number.
     @param postalCode customer postal code.
     */
    public customer(int customerID, String customerName, String Address, String postalCode, String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate,String lastUpdatedBy,int divisionID ,String localCreateDate,String localUpdate ){
        this.customerID = customerID;
        this.customerName = customerName;
        this.Address = Address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
        this.localUpdate = localUpdate;
        this.localCreateDate = localCreateDate;


    }
    /** method that returns allCustomers observable list.
      @return allCustomers.
      */
    public static ObservableList<customer> getAllCustomers() {
        return allCustomers;
    }
    /** method that gets customer ID.
     @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }
    /** method that gets customer name.
     @return customerName.
     */
    public String getCustomerName() {
        return customerName;
    }
    /** method that gets address of customer.
     @return Address.
     */
    public String getAddress() {
        return Address;
    }
    /** method that gets postal code.
     @return postalCode.
     */
    public String getPostalCode() {
        return postalCode;
    }
    /** method that gets phone number.
     @return phone.
     */
    public String getPhone() {
        return phone;
    }
    /** method that gets create date.
     @return createDate.
     */
    public Timestamp getCreateDate() {
        return createDate;
    }
    /** method that gets created by.
     @return createdBy.
     */
    public String getCreatedBy() {
        return createdBy;
    }
    /** method that gets last update.
     @return lastUpdate
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    /** method that gets user that last updated customer record.
     @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /** method that gets division Id.
     @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }
    /** method that gets local created date.
     @return localCreateDate.
     */

    public String getLocalCreateDate() {
        return localCreateDate;
    }
    /** method that gets local update.
     @return localUpdate.
     */
    public String getLocalUpdate() {
        return localUpdate;
    }
    /** method that
     @return
     */

    public static void addCustomer(customer newCustomer) {
        allCustomers.add(newCustomer);
    }
    /** method that deletes customer object form observable list.
     @return true if successful.
     @param selectProduct the product to be deleted.
     */

    public static boolean deleteCustomer(customer selectProduct){
        if (allCustomers.contains(selectProduct)){
            allCustomers.remove(selectProduct);
            return true;
        }
        else {
            return false;
        }
    }

    @Override

    public String toString() {
        return (String.valueOf(customerID)+". " +customerName);
    }

}
