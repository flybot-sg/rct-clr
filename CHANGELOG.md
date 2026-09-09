# Changelog

All notable changes to rct-clr are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Fixed

- The generator scans `.clj` files alongside `.cljc`, so a `^:rct/test` block in a `.clj` file becomes a generated test instead of being skipped ([#17](https://github.com/flybot-sg/rct-clr/issues/17))
- An `::alias/kw` in a test expression keeps its namespace in the generated test; the generator dropped the alias and emitted `:kw`, which then failed on the CLR ([#18](https://github.com/flybot-sg/rct-clr/issues/18))
- A `=>` expectation is decided one element at a time. A seq whose head cannot invoke, such as `(1 2 3)`, compares as data, and a call beside it still evaluates. A head that does invoke runs as a call, so `(:k m)` reads as a map lookup. Write a data seq as a vector, or quote it ([#24](https://github.com/flybot-sg/rct-clr/issues/24))
