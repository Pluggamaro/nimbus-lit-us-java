package nimbus;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Storage {
    
    private static final DateTimeFormatter KEY_TIME =
            DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH-mm-ss'Z'").withZone(ZoneOffset.UTC);
    private static final ObjectMapper JSON = new ObjectMapper();
}
