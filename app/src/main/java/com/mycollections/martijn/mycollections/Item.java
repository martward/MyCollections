package com.mycollections.martijn.mycollections;

/**
 * Created by Martijn on 10/17/2014.
 */
public class Item {

    private String[] values;
    private String[] features;
    private int ID;
    private String name;

    public Item(int id, String[] f, String[] v){
        name = v[0];
        features = f;
        values = v;
        ID = id;
    }

    public int get_id(){
       return ID;
    }

    public String[] get_values(){
        return values;
    }

    public String[] get_features(){
        return features;
    }

    public String get_name(){
        return name;
    }

}
