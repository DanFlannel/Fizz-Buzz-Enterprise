package dev.logicalbytes.enterprise.fizzbuzz.persistence;

import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResult;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Production target: Redis, added because someone mentioned latency.
 * Cache invalidation is tracked in ADR-0004, which has not been written.
 */
public final class ResolutionCache {

    private final Map<Integer, FizzBuzzResult> entries = new ConcurrentHashMap<>();

    public FizzBuzzResult computeIfAbsent(int candidate, Supplier<FizzBuzzResult> loader) {
        return entries.computeIfAbsent(candidate, key -> loader.get());
    }
}
