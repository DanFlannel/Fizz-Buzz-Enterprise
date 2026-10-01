package dev.logicalbytes.enterprise.fizzbuzz.persistence;

import dev.logicalbytes.enterprise.fizzbuzz.results.FizzBuzzResult;

/** Production target: Kafka, in case another service needs to know that 45 was "FizzBuzz". */
public interface DomainEventPublisher {
    void publish(String topic, FizzBuzzResult result);
}
