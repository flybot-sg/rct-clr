# Contributing

## Filing issues

An issue is a **problem statement**. The title says what is wrong, the body shows it, and an optional suggestion proposes a fix.

Phrase the title as the problem, not the solution:

- Good: `A => expectation written as a list is called as a function instead of compared as data`
- Bad: `Quote list expectations in datum->form`

Body:

    ## Problem

    A minimal reproducing block, the command that surfaces it, and the output.

    ## Suggestion

    Optional. The concrete change as a code block, and why. Drop the section when you have none.

Show, do not describe. A block someone can paste beats a paragraph.

## Pull requests

PRs target `main`. Branch from it, named `<issue-number>-<short-description>`, e.g. `13-expectation-called-as-function`.

Title: `<prefix>(<scope>): <short description>`, one line.

Description:

    Closes #<issue-number>
    ---

    - First change
    - Second change

One bullet per change. The issue carries the context.

## Merging

Rebase when every commit builds and passes on its own. Squash otherwise. No merge commits.

A branch whose commits are "fix it", "address review", "typo" is one change.

## Before opening a PR

```bash
bb fmt-check
bb jvm-test      # regenerates the golden files, then Kaocha
bb magic-test    # MAGIC
bb cljr-test     # ClojureCLR
```

Read the assertion count, not the exit code: a runner that discovers no namespaces prints `Ran 0 tests` and exits 0. The CLR counts should track the JVM's.

## Golden files

`test/rct_clr/rct_generated_test.cljc` and `test/rct_clr/sample_generated_test.cljc` are generated and committed on purpose: this project's output is the thing under test, so it has to be reviewable in a diff.

Regenerate with `bb gen-clr-rct` and commit the result.

Projects that *use* rct-clr do the opposite and git-ignore theirs.

## Commits

[Conventional Commits](https://www.conventionalcommits.org/), one-line title:

    <prefix>(<scope>): <description>

Prefixes: `feat`, `fix`, `refactor`, `docs`, `test`, `chore`.

The body is a few plain sentences: what changed, and the why a reader cannot get from the diff. Do not list the files touched, narrate the steps taken, or report test results; the diff and CI already show those. LLM-generated messages tend to include all three, so trim them before committing.

Reference the issue with `Closes #42`. Issue numbers belong in commit messages and PR descriptions, never in source files or comments.
