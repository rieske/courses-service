package lt.vv.courses.api;

import java.util.Collections;
import java.util.List;

public class CsvRecord {

    public final String fileName;
    public final List<String> serializedRecords;

    public CsvRecord(String fileName, List<String> serializedRecords) {
        this.fileName = fileName;
        this.serializedRecords = Collections.unmodifiableList(serializedRecords);
    }
}
