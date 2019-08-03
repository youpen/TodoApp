package com.example.yupeng.todoapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaskListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.tasks_list_fragement, container, false);
        mRecyclerView = v.findViewById(R.id.task_list_layout_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;

    }

    // 这个方法是创建adapter， 即在recyclerView中插入数据
    private void updateUI() {
        TaskLab taskLab = TaskLab.get(getActivity()); // 单例
        List<Task> tasks = taskLab.getTasks();

        mTaskAdapter = new TaskAdapter(tasks);
        mRecyclerView.setAdapter(mTaskAdapter);
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        implements View.OnClickListener {}

        private TextView mTaskTitleTextView;
        private TextView mTaskDateTextView;

        private Task mTask;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.task_list_item, parent, false)); // 这里为什么要加false
            mTaskTitleTextView = itemView.findViewById(R.id.task_title_id);
            mTaskDateTextView = itemView.findViewById(R.id.task_date_id);
            itemView.setOnClickListener(this);
        }
        public void bind(Task task) {
            mTask = task;
            mTaskDateTextView.setText(mTask.getDate().toString());
            mTaskTitleTextView.setText(mTask.getTitle());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(), "111", Toast.LENGTH_SHORT).show();
        }
    }

    // adapter负责创建Item，绑定item
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;
        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

//        @Override
//        // TODO
//        public int getItemViewType(int position) {
//            return super.getItemViewType(position);
//        }

        @Override
        // 这个方法创建到一定数量item后就不调用了，改用pool内的item复用来绑定
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { // TODO　？？？
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}
