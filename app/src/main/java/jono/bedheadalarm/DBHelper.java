package jono.bedheadalarm;

/**
 * Created by Jono on 29/05/2016.
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

    public static final String DATABASE_NAME = "alarmdatabase.db";
    public static final String ALARMDATABASE_TABLE_NAME = "Alarms";
    public static final String ALARMDATABASE_COLUMN_ID = "id";
    public static final String ALARMDATABASE_COLUMN_NAME = "name";
    public static final String ALARMDATABASE_COLUMN_VIBRATE = "vib";
    public static final String ALARMDATABASE_COLUMN_HOURS = "hours";
    public static final String ALARMDATABASE_COLUMN_MINUTES = "minutes";
    public static final String ALARMDATABASE_COLUMN_DAYS = "days";
    public static final String ALARMDATABASE_COLUMN_RADIUS = "radius";
    public static final String ALARMDATABASE_COLUMN_TRIGGER = "trigger";
    public static final String ALARMDATABASE_COLUMN_LAT = "lat";
    public static final String ALARMDATABASE_COLUMN_LON = "lon";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table alarmdatabase " +
                        "(id Integer primary key, name text,vib int default 0, hours int, minutes int, days int, radius int, trigger int default 0, real lat, real lon)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS alarmdatabase");
        onCreate(db);
    }

    public boolean insertTimeContact  (String name, Integer vib, Integer hours, Integer minutes, Integer days)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("vib", vib);
        contentValues.put("hours", hours);
        contentValues.put("minutes", minutes);
        contentValues.put("minutes", minutes);
        contentValues.put("days", days);
        db.insert("alarmdatabase",null , contentValues);
        return true;
    }

    public boolean insertGPSContact (String name, Double lat, Double lon, Integer radius, Integer vib, Integer trigger, Integer days)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("lat", lat);
        contentValues.put("lon", lon);
        contentValues.put("radius", radius);
        contentValues.put("trigger", trigger);
        contentValues.put("vib", vib);
        contentValues.put("days", days);
        db.insert("alarmdatabase",null , contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from alarmdatabase where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ALARMDATABASE_TABLE_NAME);
        return numRows;
    }

    public boolean updateTimeContact (Integer id, String name, Integer vib, Integer hours, Integer minutes, Integer days)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("vib", vib);
        contentValues.put("hours", hours);
        contentValues.put("minutes", minutes);
        contentValues.put("days", days);
        db.update("alarmdatabase", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean updateGPSContact (Integer id, String name, Double lat, Double lon, Integer radius, Integer vib, Integer trigger, Integer days)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("lat", lat);
        contentValues.put("lon", lon);
        contentValues.put("radius", radius);
        contentValues.put("trigger", trigger);
        contentValues.put("vib", vib);
        contentValues.put("days", days);
        db.update("alarmdatabase", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("alarmdatabase",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public List<Alarm> getAllAlarms() {

        final List<Alarm> alarms = new ArrayList<Alarm>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from alarmdatabase", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            alarms.add(
                    new Alarm(
                            res.getString(res.getColumnIndex(ALARMDATABASE_COLUMN_ID)),
                            res.getString(res.getColumnIndex(ALARMDATABASE_COLUMN_NAME)),
                            res.getInt(res.getColumnIndex(DBHelper.ALARMDATABASE_COLUMN_HOURS)),
                            res.getInt(res.getColumnIndex(DBHelper.ALARMDATABASE_COLUMN_MINUTES)),
                            res.getInt(res.getColumnIndex(ALARMDATABASE_COLUMN_VIBRATE)),
                            res.getInt(res.getColumnIndex(ALARMDATABASE_COLUMN_DAYS))
                    )
            );
            res.moveToNext();
        }

        return alarms;
    }
}
