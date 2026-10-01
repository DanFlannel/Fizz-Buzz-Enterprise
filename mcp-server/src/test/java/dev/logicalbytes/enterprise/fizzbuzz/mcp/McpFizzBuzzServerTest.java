package dev.logicalbytes.enterprise.fizzbuzz.mcp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.logicalbytes.enterprise.fizzbuzz.flags.StaticFeatureFlagService;
import dev.logicalbytes.enterprise.fizzbuzz.orchestration.FizzBuzzApplicationContext;
import java.util.Set;
import org.junit.jupiter.api.Test;

class McpFizzBuzzServerTest {

    private static FizzBuzzApplicationContext context() {
        return FizzBuzzApplicationContext.bootstrap(line -> { }, new StaticFeatureFlagService());
    }

    @Test
    void agentCanResolveFizzBuzz() {
        McpFizzBuzzServer server = new McpFizzBuzzServer(context(),
                new AgentPermissionPolicy("agent", Set.of(McpTool.RESOLVE_FIZZBUZZ)));
        assertEquals("FizzBuzz", server.call(McpTool.RESOLVE_FIZZBUZZ, 45));
    }

    @Test
    void agentCannotRedefineFizz() {
        McpFizzBuzzServer server = new McpFizzBuzzServer(context(),
                new AgentPermissionPolicy("agent", Set.of(McpTool.RESOLVE_FIZZBUZZ)));
        assertThrows(ToolCallDeniedException.class, () -> server.call(McpTool.REGISTER_RULE, 7));
    }

    @Test
    void approvedAgentCanRegisterBazz() {
        McpFizzBuzzServer server = new McpFizzBuzzServer(context(),
                new AgentPermissionPolicy("admin", Set.of(McpTool.RESOLVE_FIZZBUZZ, McpTool.REGISTER_RULE)));
        server.call(McpTool.REGISTER_RULE, 7);
        assertEquals("Bazz", server.call(McpTool.RESOLVE_FIZZBUZZ, 7));
        assertEquals("FizzBazz", server.call(McpTool.RESOLVE_FIZZBUZZ, 21));
    }
}
