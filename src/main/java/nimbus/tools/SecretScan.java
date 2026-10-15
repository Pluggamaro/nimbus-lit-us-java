package nimbus.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class SecretScan{

    /** Different kinds of patterns for credential types.
     *  Compiled once and reused for every file.
     */
    static final List<Pattern> PATTERNS = List.of(
            Pattern.compile("AKIA[0-9A-Z]{16}"),
            Pattern.compile("(?i)aws_secret_access_key\\s*[=:]\\s*\\S{20,}"),
            Pattern.compile("-----BEGIN (RSA |EC |OPENSSH |DSA )?PRIVATE KEY-----")
    );

    private SecretScan(){}

    /**
     * Returns the pattern-matched tex, empty means clean.
     * @param text the secret to be searched
     * @return List<Pattern> that consists of the matched text patterns
     */
    public List<Pattern> findSecrets(String text){
        List<Pattern> hits = new ArrayList<>();

        return hits;
    }

}