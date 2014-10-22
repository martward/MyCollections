package com.mycollections.martijn.mycollections;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Martijn on 10/22/2014.
 */
public class Export_Collection {

    private Context context;
    private String collectionName;
    private DBConnect db;

    public Export_Collection(Context c, String cn){
        context = c;
        collectionName = cn;
        db = new DBConnect(context, collectionName);
    }

    public void export() {
        try {
            String fileName = collectionName + ".csv";
            File myDir = new File(Environment.getExternalStorageDirectory(), "My Collections");
            if(!myDir.exists()) {
                myDir.mkdir();
            }
            String filePath = myDir + "/" + fileName;
            System.out.println(filePath);
            FileWriter writer = new FileWriter(filePath);

            // add column names
            String[] featureNames = db.get_feature_names();
            for(int i = 0; i < featureNames.length; i++){
                String f;
                if(featureNames[i].contains("_spc")){
                    f = featureNames[i].replace("_spc", " ");
                } else{
                    f = featureNames[i];
                }
                writer.append(f);
                if(i < featureNames.length - 1){
                    writer.append(",");
                }
            }
            writer.append("\n");

            // get all items
            ArrayList<String> items = db.get_items();

            // add items to file
            for(int j = 0; j<items.size(); j++){
                ArrayList<String> values = db.get_values(Integer.parseInt(items.get(j)));
                for(int k = 0; k < values.size(); k++){
                    writer.append(values.get(k));
                    if(k < values.size() - 1){
                        writer.append(",");
                    }
                }
            }
            // write and then close the file
            writer.flush();
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}

