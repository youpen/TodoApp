package com.example.yupeng.todoapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.UUID;

public class TaskActivity extends SingleFragmentActivity {

    public static final String EXTRA_TASK_ID = "com.youpen.android.tasktodo.task_id";

    @Override
    protected Fragment createFragment() {
//        return new TaskFragment();
        UUID taskId = (UUID) getIntent().getSerializableExtra(EXTRA_TASK_ID);
        return TaskFragment.newInstance(taskId);
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }


}
