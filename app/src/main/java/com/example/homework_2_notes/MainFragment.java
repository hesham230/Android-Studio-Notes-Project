package com.example.homework_2_notes;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {


    private ListView listView;
    View view;
    NoteAdapter _adapter;

    private static MainFragment single_instance;




    @SuppressLint("ValidFragment")
    private MainFragment() {


    }


    // static method to create instance of Singleton class
    public static MainFragment getInstance()
    {
        if (single_instance == null)
            single_instance = new MainFragment();

        return single_instance;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);
        List<Notes> list = ManagerDb.getInstance().getAllItems();

        _adapter = new NoteAdapter(view.getContext(),R.layout.row, (ArrayList<Notes>) list);

        listView = view.findViewById(R.id._list);


        // Create adapter from the type NoteAdapter
        listView.setAdapter(_adapter);

        return view;

    }


    public void showCustomAlertDialog(View view,int pos) {
        List<Notes> list = ManagerDb.getInstance().getAllItems();
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("Position", pos);
        args.putString("Time",list.get(pos).getTime());
        args.putString("Description",list.get(pos).getDesc());
        frag.setArguments(args);
        frag.show(getFragmentManager(), "dialog");
    }




}
