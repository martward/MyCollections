package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Martijn on 10/12/2014.
 */
public class Item_Adapter extends BaseAdapter {

    private static int numFeatures;
    private static String[] values;
    private static Context context;
    private static String[] features;
    private Item item;

    Item_Adapter(Item it,Context c){
        context = c;
        item = it;
        features = item.get_features();
        numFeatures = features.length;
        values = item.get_values();
    }

    @Override
    public int getCount() {
        // -1 because we don't want to show the name in the gridview
        return numFeatures * 2;
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
        TextView v = new TextView(context);
        //i = i+2;
        if(i%2 == 0){
            // feature
            String feature;
            if(features[i/2].contains("_spc")){
                feature = features[i/2].replace("_spc", " ");
            }else {
                feature = features[i/2];
            }
            v.setText(feature);
            v.setTextSize(28);
        }else{
            // value
            v.setText(values[i/2]);
            v.setTextSize(24);
            v.setGravity(1);
            v.setBackgroundColor(Color.WHITE);
        }
        v.setTextColor(Color.BLACK);
        v.setPadding(10,10,10,10);
        return v;
    }
}
