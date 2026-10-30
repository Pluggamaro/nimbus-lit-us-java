package nimbus;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
import java.util.jar.JarFile;

import org.junit.jupiter.api.Test;

class PackagingTest {
    static final Path ROOT = Path.of("").toAbsolutePath();

    @Test
    void packageScriptBuildsJarWithHandlerAndNoSecrets() throws Exception {
        Process p = new ProcessBuilder("bash", "scripts/package.sh")
                .directory(ROOT.toFile()).inheritIO().start();
        assertTrue(p.waitFor() == 0);
        try (JarFile jar = new JarFile(ROOT.resolve("build/lambda.jar").toFile())) {
            List<String> names = jar.stream().map(e -> e.getName()).toList();
            assertTrue(names.contains("nimbus/Handler.class"));
            assertFalse(names.stream().anyMatch(n -> n.startsWith("src/") || n.startsWith("docs/")
                    || n.startsWith(".env") || n.startsWith(".git")));
        }
    }
}
