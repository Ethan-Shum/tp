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

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

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

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

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

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


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

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

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

| Priority | As a …​                                     | I want to …​                                 | So that I can…​                                                        |
|----------|--------------------------------------------|---------------------------------------------|-----------------------------------------------------------------------|
| `* * *`  | first time user                            | add an application record                   | track my application                                                  |
| `* * *`  | first time user                            | view all applications in a list             | see all my applications                                               |
| `* * *`  | first time user                            | delete application records                  | remove companies I am no longer interested in                         |
| `* * *`  | first time user                            | edit an application's details               | correct mistakes I made                                               |
| `* * *`  | user                                       | update an application status                | track the progress of my application                                  |
| `* * *`  | user                                       | tag a deadline to each company              | track my deadlines efficiently                                        |
| `* * *`  | user                                       | save data on my hard disk                   | access my records locally                                             |
| `* * *`  | user                                       | add a job URL to an entry                   | quickly revisit the job posting                                       |
| `* *`    | user                                       | sort company list by deadline               | know which deadlines are coming up                                    |
| `* *`    | user                                       | filter the list by status                   | see only my active applications                                       |
| `* *`    | user                                       | view the potential pay of each position     | know which applications result in higher pay                          |
| `* *`    | user                                       | filter the list by pay range                | determine expected salary levels                                      |
| `* *`    | user                                       | view total number of applications by status | track my overall progress                                             |
| `* *`    | user                                       | auto save my data after every command       | avoid losing data if the terminal closes                              |
| `* *`    | user                                       | copy contact info between entries           | avoid retyping everything                                             |
| `* *`    | user                                       | favourite companies                         | track companies I am particularly interested in                       |
| `* *`    | first time user                            | see dummy data                              | understand how the data is structured                                 |
| `* *`    | first time user                            | find company contact details by name        | follow up with companies easily                                       |
| `*`      | user                                       | pin the application window on top           | keep the logbook visible while browsing job portals                   |
| `*`      | user                                       | undo a command                              | recover from accidental deletions                                     |
| `*`      | expert user                                | use short aliases                           | type commands faster                                                  |
| `*`      | expert user                                | use tab completion                          | avoid typing full commands                                            |
| `*`      | expert user                                | cycle through previous commands             | reuse previously typed commands                                       |
| `*`      | expert user                                | bulk delete applications by status          | remove rejected or ghosted applications quickly                       |
| `*`      | expert user                                | export data into another file format        | keep backups or reuse data elsewhere                                  |
| `*`      | expert user                                | add tags to companies                       | record additional information about companies                         |


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

* 2d. An application with the same company, role, and application date already exists.

    * 2d1. LockedIn shows an error message indicating that a duplicate application record already exists.

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

    * 2a1. LockedIn shows an error message.

      Use case ends.


**Use case: Add Job URL**

**Preconditions:**
* At least one application record exists in the system.

**MSS:**

1. User wants to add a job URL to an application record.
2. User requests to view the application records.
3. LockedIn shows the application records.
4. User specifies the application record and the URL to be added.
5. LockedIn updates the application record with the URL.
6. LockedIn shows a confirmation message.

   Use case ends.

**Extensions:**

* 4a. The specified index is invalid.

    * 4a1. LockedIn shows an error message.

      Use case resumes at step 3.

* 4b. The specified URL is invalid.

    * 4b1. LockedIn shows an error message.

      Use case resumes at step 3.


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

1.  Should work on mainstream OS as long as Java 17 or above is installed.
2.  Should be able to store and manage at least 1000 application records without noticeable sluggishness for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should automatically save data after each modifying command.
5.  Should store data locally on the user’s device and not require Internet access for normal operation.
6.  Is not required to automate the internship application process since it only serves as a local application logbook.
7.  Should respond to typical commands within 2 seconds under normal usage conditions.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, macOS.
* **Application record**: A stored entry representing one internship application, including details such as company, role, application date, URL, status, and note.
* **Current displayed list**: The list of application records currently shown to the user, which may be the full list or a filtered subset.
* **Index**: The 1-based position of an application record in the current displayed list.
* **Status**: The current stage of an application, such as Applied, OA, Interview, Offered, Rejected, or Withdrawn.
* **CLI (Command-Line Interface)**: A way of interacting with the application by typing commands.
* **Local storage**: Data saved on the user’s own device rather than on an online server.
* **Job URL**: A web link attached to an application record for quick access to the original job posting or company page.
* **Alias**: A user-defined shortcut for an existing command word.

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

    2. Double-click the jar file.  
       Expected: The GUI shows a set of sample application records. The window size may not be optimum.

2. Saving window preferences

    1. Resize the window to a preferred size. Move the window to a different location. Close the window.

    2. Re-launch the app by double-clicking the jar file.  
       Expected: The most recent window size and location are retained.

3. Exiting the application

    1. Test case: `exit`  
       Expected: The application closes.

    2. Test case: `exit now`  
       Expected: The application closes, as extra words after `exit` are ignored.


### Listing applications

1. Listing all applications

    1. Test case: `list`  
       Expected: All application records are shown.

    2. Test case: `list abc`  
       Expected: All application records are shown, as extra words after `list` are ignored.


### Deleting an application

1. Deleting an application while all applications are being shown

    1. Prerequisites: Use the `list` command. Multiple application records in the list.

    2. Test case: `delete 1`  
       Expected: The first application is deleted from the list. Details of the deleted application are shown in the result message.

    3. Test case: `delete 0`  
       Expected: No application is deleted. An error message is shown.

    4. Other incorrect delete commands to try: `delete`, `delete x`, `delete 999`  
       Expected: Similar to the previous case.


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


### Finding applications

1. Finding applications by criteria

    1. Prerequisites: Multiple application records exist.

    2. Test case: `find n/Google`  
       Expected: Only matching applications are shown.

    3. Test case: `find`  
       Expected: An error message is shown.

    4. Test case: `find n/NoSuchCompany`  
       Expected: An empty filtered list is shown.


### Copying a URL

1. Copying an application URL

    1. Prerequisites: At least one displayed application has a URL.

    2. Test case: `copy 1`  
       Expected: The URL of the specified application is copied to the clipboard. A success message is shown.

    3. Test case: `copy 999`  
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


### Saving data

1. Dealing with missing/corrupted data files

    1. Delete or rename the data file, then launch the app.  
       Expected: The app starts successfully and recreates the data file.

    2. Edit the data file into an invalid JSON format, then launch the app.  
       Expected: The app handles the corrupted data file gracefully according to the documented behavior.

2. Verifying automatic save

    1. Add, edit, or delete an application, then close the app and relaunch it.  
       Expected: The latest changes are retained.
