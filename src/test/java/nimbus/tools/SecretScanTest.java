package nimbus.tools;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SecretScanTest {
    static final String FAKE_KEY = "AKIA" + "IOSFODNN7EXAMPLE";

    /**
     * Tests that the SecretScan tool detects an AWS access key ID in a string.
     */
    @Test
    void detectsAwsAccessKeyId(){
        assertFalse(SecretScan.findSecrets("aws_access_key_id="+FAKE_KEY).isEmpty());
    }

    /**
     * Tests that the SecretScan tool detects a private key header in a string.
     */
    @Test
    void detectsPrivateKeyHeader(){
        assertFalse(SecretScan.findSecrets("-----BEGIN RSA " + "PRIVATE KEY-----").isEmpty());
    }

    /**
     * Tests that the SecretScan tool detects an AWS secret key assignment in a string.
     */
    @Test
    void detectsAwsSecretKeyAssignment(){
        assertFalse(SecretScan.findSecrets("aws_secret_access_key=" + "x".repeat(40)).isEmpty());
    }

    /**
     * Tests that the SecretScan tool does not detect any secrets in a clean string.
     */
    @Test
    void cleanTextHasNoFindings(){
        assertEquals(List.of(), SecretScan.findSecrets("TABLE_NAME=nimbus-lit-dev"));
    }

    /**
     * Tests that the SecretScan tool does not detect any secrets in the repo.
     */
    @Test
    void repoHasNoTrackedSecrets(){
        assertTrue(SecretScan.scanRepo().isEmpty());
    }
}