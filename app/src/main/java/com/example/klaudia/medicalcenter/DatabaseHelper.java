package com.example.klaudia.medicalcenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DatabaseHelp";
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
        Log.i("db","create"+ DB_NAME);
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

        db.insert(User.TABLE, null, values);
        db.update(User.TABLE, values, User.ID + " = ?",
                new String[]{"1"});
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
    public void insertAccount(List<Account> accountList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (Account account: accountList) {
            values.put(Account.NAME, account.getName());
            values.put(Account.VALUE, account.getValue());
        }

        db.insert(Account.TABLE, null, values);
        db.close();
    }

    public void insertOneAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Account.NAME, account.getName());
        values.put(Account.VALUE, account.getValue());

        db.insert(Account.TABLE, null, values);
        db.close();
    }

    public boolean checkAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String sql ="SELECT NAME FROM "+ Account.TABLE + " WHERE NAME='"+ account.getName()+"'";
        cursor= db.rawQuery(sql,null);

        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public void updateAccount(List<Account> accountList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (Account account: accountList) {
            values.put(Account.NAME, account.getName());
            values.put(Account.VALUE, account.getValue());

            db.insert(Account.TABLE, null, values);
            db.update(Account.TABLE, values, Account.ID + " = ?",
                    new String[]{"1"});
        }
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

    public void updateOneAccount(Account account) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Account.NAME, account.getName());
        values.put(Account.VALUE, account.getValue());

        db.update(Account.TABLE, values, " NAME='"+ Account.NAME + "'", null);
    }

    public ArrayList<Account> getAllAccount() {
        String Query = "SELECT * FROM " + Account.TABLE + " WHERE " + Account.VALUE + " IS NOT NULL";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        ArrayList<Account> accountList = new ArrayList<>();
        cursor.moveToFirst();

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
    public void updateExamination(Examination examination) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Examination.NAME, examination.getName());
        values.put(Examination.DATE, examination.getDate());
        values.put(Examination.DESCRIPTION, examination.getDescription());

        db.insert(Examination.TABLE, null, values);
        db.update(Examination.TABLE, values, Examination.ID + " = ?",
                new String[]{"1"});
    }

    public int getExaminationCount() {
        String countQuery = "SELECT  * FROM " + Examination.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Examination getExamination() {
        String Query = "SELECT * FROM " + Examination.TABLE + " WHERE " + Examination.ID + " = " + 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();

        Examination examination = new Examination();
        examination.setName(cursor.getString(cursor.getColumnIndex(Examination.NAME)));
        examination.setDate(cursor.getString(cursor.getColumnIndex(Examination.DATE)));
        examination.setDescription(cursor.getString(cursor.getColumnIndex(Examination.DESCRIPTION)));

        cursor.close();
        db.close();
        return examination;
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

        db.insert(Medicine.TABLE, null, values);
        db.update(Medicine.TABLE, values, Medicine.ID + " = ?",
                new String[]{"1"});
    }

    public boolean deleteMedicine(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Medicine.TABLE, Medicine.NAME + "=" + name, null) > 0;
    }

//    public int getMedicineId(Medicine medicine) {
//        String countQuery = "SELECT  * FROM " + Medicine.TABLE;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, );
//        int count = cursor.getCount();
//        cursor.close();
//        return count;
//    }

    public int getMedicineCount() {
        String countQuery = "SELECT  * FROM " + Medicine.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
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
        cursor.moveToFirst();

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
