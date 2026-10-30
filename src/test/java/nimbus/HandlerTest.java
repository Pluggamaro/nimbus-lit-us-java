package nimbus;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;

class HandlerTest {

    static APIGatewayV2HTTPEvent event(String method, String path, String body) {
        APIGatewayV2HTTPEvent e = new APIGatewayV2HTTPEvent();

        return e;
    }

}
