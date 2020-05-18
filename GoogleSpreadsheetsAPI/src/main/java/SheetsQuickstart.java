import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import util.SpreadsheetsReader;
import util.SpreadsheetsWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "Google Spreadsheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Full link
     * https://docs.google.com/spreadsheets/d/1SJOs41Slu1YB0HY67PxvDF6umLjuz3xfcvvQFspt-Bs/edit#gid=0
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1SJOs41Slu1YB0HY67PxvDF6umLjuz3xfcvvQFspt-Bs";
        final String sheetName = "Students Data";

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        Scanner ui = new Scanner(System.in);
        int input = 0;

        while (true) {
            System.out.println("\n---MENU---");
            System.out.println("[1] Read all" + "\n[2] Write new row" + "\n[3] Update cell" + "\n[4] Exit");

            if (ui.hasNextInt()) {
                input = ui.nextInt();
            }
            ui.nextLine();

            switch (input) {
                case 1:
                    List<List<Object>> values = SpreadsheetsReader.getSheetContent(spreadsheetId, service, sheetName, "A1", "F");
                    if (values == null || values.isEmpty()) {
                        System.out.println("No data found.");
                    } else {
                        for (List row : values) {
                            System.out.println(row.toString());
                        }
                    }
                    break;
                case 2:
                    List<Object> insertValue = readStudent(ui);
                    SpreadsheetsWriter.insertRow(spreadsheetId, service, sheetName, insertValue);
                    break;
                case 3:
                    String cellNumber = "";
                    String newValue = "";
                    System.out.println("Cell number: ");
                    if (ui.hasNext()) {
                        cellNumber = ui.nextLine();
                    }
                    System.out.println("New value: ");
                    if (ui.hasNextLine()) {
                        newValue = ui.nextLine();
                    }
                    SpreadsheetsWriter.updateCell(spreadsheetId, service, cellNumber, newValue);
                    break;
                case 4:
                    System.out.println("Leaving the program now...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Try again.");
                    break;
            }
        }
    }

    private static List<Object> readStudent(Scanner ui) {
        List<Object> input = new ArrayList<>();

        System.out.println("Student info");
        System.out.println("Name:");
        if (ui.hasNextLine()) {
            input.add(ui.nextLine());
        }
        System.out.println("Gender:");
        if (ui.hasNextLine()) {
            input.add(ui.nextLine());
        }
        System.out.println("Class Level:");
        if (ui.hasNextLine()) {
            input.add(ui.nextLine());
        }
        System.out.println("Home State:");
        if (ui.hasNextLine()) {
            input.add(ui.nextLine());
        }
        System.out.println("Major:");
        if (ui.hasNextLine()) {
            input.add(ui.nextLine());
        }
        System.out.println("Extracurricular Activity:");
        if (ui.hasNextLine()) {
            input.add(ui.nextLine());
        }

        return input;
    }
}