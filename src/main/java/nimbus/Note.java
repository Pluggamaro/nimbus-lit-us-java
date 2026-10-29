package nimbus;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Note(
    @JsonProperty("note_id") String noteId,
    String text,
    @JsonProperty("created_at") String creatAt  
){
    
}