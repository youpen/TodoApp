package database.TaskDbSchema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yupeng.todoapp.Task;

import static database.TaskDbSchema.TaskDbSchema.TaskTable;

public class TaskBaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "taskBase.db";

    public TaskBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TaskTable.NAME + "(" +
//                " _id integer primary key autoincrement," +
                TaskTable.Cols.UUID + "," +
                TaskTable.Cols.TITLE + "," +
                TaskTable.Cols.DATE + "," +
                TaskTable.Cols.REPORTER + "," +
                TaskTable.Cols.SOLVED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
