/*
*
* Project 7: Inventory Management
* Caleb Davenport & Roan Martin-Hayden
* EECS 1510-091: Dr. Ledgard
*
* Description:
* Main class that handles user input
* and provides the functions based on the input.
*
*/

package project7;

import java.util.*;
import javafx.collections.*;

public class InventoryManagement {
    public static final String INV_LOCATION = "Inventory\\Inventory.txt";
    public final static ObservableList<Entry> entryList
            = FXCollections.observableArrayList();
    //static ArrayList<Entry> entryList = new ArrayList<>();
    
    /**
     * Finds an entry and returns the location or -1 if not found
     * 
     * @param name Name to search for
     * @return Index location of the entry or -1 if not found
     */
    public static int findEntry(String name) {
        int location;
        
        //Normalize String
        
        
        // Not fuund value
        location = -1;
        
        // Search Algorithm
        for (int i = 0; i < entryList.size(); ++i) {
            if (customEquals(name, entryList.get(i).getName())) {
                location = i;
            }
        }
        
        return location;
    }
    
    /**
     * Adds Entry to the inventory array
     * 
     * @param name Name of entry
     * @param number Quantity of entry
     * @param notes Quantity of entry
     * 
     * @return Highest priority error if encountered, empty if success
     * 
     * TODO: Multiple Error Handling
     * TODO: Better Entry Overwrite
     */
    public static String addEntry(String name, String number, String notes) {
        String errMessage;
        int insertLoc;
    
        // Check for existing Entry
        // TODO: Update to handle errors better
//        int existingLoc = -1;
//        existingLoc = findEntry(name);
//        if (existingLoc != -1) {
//            // Error Handling code here
//        }

        errMessage = checkNameValidity(name);
        if (!"".equals(errMessage)) {
            errMessage = checkNumberValidty(number);
        }

        if ("".equals(errMessage)) {
            entryList.add(new Entry(name, number, notes));
            Collections.sort(entryList,
                            (Entry e1, Entry e2) -> e1.getName().compareTo(e2.getName()));
        }
        
        return errMessage;
    }
    
    /**
     * Checks that a name is formatted correctly
     * 
     * @param name Name to be tested
     * @return Highest priority error message, empty if OK
     */
    public static String checkNameValidity(String name) {
        String errMessage;
        
        errMessage = "";
        
        /// Tests, ordered by priority
        // Length Check
        if (name.length() == 0) {
            errMessage = "No name entered.";
        } else if (name.length() > 8) {
            errMessage = "Name is to long.";
        }
        
        return errMessage;
    }
    
    /**
     * Checks that a number is formatted correctly
     * 
     * @param number Number to be tested
     * @return Highest priority error message, empty if OK
     */
    public static String checkNumberValidty(String number) {
        String errMessage;
        
        errMessage = "";
        
        /// Tests, ordered by priority
        // Numerals Check
        if (!number.matches("[0-9]+\\.[0-9]*|[0-9]*\\.[0-9]+")) {
            errMessage = "Not a number.";
        }
        
        // Negativity Check
        if (number.matches("-")) {
            errMessage = "Can't have negative inventory.";
        }
        
        return errMessage;
    }
    
    /**
     * Returns whether strings are equal with custom qualifications
     * 
     * @param name1 First name to check
     * @param name2 Second name to check
     * @return They are equal
     */
    public static boolean customEquals(String name1, String name2) {
        
        // Ingore case
        name1 = name1.toLowerCase();
        name2 = name2.toLowerCase();
        
        // Ignore external whitespace
        name1 = name1.trim();
        name2 = name2.trim();
        
        return name1.equals(name2);
    }
    
}
