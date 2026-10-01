package dev.logicalbytes.enterprise.fizzbuzz.mcp;

import dev.logicalbytes.enterprise.fizzbuzz.modulo.NativeModuloComputationStrategy;
import dev.logicalbytes.enterprise.fizzbuzz.orchestration.FizzBuzzApplicationContext;
import dev.logicalbytes.enterprise.fizzbuzz.rules.DivisibilityRuleFactory;
import dev.logicalbytes.enterprise.fizzbuzz.rules.TokenSchemaVersion;
import java.util.Map;
import java.util.Objects;

/** Exposes FizzBuzz to AI agents. Every call is checked against the agent's policy first. */
public final class McpFizzBuzzServer {

    private final FizzBuzzApplicationContext context;
    private final AgentPermissionPolicy policy;

    public McpFizzBuzzServer(FizzBuzzApplicationContext context, AgentPermissionPolicy policy) {
        this.context = Objects.requireNonNull(context, "context");
        this.policy = Objects.requireNonNull(policy, "policy");
    }

    public String call(McpTool tool, int argument) {
        if (!policy.permits(tool)) {
            throw new ToolCallDeniedException(policy.agentId(), tool);
        }
        return switch (tool) {
            case RESOLVE_FIZZBUZZ -> context.fizzBuzzService().resolveOne(argument).value();
            case REGISTER_RULE -> {
                context.ruleRegistry().register(DivisibilityRuleFactory.getInstance().create(argument,
                        Map.of(TokenSchemaVersion.V1, "Bazz"), 30, new NativeModuloComputationStrategy()));
                yield "Rule registered. Downstream consumers have not been notified.";
            }
        };
    }
}
