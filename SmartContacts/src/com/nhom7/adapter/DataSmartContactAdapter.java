package com.nhom7.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataSmartContactAdapter extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "db_smartcontacts.db";
    public static final String TABLE_NAME = "tbl_smartcontact";
    public static final String COL_ID = "SMID";
    public static final String COL_NAME = "SMNAME";
    public static final String COL_PHONE = "SMPHONENUMBER";
    public static final String COL_EMAIL = "SMEMAIL";


    public DataSmartContactAdapter(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"(SMID INTEGER PRIMARY KEY AUTOINCREMENT, SMNAME TEXT, SMPHONENUMBER INTEGER, SMEMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String smname, int smphone, String smemail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,smname);
        contentValues.put(COL_PHONE,smphone);
        contentValues.put(COL_EMAIL,smemail);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }
    public boolean UpdateData(String smid, String smname, Integer smphone, String smemail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,smid);
        contentValues.put(COL_NAME,smname);
        contentValues.put(COL_PHONE,smphone);
        contentValues.put(COL_EMAIL,smemail);
        db.update(TABLE_NAME, contentValues, "smid = ?", new String[] {smid} );
        return true;
    }
    public boolean DeleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "smid = ?", new String[] {id});
        return true;
    }
    public Cursor getAData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE SMID ="+id,null);
        return res;
    }
    public Cursor checknull(Integer sdt)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE SMPHONENUMBER = "+sdt,null);
        return res;
    }
}