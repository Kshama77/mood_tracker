package com.moodtracker;

public class MoodEntry {
    private String date;
    private String mood;
    private String note;

    public MoodEntry(String date, String mood, String note) {
        this.date = date;
        this.mood = mood;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public String getMood() {
        return mood;
    }

    public String getNote() {
        return note;
    }

    public String toFileLine() {
        return clean(date) + "|" + clean(mood) + "|" + clean(note);
    }

    private String clean(String value) {
        return value.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }

    @Override
    public String toString() {
        return "Date: " + date + " | Mood: " + mood + " | Note: " + note;
    }
}
