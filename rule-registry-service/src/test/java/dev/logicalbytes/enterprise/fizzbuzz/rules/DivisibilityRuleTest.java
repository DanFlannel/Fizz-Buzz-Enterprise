package dev.logicalbytes.enterprise.fizzbuzz.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.logicalbytes.enterprise.fizzbuzz.modulo.NativeModuloComputationStrategy;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DivisibilityRuleTest {

    private final DivisibilityRuleFactory factory = DivisibilityRuleFactory.getInstance();

    @Test
    void zeroDivisorIsEscalatedToTheMathTeam() {
        assertThrows(InvalidDivisorException.class, () -> factory.create(0,
                Map.of(TokenSchemaVersion.V1, "Fizz"), 10, new NativeModuloComputationStrategy()));
    }

    @Test
    void missingSchemaVersionFallsBackToV1() {
        TokenContributionRule buzz = factory.create(5, Map.of(TokenSchemaVersion.V1, "Buzz"), 20,
                new NativeModuloComputationStrategy());
        assertEquals("Buzz", buzz.token(TokenSchemaVersion.V2_FIZZ_NEXT));
    }

    @Test
    void registryOrdersByPrecedenceSoItIsNeverBuzzFizz() {
        TokenContributionRule buzz = factory.create(5, Map.of(TokenSchemaVersion.V1, "Buzz"), 20,
                new NativeModuloComputationStrategy());
        TokenContributionRule fizz = factory.create(3, Map.of(TokenSchemaVersion.V1, "Fizz"), 10,
                new NativeModuloComputationStrategy());
        RuleRegistry registry = new RuleRegistry(List.of(buzz, fizz));
        assertEquals(List.of(fizz, buzz), registry.orderedSnapshot());
    }
}
