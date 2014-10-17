package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Martijn on 17-10-2014.
 */
public class DB_List {

    private static Context context;

    public DB_List(Context c){
        context = c;
    }

    public void add_collections(String collectionName){
        // add 1 to number of collections in sharedPreferences
        SharedPreferences pref = context.getSharedPreferences("DB", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        int numC = pref.getInt("numCollections", 0);
        numC += 1;
        edit.putInt("numCollections", numC);


        // add name of collection to list of collections
        String collections = pref.getString("collections","");
        if(collections.length() > 0) {
            collections = collections + ",";
        }
        collections = collections + collectionName;
        edit.putString("collections", collections);

        edit.commit();
    }

    public void delete_collection(String collectionName){
        // Delete from DB SharedPreferences & numCollections -=1
        SharedPreferences db_prefs = context.getSharedPreferences("DB", context.MODE_PRIVATE);
        int numCollections = db_prefs.getInt("numCollections", 1);
        String collections = db_prefs.getString("collections", "");
        numCollections -= 1;
        collections = collections.replace(collectionName,"");
        SharedPreferences.Editor edit = db_prefs.edit();
        edit.putString("collections", collections);
        edit.putInt("numCollections", numCollections);
        edit.commit();
    }
}
