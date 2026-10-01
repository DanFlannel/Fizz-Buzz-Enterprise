package dev.logicalbytes.enterprise.fizzbuzz.results;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.logicalbytes.enterprise.fizzbuzz.rules.TokenSchemaVersion;
import org.junit.jupiter.api.Test;

class FizzBuzzResultFactoryFactoryTest {

    @Test
    void factoryFactoryProducesAFactoryThatProducesResults() {
        FizzBuzzResult result = new FizzBuzzResultFactoryFactory()
                .createFizzBuzzResultFactory()
                .create(15, "FizzBuzz", TokenSchemaVersion.V1, "trace-1");
        assertEquals(new FizzBuzzResult(15, "FizzBuzz", TokenSchemaVersion.V1, "trace-1"), result);
    }
}
