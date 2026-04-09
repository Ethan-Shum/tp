---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# LockedIn Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is adapted from [AddressBook-Level3](https://se-education.org/addressbook-level3/) by the [SE-EDU initiative](https://se-education.org).

LockedIn relies on the following third-party libraries/frameworks:

* [JavaFX](https://openjfx.io/) for the GUI.
* [Jackson](https://github.com/FasterXML/jackson) for JSON serialization/deserialization.
* [JUnit 5](https://junit.org/junit5/) for automated testing.

The team also used GitHub Copilot for IDE-assisted autocomplete during development.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ApplicationListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Application` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Application` objects (which are contained in a `UniqueApplicationList` object).
* stores the currently 'selected' `Application` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Application>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Alias feature (`alias`, `unalias`, `alias-list`)

Alias support is implemented through a collaboration between `AddressBookParser`, `Model`, and `UserPrefs`:

1. `AddressBookParser#parseCommand` extracts the command word and checks whether it exists in the alias map.
1. If a mapping exists, the alias is rewritten to the corresponding built-in command word before dispatch.
1. `AliasCommand` validates that the target command is supported and the alias itself is not a built-in command.
1. Valid mappings are persisted via `Model#setAlias(...)`, which writes to `UserPrefs`.
1. `UnaliasCommand` removes a mapping via `Model#removeAlias(...)`.
1. `AliasListCommand` renders all current mappings in alphabetical order.

<puml src="diagrams/AliasSequenceDiagram.puml" alt="Interactions inside Logic and Model for alias creation" />

Design notes:

* Aliases are stored in user preferences so they survive restarts.
* Built-in command words cannot be reused as aliases to avoid ambiguous parsing.
* Existing aliases can be overwritten intentionally to allow iterative user customization.

### Status progression feature (`next`)

`next INDEX` advances an application status using a fixed cyclic workflow defined in `Status#getNextStatus()`:

`Applied -> OA -> Interview -> Offered -> Rejected -> Withdrawn -> Applied`

Implementation flow:

1. `NextCommandParser` parses and validates the index.
1. `NextCommand` resolves the target application from `Model#getFilteredApplicationList()`.
1. It computes the next status, creates an updated immutable `Application`, and persists it with `Model#setApplication(...)`.

<puml src="diagrams/NextSequenceDiagram.puml" alt="Interactions inside Logic and Model for next command" />

### Terminal cleanup feature (`drop`)

`drop` removes only terminal applications from the currently displayed list. In LockedIn, terminal statuses are `Rejected` and `Withdrawn`.

Implementation details:

* `DropCommandParser` rejects any extra arguments.
* `DropCommand` filters the current displayed list by `Application#hasTerminalStatus()`.
* Matching entries are deleted through `Model#deleteApplication(...)`.
* If no terminal entries are present in the current list, the command fails with a descriptive error.

<puml src="diagrams/DropActivityDiagram.puml" alt="Activity flow for drop command" width="420" />

### Notes feature (`note` and `clearnote`)

LockedIn stores free-form notes in the immutable `Application` entity through the `Note` value object.

Behavior:

* `note INDEX n/TEXT` replaces the note for the target application.
* `clearnote INDEX` resets the note to `Note.EMPTY`.
* Both commands use index-based lookup against the filtered list and persist updates through `Model#setApplication(...)`.

Validation:

* `NoteCommandParser` enforces exactly one `n/` prefix and rejects empty note text.
* `ClearNoteCommandParser` only accepts a single index argument.

### Auto-save behavior and error handling

The save behavior is centralized in `LogicManager#execute(...)`:

1. Parse command.
1. Execute command against `Model`.
1. Save address book JSON.
1. Save user preferences JSON.

If file writes fail, `LogicManager` converts IO failures into user-facing `CommandException` messages, including a specific message for insufficient write permissions.

### [Proposed] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`.

It implements the following operations:

* `VersionedAddressBook#commit()` - Saves the current address book state in history.
* `VersionedAddressBook#undo()` - Restores the previous state from history.
* `VersionedAddressBook#redo()` - Restores a previously undone state from history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()`, and `Model#redoAddressBook()`.

Example usage scenario:

Step 1. On first launch, `VersionedAddressBook` is initialized with one state, and `currentStatePointer` points to it.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Computer Science undergraduates applying for multiple internships
* need to track and manage a large number of applications simultaneously
* prefer desktop apps over other types
* prefers typing to mouse interactions
* value fast data entry and quick access to structured application information

**Value proposition**: Helps CS students manage mass applications by storing company contacts and its position details
in a CLI environment. It allows target users to log application updates, record information about company / position and check deadlines, minimizing context switching between different job websites.



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                     | I want to …​                                   | So that I can…​                                     |
|----------|--------------------------------------------|------------------------------------------------|-----------------------------------------------------|
| `* * *`  | first time user                            | add an application record                      | track my application                                |
| `* * *`  | first time user                            | view all applications in a list                | see all my applications                             |
| `* * *`  | first time user                            | delete application records                     | remove companies I am no longer interested in       |
| `* * *`  | first time user                            | edit an application's details                  | correct mistakes I made                             |
| `* * *`  | user                                       | update an application status                   | track the progress of my application                |
| `* * *`  | user                                       | save the application date for each application | track my applications efficiently                   |
| `* * *`  | user                                       | save data on my hard disk                      | access my records locally                           |
| `* * *`  | user                                       | add a job URL to an entry                      | quickly revisit the job posting                     |
| `* *`    | user                                       | sort company list by application date          | know which applications need follow-up first              |
| `* *`    | user                                       | filter the list by status                      | see only my active applications                     |
| `* *`    | user                                       | view the potential pay of each position        | know which applications result in higher pay        |
| `* *`    | user                                       | filter the list by pay range                   | determine expected salary levels                    |
| `* *`    | user                                       | view total number of applications by status    | track my overall progress                           |
| `* *`    | user                                       | auto save my data after every command          | avoid losing data if the terminal closes            |
| `* *`    | user                                       | copy contact info between entries              | avoid retyping everything                           |
| `* *`    | user                                       | favourite companies                            | track companies I am particularly interested in     |
| `* *`    | first time user                            | see dummy data                                 | understand how the data is structured               |
| `* *`    | first time user                            | find company contact details by name           | follow up with companies easily                     |
| `*`      | user                                       | pin the application window on top              | keep the logbook visible while browsing job portals |
| `*`      | user                                       | undo a command                                 | recover from accidental deletions                   |
| `*`      | expert user                                | use short aliases                              | type commands faster                                |
| `*`      | expert user                                | use tab completion                             | avoid typing full commands                          |
| `*`      | expert user                                | cycle through previous commands                | reuse previously typed commands                     |
| `*`      | expert user                                | bulk delete applications by status             | remove rejected or ghosted applications quickly     |
| `*`      | expert user                                | export data into another file format           | keep backups or reuse data elsewhere                |
| `*`      | expert user                                | add tags to companies                          | record additional information about companies       |


### Use cases

(For all use cases below, the **System** is `LockedIn` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add an application**

**MSS:**

1. User wants to add a new application record.
2. User specifies the application details.
3. LockedIn creates the application record.
4. LockedIn adds the application record to the application list.
5. LockedIn shows a confirmation message displaying the added application record.

   Use case ends.

**Extensions:**

* 2a. A required field is missing.

    * 2a1. LockedIn shows an error message indicating that the input is incomplete.

      Use case ends.

* 2b. The provided application date is invalid.

    * 2b1. LockedIn shows an error message indicating that the date is invalid.

      Use case ends.

* 2c. The provided status is invalid.

    * 2c1. LockedIn shows an error message indicating that the status is invalid.

      Use case ends.

* 2d. The provided URL is invalid, does not begin with http:// or https://

    * 2d1. LockedIn shows an error message indicating that the URL format is invalid.

      Use case ends.

* 2e. An application with the same company, role, and application date already exists.

    * 2e1. LockedIn shows an error message indicating that a duplicate application record already exists.

      Use case ends.
  
* 2f. The user provides an unrecognized or invalid prefix.

    * 2f1. LockedIn shows an error message indicating that the command format is invalid.

       Use case ends.

**Use case: Delete an application record**

**Preconditions:**
* At least one application is shown in the current displayed list.

**MSS:**

1. User wants to delete an application record.
2. User specifies the index of the application record to be deleted.
3. LockedIn deletes the application record.
4. LockedIn shows a confirmation message indicating the deleted application record.

   Use case ends.

**Extensions:**

* 2a. The specified index is invalid.

    * 2a1. LockedIn shows an error message indicating the specified index is invalid.

      Use case ends.

**Use case: List all applications**

**MSS:**

1. User wants to view all application records.
2. User requests to list all applications.
3. LockedIn retrieves all saved application records.
4. LockedIn displays all application records.

   Use case ends.

**Extensions:**

* 3a. There are no saved application records.

    * 3a1. LockedIn shows an empty application list.

      Use case ends.


**Use case: Find applications**

**MSS:**

1. User wants to search for application records that match given criteria.
2. User specifies one or more supported search prefixes and their corresponding keywords.
3. LockedIn filters the application list based on the specified criteria.
4. LockedIn displays the matching application records.

   Use case ends.

**Extensions:**

* 2a. The user does not provide any search criteria.

    * 2a1.  LockedIn shows an error message indicating that the command format is invalid.

      Use case ends.

* 2b. The user provides an unsupported or invalid prefix.

    * 2b1. LockedIn shows an error message indicating that the command format is invalid.

      Use case ends.

* 2c. The user provides an invalid status value.

    * 2c1. LockedIn shows an error message indicating that the status is invalid.

      Use case ends.

* 3a. No application records match the specified criteria.

    * 3a1. LockedIn shows an empty filtered list.

      Use case ends.

**Use case: Edit an application**

**Preconditions:**
* At least one application is shown in the current displayed list.

**MSS:**

1. User wants to update an existing application record.
2. User specifies the index of the target application and the fields to be updated.
3. LockedIn updates the specified fields of the selected application record.
4. LockedIn shows a confirmation message displaying the updated application record.

   Use case ends.

**Extensions:**

* 2a. The specified index is invalid.

    * 2a1. LockedIn shows an error message indicating the specified index is invalid.

      Use case ends.

* 2b. The user does not provide any field to edit.

    * 2b1. LockedIn shows an error message indicating that at least one editable field must be provided.

      Use case ends.

* 2c. The provided application date is invalid.

    * 2c1. LockedIn shows an error message indicating that the date is invalid.

      Use case ends.

* 2d. The provided status is invalid.

    * 2d1. LockedIn shows an error message indicating that the status is invalid.

      Use case ends.

* 2e. The provided URL is invalid.

    * 2e1. LockedIn shows an error message indicating that the URL format is invalid.

      Use case ends.

* 2f. The updated application would duplicate an existing application record.

    * 2f1. LockedIn shows an error message indicating that a duplicate application record already exists.

      Use case ends.

* 2g. The user provides an unsupported or invalid prefix.

    * 2g1. LockedIn shows an error message indicating that the command format is invalid.

      Use case ends.


**Use case: Copy job URL**

**Preconditions:**
* At least one application record is shown in the current displayed list.

**MSS:**

1. User wants to copy a job URL from an application record.
2. User specifies the index of the target application record.
3. LockedIn copies the URL of the specified application record to the system clipboard.
4. LockedIn shows a confirmation message indicating that the URL has been copied.

   Use case ends.

**Extensions:**

* 2a. The specified index is invalid.

    * 2a1. LockedIn shows an error message indicating the specified index is invalid.

      Use case ends.

* 2b. The specified application record does not have a saved URL.

    * 2b1. LockedIn shows an error message indicating that there is no URL to copy.

      Use case ends.

**Use case: Advance application status**

**Preconditions:**
* At least one application is shown in the current displayed list.

**MSS:**

1. User wants to advance an application's status.
2. User specifies the index of the target application.
3. LockedIn updates the application's status to the next stage in a cyclic status sequence (`Applied -> OA -> Interview -> Offered -> Rejected -> Withdrawn -> Applied`).
4. LockedIn shows a confirmation message with the updated application details.

   Use case ends.

**Extensions:**

* 2a. The specified index is invalid.

    * 2a1. LockedIn shows an error message indicating the specified index is invalid.

      Use case ends.



**Use case: Drop terminal applications from current list**

**MSS:**

1. User wants to remove terminal applications from the currently displayed list.
2. User executes the drop command.
3. LockedIn finds applications with status `Rejected` or `Withdrawn` in the current list.
4. LockedIn deletes those applications.
5. LockedIn shows a summary message with the number of removed applications.

   Use case ends.

**Extensions:**

* 3a. No terminal applications are found in the current displayed list.

    * 3a1. LockedIn shows an error message indicating that there is nothing to drop.

      Use case ends.


**Use case: Set a note for an application**

**Preconditions:**
* At least one application is shown in the current displayed list.

**MSS:**

1. User wants to add or update a note for an application.
2. User specifies the index of the target application and the note content after correct prefix.
3. LockedIn updates the note field for the selected application.
4. LockedIn shows a confirmation message.

   Use case ends.

**Extensions:**

* 2a. The specified index is invalid.

    * 2a1. LockedIn shows an error message indicating the specified index is invalid.

      Use case ends.

* 2b. The note input is empty.

    * 2b1. LockedIn shows an error message indicating note cannot be empty.

      Use case ends.

* 2d. The note exceeds the maximum allowed length.

    * 2d1. LockedIn shows an error message indicating that the note is too long.

      Use case ends.


**Use case: Clear a note from an application**

**Preconditions:**
* At least one application is shown in the current displayed list.

**MSS:**

1. User wants to remove a note from an application.
2. User specifies the index of the target application.
3. LockedIn clears the note field for the selected application.
4. LockedIn shows a confirmation message.

   Use case ends.

**Extensions:**

* 2a. The specified index is invalid.

    * 2a1. LockedIn shows an error message indicating the specified index is invalid.

      Use case ends.


**Use case: Create an alias for a command word**

**MSS:**

1. User wants to create an alias for an existing command word.
2. User specifies the alias and the command word to be aliased.
3. LockedIn creates the alias.
4. LockedIn shows a confirmation message displaying the alias mapping.

   Use case ends.

**Extensions:**

* 2a. The user provides input in an invalid format.

    * 2a1. LockedIn shows an error message indicating the correct format.

      Use case ends.

* 2b. The specified command word is not supported.

    * 2b1. LockedIn shows an error message indicating that only existing command words can be aliased.

      Use case ends.

* 2c. The specified alias is an existing built-in command word.

    * 2c1. LockedIn shows an error message indicating that the alias is invalid.

      Use case ends.

* 3a. The alias already exists.

    * 3a1. LockedIn updates the alias to point to the new command word.
    * 3a2. LockedIn shows a confirmation message indicating that the alias has been updated.

      Use case ends.


**Use case: Remove an existing alias**

**Preconditions:**
* At least one alias exists in LockedIn.

**MSS:**

1. User wants to remove an existing alias.
2. User specifies the alias to be removed.
3. LockedIn removes the alias.
4. LockedIn shows a confirmation message indicating the removed alias.

   Use case ends.

**Extensions:**

* 2a. The user provides input in an invalid format.

    * 2a1. LockedIn shows an error message indicating the correct format.

      Use case ends.

* 2b. The specified alias does not exist.

    * 2b1. LockedIn shows an error message indicating that the alias does not exist.

      Use case ends.


**Use case: View all saved aliases**

**MSS:**

1. User wants to view all saved aliases.
2. User requests to view the alias list.
3. LockedIn retrieves all saved aliases.
4. LockedIn displays the saved aliases in alphabetical order.

   Use case ends.

**Extensions:**

* 3a. There are no saved aliases.

    * 3a1. LockedIn shows a message indicating that no aliases have been saved.

      Use case ends.

**Use case: Clear applications from current list**

**MSS:**

1. User wants to remove all application records in the current displayed list.
2. User executes the clear command.
3. LockedIn deletes all application records in the current displayed list.
4. LockedIn shows a confirmation message indicating that the displayed application records have been removed.

   Use case ends.

**Extensions:**

* 2a. The user provides additional arguments.

    * 2a1. LockedIn shows an error message indicating that the command format is invalid.

      Use case ends.


**Use case: View command history**

**Preconditions:**
* The user has previously entered at least one command in the current session.

**MSS:**

1. User requests to navigate to a previous or next command.
2. LockedIn displays the previous or next command from the command history.

   Use case ends.

**Extensions:**

* 1a. There is no earlier command in the history.

    * 1a1. LockedIn keeps the command box unchanged.

      Use case ends.

* 1b. There is no later command in the history.

    * 1b1. LockedIn clears the command box or keeps it at the latest state.

      Use case ends.


**Use case: Save data**

**MSS:**

1. User performs an action that modifies application data.
2. LockedIn saves the updated data to the local JSON file automatically.

   Use case ends.

**Extensions:**

* 2a. LockedIn cannot write to the JSON file.

    * 2a1. LockedIn shows an error message.

      Use case ends.


**Use case: Exit the application**

**MSS:**

1. User requests to exit the application.
2. LockedIn terminates.

   Use case ends.

**Extensions:**

* 1a. The input format is invalid.

    * 1a1. LockedIn shows an error message.

      Use case ends.
  
*{More to be added}*

### Non-Functional Requirements

1. Should work on mainstream OS as long as Java `17` or above is installed.
2. Should be able to store and manage at least 1000 application records without noticeable sluggishness for typical usage.
3. A user with above-average typing speed for regular English text should be able to complete core tasks (add/edit/find/delete) faster using commands than with mouse-driven workflows.
4. All successful modifying commands should persist data automatically to local storage.
5. The application should function without Internet connectivity for all core features.
6. Typical commands should complete within 2 seconds under normal usage conditions.
7. The application should fail gracefully for invalid inputs with actionable error messages.
8. The application should preserve user aliases across restarts.
9. The application is not required to automate internship applications; it is a local tracking/logbook tool.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, macOS.
* **Application record**: A stored entry representing one internship application, including details such as company, role, application date, URL, status, and note.
* **Current displayed list**: The list of application records currently shown to the user, which may be the full list or a filtered subset.
* **Index**: The 1-based position of an application record in the current displayed list.
* **Status**: The current stage of an application, such as Applied, OA, Interview, Offered, Rejected, or Withdrawn.
* **Terminal status**: A status that indicates the application process has ended (`Rejected` or `Withdrawn`).
* **CLI (Command-Line Interface)**: A way of interacting with the application by typing commands.
* **Local storage**: Data saved on the user’s own device rather than on an online server.
* **Job URL**: A web link attached to an application record for quick access to the original job posting or company page.
* **Alias**: A user-defined shortcut for an existing command word.
* **Note**: Free-form text attached to an application entry for reminders and context.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy it into an empty folder.

    2. Open a command window. Launch the jar file using the java -jar command
       Expected: The GUI shows a set of sample application records. The window size may not be optimum.

2. Saving window preferences

    1. Resize the window to a preferred size. Move the window to a different location. Close the window.

    2. Re-launch the app by using the java -jar command.  
       Expected: The most recent window size and location are retained.

3. Exiting the application

    1. Test case: `exit`  
       Expected: The application closes.

    2. Test case: `exit now`  
       Expected: The application closes, as extra words after `exit` are ignored.

### Adding an application

1. Adding a new application

    1. Test case: `add n/Google r/Software Engineer Intern d/2025-04-01`  
       Expected: A new application is added with the given company name, role, and application date. Status defaults to `Applied`.

    2. Test case: `add n/OpenAI r/Research Intern d/2025-04-05 u/https://jobs.openai.com s/Interview`  
       Expected: A new application is added with the given company name, role, date, URL, and status.

    3. Test case: `add n/Shopee r/Backend Intern u/https://careers.shopee.sg`  
       Expected: A new application is added. The current date is used as the application date, and status defaults to `Applied`.

    4. Test case: `add n/Google r/Software Engineer Intern d/2025-04-01` when an identical application already exists  
       Expected: No application is added. An error message is shown because duplicate applications are not allowed.

    5. Test case: `add n/ r/Software Engineer Intern d/2025-04-01`  
       Expected: No application is added. An error message is shown because the company name cannot be blank.

    6. Test case: `add n/Google r/ d/2025-04-01`  
       Expected: No application is added. An error message is shown because the role cannot be blank.

    7. Test case: `add n/Google r/Software Engineer Intern d/2025-02-30`  
       Expected: No application is added. An error message is shown because the date is invalid.

    8. Test case: `add n/Google r/Software Engineer Intern u/google.com`  
       Expected: No application is added. An error message is shown because the URL must start with `http://` or `https://`.

    9. Test case: `add n/Google r/Software Engineer Intern s/InvalidStatus`  
       Expected: No application is added. An error message is shown because only supported status values are accepted.


### Listing applications

1. Listing all applications

    1. Test case: `list`  
       Expected: All application records are shown.

    2. Test case: `list abc`  
       Expected: All application records are shown, as extra words after `list` are ignored.

### Editing an application

1. Editing an application in the displayed list

    1. Prerequisites: Use the `list` command. Multiple application records in the list.

    2. Test case: `edit 1 s/Interview`  
       Expected: The first application’s status is updated to `Interview`.

    3. Test case: `edit 1`  
       Expected: No application is edited. An error message is shown.

    4. Test case: `edit 0 s/OA`  
       Expected: No application is edited. An error message is shown.

    5. Test case: `edit 1 d/2025-02-30`  
       Expected: No application is edited. An error message is shown.

### Deleting an application

1. Deleting an application while all applications are being shown

    1. Prerequisites: Use the `list` command. Multiple application records in the list.

    2. Test case: `delete 1`  
       Expected: The first application is deleted from the list. Details of the deleted application are shown in the result message.

    3. Test case: `delete 0`  
       Expected: No application is deleted. An error message is shown.

    4. Other incorrect delete commands to try: `delete`, `delete x`, `delete 999`  
       Expected: Similar to the previous case.


### Finding applications

1. Finding applications by criteria

    1. Prerequisites: Multiple application records exist.

    2. Test case: `find n/Google`  
       Expected: Only applications with company names matching `Google` are shown.

    3. Test case: `find r/Software Engineer`  
       Expected: Only applications with matching role names are shown.

    4. Test case: `find s/Interview`  
       Expected: Only applications with status `Interview` are shown.

    5. Test case: `find n/Google s/OA`  
       Expected: Only applications matching both company name `Google` and status `OA` are shown.

    6. Test case: `find n/Google r/Intern`  
       Expected: Only applications matching both company name `Google` and role containing `Intern` are shown.

    7. Test case: `find`  
       Expected: An error message is shown because at least one search prefix must be provided.

   8. Test case: `find abc`  
       Expected: An error message is shown because the input does not contain valid prefixes.

   9. Test case: `find s/InvalidStatus`  
       Expected: An error message is shown because the status is invalid and only supported status values are accepted.

### Next and drop commands

1. Advancing application status (`next`)

    1. Prerequisites: Use the `list` command. Multiple application records in the list.

    2. Test case: `next 1`  
       Expected: The first application's status advances by one step in the fixed cycle (`Applied -> OA -> Interview -> Offered -> Rejected -> Withdrawn -> Applied`).

    3. Test case: Execute `next 1` repeatedly after status becomes `Withdrawn`.  
       Expected: The next update cycles status back to `Applied`.

    4. Test case: `next 0`  
       Expected: No application is updated. An error message is shown.

2. Dropping terminal applications (`drop`)

    1. Prerequisites: Current displayed list contains at least one application with status `Rejected` or `Withdrawn`.

    2. Test case: `drop`  
       Expected: All `Rejected`/`Withdrawn` applications in the current list are deleted, and a summary is shown.

    3. Test case: `drop now`  
       Expected: No deletion occurs. An error message is shown because `drop` does not accept arguments.


### Notes commands

1. Setting notes (`note`)

    1. Prerequisites: At least one application exists.

    2. Test case: `note 1 nt/Prepare for OA this weekend.`  
       Expected: Note field for the first application is updated.

    3. Test case: `note 1 nt/`  
       Expected: No note is saved. An error message is shown.

2. Clearing notes (`clearnote`)

    1. Prerequisites: At least one application exists.

    2. Test case: `clearnote 1`  
       Expected: Note for the first application is removed.

    3. Test case: `clearnote 1 extra`  
       Expected: Command is rejected as invalid format.

### Copying a URL

1. Copying an application URL

    1. Prerequisites: At least one displayed application has a URL.

    2. Test case: `copy 1`  
       Expected: The URL of the specified application is copied to the clipboard. A success message is shown.

    3. Test case: `copy -1`  
       Expected: An error message is shown.

    4. Test case: `copy INDEX_WITHOUT_URL`  
       Expected: An error message is shown indicating that there is no URL to copy.


### Alias commands

1. Creating an alias

    1. Test case: `alias ls list`  
       Expected: Alias `ls` is created for `list`.

    2. Test case: `alias ls delete` after creating `ls -> list`  
       Expected: Alias `ls` is updated to point to `delete`.

    3. Test case: `alias list delete`  
       Expected: An error message is shown because built-in command words cannot be used as aliases.

2. Listing aliases

    1. Test case: `alias-list`  
       Expected: All saved aliases are shown in alphabetical order.

3. Removing an alias

    1. Prerequisites: At least one alias exists.

    2. Test case: `unalias ls`  
       Expected: Alias `ls` is removed.

    3. Test case: `unalias noSuchAlias`  
       Expected: An error message is shown.

### Clearing all applications

1. Clearing all applications from the list

    1. Prerequisites: Multiple application records exist.

    2. Test case: `clear`  
       Expected: All application records are removed from the application list.

    3. Test case: `find n/Google` followed by `clear`  
       Expected: All applications in the current filtered list are removed, while applications not shown in the filtered list remain in the application list.

    4. Test case: `clear now`  
       Expected: No applications are removed. An error message is shown because `clear` does not accept any arguments.

    5. Test case: `clear 1`  
       Expected: No applications are removed. An error message is shown because `clear` does not accept any arguments.

    6. Test case: Use `list` after `clear`  
       Expected: The application list is empty if all applications were previously cleared.

### Saving data

1. Dealing with missing/corrupted data files

    1. Delete or rename the data file, then launch the app.  
       Expected: The app starts successfully and recreates the data file.

    2. Edit the data file into an invalid JSON format, then launch the app.  
       Expected: The app handles the corrupted data file gracefully according to the documented behavior.

2. Verifying automatic save

    1. Add, edit, or delete an application, then close the app and relaunch it.
       Expected: The latest changes are retained.
