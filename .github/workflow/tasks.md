# Workflow Handoff Ledger

## Open Workstreams

Use checkbox items to indicate active work. The agent runner still scans for `- [ ]` lines and treats them as actionable items.

Each open workstream should use the ledger fields below so routing, blocking state, and continuation remain explicit across chats or agents.

### Open Workstream Template

- [ ] WF-000 Short title
  - Owner agent: Master Automatin Agent
  - Next agent: Automation Framework Maintainer (Read & Write)
  - Status: triage-ready | in-progress | blocked | ready-for-handoff | ready-for-verification | done
  - Blocked on: user input | artifact path | environment access | none
  - Verification target: exact Maven or TestNG command, or `docs-only`
  - Scope: one-sentence summary of the current change boundary
  - Last updated: YYYY-MM-DD
  - Notes: key handoff context, assumptions, or remaining risk

No active tracked workstreams are currently listed.

## Closed Workstreams

- [x] Coordinator-first workflow cleanup
  - Owner agent: Master Automatin Agent
  - Next agent: none
  - Status: done
  - Blocked on: none
  - Verification target: docs-only
  - Scope: align the public coordinator model, prompt routing text, input templates, and change-log workflow rules across the repo workflow assets
  - Last updated: 2026-03-30
  - Notes: Real coordinator-era example. Workflow moved through review-driven follow-up, doc cleanup, prompt wording cleanup, change-log alignment rules, structured intake guidance, validation, and final review refresh.

- [x] Screenshot folders per test method
  - Owner agent: Automation Framework Maintainer (Read & Write)
  - Next agent: none
  - Status: done
  - Blocked on: none
  - Verification target: focused reporting verification
  - Scope: isolate screenshots under per-test folders
  - Last updated: 2026-03-26
  - Notes: Historical completed example retained for reference.
