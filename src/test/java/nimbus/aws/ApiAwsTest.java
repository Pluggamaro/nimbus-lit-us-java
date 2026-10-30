package nimbus.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("aws")
class ApiAwsTest {
    static final String API = AwsSupport.env("API_URL");

    @Test
    void postValidNoteReturns201() throws Exception {
        AwsSupport.HttpResult r = AwsSupport.call(API + "/notes", "POST", "{\"text\":\"api test\"}");
        assertEquals(201, r.status());
        assertTrue(r.body().contains("api test"));
    }

    @Test
    void getNotesReturns200AndAList() throws Exception {
        AwsSupport.HttpResult r = AwsSupport.call(API + "/notes", "GET", null);
        assertEquals(200, r.status());
        assertTrue(r.body().startsWith("["));
    }

}
