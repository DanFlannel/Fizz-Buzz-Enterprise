package dev.logicalbytes.enterprise.fizzbuzz.orchestration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.logicalbytes.enterprise.fizzbuzz.flags.StaticFeatureFlagService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FizzBuzzEndToEndTest {

    /** The five-line version. Kept as the oracle. Not allowed in production. */
    private static List<String> referenceFizzBuzz() {
        List<String> lines = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            lines.add(i % 15 == 0 ? "FizzBuzz" : i % 3 == 0 ? "Fizz" : i % 5 == 0 ? "Buzz" : Integer.toString(i));
        }
        return lines;
    }

    private static List<String> run(int fizzNextRolloutPercent) {
        List<String> output = new ArrayList<>();
        FizzBuzzApplicationContext context = FizzBuzzApplicationContext.bootstrap(
                output::add, new StaticFeatureFlagService(fizzNextRolloutPercent));
        context.fizzBuzzService().execute(context.configuration());
        return output;
    }

    @Test
    void enterpriseEditionMatchesTheFiveLineVersionExactly() {
        assertEquals(referenceFizzBuzz(), run(0));
    }

    @Test
    void fullFizzNextRolloutRenamesFizzWithoutBreakingBuzz() {
        List<String> output = run(100);
        assertEquals("FizzNext", output.get(2));
        assertEquals("Buzz", output.get(4));
        assertEquals("FizzNextBuzz", output.get(14));
    }

    @Test
    void invertedRangeIsEscalated() {
        assertThrows(IllegalArgumentException.class, () -> new FizzBuzzConfiguration(100, 1));
    }
}
