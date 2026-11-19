# Client task-system findings

This note separates task UI and protocol evidence from actual quest content.
The client JAR is an unchanged client artifact; the local server currently
does not provide task data.

## What the client contains

The extracted localized text table includes UI labels and help text for:

- a task list, accept/submit actions, shared tasks, and abandoning a task;
- task guidance, task details, completion conditions, rewards, and searching
  for available tasks;
- automatic pathfinding for a task target;
- NPC indicators: red exclamation for an available task, gray exclamation
  for a currently unavailable task, red question mark for a task ready to
  turn in, and gray question mark for an in-progress task.

The tutorial text also says that the task guide can show task records, start
automatic pathfinding, and search for tasks that have not been accepted.
These strings establish that the client supports these features, but they
are generic UI/help content, not a catalog of quests.

## How task entries reach the client

The response dispatcher in `decompiled/cc.java` handles command `769` by
reading a task category byte, an integer ID, a name string, a detail string,
and a 64-bit time value. It passes those values to `bt.a(...)`.

`decompiled/bt.java` stores the resulting task record in client-side task
category arrays and draws the current task tracker. Its task storage arrays
are allocated empty; the client code does not populate them with predefined
quest entries at startup. This is consistent with the task list remaining
empty when the server does not send task updates.

## Why the local task list is empty

`local_version_server.py` currently implements login, character, map entry,
movement, status, and inventory initialization. It does not implement
command `769` or the task-list/quest interaction protocol. Therefore, the
empty task list in the supplied screenshot is expected with the current
server: no accepted task records are being supplied.

## What is and is not known

**Confirmed from static client evidence**

- The client has task list, task guidance, task detail, abandon, sharing,
  completion/reward, and pathfinding UI text.
- The client processes task updates from server command `769` and stores
  entries for display.
- The client help describes task-related NPC markers and an available-task
  search.

**Not established yet**

- A complete quest catalog: names, story text, objectives, NPC IDs, map
  targets, coordinates, prerequisites, completion rules, and rewards.
- The complete request/response sequence for listing available tasks,
  accepting, abandoning, sharing, completing, and claiming rewards.
- The exact semantics of all task category/type values in command `769`.
- Whether any task definitions are stored in opaque client resources not
  represented in the extracted text table.

The client contains UI and task-processing code, but the inspected
decompilation and extracted text do not establish that it contains the full
quest database. Server responses or a separate task-data resource would be
needed to recover actual quest content. No fabricated task should be sent
until the relevant record layouts and UI flow are traced.

## Evidence

- `decompiled/cc.java`: dispatcher case `769` parses task update fields and
  calls `bt.a(...)`.
- `decompiled/bt.java`: task-entry storage and task tracker rendering.
- `local_text_tables.json`: task list, task guide, task details, completion,
  rewards, available-task search, and NPC-marker help strings.
- `local_version_server.py`: current server command handling; no task
  command/update implementation.
- User-provided screenshot: the displayed task panel has no visible entries.
  This is runtime evidence for that session only, not evidence that the
  original game had no tasks.
