package com.example.yupeng.todoapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.add_todo_fragment);
        if (fragment == null) {
            fragment = new TaskFragment();
            fm.beginTransaction()
                    .add(R.id.add_todo_fragment, fragment)
                    .commit();
        }
    }
}
