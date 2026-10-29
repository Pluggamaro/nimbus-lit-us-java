package nimbus;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@FunctionalInterface 
public interface ObjectWriter {
    void put(String bucket, String key, byte[] body, String contentType);

    /** real implementation: sends the bytes to S3 with given client */
    static ObjectWriter s3(S3Client client){
        return (bucket, key, body, contentType) -> client.putObject(
            PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build(),
            RequestBody.fromBytes(body));
        
    }
}