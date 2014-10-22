package com.mycollections.martijn.mycollections;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import javax.xml.datatype.Duration;


public class Create_Collection_Activity extends ActionBarActivity {

    private static boolean first;
    private static Context context;
    private static String collectionName;
    private static ArrayList<String> attributes;
    private static String attrType;
    private static EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras == null){
            // hide the fields which are not used when giving the name of the collection
            TextView text = (TextView)findViewById(R.id.textViewCreateCollection);
            text.setText("Please enter the name of your collection.");
            Button finish = (Button)findViewById(R.id.buttonFinishCollection);
            finish.setVisibility(View.GONE);
            Spinner spinner = (Spinner)findViewById(R.id.spinner);
            spinner.setVisibility(View.GONE);
            // initialize attributes ArrayList
            attributes = new ArrayList<String>();
            // used in the OnClickListener
            first = true;
        } else {
            first = false;
            collectionName = intent.getStringExtra("collectionName");
            attributes = intent.getStringArrayListExtra("attributes");
        }

        /*
        Code copied from developer.android.com
        http://developer.android.com/guide/topics/ui/controls/spinner.html
         */
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.data_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerActivity());

        // making the EditText field accessible throughout the class
        editText = (EditText)findViewById(R.id.editTextCreateCollection);

        // The buttons are given an OnClickListener
        Button finish = (Button)findViewById(R.id.buttonFinishCollection);
        Button next = (Button)findViewById(R.id.buttonNextAttribute);
        finish.setOnClickListener(buttonClick);
        next.setOnClickListener(buttonClick);

        context = getBaseContext();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_collection, menu);
        return true;
    }


    /*
    Handles input from the spinner to set the type of data.
     */
    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            attrType = parent.getItemAtPosition(pos).toString();
        }
        public void onNothingSelected(AdapterView<?> parent) {
            attrType = "Text";
        }
    }

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String attrName = editText.getText().toString();
            switch (view.getId()) {
                case R.id.buttonFinishCollection:
                    create_collection(attrName);
                    // go back to home menu
                    onBackPressed();
                    break;
                case R.id.buttonNextAttribute:
                    if (first) {
                        /*
                        Adding the title of the collection
                         */
                        if (attrName.length() > 0) {
                            DB_List db_list = new DB_List(context);
                            String collections = db_list.get_collections();
                            if(attrName.contains(" ")){
                                attrName = attrName.replace(" ", "_spc");
                            }
                            // check if collection already exists
                            if(!collections.contains(attrName)) {
                                collectionName = attrName;
                                System.out.println(collectionName);
                                next();
                            }else{
                                show_toast("This collections already exists");
                            }
                        }else {
                            show_toast("Please enter a name");
                        }
                    } else {
                        /*
                        Adding an attribute of the collection
                         */
                        if (attrName.length() > 0) {
                            if (!attributes.contains(attrName)) {
                                // add name and type to attributes list before starting new intent
                                if (attrName.contains(" ")) {
                                    attrName = attrName.replace(" ", "_spc");
                                }
                                attributes.add(attrName);
                                attributes.add(attrType);
                                next();
                            } else{
                                show_toast(attrName + " feature is already added.");
                            }
                        } else {
                            show_toast("Please enter a name and a type");
                        }
                    }
            }
        }
    };

    private void create_collection(String attrName){
        // make a string of all attributes and their types
        String allAttributes = create_attribute_list(attrName);

        // create table
        DBConnect db = new DBConnect(context, collectionName);
        db.create_table(allAttributes);
        db.close();

        // add 1 to number of collections in sharedPreferences
        DB_List db_list = new DB_List(context);
        db_list.add_collections(collectionName);
    }

    /*
    Shows a toast with a given text
     */
    private void show_toast(CharSequence text){
        Toast toast = Toast.makeText(this.getBaseContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    /*
    Creates a string made up of the features and their types
     */
    private String create_attribute_list(String attrName){
        // updating features
        if(attrName.contains(" ")){
            attrName = attrName.replace(" ", "_spc");
        }
        attributes.add(attrName);
        attributes.add(attrType);

        String allAttributes = "";
        for(String s:attributes){
            allAttributes += s;
            allAttributes+= ",";
        }
        // make sure format is ok
        allAttributes = allAttributes.substring(0,allAttributes.length()-1);
        return allAttributes;
    }

    /*
    Opens a new activity to add a feature
     */
    public void next(){
        Intent nextAttribute = new Intent(context, Create_Collection_Activity.class);
        nextAttribute.putExtra("collectionName", collectionName);
        nextAttribute.putStringArrayListExtra("attributes", attributes);
        startActivity(nextAttribute);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent back = new Intent(context, Home_Activity.class);
        back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(back);
        finish();
    }

}
