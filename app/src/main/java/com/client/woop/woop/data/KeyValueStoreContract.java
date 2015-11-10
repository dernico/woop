package com.client.woop.woop.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.BaseColumns;

public final class KeyValueStoreContract {

    public KeyValueStoreContract(){}

    public static abstract class KeyValue implements BaseColumns{
        public static final String TableName = "KeyValueStore";
        public static final String Key = "KeyColumn";
        public static final String Value = "ValueColumn";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_KeyValueStore =
            "CREATE TABLE " + KeyValue.TableName + " (" +
                    KeyValue._ID + " INTEGER PRIMARY KEY," +
                    KeyValue.Key + TEXT_TYPE + COMMA_SEP +
                    KeyValue.Value + TEXT_TYPE + COMMA_SEP +
            " )";

    public static final String SQL_DELETE_KeyValueStore =
            "DROP TABLE IF EXISTS " + KeyValue.TableName;


}
