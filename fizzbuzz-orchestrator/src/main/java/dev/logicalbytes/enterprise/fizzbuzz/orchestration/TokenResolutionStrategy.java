package dev.logicalbytes.enterprise.fizzbuzz.orchestration;

import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResult;

public interface TokenResolutionStrategy {
    FizzBuzzResult resolve(int candidate);
}
