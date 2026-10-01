package dev.logicalbytes.enterprise.fizzbuzz.rules;

import dev.logicalbytes.enterprise.fizzbuzz.modulo.ModuloComputationStrategy;
import java.util.Map;

/** Thread-safe lazy singleton (initialization-on-demand holder idiom). */
public final class DivisibilityRuleFactory {

    private DivisibilityRuleFactory() {}

    private static final class Holder {
        private static final DivisibilityRuleFactory INSTANCE = new DivisibilityRuleFactory();
    }

    public static DivisibilityRuleFactory getInstance() {
        return Holder.INSTANCE;
    }

    public TokenContributionRule create(int divisor, Map<TokenSchemaVersion, String> tokens, int precedence,
                                        ModuloComputationStrategy modulo) {
        return new DivisibilityRule(divisor, tokens, precedence, modulo);
    }
}
