---
layout: default.md
title: "User Guide"
pageNav: 3
---

<h1 style="color:#e16a1f;">LockedIn User Guide</h1>

LockedIn is a desktop app for **Computer Science undergraduates applying for internships**. It helps you keep track of applications, roles, links, statuses, and notes in one place.

LockedIn is optimized for users who prefer typing commands. If you are comfortable with keyboard-driven workflows, LockedIn helps you manage applications faster while reducing context switching between job portals, spreadsheets, and email threads.

<!-- * Table of Contents -->

<page-nav-print />

---

## <span style="color:#4a90e2;">About LockedIn</span>

<br>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Who LockedIn is for
</h3>

LockedIn is designed for Computer Science undergraduates who:

* apply to many internships at once
* want to track applications across different companies and portals
* prefer fast keyboard-based input
* want one place to record important application details

<br>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
What LockedIn helps you do
</h3>

LockedIn helps you:

* record internship applications
* store company names and role titles
* save application links for quick access
* track the current stage of each application
* add notes such as reminders, interview details, or deadlines
* search for applications by company, role, application date, date range, or status
* update entries quickly as applications progress

<br>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
What LockedIn does not do
</h3>

LockedIn does **not**:

* submit job applications for you
* sync directly with job portals or email inboxes
* automatically detect application updates
* generate analytics or summaries of your applications

It is a fast CLI-based logbook for managing internship applications.

<br>

---

## <span style="color:#4a90e2;">Quick Start</span>

<br>

Follow these steps to get LockedIn running.

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
1. Install Java
</h3>

Ensure that Java `17` or above is installed on your computer.

**Mac users:** Ensure that you use the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

---

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
2. Download LockedIn
</h3>

