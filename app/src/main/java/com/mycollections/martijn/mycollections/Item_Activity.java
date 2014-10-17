package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;


public class Item_Activity extends ActionBarActivity {

    private Item item;
    private Context context;
    private String collectionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        collectionName = intent.getStringExtra("collectionName");
        int id = intent.getIntExtra("item", 0);

        GridView itemFeatures = (GridView) findViewById(R.id.itemFeatures);
        itemFeatures.setVerticalSpacing(20);

        context = itemFeatures.getContext();

        DBConnect db = new DBConnect(context, collectionName);
        ArrayList<String> values = db.get_values(id);
        String[] features = db.get_feature_names();
        db.close();

        item = new Item(id, features, values.toArray(new String[values.size()]));

        // setting the name of the item
        TextView itemName = (TextView)findViewById(R.id.itemName);
        itemName.setText(item.get_name());
        itemName.setTextSize(36);
        itemName.setPadding(15, 15, 15, 15);


        Item_Adapter adapter = new Item_Adapter(item, context);
        itemFeatures.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_edit_item) {
            Intent edit = new Intent(context, Edit_Item_Activity.class);
            edit.putExtra("item", id);
            edit.putExtra("collectionName", collectionName);
            startActivity(edit);
            finish();
            return true;
        }
        if (id == R.id.action_delete_item) {
            // delete item from db
            DBConnect db = new DBConnect(context, collectionName);
            db.delete_item(id);
            db.close();
            // back to collection
            Intent collectionIntent = new Intent(context, Collection_Activity.class);
            collectionIntent.putExtra("collectionName", collectionName);
            startActivity(collectionIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent back = new Intent(context, Collection_Activity.class);
        back.putExtra("collectionName", collectionName);
        startActivity(back);
        finish();
    }

}
