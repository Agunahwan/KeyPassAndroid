package com.agunahwan.keypass.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.agunahwan.keypass.entity.PasswordSaved;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by InspiroPC on 13/09/2015.
 */
public class PasswordSavedDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_keypass";
    private static final String TBL_PASSWORD = "tbl_password";
    private static final int DB_VERSION = 1;

    private static PasswordSavedDB dbInstance;
    private static SQLiteDatabase db;

    interface COLUMN {
        String id = "id";
        String nama = "nama";
        String username = "username";
        String password = "password";
    }

    public static final String SQL_CREATE_PASSWORD = "CREATE TABLE IF NOT EXISTS "
            + TBL_PASSWORD
            + "( "
            + COLUMN.id
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN.nama
            + " TEXT, "
            + COLUMN.username
            + " TEXT, "
            + COLUMN.password
            + " TEXT)";

    private PasswordSavedDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static PasswordSavedDB getDbInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new PasswordSavedDB(context);
            db = dbInstance.getWritableDatabase();
        }
        return dbInstance;
    }

    @Override
    public synchronized void close() {
        super.close();
        if (dbInstance != null)
            dbInstance.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(SQL_CREATE_PASSWORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TBL_PASSWORD);
        onCreate(db);
    }

    public boolean isPasswordHasData(String nama, String username) {
        db.execSQL(SQL_CREATE_PASSWORD);
        String select = "SELECT COUNT(1) FROM " + TBL_PASSWORD +
                " WHERE " + COLUMN.nama + " LIKE '%" + nama + "%'" +
                " OR " + COLUMN.username + " LIKE '%" + username + "%'";
        Cursor cursor = db.rawQuery(select, null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0)
            return true;
        else
            return false;
    }

    public List<PasswordSaved> getPassword(String nama, String username) {
        List<PasswordSaved> passwordSavedList = new ArrayList<PasswordSaved>();

        Cursor cursor = db.query(TBL_PASSWORD,
                new String[]{
                        COLUMN.id,
                        COLUMN.nama,
                        COLUMN.username,
                        COLUMN.password
                }, COLUMN.nama + " LIKE '%" + nama + "%'" + " OR " + COLUMN.username + " LIKE '%" + username + "%'",
                null, null, null, COLUMN.nama);
        cursor.moveToFirst();
        do {
            passwordSavedList.add(
                    new PasswordSaved(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN.id)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN.nama)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN.username)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN.password))
                    )
            );
        } while (cursor.moveToNext());
        return passwordSavedList;
    }

    public List<PasswordSaved> getAllPassword() {
        List<PasswordSaved> passwordSavedList = new ArrayList<PasswordSaved>();

        Cursor cursor = db.query(TBL_PASSWORD,
                new String[]{
                        COLUMN.id,
                        COLUMN.nama,
                        COLUMN.username,
                        COLUMN.password
                }, null, null, null, null, COLUMN.nama);
        cursor.moveToFirst();
        do {
            passwordSavedList.add(
                    new PasswordSaved(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN.id)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN.nama)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN.username)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN.password))
                    )
            );
        } while (cursor.moveToNext());
        return passwordSavedList;
    }

    public boolean addPasswordSaved(PasswordSaved passwordSaved) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN.nama, passwordSaved.getNama());
        contentValues.put(COLUMN.username, passwordSaved.getUsername());
        contentValues.put(COLUMN.password, passwordSaved.getPassword());

        return ((db.insert(TBL_PASSWORD, null, contentValues)) != -1) ? true : false;
    }

    public boolean editPasswordSaved(PasswordSaved passwordSaved) {
        ContentValues values = new ContentValues();

        values.put(COLUMN.nama, passwordSaved.getNama());
        values.put(COLUMN.username, passwordSaved.getUsername());
        values.put(COLUMN.password, passwordSaved.getPassword());

        return ((db.update(TBL_PASSWORD, values, COLUMN.id + "=" + String.valueOf(passwordSaved.getId()),
                null)) == 1) ? true : false;
    }

    public void deletePasswordSaved(int id) {
        db.delete(TBL_PASSWORD, COLUMN.id + "=?", new String[]{String.valueOf(id)});
    }
}
