package nimbus.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SecretScanTest {
    static final String FAKE_KEY = "AKIA" + "IOSFODNN7EXAMPLE";

    @Test
    void detectsAwsAccessKeyId(){
        assertFalse(SecretScan.findSecrets("aws_access_key_id="+FAKE_KEY).isEmpty());
    }

    @Test
    void detectsPrivateKeyHeader(){
        assertFalse(SecretScan.findSecrets("-----BEGIN RSA " + "PRIVATE KEY-----").isEmpty());
    }

    @Test
    void detectsAwsSecretKeyAssignment(){
        assertFalse(SecretScan.findSecrets("aws_secret_access_key=" + "x".repeat(40)).isEmpty());
    }
}