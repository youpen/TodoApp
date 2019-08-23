package com.example.yupeng.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

public class TaskFragment extends Fragment {

    public static final String TASK_ID_KEY = "com.example.yupeng.todoapp.taskactivity";
    private static final String ARG_TASK_ID = "task_id";
    public static final int REQUEST_CONTACT_CODE = 1;

    private Task mTask;
    private EditText mTaskEditInput;
    private Button mDateBtn;
    private CheckBox mSolvedCheckBox;
    private static int REQUEST_CODE = 0;
    private Button mSelectReporterBtn;
    private Button mSendReporter;


    @Override
    public void onPause() {
        super.onPause();
        TaskLab.get(getActivity()).updateTask(mTask);
    }

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
        updateDateBtn();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CODE) {
        Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
        mTask.setDate(date);
        updateDateBtn();
        }
        if (requestCode == REQUEST_CONTACT_CODE && data != null) {

            Uri contactUri = data.getData();
            String[] queryField = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            Cursor c = getActivity().getContentResolver().query(contactUri, queryField, null, null, null);
            try {
                if (c.getCount() == 0) {
                    return;
                }
                c.moveToFirst();
                String reporterName = c.getString(0);
                mTask.setReporter(reporterName);
                mSelectReporterBtn.setText(mTask.getReporter());
            } finally {
                c.close();
            }
        }
    }

    private void updateDateBtn() {
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
        mDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.getInstance(mTask.getDate());
                dialog.setTargetFragment(TaskFragment.this, REQUEST_CODE);
                dialog.show(fm, "DatePicker");
            }
        });
        mTaskEditInput = v.findViewById(R.id.task_title_input);
        getTaskInfo();
        updateDateBtn();
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

        mSendReporter = v.findViewById(R.id.btn_send_report);
        mSendReporter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, mTask.getReporter());
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.btn_select_reporter));
                startActivity(intent);
            }
        });
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mSelectReporterBtn = v.findViewById(R.id.btn_select_reporter);
        mSelectReporterBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivityForResult(pickContact, REQUEST_CONTACT_CODE);
            }
        });

        if (mSelectReporterBtn != null) {
            mSelectReporterBtn.setText(mTask.getReporter());
        }

        mSolvedCheckBox = v.findViewById(R.id.task_solved_checkbox);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mTask.setSolved(b);
            }
        });

        PackageManager pm = getActivity().getPackageManager();
        if (pm.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mSelectReporterBtn.setEnabled(false);
        }

        return v;
    }


}
