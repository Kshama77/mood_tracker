# MoodTracker

A simple beginner-friendly Java console application to record daily moods and notes.

## Features
- Add a mood entry with a date, mood, and note
- View all saved entries
- View a summary of how often each mood was recorded
- Delete an entry
- Save entries to a local `moods.txt` file so they remain after restarting the app

## Java concepts used
Variables, `Scanner`, loops, `ArrayList`, classes and objects, methods, conditionals, file reading/writing, and basic input validation.

## Requirements
- Java 8 or newer
- A terminal or command prompt

## Run the app

From the project folder, compile the source:

```bash
javac -d out src/main/java/com/moodtracker/*.java
```

Then run it:

```bash
java -cp out com.moodtracker.MoodTrackerApp
```

The app creates `moods.txt` in the current working directory when you save your first entry.

## Upload to GitHub
1. Create a new repository named `MoodTracker` on GitHub.
2. Unzip this project.
3. Upload the contents of the `MoodTracker` folder, or run `git init` in this folder and push it to your new repository.

## Example
Choose `1` from the menu and enter a date such as `2026-10-29`, a mood such as `Happy`, and a short note.
