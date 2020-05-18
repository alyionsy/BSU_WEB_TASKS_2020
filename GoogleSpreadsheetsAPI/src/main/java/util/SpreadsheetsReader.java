package util;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.List;

public class SpreadsheetsReader {
    public static List<List<Object>> getSheetContent(String spreadsheetId, Sheets service, String sheetName, String upperLeftCellName, String lowerRightCellName) throws IOException {
        String range = "" + sheetName + "!" + upperLeftCellName + ":" + lowerRightCellName;

        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        return response.getValues();
    }
}
