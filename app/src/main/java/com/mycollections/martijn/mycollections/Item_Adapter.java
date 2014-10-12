package com.mycollections.martijn.mycollections;

import android.content.Context;
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
    private static ArrayList<String> values;
    private static Context context;

    Item_Adapter(Context c, ArrayList<String> v){
        numFeatures = v.size();
        values = v;
        context = c;
    }

    @Override
    public int getCount() {
        return numFeatures-1;
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
        TextView feature = new TextView(context);
        feature.setText(values.get(i+1));
        feature.setTextSize(24);
        return feature;
    }
}
