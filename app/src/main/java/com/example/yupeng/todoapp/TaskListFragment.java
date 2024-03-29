package com.example.yupeng.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    private static String SAVE_TITLE_KEY = "SAVE_TITLE_KEY";

    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private ImageView mTaskSolvedImage;
    private Boolean mIsSubtitleVisible = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            mIsSubtitleVisible = savedInstanceState.getBoolean(SAVE_TITLE_KEY);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.new_task:
                Task task = new Task();
                TaskLab.get(getActivity()).addTask(task);
                Intent intent = TaskPageActivity.newIntent(getActivity(), task.getUUID());
                TaskFragment.newInstance(task.getUUID());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mIsSubtitleVisible = !mIsSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.tasks_list_fragement, container, false);

        mTaskSolvedImage = v.findViewById(R.id.image_solved_id);

        mRecyclerView = v.findViewById(R.id.task_list_layout_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mIsSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVE_TITLE_KEY, mIsSubtitleVisible);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    // 这个方法是创建adapter， 即在recyclerView中插入数据
    private void updateUI() {
        TaskLab taskLab = TaskLab.get(getActivity()); // 单例
        List<Task> tasks = taskLab.getTasks();
        // 此处为什么要判断， adapter为什么可能变null？
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.notifyDataSetChanged();
            mTaskAdapter.setTasks(tasks);
        }
        updateSubtitle();
    }

    private void updateSubtitle() {
        TaskLab taskLab = TaskLab.get(getActivity());
        int nTasks = taskLab.getTasks().size();
        String subtitle = getString(R.string.subtitle_format, nTasks);
        if (!mIsSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);


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
            mTaskSolvedImage = itemView.findViewById(R.id.image_solved_id);
            itemView.setOnClickListener(this);
        }

        public void bind(Task task) {
            mTask = task;
            mTaskDateTextView.setText(mTask.getDate().toString());
            mTaskTitleTextView.setText(mTask.getTitle());
            mTaskSolvedImage.setVisibility(mTask.getSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            // 为什么跳转页面要跳到一个新的Activity， 而不是替换当前Activiti的Fragment
            Intent intent = TaskPageActivity.newIntent(getActivity(), mTask.getUUID());
//            intent.putExtra(TaskActivity.EXTRA_TASK_ID, mTask.getUUID());
            startActivity(intent);
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

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}
