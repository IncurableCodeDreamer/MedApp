package com.example.klaudia.medicalcenter;

public class Medicine {
    private String name;
    private String frequency;

    public Medicine(String name, String frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public Medicine() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public static final String TABLE = "medicine";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String FREQUENCY = "frequency";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE + " ( " +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FREQUENCY + " TEXT, " +
                    NAME + " TEXT, " + "); ";
}