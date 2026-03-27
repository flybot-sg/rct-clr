# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

rct-clr generates CLR-compatible test files from Rich Comment Tests (`^:rct/test` blocks). RCT depends on JVM-only libraries (rewrite-clj, tools.namespace), so this tool pre-extracts test data into plain `.cljc` files that Magic/Nostrand can run with only `clojure.test` and `matcho.core`.

**Two-stage pipeline:**
1. **Extract (JVM):** `rct-clr.gen` scans `.cljc` source files, finds `^:rct/test` blocks, writes assertions to a generated `.cljc` test file
2. **Test (CLR):** Run the generated file on Magic/Nostrand — no JVM-only deps needed

## Common Commands

```bash
# Start nREPL for development
bb dev

# Run all JVM tests (regenerates golden files first, then runs Kaocha)
bb jvm-test

# Run only RCT-focused tests
bb rct

# Run tests filtered by metadata
clojure -M:dev:test --focus-meta :rct         # RCT tests only

# Run CLR tests via Nostrand (regenerates golden files, then runs on Magic)
bb clr-test

# Generate CLR-compatible golden files from ^:rct/test blocks
bb gen-clr-rct

# Format check / fix
bb fmt-check
bb fmt-fix

# Run generator directly with options
clojure -M:dev -m rct-clr.gen -o <output-path> -n <namespace> [-s <src-dir>]
```

## Architecture

The entire generator lives in `src/rct_clr/gen.cljc`. Key flow:

- `find-cljc-files` → scans directories for `.cljc` files only (`.clj` ignored)
- `file->rct-blocks` → extracts RCT test data using rewrite-clj
- `datum->form` → converts each RCT datum to a test form based on assertion type:
  - `nil` → side-effect (bare eval)
  - `=>` → `clojure.test/is`
  - `=>>` → `matcho.core/assert` (pattern matching)
  - `throws=>>` → try/catch with matcho matching
- `write-preamble`, `write-block-fn`, `write-deftest` → emit the generated file

Generated tests use `eval` with `*ns*` binding for namespace isolation.

**Golden files:** Two generated `.cljc` files are checked in as golden files for snapshot testing — re-generate with `bb gen-clr-rct`:
- `test/rct_clr/rct_generated_test.cljc` (from `src/`) — snapshot of generator output for gen.cljc's own RCT tests; cannot run on CLR due to JVM-only deps in `rct-clr.gen`
- `test/rct_clr/sample_generated_test.cljc` (from `examples/` + `examples_clr/`) — CLR-runnable; exercises all assertion types with no JVM-only deps; runs on Magic/Nostrand

**Example source directories** (three dirs with different scanning rules):
- `examples/` — cross-platform RCT tests, scanned by both the RCT runner (`bb rct`) and the generator (`bb gen-clr-rct`)
- `examples_clr/` — CLR-only tests with reader conditionals in test expressions; scanned by the generator only (the RCT runner can't eval `#?` in test expressions — see #10)
- `examples_jvm/` — JVM-only interop tests (e.g. `.getMessage`); scanned by the RCT runner only (the generator would emit JVM interop in CLR output)

**CLR-specific files:**
- `project.edn` — Nostrand project config (doesn't resolve transitive deps, so matcho is listed directly)
- `dotnet.clj` — CLR entry points for `nos` (build, run-tests); exits non-zero on test failures

## Test Configuration

- `tests.edn` — Kaocha config focusing on `:rct` tests, skipping `:unit` and `:clr-only`
- `test/rct_clr/rc_test.clj` — JVM test runner that scans `src/`, `examples/`, and `examples_jvm/` for RCT blocks
- `gen.cljc` has inline `^:rct/test` blocks after each function — `bb rct` picks these up automatically
- `gen_test.clj` — integration tests for writer functions, file discovery, and golden file snapshot matching
- `bb jvm-test` regenerates golden files before running tests, so golden file snapshot tests catch uncommitted regeneration drift

## RCT Test Style

- **One `^:rct/test` block per function**, placed right after the function it tests
- **Default to `;=>` (exact match), not `;=>>` (supermap)** — `=>>` silently ignores extra keys, hiding bugs. Only use `=>>` when `=>` is impractical. Use `select-keys` + `;=>` when you only care about a subset of keys.
- **Define named defs for reusable test values** at the top of test blocks
- **Express expected values as transformations** of input state (`assoc`/`merge`) rather than writing out full maps
- **Quote list-form expectations** — RCT evaluates the expected value, so `=> (foo x)` calls `foo`. Use `=> '(foo x)` to compare against the list literal.
- **When using `;=>>`, explicitly assert `nil` for keys that should be absent** — supermap silently ignores missing keys.
- **Use `assoc-in` when overriding nested keys** — don't replace parent maps, which silently drops sibling keys.
- **For strict collection comparisons**, prefer `;=>` over `m/assert` with `^:matcho/strict` — better error reporting and simpler.
- **Each function's RCT tests cover its own branches**, not the branches of functions it calls.

## Integration Test Style (`deftest`)

- **Don't duplicate RCT coverage** — don't add an integration test that exercises the same code path already covered by an RCT unless it adds distinct integration value (e.g., multi-phase flow or dispatch correctness). If the only difference is a different branch of a condition, that's a unit-level distinction the RCT should cover.
- **Build expected maps from fixtures, not individual field assertions** — compare full state maps via `(is (= expected actual))`, never individual field checks.
- **Declare all states and expectations in `let`, assertions in `testing`** — compute all intermediate and expected states in a single `let` block, place all `(is ...)` assertions inside `testing` blocks at the end. Never use `_ (is ...)` inline in let bindings.
- **Behavioral checks can use individual assertions** — checks on computed functions are fine as individual assertions since they verify behavior rather than state shape.
- **For unpredictable state**, use `dissoc` pattern — compare everything except the unpredictable parts, then assert the unpredictable parts changed.
- **Shared fixtures as top-level `def`s** with docstrings for reusable test states.

## Code Review Checklist

- Can existing functions be reused instead of writing new helpers?
- Can validation branches be combined into fewer checks?
- Are similar patterns handled consistently?
- Are docs up to date with changed behavior?

