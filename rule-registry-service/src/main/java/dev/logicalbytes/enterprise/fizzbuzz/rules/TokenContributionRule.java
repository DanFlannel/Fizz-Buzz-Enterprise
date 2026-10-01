package dev.logicalbytes.enterprise.fizzbuzz.rules;

/** A rule that may contribute a token for a given integer. */
public interface TokenContributionRule {
    boolean appliesTo(int candidate);

    String token(TokenSchemaVersion version);

    /** Lower runs first. Guarantees "FizzBuzz" and never "BuzzFizz". */
    int precedence();
}
