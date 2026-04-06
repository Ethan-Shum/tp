# Release Note: LockedIn v1.4

**LockedIn v1.4** introduces command customization, improved workflow efficiency, and UI enhancements while maintaining the fast CLI-driven experience from v1.3.

## What's New

### Command Customization with Aliases

- **`alias`** — Create custom shortcuts for command words
  - Example: `alias ls list` allows you to use `ls` instead of `list`
  - Overwrite aliases to update them
  
- **`unalias`** — Remove command aliases
  - Example: `unalias ls` removes the shortcut
  
- **`alias-list`** — View all currently saved aliases
  - Displays all aliases in `ALIAS -> COMMAND` format

### Faster Workflow Management

- **Command History** — Use `Up` and `Down` arrow keys in the command box to browse previously entered commands
  - No need to retype similar commands

- **`drop` Command** — Bulk delete applications with `Rejected` or `Withdrawn` status
  - Quickly clean up your list without manually deleting one by one
  - Format: `drop` (removes all rejected/withdrawn applications)

### Enhanced User Interface

- **Responsive Layout** — Application list now adapts to different window sizes
  - Fixed-width layout for windows < 1000px width
  - Percentage-based responsive layout for larger windows
  - Optimized column widths for better readability on any screen size

### Bug Fixes & Improvements

- Fixed issues with the `find` command
- Enhanced UML and sequence diagrams for better documentation

## All Supported Commands (v1.4)

- `help` — View help window
- `add` — Add a new internship application
- `list` — Show all applications
- `find` — Search applications by company, role, date, or status
- `edit` — Modify application details
- `next` — Move an application to the next status stage
- `copy` — Copy application URL to clipboard
- `delete` — Delete a specific application
- `drop` — Delete all rejected/withdrawn applications
- `alias` — Create command shortcuts
- `unalias` — Remove command shortcuts
- `alias-list` — View all aliases
- `clear` — Clear all applications
- `exit` — Close LockedIn

## Example Usage (New Features)

```
alias ls list              # Create shortcut for list
ls                         # Now works like 'list'
alias-list                 # View all saved aliases
drop                       # Delete all rejected/withdrawn applications in the current list
unalias ls                 # Remove the 'ls' shortcut
```


