package database.TaskDbSchema;

public class TaskDbSchema {

    public static final class TaskTable {
        public static final String NAME = "tasks";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String REPORTER = "reporter";
        }
    }


}
