package com.example.klaudia.medicalcenter;

public class User {
    private String name;
    private String surname;
    private String email;
    private String birthDate;
    private int age;

    public User(String name, String surname, String email, String birthDate, int age) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.age = age;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() { return email; }

    public String getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String EMAIL = "email";
    public static final String BIRTHDATE = "birthDate";
    public static final String AGE = "age";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE + " ( " +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT, " +
                    SURNAME + " TEXT, " +
                    EMAIL + " TEXT, " +
                    BIRTHDATE + " TEXT, " +
                    AGE + " NUMBER, " +"); ";

}