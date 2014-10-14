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

    Collection_Adapter(Context con, ArrayList<String> allItems){
        context = con;
        items = allItems;
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
            LinearLayout li = new LinearLayout(context);

            TextView letter = new TextView(context);
            letter.setTextSize(48);
            String substring = items.get(i).substring(0, 1);
            letter.setText(substring.toUpperCase());
            letter.setTextColor(Color.RED);
            letter.setBackgroundColor(Color.rgb(153,217,234));
            letter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT, (float) 1.0));
            letter.setGravity(1);

            TextView item = new TextView(context);
            item.setText(items.get(i));
            item.setTextSize(30);
            item.setBackgroundColor(Color.WHITE);
            item.setPadding(15, 20, 15, 15);
            item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT, (float) 0.25));

            li.setOrientation(LinearLayout.HORIZONTAL);
            li.setPadding(5, 5, 5, 10);

            li.addView(letter);
            li.addView(item);
            return li;
        }
    }
}
