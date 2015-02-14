package com.scubbo.lifetracker.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.scubbo.lifetracker.app.questions.QuestionType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AppHelper";
    private static final String TABLE_CREATE =
            "CREATE TABLE QUESTION_TYPES " +
            "(type varchar(12))";
    private static final String TABLE_POPULATE = buildTablePopulateString();

    public static DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        // http://www.androiddesignpatterns.com/2012/05/correctly-managing-your-sqlite-database.html
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private static String buildTablePopulateString() {
        StringBuilder sb = new StringBuilder("INSERT INTO QUESTION_TYPES VALUES (");
        sb.append(StringUtils.join(QuestionType.values(), ","));
        sb.append(")");
        return sb.toString();
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_POPULATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addQuestion(QuestionType questionType, String questionText) {
        //This can be way more extensible.
        switch (questionType) {
            case BOOLEAN:
                addBooleanQuestion(questionText);
                break;
            default:
                throw new RuntimeException("Unexpected questionType passed to addQuestion");
        }
    }

    public List<String> getQuestionTexts() {
        List<String> outputList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("QUESTIONS",new String[]{"text"},null,null,null,null,null);
        while (c.moveToNext()) {
            outputList.add(c.getString(0));
        }
        return outputList;
    }

    public List<Map<String,String>> getQuestionIdsAndTexts() {
        List<Map<String,String>> outputList = new ArrayList<Map<String,String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("QUESTIONS", new String[]{"rowID","text"}, null, null, null, null, null);
        while(c.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(c.getInt(0)));
            map.put("text", c.getString(1));
            outputList.add(map);
        }
        return outputList;
    }

    private boolean tableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT name FROM sqlite_master ");
        sb.append("WHERE type IN ('table','view') AND name NOT LIKE 'sqlite_%' ");
        sb.append("UNION ALL ");
        sb.append("SELECT name FROM sqlite_temp_master ");
        sb.append("WHERE type IN ('table','view') ");
        sb.append("ORDER BY 1");

        Cursor c = db.rawQuery(sb.toString(), null);
        while (c.moveToNext()) {
            if (c.getString(0).equals(tableName)) {
                return true;
            }
        }
        return false;
    }

    private void addBooleanQuestion(String questionText) {
        //if questions table exists, don't create it
        //TODO: Extract this to setup
        if (!tableExists("QUESTIONS")) {
            this.getWritableDatabase().execSQL("CREATE TABLE QUESTIONS " +
                            "(type varchar(12), " +
                            "text varchar(256))"
            );
        }

        this.getWritableDatabase().execSQL("INSERT INTO QUESTIONS (type, text) VALUES (\"" +
                QuestionType.BOOLEAN.toString() + "\", \"" + questionText + "\")");

    }

    public void addBooleanAnswer(Integer questionId, Boolean answer, Long dateInMillis) {
        //TODO: Extract this to setup
        if (!tableExists("ANSWERS_" + QuestionType.BOOLEAN.toString())) {
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE ANSWERS_" + QuestionType.BOOLEAN.toString() + " ");
            sb.append("(");
            sb.append("questionId int,");
            sb.append("answer int,");
            sb.append("date int");
            sb.append(")");
            this.getWritableDatabase().execSQL(sb.toString());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ANSWERS_" + QuestionType.BOOLEAN.toString() + " ");
        sb.append("(questionId, answer, date) ");
        sb.append("VALUES (" + questionId + "," + (answer ? "1" : "0") + "," + dateInMillis + ")");
        this.getWritableDatabase().execSQL(sb.toString());
    }

    public Map<String, String> getQuestionView(Integer questionId) {
        Map<String, String> returnMap = new HashMap<String, String>();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT type, text ");
        sb.append("FROM QUESTIONS ");
        sb.append("WHERE rowID=='" + questionId.toString() + "'");
        Cursor c = this.getReadableDatabase().rawQuery(sb.toString(), null);
        while (c.moveToNext()) {
            returnMap.put("type", c.getString(0));
            returnMap.put("text", c.getString(1));
        }
        return returnMap;
    }
}