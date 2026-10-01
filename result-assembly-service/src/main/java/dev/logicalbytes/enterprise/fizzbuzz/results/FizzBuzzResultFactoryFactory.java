package dev.logicalbytes.enterprise.fizzbuzz.results;

/** The factory needed a factory. This was raised in design review and nobody objected in time. */
public final class FizzBuzzResultFactoryFactory {
    public FizzBuzzResultFactory createFizzBuzzResultFactory() {
        return new DefaultFizzBuzzResultFactory();
    }
}
