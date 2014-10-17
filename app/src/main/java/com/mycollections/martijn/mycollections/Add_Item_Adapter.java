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
import java.util.Arrays;

public class Add_Item_Adapter extends BaseAdapter {

    private static Context context;
    private static String[] features;
    private static String[] types;
    private static ArrayList<EditText> allEditFields;
    private static String collectionName;

    Add_Item_Adapter(Context con, String name){
        collectionName = name;
        context = con;

        DBConnect db = new DBConnect(context, collectionName);
        features = db.get_feature_names();
        types = db.get_feature_types();
        System.out.println(features.length);
        System.out.println(types.length);
        db.close();

        create_edits();
    }

    /*
    This function creates all edit fields, so the don't get cleared when the adapter
    is updated
     */
    private void create_edits() {
        ArrayList<EditText> edit = new ArrayList<EditText>();
        int id = 0;
        for(int i = 0; i < (types.length); i++){
            EditText text = new EditText(context);
            text.setId(id);
            text.setSingleLine();
            text.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            if(types[i].equalsIgnoreCase("integer")){
                text.setKeyListener(new DigitsKeyListener());
            } else{
                text.setSingleLine();
                text.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            }
            edit.add(text);
            id+=1;
        }
        allEditFields = edit;
    }

    @Override
    public int getCount() {
        return (features.length * 2);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /*
    This field either returns a textview with the feature name
    or am edittext, depending on the position
     */
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
            featureName.setTextSize(22);
            return featureName;
        } else{
            return allEditFields.get(i/2);
        }
    }

    /*
    Gets a value from an edittext
     */
    public String get_value(int pos){
        return allEditFields.get(pos).getText().toString();
    }

    /*
    Sets the value of an edittext
     */
    public void set_value(int pos, String value){
        allEditFields.get(pos/2).setText(value);
    }


}
