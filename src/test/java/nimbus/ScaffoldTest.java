package nimbus;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScaffoldTest {
    @Test
    void nimbusPackageIsOnTheClasspath() {
        assertEquals("nimbus", Scaffold.NAME);
    }

    @Test
    void awsTestsAreExcludedByDefault() throws Exception {
        String pom = Files.readString(Path.of("pom.xml"));
        assertTrue(pom.contains("<excludedGroups>${excludedGroups}</excludedGroups>"));
        assertTrue(pom.contains("<excludedGroups>aws</excludedGroups>"));
    }
}