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
import javafx.collections.*;

public class InventoryManagement {
    private static String invLocation = "";
    private static final ArrayList<Entry> ENTRY_LIST = new ArrayList<>();
    private static String filterCriterion = "Name";
    public static enum forceBehavior { ORIGINAL, NEW };

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
        for (int i = 0; i < ENTRY_LIST.size(); ++i) {
            if (customEquals(name, ENTRY_LIST.get(i).getName())) {
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
    public static EntryReport checkAddEntry(String name, String number,
                                       String notes) {
        Entry attemptedEntry;
        ObservableList<Entry> nameMatches;
        ObservableList<Entry> wholeMatches;
        String nameErrorMessage;
        String numberErrorMessage;
    
        // Find Name Matches
        nameMatches = FXCollections.observableArrayList();
        for (Entry e : ENTRY_LIST) {
            if (customEquals(e.getName(), name)) { 
                nameMatches.add(e);
            }
        }
                
        // Find whole Matches
        wholeMatches = FXCollections.observableArrayList();
        for (Entry e : ENTRY_LIST) {
            if (customEquals(e.getName(), name) &&
                customEquals(e.getNumber(), number) &&
                customEquals(e.getNotes(), notes)) wholeMatches.add(e);
        }
                
        
        // Name based Error Message creation
        nameErrorMessage = checkNameValidity(name);
        numberErrorMessage = checkNumberValidty(number);

        attemptedEntry = new Entry(name, number, notes);
        
        return new EntryReport(attemptedEntry, nameMatches, wholeMatches,
                nameErrorMessage, numberErrorMessage);
    }
    
    /**
     * Adds entry (regardless of duplicate status).
     * 
     * @param name Name to be entered (depending on overwriteName if editing
     * existing entry)
     * @param number Number to be entered
     * @param notes Notes to be entered
     */
    public static void addEntry(String name, String number, String notes) {
        ENTRY_LIST.add(new Entry(name, number, notes));
        customSort(ENTRY_LIST);
    }
    
    public static void editEntry(Entry replacedEntry, String name,
                                  String number, String notes) {
        ENTRY_LIST.add(ENTRY_LIST.indexOf(replacedEntry), new Entry(name, number, notes));
        deleteEntry(replacedEntry);
        customSort(ENTRY_LIST);
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
        if (index > 0 && index < ENTRY_LIST.size()) {
            ENTRY_LIST.remove(index);
            return "";
        } else {
            return "Index out of range.";
        }
    }
    
        /**
     * Deletes entry as specified by the object itself
     * 
     * @param entry Object to delete
     * @return Error message if occurred
     */
    public static String deleteEntry(Entry entry) {
        String errMessage;
        
        errMessage = "";
        ENTRY_LIST.remove(entry);
        
        return errMessage;
    }
    
    /**
     * Loads entries from a file into the stored inventory
     * 
     * @param pathStr Path to load from
     * @return Error message if found
     */
    public static String loadInventory(String pathStr) {
        String errMessage;
        File file;
        Scanner invIn;
        String[] entryVals;
        
        invLocation = pathStr;
        
        ENTRY_LIST.clear();
        
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
    
    /**
     * Store inventory into specified file
     * 
     * @param pathStr Location at which to store file
     * @return Error message if found
     * 
     * TODO: Overwrite warning
     */
    public static String saveInventory(String pathStr) {
        String errMessage;
        PrintStream invOut;
        
        errMessage = "";
        try {
            invOut = new PrintStream(pathStr);
            for (Entry entryOut : ENTRY_LIST) {
                invOut.printf("%s\t%s\t%s", entryOut.getName(),
                                  entryOut.getNumber(), entryOut.getNotes());
            }
            invOut.close();
        } catch (FileNotFoundException ex) {
//            errMessage = "File not found.";
            System.out.println(ex.getMessage());
        }
        
        return errMessage;
    }
    
    /**
     * Clears entire Inventory (for new list)
     * 
     * @return errors, if found.
     */
    public static String clearInventory() {
        String errMessage;
        
        ENTRY_LIST.clear();
        errMessage = "";
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
            errMessage = "Name is too long.";
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
        if (!number.matches("[0-9]+(\\.[0-9]*)?")) {
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
    
    /**
     * Returns whether testStr contains filterStr. Case and external whitespace
     * ignored
     * 
     * @param testStr String to be checked for filterStr
     * @param filterStr String to be found in testStr
     * @return They are equal
     */
    public static boolean customContains(String testStr, String filterStr) {
        
        // Ingore case
        testStr = testStr.toLowerCase();
        filterStr = filterStr.toLowerCase();
        
        // Ignore external whitespace
        testStr = testStr.trim();
        filterStr = filterStr.trim();
        
        return testStr.contains(filterStr);
    }
    
    /**
     * Sorts entry objects based on their names
     * 
     * @param list List to be sorted (Affects list)
     */
    @SuppressWarnings("Convert2Lambda")
    private static void customSort(ArrayList<Entry> list) {
        Comparator entrycomparator;
        
        entrycomparator = new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                return e1.getName().compareTo(e2.getName());
            }
        };
        
        Collections.sort(list, entrycomparator);
    }
    
    /**
     * Returns a filtered (and observable) version of list given the string and
     * 
     * 
     * @param filtStr String to filter the text by
     * @return ObserableList with only the elements to display
     */
    public static ObservableList<Entry> filteredEntries(String filtStr) {
        ObservableList<Entry> filtList;
        
        filtList = FXCollections.observableArrayList();
        if ("Notes".equals(filterCriterion)) {
            for (Entry e : ENTRY_LIST) {
                if (customContains(e.getNotes(), filtStr)) filtList.add(e);
            }
        } else {
            for (Entry e : ENTRY_LIST) {
                if (customContains(e.getName(), filtStr)) filtList.add(e);
            }
        }
        
        return filtList;
    }
        
    /**
     * Get the field that the filter will use
     * @return field that the filter will use
     */
    public static String getFilterCriterion() {
        return filterCriterion;
    }
    
    /**
     * Set the field that the filter will use
     * @param filterCriterion field that the filter will use
     */
    public static void setFilterCriterion(String filterCriterion) {
        InventoryManagement.filterCriterion = filterCriterion;
    }
    
    /**
     * Gets the last loaded path
     * 
     * @return Last loaded path
     */
    public static String getInvLoc() { return invLocation; }
}
