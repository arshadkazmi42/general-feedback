package com.arshad.feedback.global;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.arshad.feedback.model.QuestionData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by Arshad on 8-8-16.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "arshadfeedback";

    // Tables name
    private static final String TABLE_USER = "users";
    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_OPTIONS = "options";
    private static final String TABLE_USER_RESPONSE = "response";

    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question";


    private static final String KEY_QUESTION_ID = "questionId";
    private static final String KEY_OPTION = "option";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";

    private static final String KEY_USER_ID = "userId";


    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_QUESTION + " TEXT " +
            ")" ;

    private static final String CREATE_TABLE_OPTIONS = "CREATE TABLE IF NOT EXISTS " + TABLE_OPTIONS +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_OPTION + " TEXT, " +
            KEY_QUESTION_ID + " INTEGER " +
            ")" ;

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT, " +
            KEY_EMAIL + " TEXT, " +
            KEY_PHONE + " TEXT, " +
            KEY_ADDRESS + " TEXT " +
            ")";

    private static final String CREATE_TABLE_USER_RESPONSE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_RESPONSE +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_QUESTION + " TEXT, " +
            KEY_OPTION + " TEXT, " +
            KEY_USER_ID + " INTEGER " +
            ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_USER_RESPONSE);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_OPTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS);
    }

    public int addUser(String email, String name, String phone, String address) {
        int questionId;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_EMAIL, email);
            values.put(KEY_NAME, name);
            values.put(KEY_PHONE, phone);
            values.put(KEY_ADDRESS, address);

            // Inserting Row
            questionId = (int) db.insert(TABLE_USER, null, values);
            db.close(); // Closing database connection
            return questionId;
        }catch (SQLiteDatabaseLockedException error){
            error.printStackTrace();
        }
        return 0;

    }

    public void addUserResponse(int userId, String question, String response) {
        try {
            Log.e(TAG, "Response: " + userId + " "+ question + " " + response);
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_QUESTION, question);
            values.put(KEY_OPTION, response);
            values.put(KEY_USER_ID, userId);

            // Inserting Row
            int responseId = (int) db.insert(TABLE_USER_RESPONSE, null, values);
            db.close(); // Closing database connection
            Log.e(TAG, "ResponseID: " + responseId);

        }catch (SQLiteDatabaseLockedException error){
            error.printStackTrace();
        }
    }

    public void addQuestion(String question, List<String> options) {
        Log.e(TAG, options.toString());
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_QUESTION, question);

            // Inserting Row
            int questionId = (int) db.insert(TABLE_QUESTIONS, null, values);
            db.close(); // Closing database connection
            Log.e(TAG, "QuestionID: " + questionId);
            addOptions(options, questionId);
        }catch (SQLiteDatabaseLockedException error){
            error.printStackTrace();
        }
    }

    public void addOptions(List<String> options, int questionId){
        Log.e(TAG, "Options: " + options.toString() + " id: " + questionId );
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            for(String option: options) {
                ContentValues values = new ContentValues();
                values.put(KEY_OPTION, option);
                values.put(KEY_QUESTION_ID, questionId);
                db.insert(TABLE_OPTIONS, null, values);
            }
            db.close();
        } catch (SQLiteDatabaseLockedException e){
            e.printStackTrace();
        }
    }

    public List<QuestionData> getQuestion() {
        List<QuestionData> questions = new ArrayList<>();
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    QuestionData data = new QuestionData();
                    data.setId(cursor.getInt(0));
                    data.setQuestion(cursor.getString(1));
                    questions.add(data);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (SQLiteDatabaseLockedException error){
            error.printStackTrace();
        }
        return getOptions(questions);
    }

    public List<QuestionData> getOptions(List<QuestionData> question) {
        List<String> options;
        List<QuestionData> questionDatas = new ArrayList<>();
        for(QuestionData data: question) {
            options = new ArrayList<>();
            try {
                String query = "SELECT * FROM " + TABLE_OPTIONS + " WHERE " + KEY_QUESTION_ID + " = " + data.getId();
                Log.e(TAG, "Quety: " + query);
                SQLiteDatabase db = this.getWritableDatabase();
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        options.add(cursor.getString(1));
                    } while (cursor.moveToNext());
                }
                data.setOptions(options);
                Log.e(TAG, "options: " + options.toString());
            } catch (SQLiteDatabaseLockedException e) {
                e.printStackTrace();
            }
            questionDatas.add(data);
        }
        return question;
    }

    public void exportData(){
        try {
            String query = "SELECT * FROM " + TABLE_QUESTIONS;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            exportToExcel(cursor);
        } catch (SQLiteDatabaseLockedException e) {
            e.printStackTrace();
        }
    }

    public Cursor getUserCursor(){
        try {
            String query = "SELECT * FROM " + TABLE_USER;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
//            Log.e(TAG, "CursorUser: " + cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            return cursor;
        } catch (SQLiteDatabaseLockedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cursor getUserResponseCursor(int id){
        try {
            String query = "SELECT * FROM " + TABLE_USER_RESPONSE + " WHERE " + KEY_USER_ID + " = " + id;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
//            Log.e(TAG, "CursorUser: " + cursor.getString(cursor.getColumnIndex(KEY_OPTION)));
            return cursor;
        } catch (SQLiteDatabaseLockedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void exportToExcel(Cursor cursor) {

        final String fileName = "GeneralFeedback.xls";

        //Saving file in external storage
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "");

        //create directory if not exist
        if(!directory.isDirectory()){
            directory.mkdirs();
        }

        //file path
        File file = new File(directory, fileName);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook;

        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("User", 0);

            try {
                sheet.addCell(new Label(0, 0, "id")); // column and row
                sheet.addCell(new Label(1, 0, "Name")); // column and row
                sheet.addCell(new Label(2, 0, "Email"));
                sheet.addCell(new Label(3, 0, "Phone"));
                sheet.addCell(new Label(4, 0, "Address"));
                int i = 5;
                if(cursor.moveToFirst()) {
                    do {
                        sheet.addCell(new Label(i, 0, cursor.getString(1)));
                        i = i + 1;
                    } while(cursor.moveToNext());
                }

                Cursor userCursor = getUserCursor();

                if (userCursor.moveToFirst()) {
                    do {
                        int id = userCursor.getInt(0);
                        String name = userCursor.getString(1);
                        String email = userCursor.getString(2);
                        String phone = userCursor.getString(3);
                        String address = userCursor.getString(4);

                        i = userCursor.getPosition() + 1;
                        sheet.addCell(new Label(0, i, id + ""));
                        sheet.addCell(new Label(1, i, name));
                        sheet.addCell(new Label(2, i, email));
                        sheet.addCell(new Label(3, i, phone));
                        sheet.addCell(new Label(4, i, address));

                        int j=5;
                        Cursor responseCursor = getUserResponseCursor(id);
                        if (responseCursor.moveToFirst()) {
                            do{
                                String response = responseCursor.getString(2);
                                sheet.addCell(new Label(j, i, response));
                                j = j + 1;
                            }while (responseCursor.moveToNext());
                        }
                        responseCursor.close();
                    } while (userCursor.moveToNext());
                }
                //closing cursor
                userCursor.close();
                cursor.close();
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAll(){
        String deleteOptions = "DELETE FROM " + TABLE_OPTIONS;
        String deleteQuestions = "DELETE FROM " + TABLE_QUESTIONS;
        String deleteUsers = "DELETE FROM " + TABLE_USER;
        String deleteUserResponse = "DELETE FROM " + TABLE_USER_RESPONSE;

        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery(deleteOptions, null);
        db.rawQuery(deleteQuestions, null);
        db.rawQuery(deleteUsers, null);
        db.rawQuery(deleteUserResponse, null);
        db.close();
    }

    public void clearFeedback(){
        String deleteUserResponse = "DELETE FROM " + TABLE_USER_RESPONSE;
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery(deleteUserResponse, null);
        db.close();
    }
}