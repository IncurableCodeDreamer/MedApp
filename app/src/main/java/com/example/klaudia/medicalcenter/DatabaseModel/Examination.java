package com.example.klaudia.medicalcenter.DatabaseModel;

public class Examination {
    private String name;
    private String date;
    private String address;
    private String type;
    private String addInfo;
    private boolean nofi;
    private String description;
    private String hour;
    private int id;
    private String note;

    public Examination(String name, String date, String description, String address, String type, String addInfo, boolean nofi, String note, String hour) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.addInfo = addInfo;
        this.address = address;
        this.nofi = nofi;
        this.type = type;
        this.note = note;
        this.hour = hour;
    }

    public Examination() {
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public String getNote() {
        return note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public boolean isNofi() {
        return nofi;
    }

    public void setNofi(boolean nofi) {
        this.nofi = nofi;
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
    public static final String ADDINFO = "addInfo";
    public static final String ADDRESS = "address";
    public static final String NOTIFY = "notifycation";
    public static final String TYPE = "type";
    public static final String NOTE = "note";
    public static final String HOUR = "hour";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE + " ( " +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT, " +
                    DATE + " TEXT, " +
                    ADDINFO + " TEXT, " +
                    ADDRESS + " TEXT, " +
                    NOTIFY + " TEXT, " +
                    HOUR + " TEXT, " +
                    TYPE + " TEXT, " +
                    NOTE + " TEXT, " +
                    DESCRIPTION + " TEXT " + "); ";
}