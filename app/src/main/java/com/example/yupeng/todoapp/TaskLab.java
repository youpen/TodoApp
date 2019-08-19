package com.example.yupeng.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import database.TaskDbSchema.TaskBaseHelper;
import database.TaskDbSchema.TaskDbSchema;

import static database.TaskDbSchema.TaskDbSchema.*;

public class TaskLab {
    private static TaskLab sTaskLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskTable.Cols.UUID, task.getUUID().toString());
        values.put(TaskTable.Cols.TITLE, task.getTitle());
        values.put(TaskTable.Cols.DATE, task.getDate().toString());
        values.put(TaskTable.Cols.SOLVED, task.getSolved() ? 1 : 0);
        return values;
    }

    // TODO 单例模式的get方法为什么要传入context
    public static TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context);
        }
        return sTaskLab;
    }

    private TaskLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TaskBaseHelper(mContext)
                .getWritableDatabase();
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor =  queryTasks(null, null);
        while (!cursor.isAfterLast()) {
            String uuidString = cursor.getString(cursor.getColumnIndex(TaskTable.Cols.UUID));
            Long date = cursor.getLong(cursor.getColumnIndex(TaskTable.Cols.DATE));
            int solved = cursor.getInt(cursor.getColumnIndex(TaskTable.Cols.SOLVED));
            String title = cursor.getString(cursor.getColumnIndex(TaskTable.Cols.TITLE));
            Task task = new Task(UUID.fromString(uuidString));
            task.setDate(new Date(date));
            task.setTitle(title);
            task.setSolved(solved == 1);
            tasks.add(task);
        }

        return tasks;
    }

    public Task getTask(UUID uuid) {
//        return queryTasks(uuid.toString(), )
//        return null;
        Cursor cursor =  queryTasks(TaskTable.Cols.UUID + " = ?",
                new String[] { uuid.toString() });
        while (!cursor.isAfterLast()) {
            String uuidString = cursor.getString(cursor.getColumnIndex(TaskTable.Cols.UUID));
            Long date = cursor.getLong(cursor.getColumnIndex(TaskTable.Cols.DATE));
            int solved = cursor.getInt(cursor.getColumnIndex(TaskTable.Cols.SOLVED));
            String title = cursor.getString(cursor.getColumnIndex(TaskTable.Cols.TITLE));
            Task task = new Task(UUID.fromString(uuidString));
            task.setDate(new Date(date));
            task.setTitle(title);
            task.setSolved(solved == 1);
        }
    }

    public void addTask(Task task) {
        ContentValues value = getContentValues(task);
        mDatabase.insert(TaskTable.NAME, null, value);
    }

    public void updateTask(Task task) {
        String uuidString = task.getUUID().toString();
        ContentValues value = getContentValues(task);
        mDatabase.update(TaskTable.NAME, value,
                    TaskTable.Cols.UUID + " = ? ", // TODO ？
                    new String[] { uuidString }
                );
    }

    private Cursor queryTasks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TaskTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return cursor;
    }
}
