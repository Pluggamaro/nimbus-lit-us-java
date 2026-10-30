package nimbus;

import java.util.List;
import java.util.logging.Handler;

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

}
