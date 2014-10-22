package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Martijn on 10/12/2014.
 */
public class Collection_Adapter extends BaseAdapter {

    private static Context context;
    private static int numItems;
    private static Item[] items;

    Collection_Adapter(Context con, ArrayList<String> allItems, String collectionName){
        context = con;
        numItems = allItems.size();

        items = new Item[numItems];
        if(allItems.size() > 0){
            DBConnect db = new DBConnect(context, collectionName);
            String[] features = db.get_feature_names();
            for(int i=0; i<numItems; i++){
                int id = Integer.parseInt(allItems.get(i));
                String[] values = db.get_values(id).toArray(new String[features.length]);
                Item item = new Item(id, features, values);
                items[i] = item;
            }
            db.close();
        }
    }

    @Override
    public int getCount() {
        if(numItems == 0){
            return 1;
        }else {
            return items.length;
        }
    }

    @Override
    public Item getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(numItems == 0 & i == 0){
            // if no items are present
            TextView noItems = new TextView(context);
            noItems.setText("No items found");
            noItems.setTextSize(30);
            noItems.setBackgroundColor(Color.WHITE);
            noItems.setPadding(10,10,10,10);
            return noItems;
        }else {

            // System.out.println(items.get(i).get_id());

            // creating the row to be shown
            LinearLayout row = new LinearLayout(context);

            // creating letter next to the name of the collection
            TextView letter = new TextView(context);
            letter.setTextSize(52);
            String substring = items[i].get_name().substring(0, 1);
            letter.setText(substring.toUpperCase());
            letter.setTextColor(Color.RED);
            letter.setBackgroundColor(Color.rgb(153,217,234));
            letter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT, (float) 1));
            letter.setGravity(1);

            // creating textview with the name of the item
            TextView item = new TextView(context);
            String it = items[i].get_name();
            if(it.contains("_spc")){
                it = it.replace("_spc", "");
            }

            // linearlayout with the name of the item and a preview of it's values
            LinearLayout textRow = new LinearLayout(context);

            item.setText(it);
            item.setTextSize(30);
            item.setBackgroundColor(Color.WHITE);
            item.setTextColor(Color.BLACK);

            // creating textview with up to three values of features of the item
            TextView values = new TextView(context);
            if(items[i].get_values().length > 0){
                String str = "\t";
                for(int j=1; j < items[i].get_values().length; j++){
                    if(j < 3) {
                        str = str + items[i].get_values()[j];
                        //System.out.println(str);
                        if(j < 2) {
                            str = str + ", ";
                        }
                    }
                }
                // delete last comma and space
                str = str.substring(0, str.length());
                values.setText(str);
                values.setTextColor(Color.BLACK);
            }else {
                values.setText("No values found");
            }
            values.setBackgroundColor(Color.WHITE);
            textRow.setOrientation(LinearLayout.VERTICAL);
            textRow.addView(item);
            textRow.addView(values);

            textRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT, (float) 0.2));
            textRow.setPadding(15, 20, 15, 15);
            textRow.setBackgroundColor(Color.WHITE);

            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 5, 5, 10);

            // add everything to the row
            row.addView(letter);
            row.addView(textRow);
            return row;
        }
    }
}
