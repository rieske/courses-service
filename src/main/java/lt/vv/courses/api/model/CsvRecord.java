package lt.vv.courses.api.model;

import java.util.List;

public class CsvRecord {

    private final String fileName;
    private final List<String> serializedRecords;

    public CsvRecord(String fileName, List<String> serializedRecords) {
        this.fileName = fileName;
        this.serializedRecords = serializedRecords;
    }

    public String getFileName() {
        return this.fileName;
    }

    public List<String> getSerializedRecords() {
        return this.serializedRecords;
    }

}
