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

        SharedPreferences collectionPrefs = getSharedPreferences(collectionName, MODE_PRIVATE);

        TextView title = (TextView) findViewById(R.id.collectionTitle);
        title.setText(collectionName);

        GridView itemsView = (GridView) findViewById(R.id.collectionItems);
        itemsView.setVerticalSpacing(15);
        itemsView.setHorizontalSpacing(10);
        context = itemsView.getContext();

        DBConnect db = new DBConnect(context, collectionName);
        allItems = db.get_items();
        db.close();

        adapter = new Collection_Adapter(context, allItems);
        itemsView.setAdapter(adapter);

        itemsView.setOnItemClickListener(respondToClick);
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
            String item = adapter.getItem(i);
            Intent itemIntent = new Intent(context, Item_Activity.class);
            itemIntent.putExtra("collectionName", collectionName);
            itemIntent.putExtra("item", item);
            startActivity(itemIntent);
        }
    };
}
