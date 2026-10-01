package dev.logicalbytes.enterprise.fizzbuzz.mcp;

/** Tools this server exposes to AI agents over the Model Context Protocol. */
public enum McpTool {
    /** Read path. Safe for agents. */
    RESOLVE_FIZZBUZZ,
    /** Write path. Redefines what Fizz means for every downstream consumer. */
    REGISTER_RULE
}
