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
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;


public class Collection_Activity extends ActionBarActivity {

    private static String collectionName;
    private static Collection_Adapter adapter;
    private static Context context;
    private static ArrayList<String> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Intent intent = getIntent();
        collectionName = intent.getStringExtra("collectionName");

        TextView title = (TextView) findViewById(R.id.collectionTitle);
        String name;
        if(collectionName.contains("_spc")){
             name = collectionName.replace("_spc", " ");
        }else{
            name = collectionName;
        }
        title.setText(name);
        title.setTextColor(Color.BLACK);
        context = getBaseContext();

        GridView itemsView = (GridView) findViewById(R.id.collectionItems);
        itemsView.setVerticalSpacing(15);
        itemsView.setHorizontalSpacing(5);

        DBConnect db = new DBConnect(context, collectionName);
        allItems = db.get_items();
        db.close();

        adapter = new Collection_Adapter(context, allItems, collectionName);
        itemsView.setAdapter(adapter);
        if(allItems.size() > 0) {
            itemsView.setOnItemClickListener(respondToClick);
            itemsView.setOnItemLongClickListener(respondToHold);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_item) {
            Intent addItem = new Intent(context, Add_Item_Activity.class);
            addItem.putExtra("collectionName", collectionName);
            startActivity(addItem);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public AdapterView.OnItemClickListener respondToClick = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Item item = adapter.getItem(i);
            Intent itemIntent = new Intent(context, Item_Activity.class);
            itemIntent.putExtra("collectionName", collectionName);
            itemIntent.putExtra("item", item.get_id());
            startActivity(itemIntent);
            finish();
        }
    };

    /*
    This function creates a dialog that asks the user if he wants to delete the item
     */
    public AdapterView.OnItemLongClickListener respondToHold = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            final String item = adapter.getItem(i).get_name().replace("_spc", "");
            final int itemId = adapter.getItem(i).get_id();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setTitle("Delete " + item);
            builder1.setMessage("Ae you sure you wish to delete this item?");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Delete item from db
                            DBConnect db = new DBConnect(context, collectionName);
                            db.delete_item(itemId);
                            db.close();
                            // Restart activity
                            Intent collectionIntent = new Intent(context, Collection_Activity.class);
                            collectionIntent.putExtra("collectionName", collectionName);
                            startActivity(collectionIntent);
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

    @Override
    public void onBackPressed(){
        Intent back = new Intent(context, Home_Activity.class);
        startActivity(back);
        finish();
    }
}
