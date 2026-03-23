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

# Run all JVM tests (Kaocha)
bb test

# Run only RCT-focused tests
bb rct

# Generate CLR-compatible test file from ^:rct/test blocks
bb gen-clr-rct

# Full CLR pipeline: generate tests, clear cache, run CLR tests
bb clr-test

# Format check / fix
bb fmt-check
bb fmt-fix

# Run generator directly with options
clojure -M:dev -m rct-clr.gen -o <output-path> -n <namespace> [-s <src-dir>]
```

## Architecture

The entire generator lives in `src/rct_clr/gen.clj` (~194 lines). Key flow:

- `find-cljc-files` → scans directories for `.cljc` files only (`.clj` ignored)
- `file->rct-blocks` → extracts RCT test data using rewrite-clj
- `datum->form` → converts each RCT datum to a test form based on assertion type:
  - `nil` → side-effect (bare eval)
  - `=>` → `clojure.test/is`
  - `=>>` → `matcho.core/assert` (pattern matching)
  - `throws=>>` → try/catch with matcho matching
- `write-preamble`, `write-block-fn`, `write-deftest` → emit the generated file

Generated tests use `eval` with `*ns*` binding for namespace isolation. The generated file (`test/rct_clr/rct_generated_test.cljc`) is gitignored.

## Test Configuration

- `tests.edn` — Kaocha config focusing on `:rct` tests, skipping `:unit` and `:clr-only`
- `tests_with_plugins.edn` — same with profiling and cloverage plugins
- `test/rct_clr/rc_test.clj` — JVM test runner that scans `src/` for RCT blocks

## CLR / Nostrand

- `project.edn` — CLR deps (matcho on the `magic` branch)
- `dotnet.clj` — CLR build/test orchestration (called via `nos dotnet/build`, `nos dotnet/run-tests`)
- Magic compilation flags: `*strongly-typed-invokes*`, `*direct-linking*` enabled; `*elide-meta*` disabled
