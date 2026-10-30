package nimbus;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryNoteRepository implements NoteRepository {

    private final List<Note> notes = new CopyOnWriteArrayList<>();

    @Override
    public void add(Note note) {
        notes.add(note);
    }

}
