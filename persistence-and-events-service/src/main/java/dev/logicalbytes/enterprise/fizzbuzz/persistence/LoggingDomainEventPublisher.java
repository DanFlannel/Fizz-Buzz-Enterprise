package dev.logicalbytes.enterprise.fizzbuzz.persistence;

import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResult;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggingDomainEventPublisher implements DomainEventPublisher {

    private static final Logger LOG = Logger.getLogger(LoggingDomainEventPublisher.class.getName());

    @Override
    public void publish(String topic, FizzBuzzResult result) {
        LOG.log(Level.FINE, "publish topic={0} candidate={1} value={2}",
                new Object[] {topic, result.candidate(), result.value()});
    }
}
