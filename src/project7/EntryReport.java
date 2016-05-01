/*
*
* Project 7: Inventory Management
* Caleb Davenport & Roan Martin-Hayden
* EECS 1510-091: Dr. Ledgard
*
* Description: Class to convey information about a potential entry. Contians
* information about the attempted entry, validation errors, and duplicates are
* contained.
*/

package project7;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class to convey information about a potential entry.
 */
public class EntryReport {
    private final Entry ATTEMPTED_ENTRY;
    private final ObservableList<Entry> NAME_MATCHES;
    private final ObservableList<Entry> WHOLE_MATCHES;
    private final String NAME_ERROR_MSG;
    private final String NUMBER_ERROR_MSG;
    private final boolean ERROR_FLAG;
    private final boolean NAME_MATCHES_FLAG;
    private final boolean WHOLE_MATCHES_FLAG;

    /**
     * Constructor for and entry report with no flags.
     */
    public EntryReport() {
        ATTEMPTED_ENTRY = new Entry();
        NAME_MATCHES = FXCollections.observableArrayList();
        WHOLE_MATCHES = FXCollections.observableArrayList();
        ERROR_FLAG = false;
        NAME_MATCHES_FLAG = false;
        WHOLE_MATCHES_FLAG = false;
        NAME_ERROR_MSG = "";
        NUMBER_ERROR_MSG = "";
    }
    
    /**
     * Constructor for a full EntryReport.
     * @param attemptedEntry Entry object that was checked.
     * @param nameMatches List of Entry objects whose name matches.
     * @param wholeMatches List of Entry objects whom match entirely.
     * @param nameErrorMessage Error message about the name of attemptedEntry.
     * @param numberErrorMessage Error message about the number of
     * attemptedEntry.
     */
    public EntryReport(Entry attemptedEntry, ObservableList<Entry> nameMatches,
                       ObservableList<Entry> wholeMatches,
                       String nameErrorMessage, String numberErrorMessage) {
        this.ATTEMPTED_ENTRY = attemptedEntry;
        this.NAME_MATCHES = nameMatches;
        this.WHOLE_MATCHES = wholeMatches;
        this.ERROR_FLAG = !("".equals(nameErrorMessage) &&
                          "".equals(numberErrorMessage));
        this.NAME_MATCHES_FLAG = !nameMatches.isEmpty();
        this.WHOLE_MATCHES_FLAG = !wholeMatches.isEmpty();
        this.NAME_ERROR_MSG = nameErrorMessage;
        this.NUMBER_ERROR_MSG = numberErrorMessage;
    }
    
    /**
     * Return checked entry object.
     * @return Entry Object
     */
    public Entry getATTEMPTED_ENTRY() {
        return ATTEMPTED_ENTRY;
    }

    /**
     * Returns list of Entry objects whose name matches.
     * @return List of name matches
     */
    public ObservableList<Entry> getNAME_MATCHES() {
        return NAME_MATCHES;
    }

    /**
     * Returns list of Entry objects whom match entirely.
     * @return list of whole matches
     */
    public ObservableList<Entry> getWHOLE_MATCHES() {
        return WHOLE_MATCHES;
    }

    /**
     * Returns error message about the name of attempted Entry.
     * @return Error message
     */
    public String getNAME_ERROR_MSG() {
        return NAME_ERROR_MSG;
    }

    /**
     * Returns Error message about the number of attemptedEntry.
     * @return Error message
     */
    public String getNUMBER_ERROR_MSG() {
        return NUMBER_ERROR_MSG;
    }

    /**
     * Returns if there is a validation error.
     * @return Validation error flag (true = error)
     */
    public boolean isERROR_FLAG() {
        return ERROR_FLAG;
    }

    /**
     * Returns if there were any name matches.
     * @return Name matches flag (true = matches)
     */
    public boolean isNAME_MATCHES_FLAG() {
        return NAME_MATCHES_FLAG;
    }

    /**
     * Returns if there were any whole matches.
     * @return Whole matches flag (true = matches)
     */
    public boolean isWHOLE_MATCHES_FLAG() {
        return WHOLE_MATCHES_FLAG;
    }
    
    /**
     * Returns if there were any matches of any type.
     * @return Any matches flag (true = matches)
     */
    public boolean isAnyMatches() {
        return NAME_MATCHES_FLAG || WHOLE_MATCHES_FLAG;
    }
    
    /**
     * Returns whether entry checks out entirely.
     * @return true = all clear
     */
    public boolean isOK() {
        return !(NAME_MATCHES_FLAG || WHOLE_MATCHES_FLAG || ERROR_FLAG);
    }
}
