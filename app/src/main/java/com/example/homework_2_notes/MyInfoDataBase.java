package com.example.homework_2_notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

public class MyInfoDataBase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    // database name
    private static final String DATABASE_NAME = "notes.db";
    // table name
    private static final String TABLE_NAME = "mynotes";
    // table columns

    private static final String COL1 = "id";
    private static final String COL2 = "noteTime";
    private static final String COL3 = "noteDesc";
    private static final String COL4 = "image";

    private static final String[] TABLE_NOTES_COLUMNS = {COL1, COL2,
            COL3, COL4};

    private SQLiteDatabase db = null;



    MyInfoDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                    + COL1 + " INTEGER PRIMARY KEY, "
                    + COL2 + " TEXT, "
                    + COL3 + " TEXT, "
                    + COL4 + " INTEGER)";

            db.execSQL(createTable);
        } catch (Throwable t){
            t.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public Notes readNote(int id) {
        Notes notes = null;
        Cursor cursor = null;

        cursor = db.query(TABLE_NAME, TABLE_NOTES_COLUMNS, COL1 + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            notes = new Notes();
            notes.setId(cursor.getInt(0));
            notes.setTime(cursor.getString(1));
            notes.setDesc(cursor.getString(2));

            if (cursor != null) {
                cursor.close();
            }

        }
        return notes;
    }

    public List<Notes> getAllNotes(){
        List<Notes> result = new ArrayList<Notes>();
        Cursor cursor = null;
        try {


            cursor = db.query(TABLE_NAME, TABLE_NOTES_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Notes item = cursorToItem(cursor);
                result.add(item);
                cursor.moveToNext();
            }
        }catch (Throwable t){
            t.printStackTrace();
        }
        finally {

        if(cursor != null) {
            cursor.close();
        }

        }
        return result;
    }

    private Notes cursorToItem(Cursor cursor){
        Notes result = new Notes();
        try {
            result.setId(cursor.getInt(0));
            result.setTime(cursor.getString(1));
            result.setDesc(cursor.getString(2));
            result.setPic(cursor.getInt(3));
        }catch (Throwable t){
            t.printStackTrace();
        }
        return result;
    }



    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void close() {
        try {
            db.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void deleteItem(Notes item) {

        try {

            // delete item
            db.delete(TABLE_NAME, COL1 + " = ?",
                    new String[] { String.valueOf(item.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public int updateItem(Notes item) {
        int cnt = 0;
        try {

            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(COL1,item.getId());
            values.put(COL2, item.getTime());
            values.put(COL3, item.getDesc());
            values.put(COL4,item.getPic());


            // update
            cnt = db.update(TABLE_NAME, values, COL1 + " = ?",
                    new String[] { String.valueOf(item.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;
    }

    public Notes readItem(int id) {
        Notes item = null;
        Cursor cursor = null;
        try {

            cursor = db.query(TABLE_NAME, TABLE_NOTES_COLUMNS, COL1 + " = ?",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);



            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){

                cursor.moveToFirst();

                item = new Notes();
                item.setId(cursor.getInt(0));
                //item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ITEM_COLUMN_ID))));
                item.setTime(cursor.getString(1));
                item.setDesc(cursor.getString(2));

                //images
                int image = cursor.getInt(3);

                    if (image != 0) {
                        item.setPic(image);
                    }
                }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return item;
    }

    public void createItem(Notes item) {

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(COL1,item.getId());
            values.put(COL2, item.getTime());
            values.put(COL3, item.getDesc());
            values.put(COL4,item.getPic());

            //images
            int image1 = item.getPic();
            if (image1 != 0) {
                values.put(COL4, image1);

            }

            // insert item
            db.insert(TABLE_NAME, null, values);

        } catch (Throwable t) {
            t.printStackTrace();
        }


    }

}
