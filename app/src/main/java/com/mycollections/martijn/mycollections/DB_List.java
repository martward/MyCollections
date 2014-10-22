package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Martijn on 17-10-2014.
 */
public class DB_List {

    private static Context context;
    private static SharedPreferences pref;
    private String collections;
    private int numCollections;

    public DB_List(Context c){
        context = c;
        pref =  context.getSharedPreferences("DB", context.MODE_PRIVATE);
        collections = pref.getString("collections", "");
        numCollections = pref.getInt("numCollections", 0);
    }

    public void add_collections(String collectionName){
        // add 1 to number of collections in sharedPreferences
        SharedPreferences.Editor edit = pref.edit();
        numCollections += 1;
        edit.putInt("numCollections", numCollections);


        // add name of collection to list of collections
        if(collections.length() > 0) {
            collections = collections + ",";
        }
        collections = collections + collectionName;
        edit.putString("collections", collections);

        edit.commit();
    }

    public void delete_collection(String collectionName){
        // Delete from DB SharedPreferences & numCollections -=1
        numCollections -= 1;
        collections = collections.replace(collectionName,"");
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("collections", collections);
        edit.putInt("numCollections", numCollections);
        edit.commit();
    }

    public String get_collections(){
        return collections;
    }
}
