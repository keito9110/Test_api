package com.example.testapi_sotuken;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.SimpleFormatter;

public class CommEnterRead extends AppCompatActivity {

    private TextView textView;
    private ListView lstv;
    private ArrayAdapter<Test> adapter;
    private CreateUserDB helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB作成
        helper = new CreateUserDB(getApplicationContext());

        // 変数textViewに表示するテキストビューのidを格納
        textView = findViewById(R.id.text_view);

        //　リストidを格納
        lstv = (ListView) findViewById(R.id.listArea);
        //Test中のtoStringとlist_viewとの結び付け
        adapter = new ArrayAdapter<Test>
                (this, R.layout.list_view, R.id.txtout);
        //リストにセットする
        lstv.setAdapter(adapter);
    }

    /**
     * データベースを読み込む
     * ListAreaに表示
     */
    public void readData(View view){
        ArrayList<Test> psList = new ArrayList<Test>();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                "testdb",
                new String[] { "title", "score","date"},
                null,
                null,
                null,
                null,
                null

        );

        cursor.moveToFirst();



        for (int i = 0; i < cursor.getCount(); i++) {
            String str = cursor.getString(0)+" : "+cursor.getString(1)+"点"+"\n"+cursor.getString(2);
            cursor.moveToNext();
            Test list = new Test(str);
            adapter.add(list);
        }
        cursor.close();
    }
    /**
     * データベースにデータを保存
     */
    public void saveData(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        EditText editTextTitle = findViewById(R.id.edit_text_key);
        EditText editTextScore = findViewById(R.id.edit_text_value);
        String title = editTextTitle.getText().toString();
        String score = editTextScore.getText().toString();
        values.put("title", title);
        values.put("score", score);
        values.put("date" , delteDate());
        String str = title+ ":" +score;

        db.insert("testdb", null, values);
        Test list = new Test(str);
        adapter.add(list);
    }
    /**
    画面遷移例
     */
    public void login(View view){
        Intent intent = new Intent(CommEnterRead.this, MainActivity2.class);
        startActivity(intent);
    }

    /**
     * 削除対象の日付取得
     */
    public String delteDate() {
        Date date = new Date();

        Calendar day = Calendar.getInstance();
        day.setTime(date);

        day.add(Calendar.MONTH , -1);
        date = day.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.format(date);
    }

    /**
     * 本日の日付取得
     */
    public String nowDate(){
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.format(today);
    }

    /**
     * テーブルデータを削除
     */
     public void delete(View v){
         SQLiteDatabase db = helper.getWritableDatabase();
         String now = delteDate();

//       db.delete("testdb","_id = ? AND date = ?",new String[]{"1",now});// 複数指定する場合
         db.delete("testdb","date < ?",new String[]{now});
    }

    /**
     * 携帯内のデータベース削除
     */
    public void DBdelete(View v){
        deleteDatabase("TestDB.db");
     }
}