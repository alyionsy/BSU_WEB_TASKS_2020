package util;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class SpreadsheetsWriter {
    public static void insertRow(String spreadsheetId, Sheets service, String sheetName, List<Object> row) throws IOException {
        ValueRange requestBody = new ValueRange().setValues(Collections.singletonList(row));
        Sheets.Spreadsheets.Values.Append request = service.spreadsheets().values()
                .append(spreadsheetId, sheetName, requestBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS");

        AppendValuesResponse response = request.execute();
        System.out.println(response);
    }

    public static void updateCell(String spreadsheetId, Sheets service, String cellName, Object newValue) throws IOException {
        ValueRange requestBody = new ValueRange().setValues(Collections.singletonList(Collections.singletonList(newValue)));
        Sheets.Spreadsheets.Values.Update request = service.spreadsheets().values()
                .update(spreadsheetId, cellName, requestBody)
                .setValueInputOption("RAW");

        UpdateValuesResponse response = request.execute();
        System.out.println(response);
    }
}
