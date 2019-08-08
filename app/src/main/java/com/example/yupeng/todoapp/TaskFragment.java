package com.example.yupeng.todoapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

public class TaskFragment extends Fragment {

    public static final String TASK_ID_KEY = "com.example.yupeng.todoapp.taskactivity";
    private static final String ARG_TASK_ID = "task_id";

    private Task mTask;
    private EditText mTaskEditInput;
    private Button mDateBtn;
    private CheckBox mSolvedCheckBox;

    static public TaskFragment newInstance(UUID taskId) {
//        fragment argument的使用有点复杂。为什么不直接在TaskFragment里创建一个实例变量呢?
//        或者像我之前想的那样，intent里putExtra的Key是Fragment的静态变量
//        创建实例变量的方式并不可靠。这是因为，在操作系统重建fragment时(设备配置发生改变) 用户暂时离开当前应用(操作系统按需回收内存)，
//        任何实例变量都将不复存在。而存在fragment argument里可以继续从getArgument中取回来。
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);
        TaskFragment instance =  new TaskFragment();
        instance.setArguments(args);
        return instance;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        mTask = new Task();
//    }

    private void getTaskInfo() {
//        UUID mTaskId = (UUID) getActivity().getIntent().getSerializableExtra(TASK_ID_KEY);
//        mTask = TaskLab.get(getActivity()).getTask(mTaskId);
        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        mTask = TaskLab.get(getActivity()).getTask(taskId);
        mTaskEditInput.setText(mTask.getTitle());
        mDateBtn.setText(mTask.getDate().toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }
        View v = inflater.inflate(R.layout.todo_fragment, container, false);
        mDateBtn = v.findViewById(R.id.task_new_data_btn);
//        mDateBtn.setText(mTask.getDate().toString());
//        mDateBtn.setEnabled(false);
        mTaskEditInput = v.findViewById(R.id.task_title_input);
        getTaskInfo();

        mTaskEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTask.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSolvedCheckBox = v.findViewById(R.id.task_solved_checkbox);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mTask.setSolved(b);
            }
        });

        return v;
    }
}
