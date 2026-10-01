package dev.logicalbytes.enterprise.fizzbuzz.rules;

import dev.logicalbytes.enterprise.fizzbuzz.modulo.ModuloComputationStrategy;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Immutable divisibility rule. Value semantics, because we are not animals. */
public record DivisibilityRule(int divisor, Map<TokenSchemaVersion, String> tokens, int precedence,
                               ModuloComputationStrategy modulo) implements TokenContributionRule {

    public DivisibilityRule {
        if (divisor == 0) {
            throw new InvalidDivisorException("Divisor must be non-zero. Please consult the math team.");
        }
        if (!tokens.containsKey(TokenSchemaVersion.V1)) {
            throw new IllegalArgumentException("Every rule must support schema V1 until the migration completes.");
        }
        tokens = Map.copyOf(tokens);
        Objects.requireNonNull(modulo, "modulo");
    }

    @Override
    public boolean appliesTo(int candidate) {
        return modulo.remainder(candidate, divisor) == 0;
    }

    @Override
    public String token(TokenSchemaVersion version) {
        return Optional.ofNullable(tokens.get(version)).orElseGet(() -> tokens.get(TokenSchemaVersion.V1));
    }
}
