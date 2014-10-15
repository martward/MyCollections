package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Martijn on 10/11/2014.
 */
public class DBConnect extends SQLiteOpenHelper {

    private static SQLiteDatabase db;
    private static String dbName;
    private static String tableName = "dbTable";

    public DBConnect(Context context, String dbN){
        super(context, dbN, null, 1);
        dbName = dbN;
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("creating database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        onCreate(sqLiteDatabase);
    }

    public void create_table(String f){
        String[] allFeatures = f.split(",");
        String createTable = "create table " + tableName + " (" ;
        for(int i = 0; i<allFeatures.length;i+=2){
            String type;
            if ( allFeatures[i+1]== "Number"){
                type = "integer";
            }else {
                type = "text";
            }
            createTable = createTable + allFeatures[i] + " " + type + ", ";
        }
        createTable = createTable.substring(0,createTable.length()-2);
        createTable = createTable + ");";
        db.execSQL(createTable);
    }

    // returns arraylist with the names of all items
    public ArrayList<String> get_items(){
        ArrayList<String> items = new ArrayList<String>();
        String query = "SELECT * FROM " + tableName;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                items.add(cursor.getString(0));
            }
        }
        return items;
    }

    public void close(){
        db.close();
    }

    public void insert_row(String[] featuresAndValues){
        System.out.println(featuresAndValues.length);
        String[] features = new String[featuresAndValues.length/2];
        String[] values = new String[featuresAndValues.length/2];
        for(int i=0; i<featuresAndValues.length; i+=2){
            if(i == 0) {
                features[i] = featuresAndValues[i];
                values[i] = featuresAndValues[i + 1];
            } else{
                features[i/2] = featuresAndValues[i];
                values[i/2] = featuresAndValues[i + 1];
            }
        }
        String query = "INSERT INTO "+ tableName + " (";
        for(int j = 0; j < features.length; j++){
            query = query + features[j];
            if(j<features.length-1){
                query = query + ", ";
            }
        }
        query = query + ") VALUES (";
        for(int k=0; k < values.length; k++){
            query = query + "'" + values[k] + "'";
            if(k < values.length-1){
                query = query + ", ";
            }
        }
        query = query + ");";

        db.execSQL(query);
    }

    public ArrayList<String> get_values(String item){
        ArrayList<String> values = new ArrayList<String>();
        String query = "SELECT * FROM " + tableName + " WHERE Name = '" + item + "'";
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()){
            for(int i=0; i<3;i++){
                if(c.getString(i) != null){
                    values.add(c.getString(i));
                }else{
                    System.out.println("Int");
                    values.add(Integer.toString(c.getInt(i)));
                }
            }
        }
        return values;
    }

    public void delete_item(String item){
        String query = "DELETE FROM " + tableName + " WHERE Name = '" + item + "'";
        db.execSQL(query);
    }

}
