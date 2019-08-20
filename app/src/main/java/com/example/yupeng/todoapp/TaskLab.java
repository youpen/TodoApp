package com.example.yupeng.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.TaskDbSchema.TaskBaseHelper;
import database.TaskDbSchema.TaskCursorWrapper;

import static database.TaskDbSchema.TaskDbSchema.*;

public class TaskLab {
    private static TaskLab sTaskLab;
    private Context mContext;
    private SQLiteOpenHelper mHelper;

    private ContentValues getContentValue(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskTable.Cols.UUID, task.getUUID().toString());
        contentValues.put(TaskTable.Cols.TITLE, task.getTitle());
        contentValues.put(TaskTable.Cols.SOLVED, task.getSolved() ? 1 : 0);
        contentValues.put(TaskTable.Cols.DATE, task.getDate().toString());
        return contentValues;
    }

    public static TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context);
        }
        return sTaskLab;
    }

    private TaskLab(Context context) {
        mContext = context.getApplicationContext();
        mHelper = new TaskBaseHelper(mContext);
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryTask(null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    Task task = cursor.getTaskFromCursor();
                    tasks.add(task);
                }
            } finally {
                cursor.close();
            }
        }
        return tasks;
    }

    public Task getTask(UUID uuid) {
        TaskCursorWrapper cursor = queryTask(TaskTable.Cols.UUID + " = ?", new String[]{uuid.toString()});
        if (cursor != null) {
            try {
                if (cursor.getCount() == 0) {
                    return null;
                }
                return cursor.getTaskFromCursor();
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    public void addTask(Task task) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = getContentValue(task);
        db.insert(TaskTable.NAME, null, contentValues);
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = getContentValue(task);
        db.update(TaskTable.NAME, contentValues, "= ?", new String[]{task.getUUID().toString()});
    }

    public TaskCursorWrapper queryTask(String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(
                TaskTable.NAME,
                null, // 查询所有column
                whereClause,
                whereArgs,
                null, null, null);
        return new TaskCursorWrapper(cursor);
    }
}
