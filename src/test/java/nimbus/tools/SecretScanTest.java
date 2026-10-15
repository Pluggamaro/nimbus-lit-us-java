package nimbus.tools;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void cleanTextHasNoFindings(){
        assertEquals(List.of(), SecretScan.findSecrets("TABLE_NAME=nimbus-lit-dev"));
    }

    @Test
    void repoHasNoTrackedSecrets(){
        assertTrue(SecretScan.scanRepo().isEmpty());
    }
}