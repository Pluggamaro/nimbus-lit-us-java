package nimbus;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
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

    //scans for precise headings in the architecture doc
    @Test
    void architectureHasRequiredSections()throws IOException{
        Set<String> required = Set.of("## Problem and users", "## MVP scope", "## Architecture",
                "## Data flow", "## Trust boundaries",
                "## Service rationale", "## Environment strategy");
        assertTrue(headings("architecture.md").containsAll(required));
    }

    @Test 
    void everyAwsServiceHasARationale()throws IOException{
        String rationale = read("architecture.md").split("## Service rationale")[1];

        for(String service : List.of("IAM", "S3", "Lambda", "API Gateway", "DynamoDB", "CloudWatch")){
            assertTrue(rationale.contains(service));
        }

    }

    @Test 
    void awsServicesDocsHasConventions()throws IOException{
        assertTrue(headings("aws-service.md").containsAll(List.of("## Region", "## Naming convention", 
        "## Tagging convention", "## Shared responsibility")));
    }

    /**
     * Tests that the cost doc has the required sections
     */
    @Test 
    void costDocHasRulesAndResourceLog()throws IOException{
        assertTrue(headings("cost.md").containsAll(Set.of("## Cost rules", "## Budget", "## Resource cost log")));
    }

    /**
     * Tests that the cost rules forbid hourly billed services.
     */
    @Test 
    void costRulesForbidHourlyBilledServices() throws IOException{
        String text = read("cost.md");
        for(String service : List.of("EC2", "RDS", "NAT Gateway", "load balance")){
            assertTrue(text.contains(service), service);
        }
    }
}