package nimbus.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
            assertEquals(7, rule.expiration().days()); 
            assertEquals("exports/", rule.filter().prefix());
            assertEquals("Enabled", rule.statusAsString());           
        }
    } 

    @Test
    void anonymousReadIsDenied() throws Exception {
        URI uri = URI.create("https://" + BUCKET + ".s3.amazonaws.com/exports/hello.json");
        HttpResponse<Void> res = HttpClient.newHttpClient()
                .send(HttpRequest.newBuilder(uri).build(), HttpResponse.BodyHandlers.discarding());
        assertEquals(403, res.statusCode());
    }

}