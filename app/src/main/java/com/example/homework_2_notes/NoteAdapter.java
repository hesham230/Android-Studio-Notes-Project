package com.example.homework_2_notes;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;


public class NoteAdapter extends ArrayAdapter<Notes> {

    /*
        Note Adapter class that allows us
        to create an adapter
     */


    // Attributes
    private Context context;
    private ArrayList<Notes> myList;
    private TextView txtNum;
    private TextView txtDesc;



    // Constructor
    public NoteAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Notes> myList) {
        super(context, resource);
        this.context = context;
        this.myList = myList;

    }


    /*
    get count method that returns the size of the list
     */
    @Override
    public int getCount() {
        return myList.size();
    }


    /*
    get item method that return the position of the elements
     */
    @Nullable
    @Override
    public Notes getItem(int position) {
        return myList.get(position);
    }


    /*
    get view method that sets the attributes
    and show the picture, time and description in our listView

    inside this method we create and use an inflater Object,
    and we set our Buttons delete and edit

     */
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        LayoutInflater inflater =LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.row,null);

         txtNum = v.findViewById(R.id.name);
         txtDesc = v.findViewById(R.id.desc);
        final ImageView imgView = v.findViewById(R.id.imgView);
        final Button btnDelete = v.findViewById(R.id.btn_del);
        final Button btnEdit = v.findViewById(R.id.btn_edit);

        final Notes notes = (Notes)getItem(position);

        txtNum.setText(notes.getTime());
        txtDesc.setText(notes.getDesc());
        imgView.setImageResource(notes.getPic());


        /*
        set on click method to our
        button delete that allows the user to delete each note
        that he choose's
         */
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder _builder = new AlertDialog.Builder(context);
                _builder.setMessage("Are You Sure You Want To Delete ? ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ManagerDb.getInstance().deleteItem(notes);
                                myList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context,"Note " + txtDesc.getText().toString() + " Deleted Successfully "
                                        ,Toast.LENGTH_LONG).show();

                            }
                        })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                AlertDialog alert = _builder.create();
                alert.setTitle("Warning !!!");
                alert.show();

            }
        });


        /*
        set on click method to our button edit,
        that allows the user to edit each note that he choose's

        in this method we create an intent Object,
        this object allows us to send our info to another activity
         */
        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MainFragment mainFragment = MainFragment.getInstance();
                mainFragment.showCustomAlertDialog(v,position);

            }
        });


        return v;

    }

    /*
       edit Method we create it and we use it
       in the MainActivity.java class in the method OnActivityResult,
       this method sets the new values of description and time
       when the user makes changes of a specific note by clicking the
       edit button
     */

            public void edit(int pos, String time, String desc) {

                Notes n = myList.get(pos);

                n.setTime(time);
                n.setDesc(desc);
                ManagerDb.getInstance().updateItem(n);
                notifyDataSetChanged();
            }
}
