package com.silvanix.image;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayrokid on 13/02/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "images.db";
    public static final String TABLE_NAME = "images_table";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String LOG_DB = "LOG-DB-DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, IMAGE BLOB NOT NULL) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertBitmap(Bitmap bm) {

        //convert the image into byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] buffer = out.toByteArray();

        // open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        // Start the transaction
        db.beginTransaction();
        ContentValues values;
        try
        {
            values = new ContentValues();
            values.put(COL_NAME, buffer);
            // Insert Row
            long result = db.insert(TABLE_NAME, null, values);
            //Log.i("Insert " + result);
            // Insert Into database successfully
            db.setTransactionSuccessful();
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            // End the Transaction
            db.close();
        }
    }

    // Get all images
    public List<byte[]> getAllImage() {
        List<byte[]> imageList = new ArrayList<>();
//        List<Gambar> imageList = new ArrayList<Gambar>();
        // select all query
        String selectQuery = "SELECT * FROM "+TABLE_NAME+" ORDER BY "+COL_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        // Start the transaction
        db.beginTransaction();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // Looping
        if(cursor.moveToFirst()) {
            do {
//                Gambar gambar = new Gambar();
//                gambar.set_id(Integer.parseInt(cursor.getString(0)));
//                gambar.set_name(cursor.getString(1));

//                imageList.add(gambar);

                byte[] image = cursor.getBlob(cursor.getColumnIndex(COL_NAME));

                imageList.add(image);


            } while (cursor.moveToNext());
        }
        db.setTransactionSuccessful();

        db.endTransaction();
        // End the Transaction
        db.close();

        return imageList;
    }

    public Bitmap getBitmap(int id) throws SQLException {
        Bitmap bitmap = null;
        //Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();

        // Start the transaction
        db.beginTransaction();

        String selectQuery = "SELECT * FROM "+TABLE_NAME+" WHERE id = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0)
        {
            while (cursor.moveToNext()){
                // Convert blob data to byte array
                byte[] blob = cursor.getBlob(cursor.getColumnIndex(COL_NAME));

                // Convert the byte array to Bitmap
                bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
            }
        }
        db.setTransactionSuccessful();

        db.endTransaction();
        // End the Transaction
        db.close();

        return bitmap;
    }
}
