package dev.logicalbytes.enterprise.fizzbuzz.orchestration;

/** The range under management. */
public record FizzBuzzConfiguration(int lowerBoundInclusive, int upperBoundInclusive) {
    public FizzBuzzConfiguration {
        if (lowerBoundInclusive > upperBoundInclusive) {
            throw new IllegalArgumentException("Lower bound exceeds upper bound. Escalate to the range committee.");
        }
    }
}
