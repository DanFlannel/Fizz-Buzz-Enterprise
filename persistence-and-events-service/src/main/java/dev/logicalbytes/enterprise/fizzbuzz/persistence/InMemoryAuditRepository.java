package dev.logicalbytes.enterprise.fizzbuzz.persistence;

import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResult;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/** Append-only. There is no delete method, and that was a decision. */
public final class InMemoryAuditRepository implements AuditRepository {

    private final List<FizzBuzzResult> rows = new CopyOnWriteArrayList<>();

    @Override
    public void append(FizzBuzzResult result) {
        rows.add(result);
    }

    @Override
    public List<FizzBuzzResult> history() {
        return List.copyOf(rows);
    }
}
