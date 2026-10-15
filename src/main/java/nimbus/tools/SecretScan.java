package nimbus.tools;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public static List<String> findSecrets(String text){
        List<String> hits = new ArrayList<>();
        for(Pattern pattern : PATTERNS){
            if(pattern.matcher(text).find()){
                hits.add(pattern.pattern());
            }
        }
        return hits;
    }

    /**
     * Reads every git tracked file and returns its path for every
     * hit(match)
     * @return List<String> : The list containing the files that are git tracked and
     * containing secrets.
     */

    public static List<String> scanRepo(){
        List<String> hits = new ArrayList<>();
        for(String file: trackedFiles()) {
            Path path = Path.of(file);
            if (!Files.isRegularFile(path)) {
                continue;
            }
            String content;
            try {
                content = Files.readString(path, StandardCharsets.UTF_8);
            } catch (MalformedInputException e) {
                // skip binary files
                continue;
            }catch(IOException e){
                throw new UncheckedIOException(e);
            }
            for(String hit : findSecrets(content)){
                hits.add(file + ": "+hit);
            }
        }
        return hits;
    }

    /**
     * Non tracked files are automatically skipped.
     * @return List<String> : files found to be tracked by git
     */
    private static List<String> trackedFiles() {
        try (Process gitFiles = new ProcessBuilder("git", "ls-files").start();){
            String output = new String(gitFiles.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            if (gitFiles.waitFor() != 0) {
                throw new IllegalStateException("Git files failed");
            }
            return output.lines().filter(line -> !line.isBlank()).toList();
        }catch(IOException e){
            throw new UncheckedIOException(e);
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
            throw new InternalError("Git files interrupted");
        }
    }


}