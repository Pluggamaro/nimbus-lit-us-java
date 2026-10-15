package nimbus;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DocsTest {
    //locate docs
    static final Path DOCS= Path.of("docs");

    /**
     * reads the DOC file
     * @param name(String) : name of the doc to read
     * @return String : name of the doc found/reoslved
     */

    static String read(String name) throws IOException {
        return Files.readString(DOCS.resolve(name));
    }

    /**
     * scans mark down for headings
     * @param name(String) : name of the markdown to search for headings
     * @return Set<String> : a collection of unique headings found
     */
    static Set<String> headings(String name) throws IOException {
        return Arrays.stream(read(name).split("\n"))
                .map(String::strip).filter(line -> line.startsWith("##"))
                .collect(Collectors.toSet());
    }

    @Test
    void architectureHasRequiredSections()throws IOException{
        Set<String> required = Set.of("## Problem and users", "## MVP scope", "## Architecture",
                "## Data flow", "## Trust boundaries",
                "## Service rationale", "## Environment strategy");
        assertTrue(headings("architecture.md").containsAll(required));
    }
}