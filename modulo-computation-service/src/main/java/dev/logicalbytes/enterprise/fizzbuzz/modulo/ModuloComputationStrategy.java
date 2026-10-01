package dev.logicalbytes.enterprise.fizzbuzz.modulo;

/** Computes remainders. This is an interface so that ADR-0001 had something to decide. */
public interface ModuloComputationStrategy {
    int remainder(int dividend, int divisor);
}
