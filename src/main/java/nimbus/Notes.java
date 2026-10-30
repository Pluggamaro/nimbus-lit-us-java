package nimbus;

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

        return trimmed;
    }
}
