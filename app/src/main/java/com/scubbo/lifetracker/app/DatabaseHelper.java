package com.scubbo.lifetracker.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.scubbo.lifetracker.app.questions.QuestionType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AppHelper";
    private static final String TABLE_CREATE =
            "CREATE TABLE QUESTION_TYPES " +
            "(type varchar(12))";
    private static final String TABLE_POPULATE = buildTablePopulateString();

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
        if (!tableExists("QUESTIONS")) {
            this.getWritableDatabase().execSQL("CREATE TABLE QUESTIONS " +
                            "(type varchar(12), " +
                            "text varchar(256))"
            );
        }

        this.getWritableDatabase().execSQL("INSERT INTO QUESTIONS (type, text) VALUES (\"" +
                QuestionType.BOOLEAN.toString() + "\", \"" + questionText + "\")");

    }
}