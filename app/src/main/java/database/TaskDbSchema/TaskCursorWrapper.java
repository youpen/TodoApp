package database.TaskDbSchema;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.yupeng.todoapp.Task;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {

    private Cursor mCursor;
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
        mCursor = cursor;
    }

    public Task getTaskFromCursor() {
        Long date = mCursor.getLong(mCursor.getColumnIndex(TaskDbSchema.TaskTable.Cols.DATE));
        String uuidString = mCursor.getString(mCursor.getColumnIndex(TaskDbSchema.TaskTable.Cols.UUID));
        String title = mCursor.getString(mCursor.getColumnIndex(TaskDbSchema.TaskTable.Cols.TITLE));
        Boolean solved = mCursor.getInt(mCursor.getColumnIndex(TaskDbSchema.TaskTable.Cols.SOLVED)) == 1;
        Task task = new Task(UUID.fromString(uuidString));
        task.setSolved(solved);
        task.setTitle(title);
        task.setDate(new Date(date));
        return task;
    }
}
