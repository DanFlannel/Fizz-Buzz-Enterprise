package dev.logicalbytes.enterprise.fizzbuzz.persistence;

import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResult;
import java.util.List;

/** Immutable FizzBuzz audit history, for SOX. Production target: PostgreSQL (see deploy/terraform). */
public interface AuditRepository {
    void append(FizzBuzzResult result);

    List<FizzBuzzResult> history();
}
