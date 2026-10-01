# ADR-0003: Not using Spring

- Status: Accepted
- Date: 2026-10-01

## Context

A Spring Boot application with dependency injection was proposed.

## Decision

Rejected. Spring would hide the dependency graph behind annotations, and this
organization has fought hard for every one of those dependencies. We wire everything
by hand in `FizzBuzzApplicationContext` so that each layer of indirection is visible,
reviewable, and attributable to a specific architect.

## Consequences

- Startup time remains under 200 ms, which is embarrassing for an enterprise product.
  A startup latency SLO will be added so it at least looks intentional.
- The bootstrap method is long. That is the point.
