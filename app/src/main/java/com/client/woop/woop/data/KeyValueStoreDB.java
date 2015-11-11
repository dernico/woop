package com.client.woop.woop.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.client.woop.woop.models.KeyValueModel;

public class KeyValueStoreDB {

    private KeyValueStoreDBHelper _helper;

    public KeyValueStoreDB(Context context){
        _helper = new KeyValueStoreDBHelper(context);
    }


    public KeyValueModel insertKeyValue(final String key, final String value){
        deleteKey(key);

        SQLiteDatabase db = _helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KeyValueStoreContract.KeyValue.Key, key);
        values.put(KeyValueStoreContract.KeyValue.Value, value);

        long newRowId;
        newRowId = db.insert(
                KeyValueStoreContract.KeyValue.TableName,
                null,
                values);

        //TODO: verify newrowid > 0
        KeyValueModel model = new KeyValueModel();
        model.id = newRowId;
        model.key = key;
        model.value = value;
        return model;
    }

    public KeyValueModel getKeyValue(String key){

        SQLiteDatabase db = _helper.getReadableDatabase();

        String[] projection = {
                KeyValueStoreContract.KeyValue._ID,
                KeyValueStoreContract.KeyValue.Key,
                KeyValueStoreContract.KeyValue.Value
        };

        String selection = KeyValueStoreContract.KeyValue.Key + " = ?";

        String [] selectionArgs = { key };

        Cursor c = db.query(
                KeyValueStoreContract.KeyValue.TableName,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                     // The sort order
        );

        if( c.getCount() < 1){
            return null;
        }

        c.moveToFirst();

        long itemId = c.getLong(
                c.getColumnIndex(KeyValueStoreContract.KeyValue._ID)
        );

        String itemKey = c.getString(
                c.getColumnIndexOrThrow(KeyValueStoreContract.KeyValue.Key));

        String itemValue = c.getString(
                c.getColumnIndexOrThrow(KeyValueStoreContract.KeyValue.Value));

        KeyValueModel model = new KeyValueModel();
        model.id = itemId;
        model.key = itemKey;
        model.value = itemValue;
        return model;
    }

    public void deleteKey(String key){
        SQLiteDatabase db = _helper.getWritableDatabase();

        String selection = KeyValueStoreContract.KeyValue.Key + " = ?";
        String[] selectionArgs = { key };
        db.delete(KeyValueStoreContract.KeyValue.TableName, selection, selectionArgs);
    }
}
