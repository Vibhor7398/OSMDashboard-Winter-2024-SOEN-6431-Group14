package de.storchp.opentracks.osmplugin.utils;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import de.storchp.opentracks.osmplugin.MapsActivity;
import de.storchp.opentracks.osmplugin.OptionsActivity;
import de.storchp.opentracks.osmplugin.R;

public class Filter extends MapsActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private final String[] filterOptions = {"Week", "Month", "Season"};
    static int size = 0;

//    public static void applyFilter(Context context) {
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Filter")
//                .setItems(filterOptions, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String selectedOption = filterOptions[which];
//                    }
//                })
//                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//    }

    public static int getBarSize(){
        return size;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filterbar);

        spinner = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,filterOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0 ->
                // Whatever you want to happen when the first item gets selected
                    size = 7;
            case 1 ->
                // Whatever you want to happen when the second item gets selected
                    size = 12;
            case 2 ->
                // Whatever you want to happen when the third item gets selected
                    size = 3;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        size = 7;
    }
}