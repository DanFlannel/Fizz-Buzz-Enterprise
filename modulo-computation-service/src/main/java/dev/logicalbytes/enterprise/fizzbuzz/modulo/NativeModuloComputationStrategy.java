package dev.logicalbytes.enterprise.fizzbuzz.modulo;

/** The approved strategy. Uses the {@code %} operator, per ADR-0001. */
public final class NativeModuloComputationStrategy implements ModuloComputationStrategy {
    @Override
    public int remainder(int dividend, int divisor) {
        return dividend % divisor;
    }
}
