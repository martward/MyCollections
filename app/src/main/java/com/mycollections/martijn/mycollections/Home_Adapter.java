package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Martijn on 10/4/2014.
 */
public class Home_Adapter extends BaseAdapter {

    private static int numCollections;
    private static Context context;

    Home_Adapter(SharedPreferences prefs, Context con){
        numCollections = prefs.getInt("numCollections", 0);
        context = con;
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
    public Object getItem(int i) {
        return null;
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
            noCollections.setTextSize(32);
            return noCollections;
        }else {
            // NEED TO MAKE LATER
            TextView someCollections = new TextView(context);
            someCollections.setText("Some collections found");
            return someCollections;
        }
    }
}
