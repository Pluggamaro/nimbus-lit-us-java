package nimbus.aws;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import
import software.amazon.awssdk.regions.Region;

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
            throw new IllegalStateException("Set "+name+ " in your shell (see .env.example)"):
        }
        return value;
    }

    /** Reads the region,
     *  defaulting to Ireland
     *  @return Region name of the region or default
     */
    public static Region region(){
        return Region.of(System.getenv().getOrDefault("AWS_REGION", "us-west-1"));
    }

    /** Reads the profile or
     *  returns the default profile
     *  @return ProfileCredentialsProvider
     */
    public static ProfileCredentialsProvider profile(){
        return ProfileCredentialsProvider.create(System.getenv().getOrDefault("AWS_PROFILE","nimbus"));
    }

}