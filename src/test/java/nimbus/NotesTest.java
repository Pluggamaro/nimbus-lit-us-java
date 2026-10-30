package nimbus;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class NotesTest {
 
    @Test
    void rejectsPayloadThatIsNotAnObject() {
        assertThrows(ValidationException.class, () -> Notes.validateText("hello"));
    }



}
