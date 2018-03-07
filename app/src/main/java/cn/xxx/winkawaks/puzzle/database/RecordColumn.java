package cn.xxx.winkawaks.puzzle.database;

/**
 * Created by 54713 on 2018/2/11.
 */

public class RecordColumn {

    public static final String ID = "ID";
    public static final String RECORD_TIME = "RECORD_TIME";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String STEP = "STEP";

    public static final int ID_COLUMN = 0;
    public static final int RECORD_TIME_COLUMN = 1;
    public static final int CREATE_TIME_COLUMN = 2;
    public static final int STEP_COLUMN = 3;


    public static final String[] PROJECTION = {
        ID, RECORD_TIME, CREATE_TIME, STEP
    };

}
