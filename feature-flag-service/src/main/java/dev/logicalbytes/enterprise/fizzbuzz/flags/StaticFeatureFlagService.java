package dev.logicalbytes.enterprise.fizzbuzz.flags;

/** Rollout stays at 0% until Q3 planning approves the canary. */
public final class StaticFeatureFlagService implements FeatureFlagService {

    private final int fizzNextRolloutPercent;

    public StaticFeatureFlagService() {
        this(0);
    }

    public StaticFeatureFlagService(int fizzNextRolloutPercent) {
        if (fizzNextRolloutPercent < 0 || fizzNextRolloutPercent > 100) {
            throw new IllegalArgumentException("Rollout percent must be 0-100. 110% effort is not a valid config.");
        }
        this.fizzNextRolloutPercent = fizzNextRolloutPercent;
    }

    @Override
    public int fizzNextRolloutPercent() {
        return fizzNextRolloutPercent;
    }
}
