/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project7;

/**
 *
 * @author rmartin-
 */
public class EntryReport {
    private final Entry ATTEMPTED_ENTRY;
    private final Entry FOUND_ENTRY;
    private final int FOUND_INDEX;
    private final boolean HAS_FOUND_ENTRY;
    private final String ERROR_MESSAGE;

    public EntryReport(Entry attemptedEntry) {
        this(attemptedEntry, new Entry(), -1, "");
    }
    
    public EntryReport(Entry attemptedEntry, String errMessage) {
        this(attemptedEntry, new Entry(), -1, errMessage);
    }
    
    public EntryReport(Entry attemptedEntry, Entry foundEntry, int foundIndex) {
        this(attemptedEntry, foundEntry, foundIndex, "");
    }
    
    public EntryReport(Entry attemptedEntry, Entry foundEntry, int foundIndex, String errMessage) {
        this.ATTEMPTED_ENTRY = attemptedEntry;
        this.FOUND_ENTRY = foundEntry;
        this.HAS_FOUND_ENTRY = foundEntry.exists();
        this.FOUND_INDEX = foundIndex;
        this.ERROR_MESSAGE = errMessage;
    }
    
    public Entry getAttemptedEntry() { return ATTEMPTED_ENTRY; }
    public Entry getFoundEntry() { return FOUND_ENTRY; }
    public int getFoundIndex() { return FOUND_INDEX; }
    public boolean hasFoundEntry() { return HAS_FOUND_ENTRY; }

}
