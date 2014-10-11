package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Martijn on 10/11/2014.
 */
public class DBConnect extends SQLiteOpenHelper {

    private static SQLiteDatabase readableDatabase;
    private static SQLiteDatabase writableDatabase;
    private static String dbName = "MyDB";
    private static String tableName;

    public DBConnect(Context context, String name){
        super(context, name, null, 1);
        tableName = name;
        readableDatabase = getReadableDatabase();
        writableDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public void create_table(String features){
        String[] allFeatures = features.split(",");
        String createTable = "create table " + tableName + " (" ;
        for(int i = 0; i<allFeatures.length;i+=2){
            String type;
            if ( allFeatures[i+1]== "Number"){
                type = "integer";
            }else if( allFeatures[i+1]== "Date"){
                type = "date";
            }else {
                type = "text";
            }
            createTable = createTable + allFeatures[i] + " " + allFeatures[i+1] + ", ";
        }
        createTable = createTable.substring(0,createTable.length()-2);
        createTable = createTable + ");";
        System.out.println(createTable);
        writableDatabase.execSQL(createTable);
    }

    public void get_values(Cursor cursor){
        if(cursor.moveToFirst()) {
            do {
                System.out.println(cursor.getString(1));
            } while (cursor.moveToNext());
        }
    }

    public String[] get_items(){
        String[] items = new String[0];
        return items;
    }

    public String get_feature(String feature){
        String str = "";
        return str;
    }   

}
