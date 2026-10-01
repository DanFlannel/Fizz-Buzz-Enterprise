# ADR-0002: Migrating "Fizz" to "FizzNext" without breaking downstream consumers

- Status: Accepted, rollout at 0%
- Date: 2026-10-01
- Target: Q3 FY27 (moved from Q2 FY27, moved from Q1 FY27)

## Context

Product has asked for "Fizz" to be renamed "FizzNext" to signal innovation. At least
eleven downstream consumers parse the literal string `Fizz` from the
`fizzbuzz.results.v1` Kafka topic. Two of them are spreadsheets.

## Decision

1. Introduce `TokenSchemaVersion { V1, V2_FIZZ_NEXT }` in the rule registry.
2. Gate the new token behind the `fizz-next-token` feature flag in
   `feature-flag-service`, rolled out by percentage (0 to 100, inclusive; 110 is
   rejected, product has been informed).
3. Results continue to publish to `fizzbuzz.results.v1`. A `v2` topic will be created
   when the first consumer asks for it, which no one has.
4. "Buzz" is unaffected. Buzz has always been stable and we thank Buzz for its service.

## Rollout plan

| Quarter | Milestone |
|---------|-----------|
| Q4 FY26 | Flag created, 0% rollout, dashboard exists |
| Q1 FY27 | 1% rollout to internal dogfood (Gary) |
| Q2 FY27 | 10%, consumer readiness survey sent |
| Q3 FY27 | 50%, survey reminder sent |
| Q4 FY27 | 100%, "FizzNextBuzz" ships |
| Q1 FY28 | Begin planning "BuzzNext" |

## Consequences

- Composite token becomes `FizzNextBuzz`. Brand has approved this, reluctantly.
- `FizzBuzzEndToEndTest` covers the full rollout so we find out before the spreadsheets do.
