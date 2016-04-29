package project7;

import javafx.collections.ObservableList;

public class EntryReport {
    private final Entry ATTEMPTED_ENTRY;
    private final ObservableList<Entry> NAME_MATCHES;
    private final ObservableList<Entry> WHOLE_MATCHES;
    private final ObservableList<String> ERR_MESSAGES;
    private final boolean ERROR_FLAG;
    private final boolean NAME_MATCHES_FLAG;
    private final boolean WHOLE_MATCHES_FLAG;

    public EntryReport(Entry attemptedEntry, ObservableList<Entry> nameMatches,
                       ObservableList<Entry> wholeMatches,
                       ObservableList<String> errMessages) {
        this.ATTEMPTED_ENTRY = attemptedEntry;
        this.NAME_MATCHES = nameMatches;
        this.WHOLE_MATCHES = wholeMatches;
        this.ERR_MESSAGES = errMessages;
        this.ERROR_FLAG = !errMessages.isEmpty();
        this.NAME_MATCHES_FLAG = !nameMatches.isEmpty();
        this.WHOLE_MATCHES_FLAG = !wholeMatches.isEmpty();
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

    public ObservableList<String> getERR_MESSAGES() {
        return ERR_MESSAGES;
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
        return NAME_MATCHES_FLAG && WHOLE_MATCHES_FLAG;
    }
}
