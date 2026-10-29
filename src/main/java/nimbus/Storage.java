package nimbus;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Storage {
    
    private static final DateTimeFormatter KEY_TIME =
            DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH-mm-ss'Z'").withZone(ZoneOffset.UTC);
    private static final ObjectMapper JSON = new ObjectMapper();

     /** Writes the notes as JSON under exports/ and returns the object key. */
    public static String putExport(ObjectWriter writer, String bucket, List<Note> notes, Instant now) {
        String key = "exports/notes-" + KEY_TIME.format(now) + ".json";
        try {
            writer.put(bucket, key, JSON.writeValueAsBytes(notes), "application/json");
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("could not serialise notes", e);
        }
        return key;
    }
}
