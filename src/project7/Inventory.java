/*
* Project 7: Inventory Management
* Caleb Davenport & Roan Martin-Hayden
* EECS 1510-091: Dr. Ledgard
*
* Description:
* Main class that handles user input
* and provides the functions based on the input.
*
* @(1.0)Inventory.java 1.0 4/30/2016 [Roan Martin-Hayden, Caleb Davenport]
*
* Copyright (c) 2016 Roan Martin-Hayden, Caleb Davenport. All Rights Reserved
*/

package project7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static final ArrayList<Entry> ENTRY_LIST = new ArrayList<>();
    private static String filterCriterion = "Name";

    /**
     * Finds an entry and returns the location or -1 if not found.
     * @param name Name to search for
     * @return Index location of the entry or -1 if not found
     */
    public static int entryIndex(String name) {
        int location;
        location = -1;
        // Search Algorithm
        for (int i = 0; i < ENTRY_LIST.size(); ++i) {
            if (customEquals(name, ENTRY_LIST.get(i).name())) {
                location = i;
            }
        }
        
        return location;
    }
    
    /**
     * Adds Entry to the inventory array.
     * @param name Name of entry
     * @param number Quantity of entry
     * @param notes Quantity of entry
     * @return Highest priority error if encountered, empty if success
     */
    public static EntryReport addEntryReport(String name, String number,
                                             String notes) {
        Entry attemptedEntry;
        ObservableList<Entry> matchesByName;
        ObservableList<Entry> matchesInWhole;
        String nameErrorMessage;
        String numberErrorMessage;
    
        // Find Name Matches
        matchesByName = FXCollections.observableArrayList();
        for (Entry entry : ENTRY_LIST) {
            if (customEquals(entry.name(), name)) { 
                matchesByName.add(entry);
            }
        }
                
        // Find whole Matches
        matchesInWhole = FXCollections.observableArrayList();
        for (Entry entry : ENTRY_LIST) {
            if (customEquals(entry.name(), name) &&
                customEquals(entry.number(), number) &&
                customEquals(entry.notes(), notes)) {
                
                matchesInWhole.add(entry);
            }
        }
                
        
        // Name based Error Message creation
        nameErrorMessage = nameValidityError(name);
        numberErrorMessage = numberValidityError(number);

        attemptedEntry = new Entry(name, number, notes);
        
        return new EntryReport(attemptedEntry, matchesByName, matchesInWhole,
                               nameErrorMessage, numberErrorMessage);
    }
    
    /**
     * Adds entry (regardless of duplicate status).
     * @param name Name to be entered (depending on overwriteName if editing
     * existing entry)
     * @param number Number to be entered
     * @param notes Notes to be entered
     */
    public static void addEntry(String name, String number, String notes) {
        ENTRY_LIST.add(new Entry(name, number, notes));
        customSort(ENTRY_LIST);
    }
    
    /**
     * Edits an entry, essential replacing the old entry with entered
     * information.
     * @param replacedEntry Entry object of the entry to be replaced
     * @param name Name be entered
     * @param number Number to be entered
     * @param notes Notes to be entered
     */
    public static void editEntry(Entry replacedEntry, String name,
                                 String number, String notes) {
        addEntry(name, number, notes);
        deleteEntry(replacedEntry);
    }

    
    /**
     * Finds entry by name and deletes it.
     * @param name name of entry to delete
     */
    public static void deleteEntry(String name) {
        int index;
        
        index = entryIndex(name);
        deleteEntry(index);
    }

    /**
     * Deletes entry at the given index. 
     * @param index location of entry to delete
     */
    public static void deleteEntry(int index) {
            ENTRY_LIST.remove(index);
    }
    
    /**
     * Deletes entry as specified by the object itself.
     * @param entry Object to delete
     */
    public static void deleteEntry(Entry entry) {
        ENTRY_LIST.remove(entry);
    }
    
    /**
     * Loads entries from a file into the stored inventory.
     * @param pathStr Path to load from
     * @return Error message if found
     */
    public static String loadInventory(String pathStr) {
        String errMessage;
        File file;
        Scanner inventoryIn;
        String[] tempVals;
        String[] entryVals;
        
        ENTRY_LIST.clear();
        
        entryVals = new String[3];
        Arrays.fill(entryVals, "");
        errMessage = "";
        file = new File(pathStr);
        try {
            inventoryIn = new Scanner(file);
            while (inventoryIn.hasNextLine()) {
                tempVals = inventoryIn.nextLine().split("\t");
                System.arraycopy(tempVals, 0, entryVals, 0, tempVals.length);
                addEntry(entryVals[0], entryVals[1], entryVals[2]);
            }
        } catch (FileNotFoundException ex) {
            errMessage = "File not found.";
        }
        
        return errMessage;
    }
    
    /**
     * Store inventory into specified file.
     * @param pathStr Location at which to store file
     * @return Error message if found
     */
    public static String saveInventory(String pathStr) {
        String errMessage;
        PrintStream inventoryOut;
        
        errMessage = "";
        try {
            inventoryOut = new PrintStream(pathStr);
            for (Entry entryOut : ENTRY_LIST) {
                inventoryOut.printf("%s\t%s\t%s\n", entryOut.name(),
                                  entryOut.number(), entryOut.notes());
            }
            inventoryOut.close();
        } catch (FileNotFoundException ex) {
            errMessage = "File not found.";
            System.err.println(ex.getMessage());
        }
        
        return errMessage;
    }
    
    /**
     * Clears entire Inventory (for new list).
     */
    public static void clearInventory() {
        ENTRY_LIST.clear();
    }
    
    /**
     * Checks that a name is formatted correctly.
     * @param name Name to be tested
     * @return Highest priority error message, empty if OK
     */
    public static String nameValidityError(String name) {
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
     * Checks that a number is formatted correctly.
     * @param number Number to be tested
     * @return Highest priority error message, empty if OK
     */
    public static String numberValidityError(String number) {
        String errMessage;
        
        errMessage = "";
        
        /// Tests, ordered by priority
        // Numerals Check
        if (!number.matches("-?\\d*\\.?\\d+")) {
            errMessage = "Not a number.";
        }
        
        // Negativity Check
        if (number.matches("-\\d*\\.?\\d")) {
            errMessage = "Can't have negative inventory.";
        }
        
        return errMessage;
    }
    
    /**
     * Returns whether strings are equal with custom qualifications.
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
     * Returns whether testStr contains filterStr (Case and external whitespace
     * ignored).
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
     * Sorts entry objects based on their names.
     * @param list List to be sorted (Affects list)
     */
    @SuppressWarnings("Convert2Lambda")
    private static void customSort(ArrayList<Entry> list) {
        Comparator entryComparator;
        
        entryComparator = new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                return e1.name().compareToIgnoreCase(e2.name());
            }
        };
        
        Collections.sort(list, entryComparator);
    }
    
    /**
     * Returns a filtered (and observable) version of list given the string and 
     * criterion.
     * @param filtStr String to filter the text by
     * @return ObserableList with only the elements to display
     */
    public static ObservableList<Entry> filteredEntries(String filtStr) {
        ObservableList<Entry> filtList;
        
        filtList = FXCollections.observableArrayList();
        if ("Notes".equals(filterCriterion)) {
            for (Entry e : ENTRY_LIST) {
                if (customContains(e.notes(), filtStr)) filtList.add(e);
            }
        } else {
            for (Entry e : ENTRY_LIST) {
                if (customContains(e.name(), filtStr)) filtList.add(e);
            }
        }
        
        return filtList;
    }
        
    /**
     * Get the field that the filter will use.
     * @return field that the filter will use
     */
    public static String filterCriterion() {
        return filterCriterion;
    }
    
    /**
     * Set the field that the filter will use.
     * @param filterCriterion field that the filter will use
     */
    public static void setFilterCriterion(String filterCriterion) {
        Inventory.filterCriterion = filterCriterion;
    }
}
