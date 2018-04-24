package qapp.mangosoft.com.qappproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by User on 2/10/2018.
 */

public class TestAdapter {

    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;


    //Bd quran table fields
    private String BDQuran="BDQuran";
    private String DatabaseID="DatabaseID";
    private String SuraID="SuraID";
    private String VerseID="VerseID";
    private String AyahText="AyahText";

    public TestAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public TestAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public Cursor getTestData()
    {
        try
        {
            String sql ="SELECT * FROM FAQ";

            Cursor mCur = mDb.rawQuery(sql,null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }









    public ArrayList<String> getSurasAyats(){

        Cursor cursor=null;
        ArrayList<String> ayats=new ArrayList<String>();

        try{
            cursor=mDb.query("quran",new String[]{"verse"},null,null,null,null,null);
            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                ayats.add(cursor.getString(cursor.getColumnIndex("verse")));

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return ayats;
    }


    public ArrayList<String> getBDSurasAyats(){

        Cursor cursor=null;
        ArrayList<String> ayats=new ArrayList<String>();

        try{
            cursor=mDb.query(BDQuran,new String[]{AyahText},null,null,null,null,null);
            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                ayats.add(cursor.getString(cursor.getColumnIndex(AyahText)));

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return ayats;
    }






    public int getVerseNo(String verse){

        Cursor cursor=null;
        int versNo=0;

        try{
            cursor=mDb.query("quran",new String[]{"verse_no"}," verse=? ",new String[]{verse},null,null,null);
            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                versNo=(cursor.getInt(cursor.getColumnIndex("verse_no")));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return versNo;
    }

    public int getBDVerseNo(String verse){

        Cursor cursor=null;
        int versNo=0;

        try{
            cursor=mDb.query(BDQuran,new String[]{VerseID}," AyahText=? ",new String[]{verse},null,null,null);
            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                versNo=(cursor.getInt(cursor.getColumnIndex("VerseID")));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return versNo;
    }


    public int getSuraNo(String verse){

        Cursor cursor=null;

        int suraNo=0;

        try{
            cursor=mDb.query("quran",new String[]{"sura_no"}," verse=? ",new String[]{verse},null,null,null);
            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                suraNo =cursor.getInt(cursor.getColumnIndex("sura_no"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return suraNo;
    }

    public int getBDSuraNo(String verse){

        Cursor cursor=null;

        int suraNo=0;

        try{
            cursor=mDb.query("BDQuran",new String[]{"SuraID"}," AyahText=? ",new String[]{verse},null,null,null);
            for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
                suraNo =cursor.getInt(cursor.getColumnIndex("SuraID"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return suraNo;
    }




}
