package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Martijn on 10/17/2014.
 */
public class Edit_Item_Adapter extends BaseAdapter {

    private Item item;
    private Context context;
    private String[] features;
    private String[] values;
    private String[] types;
    private EditText[] allEditFields;

    public Edit_Item_Adapter(Item it, String[] t, Context c){
        item = it;
        context = c;
        features = item.get_features();
        values = item.get_values();
        types = t;
        create_edits();
    }

    /*
    This function creates all edit fields, so the don't get cleared when the adapter
    is updated
    */
    private void create_edits() {
        ArrayList<EditText> edit = new ArrayList<EditText>();
        int id = 0;
        for (int i = 0; i < (types.length); i++) {
            EditText text = new EditText(context);
            text.setId(id);
            text.setCursorVisible(true);
            text.setTextColor(Color.BLACK);
            text.setText(values[i]);
            text.setBackgroundColor(Color.WHITE);
            text.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            text.setTextSize(24);
            if (types[i].equalsIgnoreCase("integer")) {
                text.setKeyListener(new DigitsKeyListener());
            } else {
                text.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            }
            edit.add(text);
            id += 1;
        }
        allEditFields = edit.toArray(new EditText[edit.size()]);
    }


    @Override
    public int getCount() {
        return (features.length + values.length);
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
        if(i % 2 == 0){
            TextView featureName = new TextView(context);
            if(features[i/2].contains("_spc")){
                String name = features[i/2].replace("_spc", " ");
                featureName.setText(name);
            }else{
                featureName.setText(features[i/2]);
            }
            featureName.setTextSize(28);
            featureName.setTextColor(Color.BLACK);
            return featureName;
        } else{
            allEditFields[i/2].setTextColor(Color.BLACK);
            return allEditFields[i/2];
        }
    }

    public String get_value(int i){
        return allEditFields[i].getText().toString();
    }
}
