package com.example.testapi_sotuken;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.getAvailableCalendarTypes;


/**
 * Created by kuwahara on 2018/09/24.
 * 参考：https://akira-watson.com/android/sqlite.html
 */
public class CreateUserDB extends SQLiteOpenHelper {

    // データーベースのバージョン
    private static final int DATABASE_VERSION = 3;

    // データーベース情報を変数に格納
//  private static final String CULUM名 = 変数名;
    private static final String DATABASE_NAME = "TestDB.db";
    private static final String TABLE_NAME = "testdb";
    private static final String _ID = "_id";
    private static final String COLUMN_NAME_TITLE = "title";
    private static final String COLUMN_NAME_SUBTITLE = "score";
    private static final String DATE = "date";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_SUBTITLE + " INTEGER," +
                    DATE + " TEXT )";

    // 複合主キー 最後にPRIMARY KEY (CULUM名,CULUM名);
    // INTEGER TEXT BLOB NULL REAL SQLlite

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    CreateUserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                SQL_CREATE_ENTRIES
        );

        saveData(db, "music3", 20, "2020/11/01");
        saveData(db, "music1", 100, "2019/10/09");
        saveData(db, "music10", 100, "2020/09/09");
        saveData(db, "music10", 100, "2020/10/09");
        saveData(db, "music4", 30, "2020/10/01");
        saveData(db, "music5", 40, nowDate());

    }

    /**
     * 本日の日付取得
     */
    public String nowDate() {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.format(today);
    }

    // 参考：https://sankame.github.io/blog/2017-09-05-android_sqlite_db_upgrade/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(
                SQL_DELETE_ENTRIES
        );
        onCreate(db);
    }

    //もしデータベースのバージョンが変わった場合データベースを引き継ぐ
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     *　データベースにデータを保存
     * @param title Create時の変数名
     * @param score
     * @param date
     */
    public void saveData(SQLiteDatabase db, String title, int score, String date) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("score", score);
        values.put("date", date);

        db.insert("testdb", null, values);
    }
}