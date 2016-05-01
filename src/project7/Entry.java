/*
* Project 7: Inventory Management
* Caleb Davenport & Roan Martin-Hayden
* EECS 1510-091: Dr. Ledgard
*
* Description:
* Entry class handles parsing data that's input
* via individual parameters or parsing the individual lines
* The data is then exported via public functions.
*
* @(1.0)Inventory.java 1.0 4/30/2016 [Roan Martin-Hayden, Caleb Davenport]
*
* Copyright (c) 2016 Roan Martin-Hayden, Caleb Davenport. All Rights Reserved
*/

package project7;

import javafx.beans.property.SimpleStringProperty;

/**
 * Holds the data of a single entry.
 * @author rmartin-
 */
public class Entry {
    private final SimpleStringProperty name;
    private final SimpleStringProperty number;
    private final SimpleStringProperty notes;
    
    /**
     * Constructor of empty class.
     */
    Entry() {
        this.name = new SimpleStringProperty("");
        this.number = new SimpleStringProperty("");
        this.notes = new SimpleStringProperty("");

    }
    
    /**
     * Full constructor of class.
     * @param name Name of entry
     * @param number Number of entry
     * @param notes Notes of entry
     */
    Entry(String name, String number, String notes) {
        this.name = new SimpleStringProperty(name);
        this.number = new SimpleStringProperty(number);
        this.notes = new SimpleStringProperty(notes);
    }
    
    /**
     * Returns name of entry.
     * @return name of entry
     */
    public String name() {
        return name.get();
    }
    
    /**
     * Returns number of entry.
     * @return number of entry object
     */
    public String number() {
        return number.get();
    }
    
    /**
     * Returns notes of entry.
     * @return notes of entry object
     */
    public String notes() {
        return notes.get();
    }
    
    /**
     * Returns property object of name.
     * @return property object of name
     */
    public SimpleStringProperty nameProperty() {
        return name;
    }
    /**
     * Returns property object of number.
     * @return property object of number
     */
    public SimpleStringProperty numberProperty() {
        return number;
    }
    /**
     * Returns property object of notes.
     * @return property object of notes
     */
    public SimpleStringProperty notesProperty() {
        return notes;
    }
}
