package cn.xxx.winkawaks.puzzle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 54713 on 2018/2/11.
 */

public class Dao extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static String sDatabaseName = "puzzle.db";
    public static final String RECORD_TABLE_INFO = "_record_news_info";
    private static Dao sDaoBaseInstance = null;

    public SQLiteDatabase mDatabase = null;

    public Dao(Context context) {
        super(context, sDatabaseName, null, DATABASE_VERSION);
    }

    public static Dao getInstance(Context context) {
        if (sDaoBaseInstance == null) {
            sDaoBaseInstance = new Dao(context);
        }
        return sDaoBaseInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createFavoriteNewsDB(db);
    }

    private void createFavoriteNewsDB(SQLiteDatabase db) {
        String info_sql = "CREATE TABLE IF NOT EXISTS " + RECORD_TABLE_INFO + "("
            + RecordColumn.ID + " integer primary key AUTOINCREMENT,"
            + RecordColumn.RECORD_TIME + " integer  default 0,"
            + RecordColumn.STEP + " varchar(10)  default '',"
            + RecordColumn.CREATE_TIME + " varchar(20)  default '')";
        try {
            db.execSQL(info_sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(" DROP TABLE IF EXISTS " + RECORD_TABLE_INFO);
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void deleteAllData() {
        execSQL("Delete from " + RECORD_TABLE_INFO);
    }

    public Cursor select(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public synchronized void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public long insert(String table, ContentValues initialValues) {
        ContentValues values;

        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        SQLiteDatabase db = getWritableDatabase();
        long rowId = -1;

        try {
            rowId = db.insert(table, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return rowId;
    }

    public int delete(String table, String selection, String[] selectionArgs) {
        SQLiteDatabase db = getWritableDatabase();
        int count = -1;

        try {
            count = db.delete(table, selection, selectionArgs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return count;
    }

    public int update(String table, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = getWritableDatabase();
        int count = -1;

        try {
            count = db.update(table, values, selection, selectionArgs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return count;
    }

    public void beginTransaction() {
        beginTransaction();
    }

    public void setTransactionSuccessful() {
        setTransactionSuccessful();
    }

    public void endTransaction() {
        endTransaction();
    }
}
