package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;


public class Add_Item_Activity extends ActionBarActivity {

    private static Add_Item_Adapter adapter;
    private static String[] allFeatures;
    private static String collectionName;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Intent intent = getIntent();
        collectionName = intent.getStringExtra("collectionName");

        GridView addItemView = (GridView) findViewById(R.id.addItemView);
        context = addItemView.getContext();
        adapter = new Add_Item_Adapter(context, collectionName);
        addItemView.setAdapter(adapter);

        get_features();

        // loading values of features when app was paused (used when changing orientation
        if(savedInstanceState!= null){
            for(int i=0; i<allFeatures.length;i+=2){
                adapter.set_value(i, savedInstanceState.getString(String.valueOf(i)));
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_save_item) {
            String[] values = new String[allFeatures.length];
            for(int j = 0; j < allFeatures.length; j++){
                if(adapter.get_value(j) != null) {
                    values[j] = adapter.get_value(j);
                }else{
                    values[j] = "";
                }
            }
            DBConnect db = new DBConnect(context, collectionName);
            db.insert_row(allFeatures, values);
            db.close();
            Intent back = new Intent(context, Collection_Activity.class);
            back.putExtra("collectionName", collectionName);
            startActivity(back);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    Saving all values in case app is paused (used when changing orientation)
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        for(int i = 0; i < allFeatures.length; i+=2){
            String id = Integer.toString(i);
            savedInstanceState.putString(id, adapter.get_value(i/2));
        }
    }

    /*
    Getting a list of all features of a collection
     */
    private static void get_features(){
        DBConnect db = new DBConnect(context, collectionName);
        allFeatures = db.get_feature_names();
        db.close();
    }

    @Override
    public void onBackPressed(){
        Intent back = new Intent(context, Collection_Activity.class);
        back.putExtra("collectionName", collectionName);
        startActivity(back);
        finish();
    }
}
