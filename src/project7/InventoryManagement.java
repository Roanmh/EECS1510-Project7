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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.*;

public class InventoryManagement {
    public static final String INV_LOCATION = "Inventory\\Inventory.txt";
    public final static ArrayList<Entry> entryList = new ArrayList<>();
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
     * TODO: Force add/Overwrite entry mechanic
     * TODO: Better Entry Overwrite
     */
    public static EntryReport addEntry(String name, String number, String notes) {
        String errMessage;
        int foundIndex;
        Entry attemptedEntry;
        Entry foundEntry;
    
        // Check for existing Entry
        foundIndex = findEntry(name);
        foundEntry = entryList.get(foundIndex);

        // Name based Error Message creation
        errMessage = checkNameValidity(name);
        if (!"".equals(errMessage)) {
            errMessage = checkNumberValidty(number);
        }

        // If no errors of conflicting entry, make enty and sort
        if ("".equals(errMessage) || foundEntry.exists()) {
            entryList.add(new Entry(name, number, notes));
            //customSort(entryList);
        }
        
        attemptedEntry = new Entry(name, number, notes);
        return new EntryReport(attemptedEntry, foundEntry, foundIndex, errMessage);
    }
    
    /**
     * Finds entry by name and deletes it.
     * 
     * @param name name of entry to delete
     * @return Error message if occurred
     */
    public static String deleteEntry(String name) {
        int index;
        
        index = findEntry(name);
        if (index == -1) return "Entry not found.";
        else return deleteEntry(index);
        
        // Could ha done this
//        return (findEntry(name) == -1) ? "blargh" : deleteEntry(findEntry(name));
    }

    /**
     * Deletes entry at the given index.
     * 
     * @param index location of entry to delete
     * @return Error message if occurred
     */
    public static String deleteEntry(int index) {
        if (index > 0 && index < entryList.size()) {
            entryList.remove(index);
            return "";
        } else {
            return "Index out of range.";
        }
    }
    
    /**
     * 
     * @param pathStr
     * @return 
     */
    public static String loadInventory(String pathStr) {
        String errMessage;
        File file;
        Scanner invIn;
        String[] entryVals;
        
        errMessage = "";
        file = new File(pathStr);
        try {
            invIn = new Scanner(file);
            while (invIn.hasNextLine()) {
                entryVals = invIn.nextLine().split("\t");
                addEntry(entryVals[0], entryVals[1], entryVals[2]);
            }
        } catch (FileNotFoundException ex) {
            errMessage = "File not found.";
        }
        
        return errMessage;
    }
    
    public static String saveInventory(String pathStr) {
        String errMessage;
        PrintStream invOut;
        
        errMessage = "";
        try {
            invOut = new PrintStream(INV_LOCATION);
            for (Entry entryOut : entryList) {
                System.out.printf("%s\t%s\t%s", entryOut.getName(),
                                  entryOut.getNumber(), entryOut.getNotes());
            }
            invOut.close();
        } catch (FileNotFoundException ex) {
            errMessage = "File not found.";
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
    
    private static void customSort(ArrayList<Entry> list) {
        Collections.sort(list,
                         (Entry e1, Entry e2) ->
                                e1.getName().compareTo(e2.getName()));

    }
    
    /**
     * Returns a filtered version of list given the string
     * @param filtStr String to filter the text by
     * @return ObserableList with only the elements to display
     */
    public static ObservableList<Entry> filteredEntries(String filtStr) {
        ObservableList<Entry> filtList;
        
        filtList = FXCollections.observableArrayList();
        for (Entry e : entryList) {
            if (e.getName().contains(filtStr)) filtList.add(e);
        }
        
        return filtList;
    }
}
