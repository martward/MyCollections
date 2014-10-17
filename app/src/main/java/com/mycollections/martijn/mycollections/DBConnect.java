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
            if ( allFeatures[i+1].equalsIgnoreCase("Number")){
                type = "integer";
            }else {
                type = "text";
            }
            createTable = createTable + allFeatures[i] + " " + type + ", ";
        }
        createTable = createTable.substring(0,createTable.length()-2);
        createTable = createTable + ");";
        System.out.println(createTable);
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
                //System.out.println(cursor.getString(1));
                //System.out.println(cursor.getString(2));
            }
        }
        cursor.close();
        return items;
    }

    public void close(){
        db.close();
    }

    public void insert_row(String[] features, String[] values){
        for(int i = 0; i < features.length; i++){
            System.out.println(features[i] + ": " + values[i]);
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
        System.out.println(query);

        db.execSQL(query);
    }

    public ArrayList<String> get_values(String item){
        ArrayList<String> values = new ArrayList<String>();
        String query = "SELECT * FROM " + tableName + " WHERE Name = ?";
        Cursor c = db.rawQuery(query,new String[] {item});


        int numF = get_feature_names().length;
        for (int i=0; i<numF; i++) {
            System.out.println(c.getString(i));
            values.add(c.getString(i));
        }
        c.close();
        return values;

    }

    public void delete_item(String item){
        String query = "DELETE FROM " + tableName + " WHERE Name = ?";
        db.execSQL(query, new String[] {item});
    }

    public String[] get_feature_names(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        return cursor.getColumnNames();
    }

    public String[] get_feature_types(){
        ArrayList<String> types = new ArrayList<String>();
        Cursor c = db.rawQuery("PRAGMA table_info(" + tableName + ")",null);
        c.moveToFirst();
        do{
            types.add(c.getString(2));
            //System.out.println(c.getString(1));
        } while(c.moveToNext());
        return types.toArray(new String[types.size()]);
    }
}
