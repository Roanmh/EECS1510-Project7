/*
*
* Project 7: Inventory Management
* Caleb Davenport & Roan Martin-Hayden
* EECS 1510-091: Dr. Ledgard
*
* Description:
* Entry class handles parsing data that's input
* via individual parameters or parsing the individual lines
* The data is then exported via public functions.
*
*/

package project7;

import javafx.beans.property.*;

public class Entry {
    private SimpleStringProperty name, number, notes;
    private boolean exists;
    Entry() {
        clear();
    }
    Entry(String name, String number, String notes) {
        this.name = new SimpleStringProperty(name);
        this.number = new SimpleStringProperty(number);
        this.notes = new SimpleStringProperty(notes);
        exists = true;
    }
    private void clear() {
        this.name = new SimpleStringProperty("");
        this.number = new SimpleStringProperty("");
        this.notes = new SimpleStringProperty("");
        exists = false;
    }
    public String getName() { return name.get(); }
    public String getNumber() { return number.get(); }
    public String getNotes() { return notes.get(); }
    public boolean exists() { return exists; }

    @Override
    public String toString() {
        String s;
        s = name + "\t" + number + "\t" + notes;
        return s;
    }
}
