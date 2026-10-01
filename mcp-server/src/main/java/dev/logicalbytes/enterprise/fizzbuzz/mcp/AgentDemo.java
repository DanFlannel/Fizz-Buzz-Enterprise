package dev.logicalbytes.enterprise.fizzbuzz.mcp;

import dev.logicalbytes.enterprise.fizzbuzz.orchestration.FizzBuzzApplicationContext;
import java.io.PrintStream;
import java.util.Set;

/** An AI agent tries to do FizzBuzz, then tries to change the rules of FizzBuzz. */
public final class AgentDemo {

    private AgentDemo() {}

    public static void main(String[] args) {
        PrintStream out = System.out;
        McpFizzBuzzServer server = new McpFizzBuzzServer(FizzBuzzApplicationContext.bootstrap(),
                new AgentPermissionPolicy("claude-agent-7", Set.of(McpTool.RESOLVE_FIZZBUZZ)));

        out.println("agent> resolve_fizzbuzz(45) = " + server.call(McpTool.RESOLVE_FIZZBUZZ, 45));
        try {
            server.call(McpTool.REGISTER_RULE, 7);
        } catch (ToolCallDeniedException denied) {
            out.println("agent> register_rule(7) DENIED: " + denied.getMessage());
        }
    }
}
