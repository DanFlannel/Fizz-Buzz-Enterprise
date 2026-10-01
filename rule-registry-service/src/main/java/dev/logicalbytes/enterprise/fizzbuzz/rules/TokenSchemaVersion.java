package dev.logicalbytes.enterprise.fizzbuzz.rules;

/** Output token schema. Downstream consumers pin a version. Changing it takes a quarter. */
public enum TokenSchemaVersion {
    V1,
    /** Q3 planning: migrate "Fizz" to "FizzNext" without breaking downstream consumers. See ADR-0002. */
    V2_FIZZ_NEXT
}
