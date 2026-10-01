package dev.logicalbytes.enterprise.fizzbuzz.modulo;

/**
 * The rejected alternative from ADR-0001. Retained because one downstream consumer
 * depends on it and their team is in a reorg.
 *
 * @deprecated Superseded by {@link NativeModuloComputationStrategy}. See ADR-0001.
 */
@Deprecated(since = "ADR-0001", forRemoval = true)
public final class RepeatedSubtractionModuloComputationStrategy implements ModuloComputationStrategy {
    @Override
    public int remainder(int dividend, int divisor) {
        int remaining = Math.abs(dividend);
        int step = Math.abs(divisor);
        while (remaining >= step) {
            remaining -= step;
        }
        return dividend < 0 ? -remaining : remaining;
    }
}
