package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Edit_Item_Activity extends ActionBarActivity {

    private String collectionName;
    private int itemId;
    private Context context;
    private Item item;
    private Edit_Item_Adapter adapter;
    private String[] features;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        collectionName = intent.getStringExtra("collectionName");
        itemId = intent.getIntExtra("item", 0);
        context = getBaseContext();

        DBConnect db = new DBConnect(context, collectionName);
        features = db.get_feature_names();
        String[] values = db.get_values(itemId).toArray(new String[features.length]);
        String[] types = db.get_feature_types();
        db.close();

        item = new Item(itemId, features, values);

        GridView feats = (GridView) findViewById(R.id.itemFeaturesEdit);
        feats.setVerticalSpacing(20);

        adapter = new Edit_Item_Adapter(item, types, context);
        feats.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem it) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = it.getItemId();
        if (id == R.id.action_edit_item) {
            String[] values = new String[features.length];
            for(int i=0; i <features.length; i++){
                if(values[i] != " ") {
                    values[i] = adapter.get_value(i);
                } else{
                    values[i] = "Unknown";
                }
            }
            DBConnect db = new DBConnect(context, collectionName);
            db.update_item(item.get_id(),values);
            db.close();
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(it);
    }

    @Override
    public void onBackPressed(){
        Intent back = new Intent(context, Collection_Activity.class);
        back.putExtra("collectionName", collectionName);
        startActivity(back);
        finish();
    }
}
