package dev.logicalbytes.enterprise.fizzbuzz.flags;

public interface FeatureFlagService {
    /** Percent of integers (0 to 100) that receive the FizzNext schema. */
    int fizzNextRolloutPercent();
}
