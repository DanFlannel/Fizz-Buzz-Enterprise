package dev.logicalbytes.enterprise.fizzbuzz.orchestration;

import dev.logicalbytes.enterprise.fizzbuzz.flags.FeatureFlagService;
import dev.logicalbytes.enterprise.fizzbuzz.flags.StaticFeatureFlagService;
import dev.logicalbytes.enterprise.fizzbuzz.modulo.ModuloComputationStrategy;
import dev.logicalbytes.enterprise.fizzbuzz.modulo.NativeModuloComputationStrategy;
import dev.logicalbytes.enterprise.fizzbuzz.observability.NoOpTracer;
import dev.logicalbytes.enterprise.fizzbuzz.persistence.InMemoryAuditRepository;
import dev.logicalbytes.enterprise.fizzbuzz.persistence.LoggingDomainEventPublisher;
import dev.logicalbytes.enterprise.fizzbuzz.persistence.ResolutionCache;
import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResultFactoryFactory;
import dev.logicalbytes.enterprise.fizzbuzz.rules.DivisibilityRuleFactory;
import dev.logicalbytes.enterprise.fizzbuzz.rules.RuleRegistry;
import dev.logicalbytes.enterprise.fizzbuzz.rules.TokenSchemaVersion;
import java.util.List;
import java.util.Map;

/** Hand-rolled dependency injection. Spring was rejected in ADR-0003 for being too lightweight. */
public final class FizzBuzzApplicationContext {

    private final FizzBuzzConfiguration configuration;
    private final FizzBuzzService fizzBuzzService;
    private final RuleRegistry ruleRegistry;

    private FizzBuzzApplicationContext(FizzBuzzConfiguration configuration, FizzBuzzService service,
                                       RuleRegistry ruleRegistry) {
        this.configuration = configuration;
        this.fizzBuzzService = service;
        this.ruleRegistry = ruleRegistry;
    }

    public static FizzBuzzApplicationContext bootstrap() {
        return bootstrap(new PrintStreamOutputSink(System.out), new StaticFeatureFlagService());
    }

    public static FizzBuzzApplicationContext bootstrap(OutputSink sink, FeatureFlagService flags) {
        ModuloComputationStrategy modulo = new NativeModuloComputationStrategy();
        DivisibilityRuleFactory rules = DivisibilityRuleFactory.getInstance();
        RuleRegistry registry = new RuleRegistry(List.of(
                rules.create(3, Map.of(TokenSchemaVersion.V1, "Fizz",
                        TokenSchemaVersion.V2_FIZZ_NEXT, "FizzNext"), 10, modulo),
                rules.create(5, Map.of(TokenSchemaVersion.V1, "Buzz"), 20, modulo)));

        TokenResolutionStrategy strategy = new CompositeTokenResolutionStrategy(
                registry,
                flags,
                new FizzBuzzResultFactoryFactory().createFizzBuzzResultFactory(),
                new NoOpTracer());
        FizzBuzzService service = new FizzBuzzService(strategy, new ResolutionCache(),
                new InMemoryAuditRepository(), new LoggingDomainEventPublisher(), sink);
        return new FizzBuzzApplicationContext(new FizzBuzzConfiguration(1, 100), service, registry);
    }

    public FizzBuzzConfiguration configuration() {
        return configuration;
    }

    public FizzBuzzService fizzBuzzService() {
        return fizzBuzzService;
    }

    /** Write access to the rules of FizzBuzz. Gate every caller. See the mcp-server module. */
    public RuleRegistry ruleRegistry() {
        return ruleRegistry;
    }
}
