package cn.xxx.winkawaks.puzzle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 54713 on 2018/2/11.
 */

public class RecordSelectHelper {
    private final Context mContext;

    private Dao mDbHelper;

    private SQLiteDatabase mSqlDB;

    public RecordSelectHelper(Context context) {
        this.mContext = context;
    }

    /**
     * 初始化数据库连接
     */
    public RecordSelectHelper open() {
        mDbHelper = new Dao(this.mContext);
        mSqlDB = mDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (mDbHelper != null)
            mDbHelper.close();
        if(mSqlDB != null)
            mSqlDB.close();
    }

    /**
     * 插入一条记录
     *
     //* @param info
     * @return
     */
    public long insertNewsInfo(RecordBean record) {
        long insertCount = -1;

        ContentValues values = new ContentValues();
        values.put(RecordColumn.RECORD_TIME, record.getRecord());
        values.put(RecordColumn.CREATE_TIME, record.getCurrentTime());
        values.put(RecordColumn.STEP, record.getStep());
        try {
            insertCount = mSqlDB.insert(Dao.RECORD_TABLE_INFO, null, values);
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }

        return insertCount;
    }

    public boolean update(RecordBean record){
        long insertCount = -1;
        ContentValues values = new ContentValues();
        values.put(RecordColumn.RECORD_TIME, record.getRecord());
        values.put(RecordColumn.CREATE_TIME, record.getCurrentTime());
        values.put(RecordColumn.STEP, record.getStep());
        String selection = RecordColumn.CREATE_TIME  + "= '" + record.getCurrentTime() + "'";
        insertCount = mSqlDB.update(Dao.RECORD_TABLE_INFO, values, selection, null);
        return insertCount > 0;
    }

    public boolean delete(String time) {
        String selection = RecordColumn.CREATE_TIME  + "= '" + time + "'";
        int ret = mSqlDB.delete(Dao.RECORD_TABLE_INFO, selection, null);
        return ret > 0;
    }

    public ArrayList<RecordBean> getSectionsListInfo() {

        ArrayList<RecordBean> list = new ArrayList<RecordBean>();
        RecordBean section = null;
        String orderBy = RecordColumn.RECORD_TIME + " ASC";
        Cursor cursor;
        try {
            cursor = mSqlDB.query(Dao.RECORD_TABLE_INFO, RecordColumn.PROJECTION,
                null, null, null,
                null, orderBy);
        } catch (Exception e) {
            return list;
        }
        if (cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                section = new RecordBean();
                String record = cursor.getString(RecordColumn.RECORD_TIME_COLUMN);
                String currentTime = cursor.getString(RecordColumn.CREATE_TIME_COLUMN);
                String step = cursor.getString(RecordColumn.STEP_COLUMN);
                section.setRecord(record);
                section.setCurrentTime(currentTime);
                section.setStep(step);
                list.add(section);
            }
        }

        cursor.close();
        cursor = null;
        return list;
    }

    /**
     * 删除
     */
    public boolean truncate() {
        int ret = mSqlDB.delete(Dao.RECORD_TABLE_INFO, null, null);
        return ret > 0;
    }
}
