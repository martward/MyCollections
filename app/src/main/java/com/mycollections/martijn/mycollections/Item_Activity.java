package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Item_Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        String collectionName = intent.getStringExtra("collectionName");
        String item = intent.getStringExtra("item");

        TextView itemName = (TextView)findViewById(R.id.itemName);
        itemName.setText(item);
        itemName.setTextSize(36);
        itemName.setPadding(15,15,15,15);

        GridView itemFeatures = (GridView) findViewById(R.id.itemFeatures);
        itemFeatures.setVerticalSpacing(20);

        Context context = itemName.getContext();

        DBConnect db = new DBConnect(context, collectionName);
        ArrayList<String> values = db.get_values(item);
        String[] features = db.get_feature_names();
        db.close();

        Item_Adapter adapter = new Item_Adapter(context, values, features);
        itemFeatures.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_, menu);
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
        return super.onOptionsItemSelected(item);
    }



}
