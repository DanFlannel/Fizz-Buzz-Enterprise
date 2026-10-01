package dev.logicalbytes.enterprise.fizzbuzz.flags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class StaticFeatureFlagServiceTest {

    @Test
    void defaultsToZeroPercentPendingQuarterlyPlanning() {
        assertEquals(0, new StaticFeatureFlagService().fizzNextRolloutPercent());
    }

    @Test
    void rejectsOneHundredTenPercentEffort() {
        assertThrows(IllegalArgumentException.class, () -> new StaticFeatureFlagService(110));
    }
}
