package dev.logicalbytes.enterprise.fizzbuzz.results;

import dev.logicalbytes.enterprise.fizzbuzz.rules.TokenSchemaVersion;

public final class DefaultFizzBuzzResultFactory implements FizzBuzzResultFactory {
    @Override
    public FizzBuzzResult create(int candidate, String value, TokenSchemaVersion version, String traceId) {
        return new FizzBuzzResult(candidate, value, version, traceId);
    }
}
