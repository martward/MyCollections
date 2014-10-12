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
    private static String features;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Intent intent = getIntent();
        collectionName = intent.getStringExtra("collectionName");

        SharedPreferences collectionPrefs = getSharedPreferences(collectionName, MODE_PRIVATE);
        features = collectionPrefs.getString("features", "");

        allFeatures = features.split(",");

        GridView addItemView = (GridView) findViewById(R.id.addItemView);
        context = addItemView.getContext();
        adapter = new Add_Item_Adapter(context, features);
        addItemView.setAdapter(adapter);



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
            String[] featuresAndValues = new String[allFeatures.length];
            for(int i = 0; i < allFeatures.length; i++){
                if(i%2 == 0){
                    featuresAndValues[i] = allFeatures[i];
                }else{
                    if(adapter.get_value(i/2) != null) {
                        featuresAndValues[i] = adapter.get_value(i/2);
                    }else{
                        featuresAndValues[i] = "";
                    }
                }
            }
            DBConnect db = new DBConnect(context, collectionName);
            db.insert_row(featuresAndValues);
            db.close();
            Intent back = new Intent(context, Collection_Activity.class);
            back.putExtra("collectionName", collectionName);
            startActivity(back);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        for(int i = 0; i < allFeatures.length; i+=2){
            String id = Integer.toString(i);
            savedInstanceState.putString(id, adapter.get_value(i/2));
        }
    }
}
