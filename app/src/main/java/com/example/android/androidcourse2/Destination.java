package com.example.android.androidcourse2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Destination extends AppCompatActivity {

    private List<String> allLocations;
    private List<String> allVisits;
    private List<String> alladdresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setSpinnerValues();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /*
    Function for loading values to spinner.
    Only locations with three and more visits will be loaded as favourite.
     */
    private void setSpinnerValues() {
        allLocations = new ArrayList<String> (Arrays.asList(getResources().getStringArray(R.array.location_name)));
        allVisits = new ArrayList<String> (Arrays.asList(getResources().getStringArray(R.array.location_visits)));
        alladdresses = new ArrayList<String> (Arrays.asList(getResources().getStringArray(R.array.location_address)));
        List<String> favouriteLocations = new ArrayList<String>();


        Spinner spinner = (Spinner) findViewById(R.id.favourite_location_spinner);

        Integer counter = 0;
        for (int i = 0; i < allLocations.size(); i++) {
            if (Integer.parseInt(allVisits.get(i)) > 2) {
                favouriteLocations.add(allLocations.get(i));
            }
        }

        ArrayAdapter<String> favouriteLocationSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, favouriteLocations);
        spinner.setAdapter(favouriteLocationSpinner);
    }

    /*
    Hide unnecesary button and show layout for adding new location and address
     */
    public void AddNew (View v) {
        Button addNewButton = (Button) findViewById(R.id.add_new_button);
        addNewButton.setVisibility(Button.INVISIBLE);
        LinearLayout addNewLayout = (LinearLayout) findViewById(R.id.add_new_layout);
        addNewLayout.setVisibility(LinearLayout.VISIBLE);
    }

    /*
    Search available vehicles to get you to one of your favourite and most visited places vithout typing their addresses.
    I tried to update number of visits into strings.xml, and then I find out that it is impossible and my hole idea doesn't have sence now.
    Maybe we can use some database, in later classes for this.
     */
    public void Find(View v) {
        Spinner spinner = (Spinner) findViewById(R.id.favourite_location_spinner);
        String locationName = spinner.getSelectedItem().toString();
        Integer index = -1;
        index = allLocations.indexOf(locationName);
        if (index != -1) allVisits.set(index, Integer.toString(Integer.parseInt(allVisits.get(index)) + 1));
        Toast.makeText(getBaseContext(), "Searching for available vehicles to get you " + locationName, Toast.LENGTH_SHORT).show();
        
    }

    public void FindNew (View v) {
        EditText newLocationEdit = (EditText) findViewById(R.id.location_name_edit);
        EditText newaddressEdit = (EditText) findViewById(R.id.location_address_edit);
        String newLocation = newLocationEdit.getText().toString();
        String newaddress = newaddressEdit.getText().toString();

        //Adding new data to array of favourite locations;
        Integer arrayLength = allLocations.size();
        allLocations.add(arrayLength, newLocation);
        alladdresses.add(arrayLength, newaddress);
        allVisits.add(arrayLength, "1"); //first visit for all new locations

        Toast.makeText(getBaseContext(), "Searching for available vehicles to get you " + newLocation, Toast.LENGTH_SHORT).show();
        Button addNewButton = (Button) findViewById(R.id.add_new_button);
        addNewButton.setVisibility(Button.VISIBLE);
        LinearLayout addNewLayout = (LinearLayout) findViewById(R.id.add_new_layout);
        addNewLayout.setVisibility(LinearLayout.INVISIBLE);
    }

}
