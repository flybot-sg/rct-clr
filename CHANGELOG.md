# Changelog

All notable changes to rct-clr are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

- A `=>` expectation runs as code, as [`rich-comment-tests`](https://github.com/robertluo/rich-comment-tests) `v1.1.82` runs it. A seq is a call, and a symbol resolves. Write `'(1 2 3)` or `[1 2 3]` to compare a seq as data ([#26](https://github.com/flybot-sg/rct-clr/issues/26))
- [`rich-comment-tests`](https://github.com/robertluo/rich-comment-tests) moves from a git sha to the `1.1.82` Clojars release ([#26](https://github.com/flybot-sg/rct-clr/issues/26))

### Fixed

- The generator scans `.clj` files alongside `.cljc`, so a `^:rct/test` block in a `.clj` file becomes a generated test instead of being skipped ([#17](https://github.com/flybot-sg/rct-clr/issues/17))
- An `::alias/kw` in a test expression keeps its namespace in the generated test; the generator dropped the alias and emitted `:kw`, which then failed on the CLR ([#18](https://github.com/flybot-sg/rct-clr/issues/18))
