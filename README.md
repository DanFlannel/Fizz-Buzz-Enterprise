# FizzBuzz Enterprise Edition

Production-grade divisibility, finally.

Prints the numbers 1 to 100, replacing multiples of 3 with `Fizz`, multiples of 5 with
`Buzz`, and multiples of both with `FizzBuzz`. It does this across eight Maven modules,
three ADRs (a fourth is pending), a Helm chart, and Terraform for Kubernetes, Kafka,
PostgreSQL and Redis.

It compiles with `-Werror`, the tests pass, and the output matches a 5-line reference
implementation. That last test is the only one that matters.

## Layout

```
.
├── modulo-computation-service       # % behind a strategy interface (ADR-0001)
├── rule-registry-service            # DivisibilityRuleFactory, TokenSchemaVersion
├── result-assembly-service          # FizzBuzzResultFactoryFactory
├── feature-flag-service             # FizzNext rollout, 0 to 100 percent
├── observability-service            # OpenTelemetry-shaped tracing around the modulo
├── persistence-and-events-service   # audit history, domain events, resolution cache
├── fizzbuzz-orchestrator            # wires it all together by hand (ADR-0003)
├── mcp-server                       # lets AI agents call FizzBuzz, within policy
├── docs/adr                         # architecture decision records
└── deploy
    ├── helm/fizzbuzz                # 6 services x 6 replicas, HPA on modulo latency
    └── terraform                    # EKS, MSK, RDS Postgres, ElastiCache Redis
```

## Request path

1. `FizzBuzzEnterpriseEdition` bootstraps `FizzBuzzApplicationContext`.
2. `FizzBuzzService` opens a span and asks `CompositeTokenResolutionStrategy` for a token.
3. The strategy walks the `RuleRegistry`. Each `DivisibilityRule` delegates to a
   `ModuloComputationStrategy`, which calls `%`.
4. `FeatureFlagService` decides whether `Fizz` is `FizzNext` (ADR-0002).
5. A `FizzBuzzResultFactory`, obtained from the `FizzBuzzResultFactoryFactory`,
   assembles a `FizzBuzzResult`.
6. The result is written to the audit repository, published to `fizzbuzz.results.v1`,
   cached, and finally printed.

## Running it

Requires Java 21 and Maven 3.9.

```sh
mvn package
java -jar fizzbuzz-orchestrator/target/fizzbuzz-orchestrator-2.0.0-RC7.jar
```

### Agent mode

The `mcp-server` module exposes two tools to AI agents: `resolve_fizzbuzz` and
`register_rule`. An agent may ask what 45 is. An agent may not decide that 7 is now
`Bazz`. Only an admin can do that.

```sh
java -jar mcp-server/target/mcp-server-2.0.0-RC7.jar
```

If letting agents call tools without letting them rewrite your business rules sounds
like a real problem, it is. That is what Statio is for.

## Service level objectives

| Objective | Target |
|-----------|--------|
| Availability | 99.999% for numbers 1 to 100 |
| p99 modulo latency | 2 microseconds |
| Correctness | 100%, enforced by a test against a 5-line reference |
| Fizz to FizzNext migration | Q3 FY27 (see ADR-0002) |

## On-call

| Week | Primary | Secondary |
|------|---------|-----------|
| 1 | Fizz | Buzz |
| 2 | Buzz | Fizz |
| 3 | FizzBuzz | Gary |

Escalation path: page the modulo operator. If the modulo operator does not respond,
fall back to repeated subtraction and open a SEV-2.

## License

See repository settings. Please do not deploy this.
