package com.example.android.imagetest.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.provider.BaseColumns._ID;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_AGE;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_EMAIL;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_NAME;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_PASSWORD;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.STUDENT_PHONE;
import static com.example.android.imagetest.data.StudentContract.StudentEntry.TABLE_NAME;

/**
 * Created by Delight on 17/05/2018.
 */

public class StudentDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "student.db";

    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    final String SQL_CREATE_STUDENTS_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + STUDENT_NAME + " TEXT NOT NULL, "
            + STUDENT_EMAIL + " TEXT NOT NULL, "
            + STUDENT_PASSWORD + " TEXT NOT NULL, "
            +  STUDENT_PHONE + " TEXT NOT NULL, "
            + STUDENT_AGE + " INTEGER NOT NULL "
                + "); ";

    sqLiteDatabase.execSQL(SQL_CREATE_STUDENTS_TABLE);

    }




    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getUserNamePassword (String searchquery){
        String[] columns = new String[]{STUDENT_EMAIL};
        searchquery = "%" + searchquery + "%";
        String where = STUDENT_EMAIL + " LIKE ?";
        String[] whereArgs = new String[]{searchquery};

        Cursor cursor = null;
        SQLiteDatabase mReadableDB= getReadableDatabase();

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.query(TABLE_NAME, columns, where, whereArgs, null, null, null);
        } catch (Exception e) {
            Log.d(TAG, "SEARCH EXCEPTION! " + e);
        }

        return cursor;

    }

    public Cursor getROUTINENAME(String search_str) {
        String query = "SELECT * FROM TBNAME where COL_NAME LIKE '%"+search_str+"%'";

        Cursor mCursor = db.rawQuery(query, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }



        public void addStudent(String name, String email, String password, String phone, int age){
            SQLiteDatabase mDB = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(STUDENT_NAME, name);
            cv.put(STUDENT_EMAIL, email);
            cv.put(STUDENT_PASSWORD, password);
            cv.put(STUDENT_PHONE, phone);
            cv.put(STUDENT_AGE, age);

            mDB.insert(TABLE_NAME, null, cv);


        }









    public Cursor getWordMatches(String query, String[] columns) {
        String selection = STUDENT_EMAIL + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TABLE_NAME);

        SQLiteDatabase mDatabaseOpenHelper = getReadableDatabase();
        Cursor cursor = builder.query(mDatabaseOpenHelper,
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }








    public boolean findEmail(String productname) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + STUDENT_EMAIL + " =  \"" + productname + "\"";


        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        //Product product = new Product();
        boolean succes = false;
        if (cursor.moveToFirst()) {

            succes = true;
        }else {
            succes = false;
        }
        db.close();
        return succes;

        }


    public boolean findPassword(String productname) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + STUDENT_PASSWORD + " =  \"" + productname + "\"";


        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        //Product product = new Product();
        boolean succes = false;
        if (cursor.moveToFirst()) {

            succes = true;
        }else {
            succes = false;
        }
        db.close();
        return succes;

    }


    public boolean nameExists(String studentName){
        String [] projection = {
                _ID,
                STUDENT_NAME,
                STUDENT_EMAIL,
                STUDENT_PASSWORD,
                STUDENT_PHONE,
                STUDENT_AGE
        };
        String selection = STUDENT_NAME + " =?";
        String[] selectionArgs = { studentName };
        String limit = "1";

        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0 );
        cursor.close();
        return exists;
    }

    public boolean emailExists(String studentEmail){
        String [] projection = {
                _ID,
                STUDENT_NAME,
                STUDENT_EMAIL,
                STUDENT_PASSWORD,
                STUDENT_PHONE,
                STUDENT_AGE
        };
        String selection = STUDENT_EMAIL + " =?";
        String[] selectionArgs = { studentEmail };
        String limit = "1";

        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0 );
        cursor.close();
        return exists;
    }

    public boolean phoneExists(String studentPhone){
        String [] projection = {
                _ID,
                STUDENT_NAME,
                STUDENT_EMAIL,
                STUDENT_PASSWORD,
                STUDENT_PHONE,
                STUDENT_AGE
        };
        String selection = STUDENT_PHONE + " =?";
        String[] selectionArgs = { studentPhone };
        String limit = "1";

        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0 );
        cursor.close();
        return exists;
    }
}
