package nimbus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

class HandlerTest {

    static APIGatewayV2HTTPEvent event(String method, String path, String body) {
        APIGatewayV2HTTPEvent e = new APIGatewayV2HTTPEvent();
        e.setRawPath(path);
        e.setBody(body);
        APIGatewayV2HTTPEvent.RequestContext ctx = new APIGatewayV2HTTPEvent.RequestContext();
        APIGatewayV2HTTPEvent.RequestContext.Http http = new APIGatewayV2HTTPEvent.RequestContext.Http();
        http.setMethod(method);
        ctx.setHttp(http);
        e.setRequestContext(ctx);
        return e;
    }

    static APIGatewayV2HTTPResponse call(Handler handler, APIGatewayV2HTTPEvent e, List<String> logs) {
        return handler.handle(e, logs::add);
    }

    @Test
    void postNotesCreatesNoteAndReturns201() {
        InMemoryNoteRepository repo = new InMemoryNoteRepository();
        APIGatewayV2HTTPResponse res = call(new Handler(repo), event("POST", "/notes", "{\"text\":\"hello\"}"), new ArrayList<>());
        assertEquals(201, res.getStatusCode());
        assertTrue(res.getBody().contains("\"text\":\"hello\""));
        assertEquals(1, repo.list().size());
    }

    @Test
    void postInvalidNoteReturns400WithMessage() {
        APIGatewayV2HTTPResponse res = call(new Handler(new InMemoryNoteRepository()),
                event("POST", "/notes", "{\"text\":\"\"}"), new ArrayList<>());
        assertEquals(400, res.getStatusCode());
        assertTrue(res.getBody().contains("\"error\""));
    }

    @Test
    void postMalformedJsonReturns400() {
        APIGatewayV2HTTPResponse res = call(new Handler(new InMemoryNoteRepository()),
                event("POST", "/notes", "{not json"), new ArrayList<>());
        assertEquals(400, res.getStatusCode());
    }

    @Test
    void getNotesReturnsNewestFirst() {
        InMemoryNoteRepository repo = new InMemoryNoteRepository();
        repo.add(new Note("1", "old", "2026-01-01T00:00:00Z"));
        repo.add(new Note("2", "new", "2026-01-02T00:00:00Z"));
        APIGatewayV2HTTPResponse res = call(new Handler(repo), event("GET", "/notes", null), new ArrayList<>());
        assertTrue(res.getBody().indexOf("new") < res.getBody().indexOf("old"));
    }

    @Test
    void unknownRouteReturns404() {
        assertEquals(404, call(new Handler(new InMemoryNoteRepository()),
                event("GET", "/nope", null), new ArrayList<>()).getStatusCode());
    }

    @Test
    void handlerLogsRouteAndStatusAsJson() {
        List<String> logs = new ArrayList<>();
        call(new Handler(new InMemoryNoteRepository()), event("GET", "/notes", null), logs);
        String last = logs.get(logs.size() - 1);
        assertTrue(last.contains("\"route\":\"GET /notes\""));
        assertTrue(last.contains("\"status\":200"));
    }

}
