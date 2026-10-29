package com.moodtracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class MoodTrackerApp {
    private static final String FILE_NAME = "moods.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<MoodEntry> entries = new ArrayList<MoodEntry>();

    public static void main(String[] args) {
        loadEntries();

        boolean running = true;
        System.out.println("================================");
        System.out.println("         MOOD TRACKER");
        System.out.println("================================");

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addEntry();
                    break;
                case "2":
                    viewEntries();
                    break;
                case "3":
                    showMoodSummary();
                    break;
                case "4":
                    deleteEntry();
                    break;
                case "5":
                    saveEntries();
                    running = false;
                    System.out.println("Your entries are saved. Take care!");
                    break;
                default:
                    System.out.println("Please choose a number from 1 to 5.");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n1. Add mood entry");
        System.out.println("2. View all entries");
        System.out.println("3. Mood summary");
        System.out.println("4. Delete an entry");
        System.out.println("5. Save and exit");
        System.out.print("Choose an option: ");
    }

    private static void addEntry() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();
        if (date.isEmpty()) {
            System.out.println("Date cannot be empty.");
            return;
        }

        System.out.println("Choose a mood: Happy, Calm, Excited, Sad, Stressed, Tired, Angry, Other");
        System.out.print("Enter mood: ");
        String mood = scanner.nextLine().trim();
        if (mood.isEmpty()) {
            System.out.println("Mood cannot be empty.");
            return;
        }

        System.out.print("Add a note (optional): ");
        String note = scanner.nextLine().trim();

        entries.add(new MoodEntry(date, mood, note));
        saveEntries();
        System.out.println("Mood entry added!");
    }

    private static void viewEntries() {
        if (entries.isEmpty()) {
            System.out.println("No mood entries yet. Add one first!");
            return;
        }

        System.out.println("\n--- Your Mood Entries ---");
        for (int i = 0; i < entries.size(); i++) {
            System.out.println((i + 1) + ". " + entries.get(i));
        }
    }

    private static void showMoodSummary() {
        if (entries.isEmpty()) {
            System.out.println("No entries available for a summary.");
            return;
        }

        Map<String, Integer> counts = new LinkedHashMap<String, Integer>();
        for (MoodEntry entry : entries) {
            String mood = entry.getMood();
            if (counts.containsKey(mood)) {
                counts.put(mood, counts.get(mood) + 1);
            } else {
                counts.put(mood, 1);
            }
        }

        System.out.println("\n--- Mood Summary ---");
        for (Map.Entry<String, Integer> item : counts.entrySet()) {
            System.out.println(item.getKey() + ": " + item.getValue());
        }
    }

    private static void deleteEntry() {
        viewEntries();
        if (entries.isEmpty()) {
            return;
        }

        System.out.print("Enter the entry number to delete (or 0 to cancel): ");
        String input = scanner.nextLine().trim();
        try {
            int number = Integer.parseInt(input);
            if (number == 0) {
                System.out.println("Delete cancelled.");
            } else if (number >= 1 && number <= entries.size()) {
                entries.remove(number - 1);
                saveEntries();
                System.out.println("Entry deleted.");
            } else {
                System.out.println("That entry number is not in the list.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void loadEntries() {
        Path path = Paths.get(FILE_NAME);
        if (!Files.exists(path)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    entries.add(new MoodEntry(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load saved entries: " + e.getMessage());
        }
    }

    private static void saveEntries() {
        Path path = Paths.get(FILE_NAME);
        try (BufferedWriter writer = Files.newBufferedWriter(
                path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (MoodEntry entry : entries) {
                writer.write(entry.toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save entries: " + e.getMessage());
        }
    }
}
