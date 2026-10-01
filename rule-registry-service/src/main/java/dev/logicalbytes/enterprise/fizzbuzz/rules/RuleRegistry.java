package dev.logicalbytes.enterprise.fizzbuzz.rules;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/** The system of record for which numbers are Fizz. Writes require approval (see McpTool.REGISTER_RULE). */
public final class RuleRegistry {

    private final List<TokenContributionRule> rules = new CopyOnWriteArrayList<>();

    public RuleRegistry(Collection<? extends TokenContributionRule> initialRules) {
        rules.addAll(initialRules);
    }

    public void register(TokenContributionRule rule) {
        rules.add(Objects.requireNonNull(rule, "rule"));
    }

    /** Rules in precedence order. */
    public List<TokenContributionRule> orderedSnapshot() {
        return rules.stream().sorted(Comparator.comparingInt(TokenContributionRule::precedence)).toList();
    }
}
