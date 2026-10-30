package nimbus.aws;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.s3.S3Client;

public final class AwsSupport {

    private AwsSupport() {}

    public record HttpResult(int status, String body){}

    /**
     * Makes a HTTP call to the given URL with the given method and body, returning the status code 
     * and body as a HttpResult.
     * This is a convenience method for making HTTP requests to AWS services.
     * @param url the URL to call
     * @param method the HTTP method to use (GET, POST, etc.)
     * @param body the body of the request
     * @return HttpResult the status code and body of the response
     * @throws Exception
     */
    static HttpResult call(String url, String method, String body) throws Exception {
    HttpRequest.Builder b = HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(10))
            .header("Content-Type", "application/json");
    b = body == null ? b.method(method, HttpRequest.BodyPublishers.noBody())
                     : b.method(method, HttpRequest.BodyPublishers.ofString(body));
    HttpResponse<String> res = HttpClient.newHttpClient().send(b.build(), HttpResponse.BodyHandlers.ofString());
    return new HttpResult(res.statusCode(), res.body());
    }

    /**
     * Returns the variable's value or
     * fails with a message telling you
     * what to set
     * @param name the env var to search
     * @return String value of the env var found
     */
    public static String env(String name){
        String value =  System.getenv(name);
        if(value == null || value.isBlank()){
            throw new IllegalStateException("Set "+name+ " in your shell (see .env.example)");
        }
        return value;
    }

    public static LambdaClient lambda() {
    return LambdaClient.builder().region(region()).credentialsProvider(profile()).build();
    }

    /** Reads the region,
     *  defaulting to Ireland
     *  @return Region name of the region or default
     */
    public static Region region(){
        return Region.of(System.getenv().getOrDefault("AWS_REGION", "eu-west-1"));
    }

    /** Reads the profile or
     *  returns the default profile
     *  @return ProfileCredentialsProvider
     */
    public static ProfileCredentialsProvider profile(){
        return ProfileCredentialsProvider.create(System.getenv().getOrDefault("AWS_PROFILE","nimbus"));
    }

    /** Returns an S3 client with the region and profile set
     * @return S3Client
     */
    public static S3Client s3() {
        return S3Client.builder().region(region()).credentialsProvider(profile()).build();
    }

}