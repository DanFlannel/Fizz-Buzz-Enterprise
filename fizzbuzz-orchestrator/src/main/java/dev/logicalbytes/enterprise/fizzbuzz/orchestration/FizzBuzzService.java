package dev.logicalbytes.enterprise.fizzbuzz.orchestration;

import dev.logicalbytes.enterprise.fizzbuzz.persistence.AuditRepository;
import dev.logicalbytes.enterprise.fizzbuzz.persistence.DomainEventPublisher;
import dev.logicalbytes.enterprise.fizzbuzz.persistence.ResolutionCache;
import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResult;
import java.util.Objects;
import java.util.stream.IntStream;

/** Resolve, cache, audit, publish, emit. In that order, per the runbook. */
public final class FizzBuzzService {

    static final String TOPIC = "fizzbuzz.results.v1";

    private final TokenResolutionStrategy strategy;
    private final ResolutionCache cache;
    private final AuditRepository audit;
    private final DomainEventPublisher events;
    private final OutputSink sink;

    public FizzBuzzService(TokenResolutionStrategy strategy, ResolutionCache cache, AuditRepository audit,
                           DomainEventPublisher events, OutputSink sink) {
        this.strategy = Objects.requireNonNull(strategy, "strategy");
        this.cache = Objects.requireNonNull(cache, "cache");
        this.audit = Objects.requireNonNull(audit, "audit");
        this.events = Objects.requireNonNull(events, "events");
        this.sink = Objects.requireNonNull(sink, "sink");
    }

    public FizzBuzzResult resolveOne(int candidate) {
        FizzBuzzResult result = cache.computeIfAbsent(candidate, () -> strategy.resolve(candidate));
        audit.append(result);
        events.publish(TOPIC, result);
        return result;
    }

    public void execute(FizzBuzzConfiguration configuration) {
        IntStream.rangeClosed(configuration.lowerBoundInclusive(), configuration.upperBoundInclusive())
                .mapToObj(this::resolveOne)
                .map(FizzBuzzResult::value)
                .forEachOrdered(sink::emit);
    }
}
