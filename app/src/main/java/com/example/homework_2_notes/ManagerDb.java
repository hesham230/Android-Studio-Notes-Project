package com.example.homework_2_notes;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ManagerDb {

    // Attributes
    private static ManagerDb instance = null;
    private Context context = null;
    private MyInfoDataBase db = null;
    private Notes selectedItem = null;

    // Singleton
    public static ManagerDb getInstance() {
        if (instance == null) {
            instance = new ManagerDb();
        }
        return instance;
    }

    public static void releaseInstance() {
        if (instance != null) {
            instance.clean();
            instance = null;
        }
    }

    private void clean() {

    }


    // open database
    public void openDataBase(Context context) {
        this.context = context;
        if (context != null) {
            db = new MyInfoDataBase(context);
            db.open();
        }
    }

    // close database
    public void closeDataBase() {
        if(db!=null){
            db.close();
        }
    }

    // create note
    public void createItem(Notes note) {
        if (db != null) {
            db.createItem(note);
        }
    }

    // get notes from database
    public List<Notes> getAllItems() {
        List<Notes> result = new ArrayList<Notes>();
        if (db != null) {
            result = db.getAllNotes();
        }
        return result;
    }


    // update note
    public void updateItem(Notes note) {
        if (db != null && note != null) {
            db.updateItem(note);
        }
    }



    public void deleteItem(Notes note) {
        if (db != null) {
            db.deleteItem(note);
        }
    }



    // get selected item
    public Notes getSelectedItem() {
        return selectedItem;
    }

    // set selected item
    public void setSelectedItem(Notes selectedItem) {
        this.selectedItem = selectedItem;
    }



}
