package dev.logicalbytes.enterprise.fizzbuzz.observability;

import java.util.UUID;
import java.util.function.Supplier;

/** Wraps every modulo operation in a span, as requested by the SRE team. The span goes nowhere. */
public final class NoOpTracer implements Tracer {

    private final ThreadLocal<String> traceId = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    @Override
    public <T> T inSpan(String spanName, Supplier<T> work) {
        return work.get();
    }

    @Override
    public String currentTraceId() {
        return traceId.get();
    }
}