Download the latest `.jar` file from the [LockedIn release page](https://github.com/AY2526S2-CS2103T-W12-2/tp/releases).

---

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
3. Choose a folder for LockedIn
</h3>

Copy the `.jar` file to the folder you want to use as the *home folder* for LockedIn.

---

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
4. Open LockedIn
</h3>

Open a terminal in that folder and run:

`java -jar lockedin.jar`

> If your downloaded file has a different name, replace `lockedin.jar` with the actual file name.

A GUI similar to the one below should appear in a few seconds. The app starts with sample data so that you can see how LockedIn works.

![firstLaunch](images/firstLaunch.png)

---

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
5. Enter a command
</h3>

Type a command in the command box and press Enter to run it.

Here are a few commands you can try:

* `list`
  Shows all saved applications.

* `add n/Google r/Software Engineer Intern d/2025-02-14 u/https://careers.google.com s/Applied`
  Adds a new application.

* `note 1 Submitted resume through referral`
  Adds a note to the 1st displayed application.

* `delete 3`
  Deletes the 3rd application shown in the current list.

* `help`
  Opens the help window.

<br>

---

## <span style="color:#4a90e2;">Features</span>

<br>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Notes about the command format
</h3>

<box type="info" seamless>

**How to read command formats**

* Words in `UPPER_CASE` are values to be supplied by the user.
  Example: in `add n/COMPANY`, `COMPANY` can be used as `Google`.

* Items in square brackets are optional.
  Example: `n/COMPANY [u/URL]` can be used as `n/Google u/https://careers.google.com` or as `n/Google`.

* Parameters can be entered in any order unless stated otherwise.
  Example: if the command specifies `n/COMPANY r/ROLE`, `r/ROLE n/COMPANY` is also accepted.

* Extra words for commands that do not take parameters, such as `help`, `list`, and `exit`, are ignored.
  Example: `help 123` is treated as `help`.

* Commands such as `clear` and `drop` do not accept any arguments.
  Example: `clear 4` will show an error message.

* Leading and trailing spaces around field values are ignored.
  Example: `add n/  Google   r/  Software Engineer  ` is treated the same as `add n/Google r/Software Engineer`.

* If you are using a PDF version of this document, be careful when copying commands that wrap across multiple lines. Spaces around line breaks may be omitted when pasted into the app.

</box>

<a id="supported-characters"></a>

<box type="info" seamless>

**Valid characters for company, role, and note**

LockedIn accepts:

* English letters (`A-Z`, `a-z`)
* digits (`0-9`)
* spaces
* these symbols:
  `` ` ~ ! @ # $ % ^ & * ( ) - _ = + [ { ] } \ | ; : ' " , < . > / ? ``

Characters outside this set are rejected.

</box>

<box type="warning" seamless>

**About non-ASCII characters and decorative fonts**

LockedIn currently validates company names, role names, and notes using a fixed ASCII-only character set. This means decorative or non-ASCII text, emojis, and many non-English characters may be rejected or behave differently from normal English text.

For best results, use standard English letters, digits, spaces, and the supported symbols listed above.

</box>

<box type="tip" seamless>

**Tip:**
You can press the `Up` and `Down` arrow keys in the command box to browse previously entered commands in the current session.

</box>

---

<a id="add"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Add an application: <code>add</code>
</h3>

Adds a new application to LockedIn.

| Before                             | After                            |
| ---------------------------------- | -------------------------------- |
| ![beforeAdd](images/beforeAdd.png) | ![afterAdd](images/afterAdd.png) |

**Format:** `add n/COMPANY r/ROLE [d/APPLICATION_DATE] [u/URL] [s/STATUS]`

**Field meaning**

* `COMPANY` — company name
* `ROLE` — position title
* `APPLICATION_DATE` — the date when you applied
* `URL` — application link or portal link
* `STATUS` — current application stage

**Notes**

* `COMPANY` and `ROLE` must not be blank.
* `COMPANY` and `ROLE` may contain only [supported characters](#supported-characters).
* `COMPANY` must be at most 100 characters long.
* `ROLE` must be at most 100 characters long.
* Company and role comparisons are case-insensitive. For example, `Google` and `GOOGLE` are treated as the same company.
* `APPLICATION_DATE` must be a valid date in the format `yyyy-MM-dd`.
* If `d/APPLICATION_DATE` is omitted, LockedIn uses the current date by default.
* `URL`, if provided, must start with `http://` or `https://`.
* If `s/STATUS` is omitted, LockedIn uses `Applied` by default.
* Duplicate applications have the same company, role, and application date. LockedIn rejects duplicate applications.

**Examples**

* `add n/Google r/Software Engineer Intern d/2025-02-14`
* `add n/OpenAI r/Research Intern d/2025-03-01 u/https://jobs.openai.com s/Interview`
* `add n/Shopee r/Backend Intern d/2025-02-20 u/https://careers.shopee.sg`

**What you should expect**

* A success message appears.
* The new application is added to the list.

---

<a id="edit"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Edit an application: <code>edit</code>
</h3>

Edits an existing application in LockedIn.

| Before                               | After                              |
| ------------------------------------ | ---------------------------------- |
| ![beforeEdit](images/beforeEdit.png) | ![afterEdit](images/afterEdit.png) |

**Format:** `edit INDEX [n/COMPANY] [r/ROLE] [d/APPLICATION_DATE] [u/URL] [s/STATUS]`

**Notes**

* `INDEX` refers to the index number shown in the displayed application list.
* `INDEX` must be a positive integer.
* You must provide at least one field to edit.
* Existing values are updated to the input values.
* `COMPANY` and `ROLE` must not be blank.
* `COMPANY` and `ROLE` may contain only [supported characters](#supported-characters).
* `COMPANY` must be at most 100 characters long.
* `ROLE` must be at most 100 characters long.
* `APPLICATION_DATE` must be a valid date in the format `yyyy-MM-dd`.
* `URL`, if provided, must start with `http://` or `https://`.
* If the edited values make the application a duplicate of an existing one, the edit is rejected.

**Examples**

* `edit 1 r/Software Engineer d/2025-03-10`
* `edit 2 n/OpenAI s/Offered`
* `edit 3 u/https://careers.example.com s/OA`

**What you should expect**

* A success message appears.
* The selected application is updated.

---

<a id="delete"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Delete an application: <code>delete</code>
</h3>

Deletes the specified application from LockedIn.

| Before                                   | After                                  |
| ---------------------------------------- | -------------------------------------- |
| ![beforeDelete](images/beforeDelete.png) | ![afterDelete](images/afterDelete.png) |

**Format:** `delete INDEX`

**Notes**

* `INDEX` refers to the index number shown in the displayed list.
* `INDEX` must be a positive integer.

**Examples**

* `delete 2`
* `find n/Google` followed by `delete 1`

**What you should expect**

* The selected application is removed from the list.

---

<a id="find"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Find applications: <code>find</code>
</h3>

Finds applications whose company names, roles, application dates, date ranges, or statuses match the given filters.

| Before                               | After                              |
| ------------------------------------ | ---------------------------------- |
| ![beforeFind](images/beforeFind.png) | ![afterFind](images/afterFind.png) |

**Format:** `find [n/COMPANY] [r/ROLE] [d/DATE_OR_DATE_RANGE] [s/STATUS]`

**Date formats supported**

* Exact date: `d/2025-03-14`
* Date range: `d/2025-03-01:2025-03-31`

**Notes**

* You must provide at least one field.
* The search is case-insensitive.
  Example: `n/google` matches `Google`.
* For company, role, and status, applications matching at least one keyword in the same field are returned.
* If multiple fields are specified, applications must match all those fields.
* `d/START_DATE:END_DATE` returns applications whose application dates fall within the range, inclusive.
* `START_DATE` must be earlier than or equal to `END_DATE`.
* All dates must be valid and must follow the format `yyyy-MM-dd`.

**Examples**

* `find n/Google`
* `find r/Intern`
* `find n/Google r/Intern`
* `find s/Applied`
* `find d/2025-03-14`
* `find d/2025-03-01:2025-03-31`
* `find n/TikTok d/2025-02-01:2025-02-28 s/Interview`

**What you should expect**

* The application list updates to show only matching entries.

<box type="tip" seamless>

**Tip:**
After using `find`, use `list` to return to the full application list.

</box>

---

<a id="list"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
List all applications: <code>list</code>
</h3>

Shows all applications in LockedIn.

| Before                               | After                              |
| ------------------------------------ | ---------------------------------- |
| ![beforeList](images/beforeList.png) | ![afterList](images/afterList.png) |

**Format:** `list`

**What you should expect**

* The full application list is shown again.
* This is useful after using `find` to return to the full list.

---

<a id="note"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Add or replace a note: <code>note</code>
</h3>

Adds or replaces the note of an existing application.

**Format:** `note INDEX NOTE`

**Notes**

* `INDEX` refers to the index number shown in the displayed list.
* `INDEX` must be a positive integer.
* `NOTE` does **not** use a prefix.
* `NOTE` must not be blank.
* `NOTE` may contain only [supported characters](#supported-characters).
* `NOTE` must be at most 500 characters long.
* If the selected application already has a note, the old note is replaced.
* Notes can be used to store reminders, interview details, recruiter names, deadlines, or any other application-related information.

**Examples**

* `note 1 Submitted resume through referral`
* `note 2 OA deadline is 2025-03-15`
* `note 3 Recruiter mentioned response within 2 weeks`

**What you should expect**

* A success message appears.
* The selected application's note is updated.

---

<a id="clearnote"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Clear an application note: <code>clearnote</code>
</h3>

Clears the note of the specified application.

| Before                                         | After                                        |
| ---------------------------------------------- | -------------------------------------------- |
| ![beforeClearNote](images/beforeClearNote.png) | ![afterClearNote](images/afterClearNote.png) |

**Format:** `clearnote INDEX`

**Notes**

* `INDEX` refers to the index number shown in the displayed list.
* `INDEX` must be a positive integer.
* The selected application's note is reset to an empty note.

**Examples**

* `clearnote 1`
* `find n/Google` followed by `clearnote 1`

**What you should expect**

* The selected application's note is cleared.
* A success message appears.

---

<a id="copy"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Copy an application URL: <code>copy</code>
</h3>

Copies the URL of an application to your system clipboard.

| Before                               | After                              |
| ------------------------------------ | ---------------------------------- |
| ![beforeCopy](images/beforeCopy.png) | ![afterCopy](images/afterCopy.png) |

**Format:** `copy INDEX`

**Notes**

* `INDEX` refers to the index number shown in the displayed list.
* `INDEX` must be a positive integer.
* The selected application must already contain a URL.

**Examples**

* `copy 1`
* `find n/Google` followed by `copy 1`

**What you should expect**

* If the selected application has a URL, LockedIn copies it to your clipboard.
* If the selected application has no URL, LockedIn shows an error message.

---

<a id="next"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Move an application to the next stage: <code>next</code>
</h3>

Moves an application to the next stage in the application workflow.

| Before                               | After                              |
| ------------------------------------ | ---------------------------------- |
| ![beforeNext](images/beforeNext.png) | ![afterEdit](images/afterNext.png) |

**Format:** `next INDEX`

**Notes**

* `INDEX` refers to the index number shown in the displayed list.
* `INDEX` must be a positive integer.
* LockedIn updates the selected application to the next stage in the status sequence.
* If you are viewing a filtered list, the filter is preserved after the command executes.

**Current status sequence**
`Applied -> OA -> Interview -> Offered -> Rejected -> Withdrawn -> Applied`

**Examples**

* `next 1`
* `list` followed by `next 3`

**What you should expect**

* The selected application's status changes to the next stage.

<box type="tip" seamless>

**Tip:**
`next` follows a fixed sequence. If you want to set a specific status directly, use `edit INDEX s/STATUS` instead.

</box>

---

<a id="drop"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Drop rejected or withdrawn applications: <code>drop</code>
</h3>

Deletes applications with `Rejected` or `Withdrawn` status from the current displayed list.

**Format:** `drop`

**Notes**

* The `drop` command does not accept any arguments.
* Only applications in the current filtered list with terminal statuses are deleted.

**What you should expect**

* Applications with `Rejected` and `Withdrawn` status from the current displayed list are removed.
* A message shows how many applications were dropped and lists them.

---

<a id="alias"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Give an alias to a command word: <code>alias</code>
</h3>

Creates a shortcut for an existing command word.

| Setting an alias                 | Success                                  |
| -------------------------------- | ---------------------------------------- |
| ![setAlias](images/setAlias.png) | ![successAlias](images/successAlias.png) |

| Using an alias                   | Result                                 |
| -------------------------------- | -------------------------------------- |
| ![useAlias](images/useAlias.png) | ![resultAlias](images/resultAlias.png) |

**Format:** `alias ALIAS COMMAND_WORD`

**Notes**

* `ALIAS` must be a single word.
* `COMMAND_WORD` must be an existing built-in command word.
* `ALIAS` cannot be an existing built-in command word.
* If an alias already exists, it is updated to point to the new command word.

**Examples**

* `alias ls list`
* `alias rm delete`

**What you should expect**

* You can use the alias in place of the original command word.
* Example: after `alias ls list`, entering `ls` will run `list`.
* The app informs you when an existing alias has been overwritten.

---

<a id="unalias"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Remove an alias: <code>unalias</code>
</h3>

Removes an existing alias.

**Format:** `unalias ALIAS`

**Examples**

* `unalias ls`

**What you should expect**

* The alias is removed.
* After `unalias ls`, entering `ls` will no longer work unless you create it again.

---

<a id="alias-list"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
List all saved aliases: <code>alias-list</code>
</h3>

Displays all currently saved aliases.

| Before                                         | After                                        |
| ---------------------------------------------- | -------------------------------------------- |
| ![beforeAliasList](images/beforeAliasList.png) | ![afterAliasList](images/afterAliasList.png) |

**Format:** `alias-list`

**Examples**

* `alias-list`

**What you should expect**

* All saved aliases are shown in the response box.
* Each alias is displayed in the format `ALIAS -> COMMAND_WORD`.
* If there are no saved aliases, a message is shown.

---

<a id="clear"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Clear applications: <code>clear</code>
</h3>

Deletes all applications from the current displayed list.

| Before                                 | After                                |
| -------------------------------------- | ------------------------------------ |
| ![beforeClear](images/beforeClear.png) | ![afterClear](images/afterClear.png) |

**Format:** `clear`

**Notes**

* The `clear` command does not accept any arguments.
* Only applications in the current filtered list are deleted.
* If you are viewing all applications using `list`, all applications are deleted.
* If you are viewing a filtered subset using `find`, only those displayed applications are deleted.

**What you should expect**

* Applications from the current displayed list are removed.
* A message shows how many applications were cleared and lists them.

<box type="warning" seamless>

**Warning:**
This command removes all applications from the current displayed list. Use it carefully.

</box>

<box type="tip" seamless>

**Tip:**
If you want to remove all applications in LockedIn, first run `list` to show all applications, then run `clear`.

</box>

---

<a id="help"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
View help: <code>help</code>
</h3>

Opens the help window.

**Format:** `help`

![help message](images/helpMessage.png)

**What you should expect**

* A help window appears.

---

<a id="exit"></a>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Exit the program: <code>exit</code>
</h3>

**Format:** `exit`

**What you should expect**

* The window closes.

<br>

---

## <span style="color:#4a90e2;">Saving and Editing Data</span>

<br>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Saving data
</h3>

LockedIn data are saved automatically after any command that changes the data.

You do not need to save manually.

<br>

<h3 style="font-size: 1.3em; color: #d9730d; margin-top: 1.2em; margin-bottom: 0.4em;">
Editing the data file
</h3>

LockedIn data are stored in:

`[JAR file location]/data/lockedin.json`

Advanced users may update data directly by editing that file.

<box type="warning" seamless>

**Caution:**
If your changes make the data file invalid, LockedIn may discard the data and start with an empty data file the next time it runs.

Before editing the data file:

* make a backup copy first
* keep the JSON format valid
* edit the file only if you are confident you understand the format

</box>

<br>

---

## <span style="color:#4a90e2;">FAQ</span>

<br>

**Q: Can I use emoji or non-English characters in company, role, or note fields?**<br>
A: No. LockedIn currently supports only English letters, digits, spaces, and a [fixed set of symbols](#supported-characters) for those fields.

<br>

**Q: Why does certain fonts not work properly?**<br>
A: LockedIn validates the company, role, and note fields using an ASCII-only character set, so other fonts not in the set may be rejected.

<br>

**Q: What date format should I use?**<br>
A: Use the format `yyyy-MM-dd`. For example, `2025-02-14`.

<br>

**Q: What does `INDEX` mean?**<br>
A: `INDEX` is the number shown next to an application in the current displayed list. Use it for commands such as `edit`, `delete`, `next`, `copy`, `note`, and `clearnote`.

<br>

**Q: Why does my command not work?**<br>
A: Check the command format carefully. Common mistakes include:

* using [unsupported characters](#supported-characters)
* exceeding the character limit
* using the wrong date format
* entering an invalid index
* forgetting a required prefix such as `n/` or `r/`
* omitting required fields

<br>

**Q: Why can’t I copy a URL?**<br>
A: The selected application may not have a URL saved. Add one first using `edit INDEX u/URL`.

<br>

**Q: How do I return to the full application list after using `find`?**<br>
A: Use the `list` command.

<br>

**Q: What statuses can an application have?**<br>
A: LockedIn currently uses these statuses: `Applied`, `OA`, `Interview`, `Offered`, `Rejected`, and `Withdrawn`.

<br>

**Q: What makes two applications duplicates?**<br>
A: Two applications are duplicates if they have the same company, role, and application date. URL, status, and note do not affect duplicate detection.

<br>

**Q: What is the difference between `delete`, `clear`, and `drop`?**<br>
A:

* `delete INDEX` removes one application.
* `clear` removes all applications in the current displayed list.
* `drop` removes only displayed applications with `Rejected` or `Withdrawn` status.

<br>

**Q: How do I move my data to another computer?**<br>
A: Install LockedIn on the other computer and replace the empty data file it creates with the data file from your current LockedIn home folder.

<br>

---

## <span style="color:#4a90e2;">Known Issues</span>

<br>

##### 1. Application opens off-screen after switching displays

**Problem:**
When using multiple screens, the application window may be placed on a secondary screen. If you later switch back to using only the primary screen, the GUI may open off-screen.

**Solution:**
Delete the `preferences.json` file before starting the application again.

##### 2. Help window does not appear after being minimized

**Problem:**
If the Help window is minimized and you run `help` again:

* the existing Help window may stay minimized
* no new Help window may be shown

**Solution:**
Manually restore the minimized Help window.

<br>

---

## <span style="color:#4a90e2;">Command Summary</span>

<br>

| Action         | Format                                                                    | Example                                                |
| -------------- | ------------------------------------------------------------------------- | ------------------------------------------------------ |
| **Add**        | `add n/COMPANY r/ROLE [d/APPLICATION_DATE] [u/URL] [s/STATUS]`            | `add n/Google r/Software Engineer Intern d/2025-02-14` |
| **Alias**      | `alias ALIAS COMMAND_WORD`                                                | `alias ls list`                                        |
| **Alias List** | `alias-list`                                                              | `alias-list`                                           |
| **Clear**      | `clear`                                                                   | `clear`                                                |
| **Clear Note** | `clearnote INDEX`                                                         | `clearnote 1`                                          |
| **Copy**       | `copy INDEX`                                                              | `copy 3`                                               |
| **Delete**     | `delete INDEX`                                                            | `delete 3`                                             |
| **Drop**       | `drop`                                                                    | `drop`                                                 |
| **Edit**       | `edit INDEX [n/COMPANY] [r/ROLE] [d/APPLICATION_DATE] [u/URL] [s/STATUS]` | `edit 2 n/OpenAI s/Offered`                            |
| **Exit**       | `exit`                                                                    | `exit`                                                 |
| **Find**       | `find [n/COMPANY] [r/ROLE] [d/DATE_OR_DATE_RANGE] [s/STATUS]`             | `find n/Google d/2025-03-01:2025-03-31`                |
| **Help**       | `help`                                                                    | `help`                                                 |
| **List**       | `list`                                                                    | `list`                                                 |
| **Next**       | `next INDEX`                                                              | `next 3`                                               |
| **Note**       | `note INDEX NOTE`                                                         | `note 1 OA deadline is 2025-03-15`                     |
| **Unalias**    | `unalias ALIAS`                                                           | `unalias ls`                                           |
