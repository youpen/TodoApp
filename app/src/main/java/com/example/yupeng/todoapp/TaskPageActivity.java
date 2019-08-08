package com.example.yupeng.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class TaskPageActivity extends AppCompatActivity {

    private String TASK_ID_KEY = "com.youpen.android.todoapp.task.id";
    private FragmentStatePagerAdapter mAdapter;
    private List<Task> mTasks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_page_layout);
        FragmentManager fg = getSupportFragmentManager();
        mTasks = TaskLab.get(this).getTasks();
        UUID taskID = (UUID) getIntent().getSerializableExtra(TASK_ID_KEY);
        mAdapter = new FragmentStatePagerAdapter(fg) {
            @Override
            public Fragment getItem(int position) {
                return TaskFragment.newInstance(mTasks.get(position).getUUID());
            }

            @Override
            public int getCount() {
                return mTasks.size();
            }
        };
    }

    public Intent newIntent (Context packageContext, UUID uuid) {
//        Intent intent = new Intent();
        Intent intent = new Intent(packageContext, TaskPageActivity.class); // 为什么要传参？参数又是什么意思
        intent.putExtra(TASK_ID_KEY, uuid);
        return intent;
    }
}
