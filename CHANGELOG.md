# Changelog

All notable changes to rct-clr are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.1.1](https://github.com/flybot-sg/rct-clr/tree/v0.1.1) - 2026-09-17

### Fixed

- The rationale in the README

## [0.1.0](https://github.com/flybot-sg/rct-clr/tree/v0.1.0) - 2026-09-16

### Added

- **Based on [robertluo/rich-comment-tests](https://github.com/robertluo/rich-comment-tests)**: the fork, not matthewdowney's original. A `=>` expectation runs as code, and the generated test matches that
- **CLR test generation**: `rct-clr.gen` reads `^:rct/test` blocks and writes a `.cljc` test file the CLR can run
- **Three assertion types**: `clojure.test/is` for `=>`, `matcho.core/assert` for `=>>`, try/catch plus matcho for `throws=>>`
- **Reader conditionals**: `#?` in an expectation resolves to the `:cljr` branch
- **Namespaced keywords**: `::kw` and `::alias/kw` resolve against the source namespace
- **REPL vars**: `*1`, `*2`, `*3` and `*e` bind across a block, so a form chains off the previous result
- **Per-form error reporting**: a throw reports against its own line, then the next form runs
- **Source scanning**: `.clj` and `.cljc`, keeping the `.cljc` when a namespace has both
- **CLI**: `-s` to scan a directory (repeatable), `-o` for the output path, `-n` for the output namespace
- **Two CLR runtimes**: the generated file runs on ClojureCLR (`cljr -X:test`) and MAGIC (`nos test`)
