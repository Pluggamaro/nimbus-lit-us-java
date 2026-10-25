package nimbus.aws;

public class S3BucketAwsTest {
    static final String BUCKET = AwsSupport.env("BUCKET_NAME");

    @Test
    void bucketBlocksAllPublicAccess() {
        try (S3Client s3 = S3Client.builder().credentialsProvider(AwsSupport.profile()).region(AwsSupport.region()).build()) {
            PublicAccessBlockConfiguration cfg = s3.getPublicAccessBlock(b -> b.bucket(BUCKET))
                    .publicAccessBlockConfiguration();
            assertTrue(cfg.blockPublicAcls() && cfg.ignorePublicAcls()
                    && cfg.blockPublicPolicy() && cfg.restrictPublicBuckets());
        }
    }
}
