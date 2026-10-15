package nimbus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
}