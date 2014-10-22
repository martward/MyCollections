package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Martijn on 10/4/2014.
 */
public class Home_Adapter extends BaseAdapter {

    private static int numCollections;
    private static String[] allCollections;
    private static Context context;
    private static int width;

    Home_Adapter(SharedPreferences prefs, Context con, int w){
        numCollections = prefs.getInt("numCollections", 0);
        if(numCollections > 0) {
            String collections = prefs.getString("collections", "");
            System.out.println(collections);
            allCollections = collections.split(",");
        }
        context = con;
        width = w;
    }

    @Override
    public int getCount() {
        if (numCollections > 0){
            return numCollections;
        }else {
            return 1;
        }
    }

    @Override
    public String getItem(int i) {
        return allCollections[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(i == 0 && numCollections == 0){
            TextView noCollections = new TextView(context);
            noCollections.setText("No databases found.");
            noCollections.setTextSize(30);
            noCollections.setTextColor(Color.GRAY);
            return noCollections;
        }else {
            LinearLayout li = new LinearLayout(context);

            // creating textview with startin letter of collection
            TextView letter = new TextView(context);
            letter.setTextSize(48);
            String substring = allCollections[i].substring(0, 1);
            letter.setText(substring.toUpperCase());
            letter.setTextColor(Color.RED);
            letter.setBackgroundColor(Color.rgb(153,217,234));
            letter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT, (float) 1.0));
            letter.setGravity(1);

            // creating textview that shows the collection name
            TextView someCollections = new TextView(context);
            if (allCollections[i].contains("_spc")) {
                String c = allCollections[i].replace("_spc", " ");
                someCollections.setText(c);
            } else {
                someCollections.setText(allCollections[i]);
            }
            someCollections.setPadding(15, 20, 15, 15);
            someCollections.setTextSize(30);
            someCollections.setBackgroundColor(Color.WHITE);
            someCollections.setTextColor(Color.BLACK);
            someCollections.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT, (float) 0.25));


            li.setOrientation(LinearLayout.HORIZONTAL);
            li.setPadding(5, 5, 5, 10);

            // adding views to linear layout
            li.addView(letter);
            li.addView(someCollections);

            return li;
        }
    }
}
