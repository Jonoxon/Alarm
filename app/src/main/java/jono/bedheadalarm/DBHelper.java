package jono.bedheadalarm;

/**
 * Created by Jono on 29/05/2016.
 * Structure of database copied + edited from http://www.tutorialspoint.com/android/android_sqlite_database.htm
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "alarmsdatabase.db";
    public static final String ALARMSDATABASE_TABLE_NAME = "Alarms";
    public static final String ALARMSDATABASE_COLUMN_ID = "id";
    public static final String ALARMSDATABASE_COLUMN_NAME = "name";
    public static final String ALARMSDATABASE_COLUMN_VIBRATE = "vib";
    public static final String ALARMSDATABASE_COLUMN_RINGTONE = "ring"; // Uri Ringtone
    public static final String ALARMSDATABASE_COLUMN_HOURS = "hours";
    public static final String ALARMSDATABASE_COLUMN_MINUTES = "minutes";
    public static final String ALARMSDATABASE_COLUMN_DAYS = "days";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table alarmsdatabase " +
                        "(id Integer primary key, name text,vib int default 0, ring text, hours int, minutes int, days int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS alarmsdatabase");
        onCreate(db);
    }

    public boolean insertContact  (String name, Integer vib, String ring, Integer hours, Integer minutes, Integer days)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("vib", vib);
        contentValues.put("ring", ring);
        contentValues.put("hours", hours);
        contentValues.put("minutes", minutes);
        contentValues.put("minutes", minutes);
        contentValues.put("days", days);
        db.insert("alarmsdatabase",null , contentValues);
        return true;
    }


    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from alarmsdatabase where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ALARMSDATABASE_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, Integer vib, String ring, Integer hours, Integer minutes, Integer days)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("vib", vib);
        contentValues.put("ring", ring);
        contentValues.put("hours", hours);
        contentValues.put("minutes", minutes);
        contentValues.put("days", days);
        db.update("alarmsdatabase", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("alarmsdatabase",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public List<Alarm> getAllAlarms() {

        final List<Alarm> alarms = new ArrayList<Alarm>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from alarmsdatabase", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            alarms.add(
                    new Alarm(
                            res.getString(res.getColumnIndex(ALARMSDATABASE_COLUMN_ID)),
                            res.getString(res.getColumnIndex(ALARMSDATABASE_COLUMN_NAME)),
                            res.getInt(res.getColumnIndex(DBHelper.ALARMSDATABASE_COLUMN_HOURS)),
                            res.getInt(res.getColumnIndex(DBHelper.ALARMSDATABASE_COLUMN_MINUTES)),
                            res.getInt(res.getColumnIndex(ALARMSDATABASE_COLUMN_VIBRATE)),
                            res.getInt(res.getColumnIndex(ALARMSDATABASE_COLUMN_DAYS))
                    )
            );
            res.moveToNext();
        }

        return alarms;
    }
}
