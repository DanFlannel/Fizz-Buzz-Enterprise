package dev.logicalbytes.enterprise.fizzbuzz.results;

import dev.logicalbytes.enterprise.fizzbuzz.rules.TokenSchemaVersion;

public interface FizzBuzzResultFactory {
    FizzBuzzResult create(int candidate, String value, TokenSchemaVersion version, String traceId);
}
