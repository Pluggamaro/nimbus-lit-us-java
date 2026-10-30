package nimbus.aws;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.LambdaClient;

@Tag("aws")
class LambdaAwsTest {
    static final String FUNCTION = AwsSupport.env("FUNCTION_NAME");

    @Test
    void directInvokeGetNotesReturns200() {
        String event = "{\"requestContext\":{\"http\":{\"method\":\"GET\"}},\"rawPath\":\"/notes\"}";
        try (LambdaClient lambda = AwsSupport.lambda()) {
            InvokeResponse out = lambda.invoke(b -> b.functionName(FUNCTION)
                    .payload(software.amazon.awssdk.core.SdkBytes.fromUtf8String(event)));
            assertTrue(out.payload().asUtf8String().contains("\"statusCode\":200"));
        }
    }
    
}
