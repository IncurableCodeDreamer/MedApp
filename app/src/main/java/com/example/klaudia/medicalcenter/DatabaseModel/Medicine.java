package com.example.klaudia.medicalcenter.DatabaseModel;

import java.io.Serializable;

public class Medicine implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String frequency;
    private String amount;
    private String addInfo;

    public Medicine(String name, String frequency, String amount, String addInfo) {
        this.name = name;
        this.frequency = frequency;
        this.amount = amount;
        this.addInfo = addInfo;
    }

    public Medicine() {
    }

    public String getName() {
        return name;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getAmount() {
        return amount;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public static final String TABLE = "medicine";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String FREQUENCY = "frequency";
    public static final String AMOUNT = "amount";
    public static final String ADDINFO = "addInfo";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE + " ( " +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FREQUENCY + " TEXT, " +
                    AMOUNT + " TEXT, " +
                    ADDINFO + " TEXT, " +
                    NAME + " TEXT " + "); ";
}