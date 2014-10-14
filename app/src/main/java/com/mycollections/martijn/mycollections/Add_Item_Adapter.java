package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

public class Add_Item_Adapter extends BaseAdapter {

    private static Context context;
    private static String[] features;
    private static ArrayList<EditText> allEditFields;

    Add_Item_Adapter(Context con, String feats){
        context = con;
        features = feats.split(",");
        create_edits();
    }

    private void create_edits() {
        ArrayList<EditText> edit = new ArrayList<EditText>();
        for(int i = 1; i < (features.length); i+=2){
            EditText text = new EditText(context);
            text.setId(i);
            System.out.println(features[i-1]);
            if(features[i].equalsIgnoreCase("number")){
                text.setKeyListener(new DigitsKeyListener());
            } else{
                text.setSingleLine();
                text.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            }
            edit.add(text);
        }
        allEditFields = edit;
    }

    @Override
    public int getCount() {
        return features.length;
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
            if(features[i].contains("_")){
                String name = features[i].replace("_", " ");
                featureName.setText(name);
            }else{
                featureName.setText(features[i]);
            }
            featureName.setTextSize(22);
            return featureName;
        } else{
            return allEditFields.get(i/2);
        }
    }

    public String get_value(int pos){
        return allEditFields.get(pos).getText().toString();
    }

    public void set_value(int pos, String value){
        allEditFields.get(pos/2).setText(value);
    }

}
