package com.example.homework_2_notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.UUID;


public class AddFragment extends Fragment {



    private Button _add;
    private EditText _edit_time;
    private EditText _edit_desc;
    NoteAdapter adapter;


    public AddFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.add_fragment,container,false);
          _edit_time = (EditText) view.findViewById(R.id.edit_time_add);
          _edit_desc = (EditText) view.findViewById(R.id.edit_description_add);


        _add = view.findViewById(R.id._btn_add);


        _add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String str1 = _edit_time.getText().toString();
                    String str2 = _edit_desc.getText().toString();
                    Notes notes = ManagerDb.getInstance().getSelectedItem();
                    String key = "xx_" + UUID.randomUUID();
                    if (notes == null) {

                        notes = new Notes(str2, str1, R.drawable.add);
                        ManagerDb.getInstance().createItem(notes);
                    }
                 else{
                        notes.setTime(str1);
                        notes.setDesc(str2);
                        if (notes.getId() == -111) {
                            ManagerDb.getInstance().createItem(notes);
                        } else {
                            ManagerDb.getInstance().updateItem(notes);
                        }

                        ManagerDb.getInstance().setSelectedItem(null);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        db.collection("notes")
                                .document(String.valueOf(notes.getId()))
                                .set(notes)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println(e);
                            }
                        });

                    }

                  // MainFragment.list.add(notes);
                   MainFragment mainFragment = MainFragment.getInstance();
                   FragmentTransaction ft = getFragmentManager().beginTransaction();
                   ft.replace(R.id.rootview, mainFragment);
                   ft.commit();
               } catch (Throwable e){
                   e.printStackTrace();
               }
            }
        });

        return view;
    }



}
