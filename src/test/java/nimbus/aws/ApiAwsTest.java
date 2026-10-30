package nimbus.aws;

import org.junit.jupiter.api.Tag;

@Tag("aws")
class ApiAwsTest {
    static final String API = AwsSupport.env("API_URL");
}
