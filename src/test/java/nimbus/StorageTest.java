package nimbus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.fasterxml.jackson.databind.ObjectWriter;

class StorageTest {

    @Test
    void putExportWritesJsonUnderExportsPrefix(){
        Map<String, byte[]> bucket = new HashMap<>();
        ObjectWriter writer = (b, key, body, contentType) -> bucket.put(key,body);
        List<Note> notes = List.of(new Note("1","hi","2026-01-01T00:00:00Z"));

        assertEquals("exports/notes-2026-01-01T00:00:00Z.json",key);
        

    }

}