# HaBot User Guide

## Introduction

HaBot is a **desktop chatbot application** designed to help you manage your tasks efficiently. It supports a variety of task types, including deadlines, events, and to-dos. HaBot is optimized for use via a Command Line Interface (CLI) while also providing a simple Graphical User Interface (GUI).

![HaBot User Interface](Ui.png)

## Contents

1. [Quick Start](#quick-start)
2. [Features](#features)
   - [Adding Deadlines](#adding-deadlines)
   - [Adding Events](#adding-events)
   - [Adding To-Dos](#adding-to-dos)
   - [Listing All Tasks](#listing-all-tasks)
   - [Marking Tasks as Done](#marking-tasks-as-done)
   - [Deleting Tasks](#deleting-tasks)
   - [Exiting the Program](#exiting-the-program)
3. [Command Summary](#command-summary)

## Installation

1. Ensure you have Java 17 or above installed on your computer.
2. Download the latest `HaBot.jar` file from the [releases page](https://github.com/your-repo/releases).
3. Place the file in your desired home folder.

### Graphical User Interface (GUI)

1. Double-click the `HaBot.jar` file to launch the application.
2. Use the GUI to interact with HaBot by typing commands into the input box and pressing Enter.

### Command Line Interface (CLI)

1. Open a terminal, navigate to the folder containing the `HaBot.jar` file, and run the command:
   ```
   java -jar HaBot.jar
   ```
2. Start typing commands into the command box and press Enter to execute them.

## Features

### 1. Adding Deadlines

Add a task with a deadline to your task list.

- **Format:**
  ```
  deadline TASK_NAME /by DATE_TIME
  ```

- **Example:**
  ```
  deadline Submit report /by 2025-09-10 23:59
  ```


### 2. Adding Events

Add an event to your task list.

- **Format:**
  ```
  event EVENT_NAME /at DATE_TIME
  ```

- **Example:**
  ```
  event Team meeting /at 2025-09-07 14:00
  ```


### 3. Adding To-Dos

Add a simple to-do task to your task list.

- **Format:**
  ```
  todo TASK_NAME
  ```

- **Example:**
  ```
  todo Buy groceries
  ```


### 4. Listing All Tasks

View all tasks in your task list.

- **Format:**
  ```
  list
  ```

- **Example Output:**
  ```
  Here are the tasks in your list:

  1. [T][ ] Buy groceries

  2. [D][X] Submit report (by: Sep 10 2025, 11:59 PM)

  3. [E][ ] Team meeting (at: Sep 7 2025, 2:00 PM)
  ```

- **Explanation of Fields:**
  - `[T]`, `[D]`, `[E]`: Indicates the type of task (To-Do, Deadline, or Event).
  - `[ ]` or `[X]`: Indicates whether the task is incomplete (`[ ]`) or completed (`[X]`).
  - Task description: The name or title of the task.
  - `(by: DATE_TIME)` or `(at: DATE_TIME)`: For deadlines and events, specifies the due date or event time.


### 5. Marking Tasks as Done

Mark a task as completed.

- **Format:**
  ```
  mark INDEX
  ```

- **Example:**
  ```
  mark 1
  ```


### 6. Deleting Tasks

Remove a task from your task list.

- **Format:**
  ```
  delete INDEX
  ```

- **Example:**
  ```
  delete 2
  ```


### 7. Exiting the Program

Exit HaBot.

- **Format:**
  ```
  bye
  ```
