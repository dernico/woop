package com.client.woop.woop.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.client.woop.woop.models.KeyValueModel;

public class KeyValueStoreDB {

    public interface IKeyValueStoreCallback{
        void done(KeyValueModel result);
    }

    private KeyValueStoreDBHelper _helper;

    public KeyValueStoreDB(Context context){
        _helper = new KeyValueStoreDBHelper(context);
    }


    public void insertKeyValue(final String key, final String value, final IKeyValueStoreCallback callback){

        new DBThread(new DBThread.IDoInBackground() {
            @Override
            public void doInBackground() {
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
                callback.done(model);
            }
        }).execute();
    }

    public void getKeyValue(final String key, final IKeyValueStoreCallback callback){

        new DBThread(new DBThread.IDoInBackground() {
            @Override
            public void doInBackground() {
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
                    callback.done(null);
                    return;
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
                callback.done(model);
            }
        }).execute();
    }

    private void deleteKey(String key){
        SQLiteDatabase db = _helper.getWritableDatabase();

        String selection = KeyValueStoreContract.KeyValue.Key + " = ?";
        String[] selectionArgs = { key };
        db.delete(KeyValueStoreContract.KeyValue.TableName, selection, selectionArgs);
    }

    public void removeKey(final String key, final IKeyValueStoreCallback callback){
        new DBThread(new DBThread.IDoInBackground() {
            @Override
            public void doInBackground() {
                deleteKey(key);
                callback.done(null);
            }
        }).execute();
    }
}
