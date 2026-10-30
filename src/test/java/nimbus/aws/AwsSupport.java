package nimbus.aws;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.s3.S3Client;

public final class AwsSupport {

    private AwsSupport() {}

    /** Returns the variable's value or
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