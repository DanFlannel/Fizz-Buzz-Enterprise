package dev.logicalbytes.enterprise.fizzbuzz.modulo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Proves ADR-0001 was a performance decision, not a correctness one. */
@SuppressWarnings("removal")
class ModuloComputationStrategyTest {

    private final ModuloComputationStrategy approved = new NativeModuloComputationStrategy();
    private final ModuloComputationStrategy legacy = new RepeatedSubtractionModuloComputationStrategy();

    @Test
    void legacyStrategyAgreesWithApprovedStrategy() {
        for (int dividend = -100; dividend <= 100; dividend++) {
            for (int divisor : new int[] {1, 3, 5, 7, 15, -3}) {
                assertEquals(approved.remainder(dividend, divisor), legacy.remainder(dividend, divisor),
                        () -> "Strategies diverged. Page the modulo on-call.");
            }
        }
    }
}
