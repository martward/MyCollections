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
    private static ArrayList<String> items;
    private static String[][] itemValues;

    Collection_Adapter(Context con, ArrayList<String> allItems, String collectionName){
        context = con;
        items = allItems;


        if(items.size() > 0){
            itemValues = new String[items.size()][];
            DBConnect db = new DBConnect(context, collectionName);
            for(int i=0; i<items.size(); i++){
                ArrayList<String> values = db.get_values(items.get(i));
                itemValues[i] = values.toArray(new String[values.size()]);
            }
            db.close();
        }

    }

    @Override
    public int getCount() {
        if(items.size() == 0){
            return 1;
        }else {
            return items.size();
        }
    }

    @Override
    public String getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(items.size() == 0 & i == 0){
            TextView noItems = new TextView(context);
            noItems.setText("No items found");
            noItems.setTextSize(30);
            noItems.setBackgroundColor(Color.WHITE);
            noItems.setPadding(10,10,10,10);
            return noItems;
        }else {
            LinearLayout row = new LinearLayout(context);

            TextView letter = new TextView(context);
            letter.setTextSize(52);
            String substring = items.get(i).substring(0, 1);
            letter.setText(substring.toUpperCase());
            letter.setTextColor(Color.RED);
            letter.setBackgroundColor(Color.rgb(153,217,234));
            letter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT, (float) 1));
            letter.setGravity(1);

            TextView item = new TextView(context);
            String it = items.get(i);
            if(it.contains("_spc")){
                it = it.replace("_spc", "");
            }

            LinearLayout textRow = new LinearLayout(context);

            item.setText(it);
            item.setTextSize(30);
            item.setBackgroundColor(Color.WHITE);

            TextView values = new TextView(context);
            if(itemValues[i].length > 0){
                String str = "\t";
                for(int j=1; j < itemValues[i].length; j++){
                    if(j < 3) {
                        str = str + itemValues[i][j];
                        if(j < 2) {
                            str = str + ", ";
                        }
                    }
                }
                values.setText(str);
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

            row.addView(letter);
            row.addView(textRow);
            return row;
        }
    }
}
