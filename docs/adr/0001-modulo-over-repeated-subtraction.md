# ADR-0001: Use `%` over repeated subtraction

- Status: Accepted
- Date: 2026-10-01
- Deciders: Architecture Review Board (quorum: 1)
- Supersedes: nothing, which concerned the board

## Context

FizzBuzz requires determining whether an integer `n` is divisible by a divisor `d`.
Two strategies were evaluated:

1. **Native modulo** (`n % d`), provided by the Java Language Specification.
2. **Repeated subtraction**, in which `d` is subtracted from `n` until the remainder is less than `d`.

Repeated subtraction had strong support from stakeholders who wanted every arithmetic
step to be individually traceable in OpenTelemetry.

## Decision

We will use native modulo, wrapped in `NativeModuloComputationStrategy` behind the
`ModuloComputationStrategy` interface so the decision can be reversed without a
rewrite.

`RepeatedSubtractionModuloComputationStrategy` is retained, annotated
`@Deprecated(since = "ADR-0001", forRemoval = true)`, and is not scheduled for removal.

## Consequences

- Positive: O(1) instead of O(n / d). At n = 100 this saves up to 33 subtractions per
  request, which finance has asked us to express as an annualized dollar figure.
- Negative: span count per request drops sharply, and the observability team's
  dashboard now looks empty.
- Neutral: `%` in Java returns a negative remainder for negative dividends. FizzBuzz
  does not support negative numbers. See the roadmap for "FizzBuzz for Integers".

## Alternatives considered

- **Bitwise tricks.** Only works for powers of two. 3 and 5 declined to become powers
  of two.
- **Lookup table of all multiples of 15.** Rejected on storage cost grounds once
  someone asked what happens after 100.
- **Calling an LLM.** Deferred to ADR-0005 pending a budget conversation.
