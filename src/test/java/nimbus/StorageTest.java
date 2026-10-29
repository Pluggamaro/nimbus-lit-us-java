package nimbus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.jupiter.api.Test;
class StorageTest {

    @Test
    void putExportWritesJsonUnderExportsPrefix(){
        Map<String, byte[]> bucket = new HashMap<>();
        ObjectWriter writer = (b, key, body, contentType) -> bucket.put(key,body);
        List<Note> notes = List.of(new Note("1","hi","2026-01-01T00:00:00Z"));

        String key = Storage.putExport(writer,"b",notes,Instant.parse("2026-01-01T00:00:00Z"));
        
        assertEquals("exports/notes-2026-01-01T00:00:00Z.json",key);

        String json = new String(bucket.get(key), StandardCharsets.UTF_8);

        System.out.println("Debug" + json);

        assertEquals(true, json.contains("\"note_id\":\"1\""));

    }

}