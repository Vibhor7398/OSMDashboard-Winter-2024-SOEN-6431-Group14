package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.util.Log;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.time.LocalDate;
import java.util.ArrayList;

public class DistanceGraph extends MapsActivity {
    private BarChart barChart;
    private Spinner filterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distancegraph);

        // Initialize views
        barChart = findViewById(R.id.bar1Chart);
        filterSpinner = findViewById(R.id.filter_spinner);

        // Populate spinner with filter options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFilter = parent.getItemAtPosition(position).toString();
                updateChart(selectedFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set default selection
        String defaultFilter = getResources().getStringArray(R.array.filter_options)[0];
        updateChart(defaultFilter);
    }

    private void updateChart(String selectedFilter) {
        switch (selectedFilter) {
            case "Week":
                drawWeeklyChart();
                break;
            case "Month":
                drawMonthlyChart();
                break;
            case "Season":
                drawSeasonalChart();
                break;
            default:
                Log.e("DistanceGraph", "Invalid filter selected");
        }
    }

    private void drawWeeklyChart() {
        LocalDate currentDate = LocalDate.now();
        LocalDate weekStart = currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);
        ArrayList
                <BarEntry> entries = new ArrayList<>();
        int[] barHeight = new int[7]; // One entry for each day of the week

        // Loop through the week starting from Sunday
        for (int i = 0; i < 7; i++) {
            LocalDate currentDay = weekStart.plusDays(i);
            AggregateDailyData data = getRandomData(currentDay);
            barHeight[i] = (int) data.distance;
        }

        // Add entries to the chart data
        for (int i = 0; i < 7; i++) {
            entries.add(new BarEntry(i, barHeight[i]));
        }

        // Set up chart data and appearance
        setChartData(entries, "Day", barChart);
    }

    private void drawMonthlyChart() {
        LocalDate currentDate = LocalDate.now();
        int daysInMonth = currentDate.lengthOfMonth();
        ArrayList
                <BarEntry> entries = new ArrayList<>();
        int[] barHeight = new int[daysInMonth];

        // Loop through the month
        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate currentDay = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), i);
            AggregateDailyData data = getRandomData(currentDay);
            barHeight[i - 1] = (int) data.distance;
        }

        // Add entries to the chart data
        for (int i = 0; i < daysInMonth; i++) {
            entries.add(new BarEntry(i, barHeight[i]));
        }

        // Set up chart data and appearance
        setChartData(entries, "Day", barChart);
    }

    private void drawSeasonalChart() {
        LocalDate currentDate = LocalDate.now();
        LocalDate start = currentDate.minusDays(currentDate.getDayOfYear() + 1);
        LocalDate end = currentDate;
        ArrayList
                <BarEntry> entries = new ArrayList<>();
        int[] barHeight = new int[3]; // One entry for each season

        // Define start dates for each season
        LocalDate startWinter = LocalDate.of(start.getYear() + 1, 1, 1);
        LocalDate startSummer = LocalDate.of(start.getYear(), 5, 1);
        LocalDate startFall = LocalDate.of(start.getYear(), 9, 1);

        // Loop through the time range and accumulate distances for each season
        while (start.isBefore(end)) {
            AggregateDailyData data = getRandomData(start);
            if (start.isBefore(startSummer)) {
                barHeight[0] += data.distance;
            } else if (start.isBefore(startFall)) {
                barHeight[1] += data.distance;
            } else if (start.isBefore(startWinter)) {
                barHeight[2] += data.distance;
            }
            start = start.plusDays(1);
        }

        // Add entries to the chart data
        for (int i = 0; i < 3; i++) {
            entries.add(new BarEntry(i, barHeight[i]));
        }

        // Set up chart data and appearance
        setChartData(entries, "Season", barChart);
    }

    private void setChartData(ArrayList
                                      <BarEntry> entries, String label, BarChart chart) {
        BarDataSet dataSet = new BarDataSet(entries, label);
        dataSet.setColor(Color.RED); // Set the color to red
        BarData barData = new BarData(dataSet);
        chart.setData(barData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(entries.size());

        Description description = new Description();
        description.setText("Time Distribution");
        description.setTextSize(16f);
        description.setYOffset(2f);
        chart.setDescription(description);

        chart.invalidate();
    }

    private AggregateDailyData getRandomData(LocalDate date) {
        int maxRuns = 40;
        int runs = (int) Math.ceil(Math.random() * maxRuns);
        double distance = (Math.random() * 10 + 10) * runs;
        double duration = distance * runs * (Math.random() + 35);

        // Logging
        Log.d("DataDebug", "Date: " + date + ", Distance: " + distance + ", Runs: " + runs + ", Duration: " + duration);

        return new AggregateDailyData(date, runs, distance, duration);
    }
}