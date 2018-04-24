package qapp.mangosoft.com.qappproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by User on 2/7/2018.
 */

public class Database extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME="FAQ_ARCHIVE.db";
    //private String DB_PATH;
    private static final int DB_VERSION=1;



    //private final String createRootTable1="create table quran(database_id integer not null,sura_no integer not null,verse_no integer not null,verse text not null );";
    private final String FAQ="create table FAQ ( id integer primary key autoincrement, question varchar(200), verse varchar(1000), answer varchar(2000),verse_no integer,sura_no integer );";


    private static final String Drop_table="drop table if exists ";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }





    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{

            sqLiteDatabase.execSQL(FAQ);


        }catch (SQLiteException sq){
            sq.printStackTrace();
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try{

            sqLiteDatabase.execSQL(Drop_table+"FAQ");
            onCreate(sqLiteDatabase);

        }catch (SQLiteException sq){
            sq.printStackTrace();
        }

    }


    public void insertInoFaq(String ques,String ans,String verse,int verse_no,int sura_no){
        SQLiteDatabase mDb=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("question",ques);
        contentValues.put("verse",verse);
        contentValues.put("answer",ans);
        contentValues.put("verse_no",verse_no);
        contentValues.put("sura_no",sura_no);

        try{
            Log.e("insertInoFaq","before insert");
            mDb.insert("FAQ",null,contentValues);
            Log.e("insertInoFaq","after insert");
        }catch (Exception e){

            e.printStackTrace();
        }

    }

    public ArrayList<String> getAllQues(){
        SQLiteDatabase mDb=getReadableDatabase();
        Cursor cursor=null;
        ArrayList<String> ques=new ArrayList<>();

        try{
            cursor=mDb.query("FAQ",new String[]{"question"},null,null,null,null,null);

            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                ques.add(cursor.getString(cursor.getColumnIndex("question")));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null)
                cursor.close();
            mDb.close();
        }

        return ques;

    }

    public ArrayList<String> getAllAns(){
        SQLiteDatabase mDb=getReadableDatabase();

        Cursor cursor=null;
        ArrayList<String> ans=new ArrayList<>();

        try{
            cursor=mDb.query("FAQ",new String[]{"answer"},null,null,null,null,null);

            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                ans.add(cursor.getString(cursor.getColumnIndex("answer")));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null)
                cursor.close();
            mDb.close();
        }

        return ans;

    }

    public ArrayList<String> getAllVerseFaq(){

        SQLiteDatabase mDb=getReadableDatabase();
        Cursor cursor=null;
        ArrayList<String> verse=new ArrayList<>();

        try{
            cursor=mDb.query("FAQ",new String[]{"verse"},null,null,null,null,null);

            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                verse.add(cursor.getString(cursor.getColumnIndex("verse")));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null)
                cursor.close();
            mDb.close();
        }

        return verse;

    }

    public ArrayList<Integer> getAllSuraNoFaq(){

        SQLiteDatabase mDb=getReadableDatabase();
        Cursor cursor=null;
        ArrayList<Integer> verse=new ArrayList<>();

        try{
            cursor=mDb.query("FAQ",new String[]{"sura_no"},null,null,null,null,null);

            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                verse.add(cursor.getInt(cursor.getColumnIndex("sura_no")));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null)
                cursor.close();
            mDb.close();
        }

        return verse;

    }

    public ArrayList<Integer> getAllVerseNoFaq(){
        SQLiteDatabase mDb=getReadableDatabase();

        Cursor cursor=null;
        ArrayList<Integer> verse=new ArrayList<>();

        try{
            cursor=mDb.query("FAQ",new String[]{"verse_no"},null,null,null,null,null);

            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                verse.add(cursor.getInt(cursor.getColumnIndex("verse_no")));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null)
                cursor.close();
            mDb.close();
        }

        return verse;

    }
}
