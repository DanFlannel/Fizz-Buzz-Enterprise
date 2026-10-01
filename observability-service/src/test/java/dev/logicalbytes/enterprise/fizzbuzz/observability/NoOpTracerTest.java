package dev.logicalbytes.enterprise.fizzbuzz.observability;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class NoOpTracerTest {

    @Test
    void spanReturnsTheWorkResultAndHasATraceId() {
        Tracer tracer = new NoOpTracer();
        assertEquals(Boolean.TRUE, tracer.inSpan("modulo.compute", () -> 15 % 3 == 0));
        assertFalse(tracer.currentTraceId().isBlank());
    }
}
