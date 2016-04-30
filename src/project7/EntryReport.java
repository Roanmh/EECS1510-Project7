package project7;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EntryReport {
    private final Entry ATTEMPTED_ENTRY;
    private final ObservableList<Entry> NAME_MATCHES;
    private final ObservableList<Entry> WHOLE_MATCHES;
    private final String NAME_ERROR_MSSG;
    private final String NUMBER_ERROR_MSSG;
    private final boolean ERROR_FLAG;
    private final boolean NAME_MATCHES_FLAG;
    private final boolean WHOLE_MATCHES_FLAG;

    public EntryReport() {
        ATTEMPTED_ENTRY = new Entry();
        NAME_MATCHES = FXCollections.observableArrayList();
        WHOLE_MATCHES = FXCollections.observableArrayList();
        ERROR_FLAG = false;
        NAME_MATCHES_FLAG = false;
        WHOLE_MATCHES_FLAG = false;
        NAME_ERROR_MSSG = "";
        NUMBER_ERROR_MSSG = "";
    }
    
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
        this.NAME_ERROR_MSSG = nameErrorMessage;
        this.NUMBER_ERROR_MSSG = numberErrorMessage;
    }
    
    public Entry getATTEMPTED_ENTRY() {
        return ATTEMPTED_ENTRY;
    }

    public ObservableList<Entry> getNAME_MATCHES() {
        return NAME_MATCHES;
    }

    public ObservableList<Entry> getWHOLE_MATCHES() {
        return WHOLE_MATCHES;
    }

    public String getNAME_ERROR_MSSG() {
        return NAME_ERROR_MSSG;
    }

    public String getNUMBER_ERROR_MSSG() {
        return NUMBER_ERROR_MSSG;
    }

    public boolean isERROR_FLAG() {
        return ERROR_FLAG;
    }

    public boolean isNAME_MATCHES_FLAG() {
        return NAME_MATCHES_FLAG;
    }

    public boolean isWHOLE_MATCHES_FLAG() {
        return WHOLE_MATCHES_FLAG;
    }
    
    public boolean isAnyMatches() {
        return NAME_MATCHES_FLAG || WHOLE_MATCHES_FLAG;
    }
    
    public boolean isOK() {
        return !(NAME_MATCHES_FLAG || WHOLE_MATCHES_FLAG || ERROR_FLAG);
    }
}
