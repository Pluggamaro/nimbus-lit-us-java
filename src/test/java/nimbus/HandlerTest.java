package nimbus;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;

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

}
