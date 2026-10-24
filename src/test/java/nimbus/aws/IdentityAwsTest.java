package nimbus.aws;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import software.amazon.awssdk.services.sts.StsClient;

/**
 * Tests for the Identity AWS service.
 */
@Tag("aws")
public class IdentityAwsTest {

    @Test 
    void callerIsDevUserNotRoot(){
        try(StsClient sts = StsClient.builder()
            .credentialsProvider(AwsSupport.profile())
            .region(AwsSupport.region()).build()){

                String arn = sts.getCallerIdentity().arn();
                assertTrue(arn.endsWith(":user/nimbus-dev"),arn);
                assertFalse(arn.endsWith(":root"),arn);
        }
    }

}