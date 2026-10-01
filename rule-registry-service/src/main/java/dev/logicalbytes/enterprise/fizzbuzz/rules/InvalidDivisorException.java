package dev.logicalbytes.enterprise.fizzbuzz.rules;

/** Raised when someone attempts to divide by zero in a FizzBuzz context. */
public final class InvalidDivisorException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    public InvalidDivisorException(String message) {
        super(message);
    }
}
