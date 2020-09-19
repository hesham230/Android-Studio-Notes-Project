package com.example.homework_2_notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyAlertDialogFragment.FragmentListener {

    // Attributes


    private FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ManagerDb.getInstance().openDataBase(this);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        MainFragment mainFragment = MainFragment.getInstance();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rootview,mainFragment);
        ft.commit();



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.show:

                MainFragment mainFragment = MainFragment.getInstance();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.rootview, mainFragment);
                ft.commit();
                return true;
            case R.id.add:
                AddFragment addFragment = new AddFragment();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.rootview,addFragment);
                fragmentTransaction.commit();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onResume() {
        ManagerDb.getInstance().openDataBase(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        ManagerDb.getInstance().closeDataBase();
        super.onPause();
    }

    @Override
    public void DialogPositiveClick(MyAlertDialogFragment dialogFragment) {
        String result = dialogFragment.getEditTime() + " " + dialogFragment.getEditDesc();
    }


}
