package com.example.klaudia.medicalcenter.DatabaseModel;

public class Account {
    private String name;
    private String value;

    public Account(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Account() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final String TABLE = "account";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String VALUE = "value";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE + " ( " +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    VALUE + " TEXT, " +
                    NAME + " TEXT " + "); ";
}