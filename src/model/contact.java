package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**The contact class is a model that receives contact table data from the database.*/
public class contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    private static ObservableList<contact> allContacts = FXCollections.observableArrayList();
    /**the contact constructor creates contact objects.
      @param contactName The contacts Name.
     @param contactID The contacts ID.
     @param contactEmail The contacts email.
     */
    public contact(int contactID, String contactName, String contactEmail){
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
    /** The getAllContacts method returns an observable list of contacts objects
      @return  allContacts.
     */
    public static ObservableList<contact> getAllContacts() { return allContacts; }
    /** The getContactID method returns the contact id.
     @return  contactID.
     */
    public int getContactID() {
        return contactID;
    }
    /** The getContactName method returns the contacts name.
     @return  contactName.
     */
    public String getContactName() {return contactName;}
    /** The getContactEmail method returns the contacts email.
     @return  contactEmail.
     */
    public String getContactEmail() { return contactEmail;}
    /** The addContact method adds contact object to an observable list.
     @param newContact will be added to allContacts observable list.
     */
    public static void addContact(contact newContact) {
        allContacts.add(newContact);
    }

    @Override

    public String toString() {
        return (contactName);
    }
}
