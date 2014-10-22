package com.mycollections.martijn.mycollections;

import android.content.ContentValues;
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

    private SQLiteDatabase db;
    private String dbName;
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
        // getting features and types from f
        String[] allFeatures = f.split(",");
        // creating the query
        String createTable = "create table " + tableName + " (id INTEGER PRIMARY KEY, " ;
        for(int i = 0; i<allFeatures.length;i+=2){
            String type;
            if ( allFeatures[i+1].equalsIgnoreCase("Number")){
                type = "integer";
            }else {
                type = "text";
            }
            createTable = createTable + allFeatures[i] + " " + type + ", ";
        }
        // deleting last comma and space
        createTable = createTable.substring(0,createTable.length()-2);
        createTable = createTable + ");";
        System.out.println(createTable);
        db.execSQL(createTable);
    }

    // returns arraylist with the names of all items
    public ArrayList<String> get_items(){
        ArrayList<String> items = new ArrayList<String>();
        // selecting all items
        String query = "SELECT * FROM " + tableName;
        Cursor cursor = db.rawQuery(query, null);
        // adding items to arraylist
        if(cursor.moveToFirst()){
            do {
                items.add(cursor.getString(0));
            }while(cursor.moveToNext());

        }
        cursor.close();
        return items;
    }

    public void close(){
        db.close();
    }

    public void insert_row(String[] features, String[] values){
        ContentValues cV = new ContentValues();
        for(int i = 0; i < values.length; i++){
            cV.put(features[i], values[i]);
        }
        db.insert(tableName,null,cV);

    }

    public ArrayList<String> get_values(int item){
        ArrayList<String> values = new ArrayList<String>();
        String itemId = Integer.toString(item);
        // selecting all values from a row
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";
        Cursor c = db.rawQuery(query,new String[] {itemId});

        c.moveToFirst();
        // get number of features
        int numF = get_feature_names().length;
        // adding all features to arraylist
        for (int i=0; i<numF; i++) {
            values.add(c.getString(i+1));
        }
        c.close();
        return values;

    }

    public void delete_item(int item){
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        String itemId = Integer.toString(item);
        db.execSQL(query, new String[] {itemId});
    }

    public String[] get_feature_names(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        String[] cn = cursor.getColumnNames();
        String[] columnNames = new String[cn.length-1];
        for(int i = 0; i < cn.length-1; i++){
            columnNames[i] = cn[i+1];
        }
        return columnNames;
    }

    public String[] get_feature_types(){
        ArrayList<String> types = new ArrayList<String>();
        // getting table info
        Cursor c = db.rawQuery("PRAGMA table_info(" + tableName + ")",null);
        c.moveToFirst();
        // skipping id
        c.moveToNext();
        // getting all types and add them to a list
        do{
            types.add(c.getString(2));
        } while(c.moveToNext());
        return types.toArray(new String[types.size()]);
    }

    public void update_item(int id, String[] values){
        String[] features = get_feature_names();
        ContentValues cV = new ContentValues();
        for(int i =0; i < features.length; i++){
            cV.put(features[i], values[i]);
        }
        db.update(tableName, cV, "id = " + id, null);
    }
}
