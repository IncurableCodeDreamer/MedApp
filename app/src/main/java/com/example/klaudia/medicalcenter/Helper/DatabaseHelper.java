package com.example.klaudia.medicalcenter.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.klaudia.medicalcenter.DatabaseModel.Account;
import com.example.klaudia.medicalcenter.DatabaseModel.Examination;
import com.example.klaudia.medicalcenter.DatabaseModel.Medicine;
import com.example.klaudia.medicalcenter.DatabaseModel.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "NewDatabase4";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(Account.CREATE_TABLE);
        db.execSQL(Examination.CREATE_TABLE);
        db.execSQL(Medicine.CREATE_TABLE);
        Log.i("db", "create" + DB_NAME);
    }

    public boolean doesDatabaseExist(Context context) {
        File dbFile = context.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Account.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Examination.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Medicine.TABLE);
        onCreate(db);
    }

    //region User
    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(User.NAME, user.getName());
        values.put(User.SURNAME, user.getSurname());
        values.put(User.EMAIL, user.getEmail());
        values.put(User.BIRTHDATE, user.getBirthDate());
        values.put(User.AGE, user.getAge());
        values.put(User.IFDONOR, user.isIfDonor());
        values.put(User.PICTURE, user.getPicture());

        db.insert(User.TABLE, null, values);
        db.close();
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(User.NAME, user.getName());
        values.put(User.SURNAME, user.getSurname());
        values.put(User.EMAIL, user.getEmail());
        values.put(User.BIRTHDATE, user.getBirthDate());
        values.put(User.AGE, user.getAge());
        values.put(User.IFDONOR, user.isIfDonor());
        values.put(User.PICTURE, user.getPicture());

        db.update(User.TABLE, values, User.ID + "=1", null);
    }

    public int getUserCount() {
        String countQuery = "SELECT * FROM " + User.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public User getUser() {
        String Query = "SELECT * FROM " + User.TABLE + " WHERE " + User.ID + " = " + 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();

        User user = new User();
        user.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
        user.setSurname(cursor.getString(cursor.getColumnIndex(User.SURNAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(User.EMAIL)));
        user.setBirthDate(cursor.getString(cursor.getColumnIndex(User.BIRTHDATE)));
        user.setAge(cursor.getInt(cursor.getColumnIndex(User.AGE)));
        user.setPicture(cursor.getBlob(cursor.getColumnIndex(User.PICTURE)));
        user.setIfDonor(cursor.getInt(cursor.getColumnIndex(User.IFDONOR)) != 0);

        cursor.close();
        db.close();
        return user;
    }
    //endregion

    //region Account
    public void insertOneAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Account.NAME, account.getName());
        values.put(Account.VALUE, account.getValue());

        db.insert(Account.TABLE, null, values);
        db.close();
    }

    public int getAccountCount() {
        String countQuery = "SELECT  * FROM " + Account.TABLE + " WHERE " + Account.VALUE + " IS NOT NULL";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Account getAccount() {
        String Query = "SELECT * FROM " + Account.TABLE + " WHERE " + Account.ID + " = " + 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();

        Account account = new Account();
        account.setName(cursor.getString(cursor.getColumnIndex(Account.NAME)));
        account.setValue(cursor.getString(cursor.getColumnIndex(Account.VALUE)));

        cursor.close();
        db.close();
        return account;
    }

    public void deleteAllAcount() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Account.TABLE, null, null);
    }

    public ArrayList<Account> getAllAccount() {
        String Query = "SELECT * FROM " + Account.TABLE + " WHERE " + Account.VALUE + " IS NOT NULL";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        ArrayList<Account> accountList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            Account account = new Account();
            account.setName(cursor.getString(cursor.getColumnIndex(Account.NAME)));
            account.setValue(cursor.getString(cursor.getColumnIndex(Account.VALUE)));
            accountList.add(account);
        }

        while (cursor.moveToNext()) {
            Account account = new Account();
            account.setName(cursor.getString(cursor.getColumnIndex(Account.NAME)));
            account.setValue(cursor.getString(cursor.getColumnIndex(Account.VALUE)));
            accountList.add(account);
        }

        cursor.close();
        db.close();
        return accountList;
    }
    //endregion

    //region Examination
    public void deleteExamination(Examination examination) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Examination.TABLE, " DATE = '" + examination.getDate() + "'", null);
    }

    public void insertExamination(Examination examination) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Examination.NAME, examination.getName());
        values.put(Examination.DATE, examination.getDate());
        values.put(Examination.DESCRIPTION, examination.getDescription());
        values.put(Examination.ADDINFO, examination.getAddInfo());
        values.put(Examination.ADDRESS, examination.getAddress());
        values.put(Examination.NOTIFY, examination.isNofi());
        values.put(Examination.TYPE, examination.getType());
        values.put(Examination.NOTE, examination.getNote());
        values.put(Examination.HOUR, examination.getHour());

        db.insert(Examination.TABLE, null, values);
        db.close();
    }

    public void updateExamination(Examination examination) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Examination.NAME, examination.getName());
        values.put(Examination.DATE, examination.getDate());
        values.put(Examination.DESCRIPTION, examination.getDescription());
        values.put(Examination.ADDINFO, examination.getAddInfo());
        values.put(Examination.ADDRESS, examination.getAddress());
        values.put(Examination.NOTIFY, examination.isNofi());
        values.put(Examination.TYPE, examination.getType());
        values.put(Examination.NOTE, examination.getNote());
        values.put(Examination.HOUR, examination.getHour());

        db.update(Examination.TABLE, values, Examination.ID + "=" + examination.getId(), null);
    }

    public boolean examinationCheck(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Examination.TABLE + " WHERE DATE='" + date + "'";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public Examination getExamination(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM " + Examination.TABLE + " WHERE DATE='" + date + "'";
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();

        Examination examination = new Examination();
        examination.setId(cursor.getInt(cursor.getColumnIndex(Examination.ID)));
        examination.setName(cursor.getString(cursor.getColumnIndex(Examination.NAME)));
        examination.setAddInfo(cursor.getString(cursor.getColumnIndex(Examination.ADDINFO)));
        examination.setAddress(cursor.getString(cursor.getColumnIndex(Examination.ADDRESS)));
        examination.setDate(cursor.getString(cursor.getColumnIndex(Examination.DATE)));
        examination.setDescription(cursor.getString(cursor.getColumnIndex(Examination.DESCRIPTION)));
        examination.setType(cursor.getString(cursor.getColumnIndex(Examination.TYPE)));
        examination.setNote(cursor.getString(cursor.getColumnIndex(Examination.NOTE)));
        examination.setHour(cursor.getString(cursor.getColumnIndex(Examination.HOUR)));

        if (cursor.getString(cursor.getColumnIndex(Examination.NOTIFY)).equals("0")) {
            examination.setNofi(false);
        } else {
            examination.setNofi(true);
        }

        cursor.close();
        db.close();
        return examination;
    }

    public ArrayList<Examination> getAllNotes() {
        String Query = "SELECT * FROM " + Examination.TABLE + " WHERE " + Examination.NOTE + " IS NOT NULL";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        ArrayList<Examination> examinationList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            Examination examination = new Examination();
            examination.setName(cursor.getString(cursor.getColumnIndex(Examination.NAME)));
            examination.setAddInfo(cursor.getString(cursor.getColumnIndex(Examination.ADDINFO)));
            examination.setAddress(cursor.getString(cursor.getColumnIndex(Examination.ADDRESS)));
            examination.setDate(cursor.getString(cursor.getColumnIndex(Examination.DATE)));
            examination.setDescription(cursor.getString(cursor.getColumnIndex(Examination.DESCRIPTION)));
            examination.setType(cursor.getString(cursor.getColumnIndex(Examination.TYPE)));
            examination.setNote(cursor.getString(cursor.getColumnIndex(Examination.NOTE)));
            examination.setHour(cursor.getString(cursor.getColumnIndex(Examination.HOUR)));

            if (cursor.getString(cursor.getColumnIndex(Examination.NOTIFY)).equals("0")) {
                examination.setNofi(false);
            } else {
                examination.setNofi(true);
            }

            examinationList.add(examination);
        }

        while (cursor.moveToNext()) {
            Examination examination = new Examination();
            examination.setName(cursor.getString(cursor.getColumnIndex(Examination.NAME)));
            examination.setAddInfo(cursor.getString(cursor.getColumnIndex(Examination.ADDINFO)));
            examination.setAddress(cursor.getString(cursor.getColumnIndex(Examination.ADDRESS)));
            examination.setDate(cursor.getString(cursor.getColumnIndex(Examination.DATE)));
            examination.setDescription(cursor.getString(cursor.getColumnIndex(Examination.DESCRIPTION)));
            examination.setType(cursor.getString(cursor.getColumnIndex(Examination.TYPE)));
            examination.setNote(cursor.getString(cursor.getColumnIndex(Examination.NOTE)));
            examination.setHour(cursor.getString(cursor.getColumnIndex(Examination.HOUR)));

            if (cursor.getString(cursor.getColumnIndex(Examination.NOTIFY)).equals("0")) {
                examination.setNofi(false);
            } else {
                examination.setNofi(true);
            }

            examinationList.add(examination);
        }

        cursor.close();
        db.close();
        return examinationList;
    }

    public ArrayList<Examination> getAllExamination() {
        String Query = "SELECT * FROM " + Examination.TABLE + " WHERE " + Examination.NAME + " IS NOT NULL";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        ArrayList<Examination> examinationList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            Examination examination = new Examination();
            examination.setId(cursor.getInt(cursor.getColumnIndex(Examination.ID)));
            examination.setName(cursor.getString(cursor.getColumnIndex(Examination.NAME)));
            examination.setAddInfo(cursor.getString(cursor.getColumnIndex(Examination.ADDINFO)));
            examination.setAddress(cursor.getString(cursor.getColumnIndex(Examination.ADDRESS)));
            examination.setDate(cursor.getString(cursor.getColumnIndex(Examination.DATE)));
            examination.setDescription(cursor.getString(cursor.getColumnIndex(Examination.DESCRIPTION)));
            examination.setType(cursor.getString(cursor.getColumnIndex(Examination.TYPE)));
            examination.setNote(cursor.getString(cursor.getColumnIndex(Examination.NOTE)));
            examination.setHour(cursor.getString(cursor.getColumnIndex(Examination.HOUR)));

            if (cursor.getString(cursor.getColumnIndex(Examination.NOTIFY)).equals("0")) {
                examination.setNofi(false);
            } else {
                examination.setNofi(true);
            }

            examinationList.add(examination);
        }

        while (cursor.moveToNext()) {
            Examination examination = new Examination();
            examination.setId(cursor.getInt(cursor.getColumnIndex(Examination.ID)));
            examination.setName(cursor.getString(cursor.getColumnIndex(Examination.NAME)));
            examination.setAddInfo(cursor.getString(cursor.getColumnIndex(Examination.ADDINFO)));
            examination.setAddress(cursor.getString(cursor.getColumnIndex(Examination.ADDRESS)));
            examination.setDate(cursor.getString(cursor.getColumnIndex(Examination.DATE)));
            examination.setDescription(cursor.getString(cursor.getColumnIndex(Examination.DESCRIPTION)));
            examination.setType(cursor.getString(cursor.getColumnIndex(Examination.TYPE)));
            examination.setNote(cursor.getString(cursor.getColumnIndex(Examination.NOTE)));
            examination.setHour(cursor.getString(cursor.getColumnIndex(Examination.HOUR)));

            if (cursor.getString(cursor.getColumnIndex(Examination.NOTIFY)).equals("0")) {
                examination.setNofi(false);
            } else {
                examination.setNofi(true);
            }

            examinationList.add(examination);
        }

        cursor.close();
        db.close();
        return examinationList;
    }
    //endregion

    //region Medicine
    public void insertMedicine(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Medicine.ADDINFO, medicine.getAddInfo());
        values.put(Medicine.AMOUNT, medicine.getAmount());
        values.put(Medicine.FREQUENCY, medicine.getFrequency());
        values.put(Medicine.NAME, medicine.getName());

        db.insert(Medicine.TABLE, null, values);
        db.close();
    }

    public void updateMedicine(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Medicine.NAME, medicine.getName());
        values.put(Medicine.FREQUENCY, medicine.getFrequency());
        values.put(Medicine.ADDINFO, medicine.getAddInfo());
        values.put(Medicine.AMOUNT, medicine.getAmount());
        //values.put(Medicine.NOTIFICATIONS, medicine.isNotifications());

        db.update(Medicine.TABLE, values, Medicine.NAME + "='" + medicine.getName() + "'", null);
    }

    public boolean deleteMedicine(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Medicine.TABLE, Medicine.NAME + "='" + name + "'", null) > 0;
    }

    public Medicine getMedicine() {
        String Query = "SELECT * FROM " + Medicine.TABLE + " WHERE " + Medicine.ID + " = " + 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();

        Medicine medicine = new Medicine();
        medicine.setName(cursor.getString(cursor.getColumnIndex(Medicine.NAME)));
        medicine.setFrequency(cursor.getString(cursor.getColumnIndex(Medicine.FREQUENCY)));
        medicine.setAddInfo(cursor.getString(cursor.getColumnIndex(Medicine.ADDINFO)));
        medicine.setAmount(cursor.getString(cursor.getColumnIndex(Medicine.AMOUNT)));

        cursor.close();
        db.close();
        return medicine;
    }

    public ArrayList<Medicine> getAllMedicine() {
        String Query = "SELECT * FROM " + Medicine.TABLE + " WHERE " + Medicine.NAME + " IS NOT NULL";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        ArrayList<Medicine> medicineList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            Medicine medicine = new Medicine();
            medicine.setAddInfo(cursor.getString(cursor.getColumnIndex(Medicine.ADDINFO)));
            medicine.setAmount(cursor.getString(cursor.getColumnIndex(Medicine.AMOUNT)));
            medicine.setFrequency(cursor.getString(cursor.getColumnIndex(Medicine.FREQUENCY)));
            medicine.setName(cursor.getString(cursor.getColumnIndex(Medicine.NAME)));
            medicineList.add(medicine);
        }

        while (cursor.moveToNext()) {
            Medicine medicine = new Medicine();
            medicine.setAddInfo(cursor.getString(cursor.getColumnIndex(Medicine.ADDINFO)));
            medicine.setAmount(cursor.getString(cursor.getColumnIndex(Medicine.AMOUNT)));
            medicine.setFrequency(cursor.getString(cursor.getColumnIndex(Medicine.FREQUENCY)));
            medicine.setName(cursor.getString(cursor.getColumnIndex(Medicine.NAME)));
            medicineList.add(medicine);
        }

        cursor.close();
        db.close();
        return medicineList;
    }
    //endregion
}
