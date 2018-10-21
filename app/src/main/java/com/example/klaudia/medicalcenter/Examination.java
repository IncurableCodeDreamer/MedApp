package com.example.klaudia.medicalcenter;

public class Examination {
    private String name;
    private String date;
    private String description;

    public Examination(String name, String date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public Examination() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final String TABLE = "examination";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE + " ( " +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT, " +
                    DATE + " TEXT, " +
                    DESCRIPTION + " TEXT, " + "); ";
}