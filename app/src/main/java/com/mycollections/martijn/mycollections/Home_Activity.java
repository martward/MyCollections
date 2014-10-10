package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;


public class Home_Activity extends ActionBarActivity {

    private static Context context;
    private static int numCollections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences db_prefs = getSharedPreferences("DB", MODE_PRIVATE);
        // check if first time the app is opened
        if(!db_prefs.contains("databaseName")){
            // if not, create SharedPreferences with database name and number of collections = 0
            SharedPreferences.Editor editor = db_prefs.edit();
            editor.putString("databaseName", "MyDatabase");
            editor.putInt("numCollections", 0);
            numCollections = 0;
            editor.commit();
        }else{
            numCollections = db_prefs.getInt("numCollections",0);
        }

        // create a GridView which will show the collections
        GridView homeView = (GridView)findViewById(R.id.homeView);
        context = homeView.getContext();
        homeView.setAdapter(new Home_Adapter(db_prefs, context));
        /*
        // create the button to create a new collection
        Button button = (Button)findViewById(R.id.newCollectionButton);
        button.setOnClickListener(buttonClick);
        */
    }
/*

    Handles the button to create a new collection.

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent createCollection = new Intent(context, Create_Collection_Activity.class);
            startActivity(createCollection);
        }
    };
*/
    private AdapterView.OnItemClickListener respondToClick = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(numCollections != 0){
                // open the selected collection
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_add){
            Intent createCollection = new Intent(context, Create_Collection_Activity.class);
            startActivity(createCollection);
        }
        return super.onOptionsItemSelected(item);
    }
}
