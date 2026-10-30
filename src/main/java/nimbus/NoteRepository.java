package nimbus;

import java.util.List;

/** Stores notes. list() must return notes newest first by createdAt. */
public interface NoteRepository {

    void add(Note note);

    List<Note> list();
}
