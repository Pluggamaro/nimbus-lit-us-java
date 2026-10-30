package nimbus;

import java.util.Map;
import java.util.function.Consumer;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Handler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private static final ObjectMapper JSON = new ObjectMapper();

    private final NoteRepository repo;

    /** Used by the Lambda runtime, which needs a no-argument constructor. */
    public Handler() {
        this(new InMemoryNoteRepository());
    }

    /** Used by tests, and by anything that wants to supply its own repository. */
    public Handler(NoteRepository repo) {
        this.repo = repo;
    }

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        return handle(event, context.getLogger()::log);
    }

    /** Routes one request, returns the response, and writes one log line. */
    public APIGatewayV2HTTPResponse handle(APIGatewayV2HTTPEvent event, Consumer<String> logger) {
        String method = event.getRequestContext().getHttp().getMethod();
        String path = event.getRawPath();

        APIGatewayV2HTTPResponse response;
        try {
            response = dispatch(method, path, event.getBody());
        } catch (ValidationException e) {
            response = respond(400, Map.of("error", e.getMessage()));
        }

        logger.accept(logLine(method + " " + path, response.getStatusCode()));

        return response;
    }

}
