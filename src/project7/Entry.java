/*
*
* Project 6: Inventory Management
* Caleb Davenport
* EECS 1510-091: Dr. Ledgard
*
* Description:
* Entry class handles parsing data that's input
* via individual parameters or parsing the individual lines
* The data is then exported via public functions.
*
*/

package inventorymanagement;

public class Entry {
    private String name, number, notes;
    private boolean exists;
    Entry() {
        clear();
    }
    Entry(String rawLine) throws Exception {
        clear();
        getDataFromLine(rawLine);
    }
    Entry(String name, String number, String notes) {
        this.name = parseName(name);
        this.number = number;
        this.notes = notes;
        exists = true;
    }
    private void getDataFromLine(String rawLine) {
        name = rawLine.substring(0, rawLine.indexOf("\t"));
        rawLine = removePreviousToken(rawLine);
        number = rawLine.substring(0, rawLine.indexOf("\t"));
        rawLine = removePreviousToken(rawLine);
        notes = rawLine;
        exists = true;
    }
    private String removePreviousToken(String n) {
        n = n.substring(n.indexOf("\t"));
        n = n.trim();
        return n;
    }
    private String parseName(String s) {
        if (s.length() > 8) s = s.substring(0, 8);
        return s;
    }
    private void clear() {
        name = "";
        number = "";
        notes = "";
        exists = false;
    }
    public String name() { return name; }
    public String number() { return number; }
    public String notes() { return notes; }
    public boolean exists() { return exists; }
    @Override
    public String toString() {
        String s;
        s = name + "\t" + number + "\t" + notes;
        return s;
    }
}
