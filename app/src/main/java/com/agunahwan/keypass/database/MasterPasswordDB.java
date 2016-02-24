package com.agunahwan.keypass.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.agunahwan.keypass.entity.MasterPassword;

/**
 * Created by InspiroPC on 12/09/2015.
 */
public class MasterPasswordDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_keypass";
    private static final String TBL_MASTER = "tbl_master";
    private static final int DB_VERSION = 1;

    private static MasterPasswordDB dbInstance;
    private static SQLiteDatabase db;

    interface COLUMN {
        String id = "id";
        String nama = "nama";
        String password = "password";
    }

    private static final String SQL_CREATE_MASTER = "CREATE TABLE IF NOT EXISTS "
            + TBL_MASTER
            + "( "
            + COLUMN.id
            + " integer primary key autoincrement not null, "
            + COLUMN.nama
            + " text, "
            + COLUMN.password
            + " text)";

    private MasterPasswordDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static MasterPasswordDB getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new MasterPasswordDB(context);
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
        db.execSQL(SQL_CREATE_MASTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TBL_MASTER);
        onCreate(db);
    }

    public boolean addMasterPassword(MasterPassword masterPassword) {
        ContentValues values = new ContentValues();

        values.put(COLUMN.nama, masterPassword.getNama());
        values.put(COLUMN.password, masterPassword.getPassword());

        return ((db.insert(TBL_MASTER, null, values)) != -1) ? true : false;
    }

    public boolean isExistMasterPassword() {
        db.execSQL(SQL_CREATE_MASTER);
        String select = "SELECT 1 FROM " + TBL_MASTER;
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public MasterPassword checkPasswordLogin(String password) {
        MasterPassword masterPassword = new MasterPassword();
        String select = "SELECT * FROM " + TBL_MASTER + " WHERE " + COLUMN.password + "='" + password + "'";
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            masterPassword.setId(cursor.getInt(0));
            masterPassword.setNama(cursor.getString(1));
            masterPassword.setPassword(cursor.getString(2));

            return masterPassword;
        } else
            return null;
    }
}
