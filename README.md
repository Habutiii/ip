# HaBot

> “Productivity is never an accident. It is always the result of a commitment to excellence, intelligent planning, and focused effort.” – Paul J. Meyer

HaBot is a simple, text-based task manager built in Java.  
It helps you organize tasks quickly without distraction. Now improved with **better commands, cleaner output, and more robust testing**.

- text-based
- easy to learn
- fast and responsive
- enhanced with **new features**

All you need to do is,

1. download it from [releases](#).
2. double-click it.
3. start adding tasks.
4. let HaBot handle the rest 😉

And yes, it’s still **FREE**!

---

## 🚀 Improvements Made
- Added **`undo` command** to undo the last mutable task (`add`, `delete`, `mark`, `unmark` and adding of events) done.
- Added **`find` command** to search tasks by keyword (case-insensitive).
- Improved **output formatting** for easier reading.
- Enhanced **unit tests** with JUnit 5 for better reliability.
- Integrated **Checkstyle** to enforce coding standards.
- Refactored classes for cleaner design and maintainability.

---

## ✨ Features
- Manage to-dos, deadlines, and events
- Search tasks with keywords (`find`)
- Mark tasks as done or undone
- Delete tasks easily
- Save and load tasks automatically from a file

Coming soon:
- GUI interface 🎨

---

## 🔧 For Developers
If you’re a Java programmer, HaBot is a great practice project.  
Here’s the entry point:

```java
public class Main {
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}
