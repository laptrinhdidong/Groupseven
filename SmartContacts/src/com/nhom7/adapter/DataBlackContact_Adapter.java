package com.nhom7.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBlackContact_Adapter extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "db_blackcontacts.db";
    public static final String TABLE_NAME_BLACK = "tbl_blackcontact";
    public static final String COL_ID = "SMID";
    public static final String COL_NAME = "SMNAME";
    public static final String COL_PHONE = "SMPHONENUMBER";
    public static final String COL_EMAIL = "SMEMAIL";


    public DataBlackContact_Adapter(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME_BLACK+"(SMID INTEGER PRIMARY KEY AUTOINCREMENT, SMNAME TEXT, SMPHONENUMBER TEXT, SMEMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+TABLE_NAME_BLACK);
        onCreate(db);
    }

//    
    public Cursor getADataBlack(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME_BLACK+" WHERE SMID ="+id,null);
        return res;
    }
    public Boolean checknullblack(String sdt)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME_BLACK+" WHERE SMPHONENUMBER = "+sdt,null);
        if(res.getCount() > 0)
        {
            return true;        	
        }
        else{
        return false;}
    }
//    BLACK CONTACT
    public Cursor getAllDataBlack()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME_BLACK,null);
        return res;
    }
    public boolean insertDataBlack(String smname, String smphone, String smemail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,smname);
        contentValues.put(COL_PHONE,smphone);
        contentValues.put(COL_EMAIL,smemail);
        long result = db.insert(TABLE_NAME_BLACK, null, contentValues);
        if(result == -1)
        {
            return false;
        }
        else {
            return true;
        }
    }
    public boolean DeleteDataBlack(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_BLACK, "smid = ?", new String[] {id});
        return true;
    }
    
}