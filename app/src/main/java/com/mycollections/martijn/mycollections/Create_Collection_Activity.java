package com.mycollections.martijn.mycollections;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class Create_Collection_Activity extends ActionBarActivity {

    private static boolean first;
    private static Context context;
    private static String collectionName;
    private static ArrayList<String> attributes;
    private static String attrStyle;
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


        /*
        The buttons are given an OnClickListener
         */
        Button finish = (Button)findViewById(R.id.buttonFinishCollection);
        Button next = (Button)findViewById(R.id.buttonNextAttribute);
        finish.setOnClickListener(buttonClick);
        next.setOnClickListener(buttonClick);

        context = next.getContext();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_collection_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    Handles input from the spinner to set the type of data.
     */
    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            attrStyle = parent.getItemAtPosition(pos).toString();
        }
        public void onNothingSelected(AdapterView<?> parent) {
            attrStyle = "Text";
        }
    }

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String attrName = editText.getText().toString();
            switch(view.getId()){
                case R.id.buttonFinishCollection:
                    // NEED TO MAKE LATER
                    System.out.println("Now saving the database");
                    break;
                case R.id.buttonNextAttribute:
                    if(first){
                        // only a name is entered, only proceed when the name is at least one
                        // character long
                        if(attrName.length() > 0) {
                            Intent nextAttribute = new Intent(context, Create_Collection_Activity.class);
                            nextAttribute.putExtra("collectionName", attrName);
                            nextAttribute.putStringArrayListExtra("attributes", attributes);
                            startActivity(nextAttribute);
                            finish();
                        }else{
                            Toast toast = Toast.makeText(context, "Please enter a name",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }else{
                        if(attrName.length() > 0 && attrStyle != null) {
                            // add name and type to attributes list before starting new intent
                            attributes.add(attrName);
                            attributes.add(attrStyle);
                            Intent nextAttribute = new Intent(context, Create_Collection_Activity.class);
                            nextAttribute.putExtra("collectionName", collectionName);
                            nextAttribute.putStringArrayListExtra("attributes", attributes);
                            startActivity(nextAttribute);
                            finish();
                        }
                        else{
                            Toast toast = Toast.makeText(context, "Please enter a name and a type",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
            }
        }
    };
}
