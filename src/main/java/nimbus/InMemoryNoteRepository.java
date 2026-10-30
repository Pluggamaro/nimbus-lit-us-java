package nimbus;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryNoteRepository implements NoteRepository {

    private final List<Note> notes = new CopyOnWriteArrayList<>();

    @Override
    public void add(Note note) {
        notes.add(note);
    }

    @Override
    public List<Note> list() {
        return notes.stream()
            .sorted((a, b) -> Instant.parse(b.createdAt()).compareTo(Instant.parse(a.createdAt())))
            .toList();
    }

}
