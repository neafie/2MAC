package com.mlong.mla;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ACHDatabase {

 
	public final static String DBNAME = "ListDatabase";
    
	public final static String TABLE_ACHESTABLE = "userstable";
    public final static String TABLE_KEYTABLE = "keytable";
   
    public final static String COLUMN_FOR = "key";
    public final static String COLUMN_LISTNAME = "listname";
    
    public final static String COLUMN_ACHNAME = "acheivementname";
    public final static String COLUMN_POINTS = "points";
    public final static String COLUMN_GENRE = "genre";
    public final static String COLUMN_ISCOMPLETED = "completed";
    public final static String COLUMN_ICON = "icon";
    public final static String COLUMN_TIMEFRAME = "timeframe";
    public final static String COLUMN_NUMBEROFCOMP = "numberofcomp";
    public final static String COLUMN_NUMBERTOCOMP = "numbertocomp";
    public final static String COLUMN_DESCRIPTION = "description";
    
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;
    private Cursor ourcursor;
   

    private static class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context) {
            super(context, DBNAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
        	//make some NOT NULL
                db.execSQL(" CREATE TABLE " + TABLE_ACHESTABLE + " (" +
                    COLUMN_FOR + " INT, " +
                    COLUMN_ACHNAME + " TEXT, " +
                    COLUMN_POINTS + " INT, " +
                    COLUMN_GENRE + " TEXT, " +
                    COLUMN_ISCOMPLETED + " INT, " +
                    COLUMN_TIMEFRAME + " INTEGER, " +
                    COLUMN_NUMBEROFCOMP + " INTEGER, " +
                    COLUMN_NUMBERTOCOMP + " INTEGER, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_ICON + " INTEGER);"
            );
                
                db.execSQL(" CREATE TABLE " + TABLE_KEYTABLE + " (" +
                        COLUMN_FOR + " INT, " +
                        COLUMN_LISTNAME + " TEXT);"
                        );
                
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACHESTABLE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_KEYTABLE);
            onCreate(db);
        }

    }
    public ACHDatabase(Context c){
        ourContext = c;
    }
    public ACHDatabase open()throws SQLException{
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }
    
    public void close(){
        ourHelper.close();
    }
    
    public long createInsert(int key, String achname, int points,
            String genre,String time, int numb, String desc, String icon) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FOR, key);
        cv.put(COLUMN_ACHNAME, achname);
        cv.put(COLUMN_POINTS, points);
        cv.put(COLUMN_GENRE, genre);
        cv.put(COLUMN_TIMEFRAME, time);
        cv.put(COLUMN_NUMBERTOCOMP, numb);
        cv.put(COLUMN_DESCRIPTION, desc);
        cv.put(COLUMN_ICON, icon);
        cv.put(COLUMN_ISCOMPLETED, 0);
        cv.put(COLUMN_NUMBEROFCOMP, 0);
        return ourDatabase.insert(TABLE_ACHESTABLE, null, cv);

        
    }
    
    public long createListInsert(int key, String listname) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FOR, key);
        cv.put(COLUMN_LISTNAME, listname);
        return ourDatabase.insert(TABLE_KEYTABLE, null, cv);

        
    }
    
    public Cursor listquery()
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_LISTNAME + " FROM " + TABLE_KEYTABLE, null);
    	return ourcursor;	
    }
    
    public Cursor keyquery()
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_FOR + " FROM " + TABLE_KEYTABLE, null);
    	return ourcursor;
    }
    
    public Cursor achievementQuery(String myList, int mykey)
    {
    	final String MY_QUERY = "SELECT " + COLUMN_ACHNAME +"," + COLUMN_DESCRIPTION + "," + COLUMN_ISCOMPLETED + " FROM " + TABLE_ACHESTABLE + " WHERE " + COLUMN_FOR + " = " + mykey;

    	ourcursor = ourDatabase.rawQuery(MY_QUERY, null);
    	
    	return ourcursor;
    }
 
    public void delete_achievement(String name)
    {
    	ourDatabase.delete(TABLE_ACHESTABLE, COLUMN_ACHNAME+"="+ "'" + name + "'", null);
    }
    
    public void delete_list(int key)
    {
    	ourDatabase.delete(TABLE_KEYTABLE, COLUMN_FOR + " = " + key, null);
    	ourDatabase.delete(TABLE_ACHESTABLE, COLUMN_FOR + " = " + key,null);
    }
    
    public Cursor getpoints(String myName)
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_POINTS + " FROM " + TABLE_ACHESTABLE + " WHERE " + COLUMN_ACHNAME + "=" + "'" + myName + "'", null);
    	return ourcursor;
    }
    
    public Cursor getdesc(String myName)
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_DESCRIPTION + " FROM " + TABLE_ACHESTABLE + " WHERE " + COLUMN_ACHNAME + "=" + "'" + myName + "'", null);
    	return ourcursor;
    }
    
    public Cursor getnumb(String myName)
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_NUMBERTOCOMP + " FROM " + TABLE_ACHESTABLE + " WHERE " + COLUMN_ACHNAME + "=" + "'" + myName + "'", null);
    	return ourcursor;
    }  
    
    public Cursor getallpoints(int key)
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_POINTS + " FROM " + TABLE_ACHESTABLE + " WHERE " + COLUMN_FOR + " = " + key, null);
    	return ourcursor;
    }
    
    public Cursor getallach(int key)
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_ACHNAME + " FROM " + TABLE_ACHESTABLE + " WHERE " + COLUMN_FOR + " = " + key, null);
    	return ourcursor;
    }
    
    public Cursor getpoints(int key)
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_POINTS + " FROM " + TABLE_ACHESTABLE + " WHERE " + COLUMN_FOR + " = " + key + " AND " + COLUMN_ISCOMPLETED + " = " + 1, null);
    	return ourcursor;
    }
    
    public Cursor getach(int key)
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_ACHNAME + " FROM " + TABLE_ACHESTABLE + " WHERE " + COLUMN_FOR + " = " + key + " AND " + COLUMN_ISCOMPLETED + " = " + 1, null);
    	return ourcursor;
    }
    
    public Cursor getiscomp(String myName)
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_ISCOMPLETED + " FROM " + TABLE_ACHESTABLE + " WHERE " + COLUMN_ACHNAME + "=" + "'" + myName + "'", null);
    	return ourcursor;
    }
    
    public Cursor getofcomp(String myName)
    {
    	ourcursor = ourDatabase.rawQuery("SELECT " + COLUMN_NUMBEROFCOMP + " FROM " + TABLE_ACHESTABLE + " WHERE " + COLUMN_ACHNAME + "=" + "'" + myName + "'", null);
    	return ourcursor;
    }
    
    public void setofcomp(String myName, int ofcomp)
    {	
    	ContentValues cv = new ContentValues();
    	cv.put(COLUMN_NUMBEROFCOMP, ofcomp);
    	ourDatabase.update(TABLE_ACHESTABLE, cv, COLUMN_ACHNAME + "=" + "'" + myName + "'" ,null);
    }
    
    public void setiscomp(String myName, int iscomp)
    {	
    	ContentValues cv = new ContentValues();
    	cv.put(COLUMN_ISCOMPLETED, iscomp);
    	ourDatabase.update(TABLE_ACHESTABLE, cv, COLUMN_ACHNAME + "=" + "'" + myName + "'" ,null);
    }
}