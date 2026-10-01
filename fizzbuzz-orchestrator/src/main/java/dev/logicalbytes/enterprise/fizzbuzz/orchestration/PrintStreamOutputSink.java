package dev.logicalbytes.enterprise.fizzbuzz.orchestration;

import java.io.PrintStream;
import java.util.Objects;

public final class PrintStreamOutputSink implements OutputSink {

    private final PrintStream stream;

    public PrintStreamOutputSink(PrintStream stream) {
        this.stream = Objects.requireNonNull(stream, "stream");
    }

    @Override
    public void emit(String line) {
        stream.println(line);
    }
}
