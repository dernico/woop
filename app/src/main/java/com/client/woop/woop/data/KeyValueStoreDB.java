package com.client.woop.woop.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.client.woop.woop.models.KeyValueModel;

public class KeyValueStoreDB {

    public interface IKeyValueStoreCallback{
        void done(KeyValueModel result);
        void error(Exception ex);
    }

    private KeyValueStoreDBHelper _helper;

    public KeyValueStoreDB(Context context){
        _helper = new KeyValueStoreDBHelper(context);
    }


    public KeyValueModel insertKeyValue(final String key, final String value){
        // Gets the data repository in write mode
        SQLiteDatabase db = _helper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        //values.put(KeyValueStoreContract.KeyValue.COLUMN_NAME_ENTRY_ID, id);
        values.put(KeyValueStoreContract.KeyValue.Key, key);
        values.put(KeyValueStoreContract.KeyValue.Value, value);

        // Insert the new row, returning the primary key value of the new row
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

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                KeyValueStoreContract.KeyValue._ID,
                KeyValueStoreContract.KeyValue.Key,
                KeyValueStoreContract.KeyValue.Value
        };

        String selection = KeyValueStoreContract.KeyValue.Key + " = ?";

        String [] selectionArgs = {
                key
        };

        // How you want the results sorted in the resulting Cursor
        /*String sortOrder =
        KeyValueStoreContract.KeyValue.COLUMN_NAME_UPDATED + " DESC";
        */
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
}
