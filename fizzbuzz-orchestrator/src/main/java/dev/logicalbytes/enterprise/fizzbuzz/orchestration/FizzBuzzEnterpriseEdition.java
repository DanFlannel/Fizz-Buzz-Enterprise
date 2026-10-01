package dev.logicalbytes.enterprise.fizzbuzz.orchestration;

/**
 * FizzBuzz, Enterprise Edition.
 *
 * <p>Reviewed by the Architecture Review Board (ARB-4471), approved by Legal, and
 * certified divisible-by-three compliant. Do not modify without a design doc.
 */
public final class FizzBuzzEnterpriseEdition {

    private FizzBuzzEnterpriseEdition() {
        throw new UnsupportedOperationException("Composition root. Use the ApplicationContext, like a professional.");
    }

    public static void main(String[] args) {
        FizzBuzzApplicationContext context = FizzBuzzApplicationContext.bootstrap();
        context.fizzBuzzService().execute(context.configuration());
    }
}
