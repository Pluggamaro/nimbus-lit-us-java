package nimbus.aws;

import org.junit.jupiter.api.Tag;

@Tag("aws")
class LambdaAwsTest {
    static final String FUNCTION = AwsSupport.env("FUNCTION_NAME");
    
}
