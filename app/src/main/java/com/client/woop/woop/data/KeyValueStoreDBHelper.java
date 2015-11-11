package com.client.woop.woop.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KeyValueStoreDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "KeyValueStore.db";


    public KeyValueStoreDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(final SQLiteDatabase db) {
        new Runnable(){
            @Override
            public void run() {

                db.execSQL(KeyValueStoreContract.SQL_CREATE_KeyValueStore);
            }
        }.run();

    }
    public void onUpgrade(final SQLiteDatabase db, int oldVersion, int newVersion) {

        new Runnable(){
            @Override
            public void run() {

                // TODO: Implement proper upgrade handling
                db.execSQL(KeyValueStoreContract.SQL_DELETE_KeyValueStore);
                onCreate(db);
            }
        }.run();


    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}