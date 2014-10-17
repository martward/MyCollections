package com.mycollections.martijn.mycollections;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;
import java.io.FileFilter;


public class Home_Activity extends ActionBarActivity {

    private static Context context;
    private static int numCollections;
    private static String collections;
    private static Home_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences db_prefs = getSharedPreferences("DB", MODE_PRIVATE);
        // check if first time the app is opened
        if(!db_prefs.contains("numCollections")){
            // if not, create SharedPreferences with database name and number of collections = 0
            SharedPreferences.Editor editor = db_prefs.edit();
            editor.putInt("numCollections", 0);
            editor.putString("collections", "");
            numCollections = 0;
            editor.commit();
        }else{
            numCollections = db_prefs.getInt("numCollections",0);
            collections = db_prefs.getString("collections", "");
        }

        // create a GridView which will show the collections
        GridView homeView = (GridView)findViewById(R.id.homeView);
        homeView.setVerticalSpacing(15);
        homeView.setHorizontalSpacing(10);

        context = homeView.getContext();
        adapter = new Home_Adapter(db_prefs, context, homeView.getWidth());
        homeView.setAdapter(adapter);
        homeView.setOnItemClickListener(respondToClick);

        if(numCollections > 0) {
            homeView.setOnItemLongClickListener(respondToHold);
        }
    }

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

        if (id == R.id.action_add_collection){
            Intent createCollection = new Intent(context, Create_Collection_Activity.class);
            startActivity(createCollection);
			finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public AdapterView.OnItemClickListener respondToClick = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String db = (String)adapter.getItem(i);
            Intent collectionIntent = new Intent(context, Collection_Activity.class);
            collectionIntent.putExtra("collectionName", db);
            startActivity(collectionIntent);
			finish();
        }
    };

    /*
    This function displays an alert when the user longpresses a collection,
    and then asks if he wishes to delete it.
     */
    public AdapterView.OnItemLongClickListener respondToHold = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            final String db = adapter.getItem(i).replace("_spc", " ");
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setTitle("Delete collection " + db);
            builder1.setMessage("Ae you sure you wish to delete this collection?");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Delete collection
                            context.deleteDatabase(db);
                            // Delete SharedPreferences
                            DB_List db_list = new DB_List(context);
                            db_list.delete_collection(db);
                            // Restart home activity
                            Intent restart = new Intent(context, Home_Activity.class);
                            startActivity(restart);
                            finish();
                        }
                    });
            builder1.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            return true;
        }
    };
}
