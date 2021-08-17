package sg.edu.rp.c346.id20013327.lolmyprofile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "championList.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_CHAMPION = "champions";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //To first create the database i think
        //Create the table for champions

        String createChampionsTableSql = "CREATE TABLE " + TABLE_CHAMPION + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, " + COLUMN_ROLE + " TEXT, "
                + COLUMN_STARS + " INTEGER )";

        db.execSQL(createChampionsTableSql);
        Log.i("info",createChampionsTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAMPION);
        onCreate(db);
    }

    public long insertChampion(String name, String role, int stars) {
        //Get instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ROLE, role);
        values.put(COLUMN_STARS, stars);

        //Insert the row into the TABLE_CHAPION
        long result = db.insert(TABLE_CHAMPION, null, values);

        //Close database connection
        db.close();
        Log.d("SQL Insert", "" + result);
        return result;
    }

    public ArrayList<Champions> getAllChampions() {
        ArrayList<Champions> championsList = new ArrayList<Champions>();
        //Selecting what from where
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_ROLE + ","
                + COLUMN_STARS + " FROM " + TABLE_CHAMPION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String role = cursor.getString(2);
                int stars = cursor.getInt(3);

                Champions newChampions = new Champions(id, name, role, stars);
                championsList.add(newChampions);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return championsList;
    }

    public ArrayList<Champions> getAllChampionsByStars(int starsFilter) {
        ArrayList<Champions> championList = new ArrayList<Champions>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_ROLE, COLUMN_STARS};
        String condition =  COLUMN_STARS + "= ?";
        String[] args = {String.valueOf(starsFilter)}; //String.valueOf(starsFilter) to change the int value of the starsFilter to String
        //If more than one condition
//        String condition = COLUMN_STARS + "= ?" + COLUMN_ROLE + "= ?";
//        String args = {String.valueOf(starsFilter), "support"};
        Cursor cursor = db.query(TABLE_CHAMPION, columns, condition, args,
                null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String role = cursor.getString(2);
                int stars = cursor.getInt(3);

                Champions newChampion = new Champions(id, name, role, stars);
                championList.add(newChampion);
            } while (cursor.moveToNext());
        }
        //Close connection
        cursor.close();
        db.close();
        return championList;
    }

    public int updateChampion(Champions data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_STARS, data.getStar());
        values.put(COLUMN_ROLE,data.getRole());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_CHAMPION, values, condition, args);
        db.close();
        return result;
    }

    public int deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_CHAMPION, condition,args);
        db.close();
        return result;
    }

    public ArrayList<String> getRole() {
        ArrayList<String> roles = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ROLE};

        Cursor cursor;
        cursor = db.query(true, TABLE_CHAMPION, columns, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                roles.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        //Close connection
        cursor.close();
        db.close();
        return roles;
    }

    public ArrayList<Champions> getAllChampionsByRole(String roleFilter) {
        ArrayList<Champions> championList = new ArrayList<Champions>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_ROLE, COLUMN_STARS};
        String condition = COLUMN_ROLE + "= ?";
        String[] args = {roleFilter};

        Cursor cursor;
        cursor = db.query(TABLE_CHAMPION,columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String role = cursor.getString(2);
                int stars = cursor.getInt(3);

                Champions newChampion = new Champions(id, name, role, stars);
                championList.add(newChampion);
            } while (cursor.moveToNext());
        }

        //Close Connection
        cursor.close();
        db.close();
        return championList;

    }
}