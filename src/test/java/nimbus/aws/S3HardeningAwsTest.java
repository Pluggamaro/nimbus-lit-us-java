package nimbus.aws;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.s3.S3Client;

@Tag("aws")
class S3HardeningAwsTest{
    static final String BUCKET = AwsSupport.env("BUCKET_NAME");

    @Test
    void lifecycleExpiresExportsAfterSevenDays(){
        try(S3Client s3 = AwsSupport.s3()){
            var rule = s3.getBucketLifecycleConfiguration(b -> b.bucket(BUCKET)).rules().stream()
                        .filter(r -> "expire-exports-7d".equals(r.id())).findFirst().orElseThrow();
                        
        }
    } 

}