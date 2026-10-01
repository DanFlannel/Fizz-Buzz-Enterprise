package dev.logicalbytes.enterprise.fizzbuzz.mcp;

import java.util.Set;

/** Per-tool permissions. An agent may run FizzBuzz. It may not redefine Fizz. */
public record AgentPermissionPolicy(String agentId, Set<McpTool> allowedTools) {

    public AgentPermissionPolicy {
        allowedTools = Set.copyOf(allowedTools);
    }

    public boolean permits(McpTool tool) {
        return allowedTools.contains(tool);
    }
}
