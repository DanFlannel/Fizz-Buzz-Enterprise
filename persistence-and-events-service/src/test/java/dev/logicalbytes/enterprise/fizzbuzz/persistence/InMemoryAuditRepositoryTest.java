package dev.logicalbytes.enterprise.fizzbuzz.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResult;
import dev.logicalbytes.enterprise.fizzbuzz.rules.TokenSchemaVersion;
import java.util.List;
import org.junit.jupiter.api.Test;

class InMemoryAuditRepositoryTest {

    @Test
    void historyIsAppendOnlyAndCannotBeRewritten() {
        AuditRepository audit = new InMemoryAuditRepository();
        audit.append(new FizzBuzzResult(45, "FizzBuzz", TokenSchemaVersion.V1, "trace-45"));

        List<FizzBuzzResult> history = audit.history();

        assertEquals(1, history.size());
        assertThrows(UnsupportedOperationException.class, history::clear);
    }
}
