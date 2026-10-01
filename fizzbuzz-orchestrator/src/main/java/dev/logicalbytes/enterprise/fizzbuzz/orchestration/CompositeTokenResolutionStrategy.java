package dev.logicalbytes.enterprise.fizzbuzz.orchestration;

import dev.logicalbytes.enterprise.fizzbuzz.flags.FeatureFlagService;
import dev.logicalbytes.enterprise.fizzbuzz.observability.Tracer;
import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResult;
import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResultFactory;
import dev.logicalbytes.enterprise.fizzbuzz.rules.RuleRegistry;
import dev.logicalbytes.enterprise.fizzbuzz.rules.TokenSchemaVersion;
import java.util.Objects;
import java.util.stream.Collectors;

/** Concatenates every applicable rule's token in precedence order; falls back to the number. */
public final class CompositeTokenResolutionStrategy implements TokenResolutionStrategy {

    private final RuleRegistry registry;
    private final FeatureFlagService flags;
    private final FizzBuzzResultFactory resultFactory;
    private final Tracer tracer;

    public CompositeTokenResolutionStrategy(RuleRegistry registry, FeatureFlagService flags,
                                            FizzBuzzResultFactory resultFactory, Tracer tracer) {
        this.registry = Objects.requireNonNull(registry, "registry");
        this.flags = Objects.requireNonNull(flags, "flags");
        this.resultFactory = Objects.requireNonNull(resultFactory, "resultFactory");
        this.tracer = Objects.requireNonNull(tracer, "tracer");
    }

    @Override
    public FizzBuzzResult resolve(int candidate) {
        TokenSchemaVersion version = schemaFor(candidate);
        String joined = registry.orderedSnapshot().stream()
                .filter(rule -> tracer.inSpan("modulo.compute", () -> rule.appliesTo(candidate)))
                .map(rule -> rule.token(version))
                .collect(Collectors.joining());
        String value = joined.isEmpty() ? Integer.toString(candidate) : joined;
        return resultFactory.create(candidate, value, version, tracer.currentTraceId());
    }

    private TokenSchemaVersion schemaFor(int candidate) {
        boolean inCanary = Math.floorMod(candidate, 100) < flags.fizzNextRolloutPercent();
        return inCanary ? TokenSchemaVersion.V2_FIZZ_NEXT : TokenSchemaVersion.V1;
    }
}
