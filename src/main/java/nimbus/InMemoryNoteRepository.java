package nimbus;

import java.util.List;

public class InMemoryNoteRepository implements NoteRepository {

    private final List<Note> notes = new CopyOnWriteArrayList<>();

}
