package DND.DND.Characters;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogCharacterActions
{
    private static final String LOG_FILE = "LogCharacterActions.txt";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logCA(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            String timestamp = LocalDateTime.now().toString();
            writer.write("[" + timestamp + "] " + message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
