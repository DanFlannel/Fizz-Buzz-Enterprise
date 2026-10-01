package dev.logicalbytes.enterprise.fizzbuzz.mcp;

public final class ToolCallDeniedException extends SecurityException {
    private static final long serialVersionUID = 1L;

    public ToolCallDeniedException(String agentId, McpTool tool) {
        super("Agent '" + agentId + "' is not permitted to call " + tool
                + ". Request filed with the rules committee.");
    }
}
