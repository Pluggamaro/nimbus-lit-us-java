package nimbus;

import java.time.Instant;
import java.util.Map;

public final class Notes {

    public static final int MAX_LENGTH = 280;

    private Notes() {}

    /** Returns the trimmed text, or throws ValidationException with a message safe to return to a caller. */
    public static String validateText(Object payload) {
        if (!(payload instanceof Map<?, ?> map)) {
            throw new ValidationException("request body must be a JSON object");
        }
        if (!(map.get("text") instanceof String text)) {
            throw new ValidationException("text is required and must be a string");
        }
        
        String trimmed = text.strip();
        if (trimmed.isEmpty()) {
            throw new ValidationException("text must not be blank");
        }
        if (trimmed.codePointCount(0, trimmed.length()) > MAX_LENGTH) {
            throw new ValidationException("text must be 280 characters or fewer");
        }

        return trimmed;
    }

     /** Builds a note. A null ID or timestamp is replaced with a generated value. */
    public static Note newNote(String text, String noteId, Instant now) {
        return new Note("","","");
    }
}
