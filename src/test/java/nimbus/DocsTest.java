package nimbus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class DocsTest {
    //locate docs
    static final Path DOCS= Path.of("docs");

    /** @read
     * reads the DOC file
     * @param name : name of the doc to read
     * @return String : name of the doc found/reoslved
     */

    static String read(String name) throws IOException {
        return Files.readString(DOCS.resolve(name));
    }

    /** @headings
     * scans mark down for headings
     * @
     */
}