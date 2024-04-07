package de.storchp.opentracks.osmplugin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsActivity extends MapsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_activity);

        // Find the RadioGroup in the layout
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        // Set listener for radio button changes
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                // Handle radio button selection
                String selectedText = radioButton.getText().toString();
                Toast.makeText(OptionsActivity.this, "Selected: " + selectedText, Toast.LENGTH_SHORT).show();


                switch (selectedText) {
                    case "Bar Graph 1" -> {
                        Intent intent = new Intent(OptionsActivity.this, option1.class);
                        startActivity(intent);
                    }
                    case "Bar Graph 2" -> {
                        Intent intent = new Intent(OptionsActivity.this, option2.class);
                        startActivity(intent);
                    }
                    case "Bar Graph 3" -> {
                        Intent intent = new Intent(OptionsActivity.this, option3.class);
                        startActivity(intent);
                    }
                    case "Line Chart" -> {
                        Intent intent = new Intent(OptionsActivity.this, option4.class);
                        startActivity(intent);
                    }
                    case "Distance Graph" -> {
                        Intent intent = new Intent(OptionsActivity.this, DistanceGraph.class);
                        startActivity(intent);
                    }
                    case "Duration Graph" -> {
                        Intent intent = new Intent(OptionsActivity.this, DurationGraph.class);
                        startActivity(intent);
                    }
                    case "Runs Graph" -> {
                        Intent intent = new Intent(OptionsActivity.this, RunsGraph.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
