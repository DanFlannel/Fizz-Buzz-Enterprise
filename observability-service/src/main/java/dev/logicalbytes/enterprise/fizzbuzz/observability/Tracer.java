package dev.logicalbytes.enterprise.fizzbuzz.observability;

import java.util.function.Supplier;

/** Tracing facade. OpenTelemetry integration is on the roadmap, below the roadmap for the roadmap. */
public interface Tracer {
    <T> T inSpan(String spanName, Supplier<T> work);

    String currentTraceId();
}
