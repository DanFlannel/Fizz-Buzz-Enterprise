package dev.logicalbytes.enterprise.fizzbuzz.results;

import dev.logicalbytes.enterprise.fizzbuzz.rules.TokenSchemaVersion;

/** The canonical output of one FizzBuzz resolution. Audited, published, and cached. */
public record FizzBuzzResult(int candidate, String value, TokenSchemaVersion schemaVersion, String traceId) {}
