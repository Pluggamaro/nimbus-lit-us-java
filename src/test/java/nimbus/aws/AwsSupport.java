package nimbus.aws;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import
import software.amazon.awssdk.regions.Region;

public final class AwsSupport {

    private AwsSupport() {}

    /** Returns the variable's value or
     * fails with a message telling you
     * what to set
     */
    public static String env(String name){
        String value =  System.getenv(name);
        if(value == null || value.isBlank()){
            throw new IllegalStateException("Set "+name+ " in your shell (see .env.example)"):
        }
        return value;
    }

    public static Region region(){
        return Region.of(System.getenv().getOrDefault("AWS_REGION", "us-west-1"));
    }



}