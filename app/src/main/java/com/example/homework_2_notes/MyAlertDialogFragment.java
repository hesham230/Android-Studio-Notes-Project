package com.example.homework_2_notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class MyAlertDialogFragment extends DialogFragment {

        private EditText _editTime;
        private EditText _editDesc;
        private Context context;


        FragmentListener mListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        try{
            mListener = (FragmentListener)context;
        } catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_layout,null);
        builder.setView(view)
                .setTitle("Edit ")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = getEditTime() + " " + getEditDesc();
                        MainFragment fragment = MainFragment.getInstance();
                        fragment._adapter.edit(getArguments().getInt("Position"),getEditTime()
                                ,getEditDesc());
                        mListener.DialogPositiveClick(MyAlertDialogFragment.this);
                        dismiss();
                    }
                });
        _editTime = view.findViewById(R.id.edit_time);
        _editDesc = view.findViewById(R.id.edit_description);

        return builder.create();
    }


    public String getEditTime(){
        return _editTime.getText().toString();
    }

    public String getEditDesc(){
        return _editDesc.getText().toString();
    }

    public interface FragmentListener{
        public void DialogPositiveClick(MyAlertDialogFragment dialog);
    }


}
