package de.storchp.opentracks.osmplugin;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import de.storchp.opentracks.osmplugin.dashboardapi.TrackPoint;


public class option4 extends MapsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option4);
        //List<Float> speeds = Arrays.asList(10f, 12f, 13f, 15f, 20f, 18f, 16f, 19f, 21f, 23f, 25f, 29f, 31f,34f,37f,40f,43f, 45f, 47f, 49f,51f, 54f, 58f, 60f,63f,67f,69f, 71f, 73f, 79f);

        // Calculate the moving average entries
        //List<Entry> movingAverageEntries = getMovingAverageEntries(speeds);

        // Setup the chart
        LineChart chart = findViewById(R.id.lineChart);
        Spinner windowSizeSpinner = findViewById(R.id.spinner_window_size);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.window_size_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        windowSizeSpinner.setAdapter(adapter);

        //windowSizeSpinner.setSelection(adapter.getPosition("5"));//default

        windowSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int windowSize = Integer.parseInt(parentView.getItemAtPosition(position).toString());
                List<Entry> movingAverageEntries = getMovingAverageEntries(windowSize);
                List<Entry> timeAverageEntries = getTimeAverageEntries(windowSize);
                setUpChart(chart, movingAverageEntries);
                setUpChart(chart, timeAverageEntries);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
//                if(movingAverageEntries != null && chart != null) {
//                    List<Entry> movingAverageEntries = getMovingAverageEntries(speeds, 5); // Using 5 as the default window size
//                    setUpChart(chart, movingAverageEntries);
//                }
            }
        });

    }


    private List<Entry> getTimeAverageEntries(int windowSize){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Entry> entries = new ArrayList<>();
        for (int i = windowSize - 1; i < TrackPoint.speedTimeEntries.size(); i++) {
            float sum = 0;
            for (int j = i - (windowSize - 1); j < i; j++) { // Note the change from j <= i to j < i
                String currentTimeStr = TrackPoint.speedTimeEntries.get(j).second;
                String nextTimeStr = TrackPoint.speedTimeEntries.get(j + 1).second;

                LocalDateTime currentTime = LocalDateTime.parse(currentTimeStr, dateTimeFormatter);
                LocalDateTime nextTime = LocalDateTime.parse(nextTimeStr, dateTimeFormatter);

                Duration duration = Duration.between(currentTime, nextTime);
                sum += (float)duration.getSeconds();
            }
            // Note: You may need to adjust how you calculate 'sum' based on what you're trying to achieve
            float average = sum / windowSize;
            entries.add(new Entry(i, average)); // Use 'i' as X-axis or convert time string if needed
        }
        return entries;
    }

    private List<Entry> getMovingAverageEntries(int windowSize) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < TrackPoint.speedTimeEntries.size(); i++) {
            if (i >= windowSize - 1) {
                float sum = 0;
                for (int j = i - (windowSize - 1); j <= i; j++) {
                    sum += TrackPoint.speedTimeEntries.get(j).first.floatValue();
                }
                float average = sum / windowSize;
                entries.add(new Entry(i, average)); // Use 'i' as X-axis or convert time string if needed
            }
        }
        return entries;
    }

    private void setUpChart(LineChart lineChart, List<Entry> entries){
        // Create a LineDataSet with the sample data
        LineDataSet dataSet = new LineDataSet(entries, "Speed");
        dataSet.setColor(Color.GREEN);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setLineWidth(2.5f); // Make the line a bit thicker
        dataSet.setCircleRadius(4f); // Increase the data point circle size
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setDrawValues(false); // Disable drawing values on top of the data points
        dataSet.setDrawFilled(false); // Fill the area below the line
        //dataSet.setFillColor(Color.BLUE); // Set a fill color
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Create a LineData object with the LineDataSet
        LineData lineData = new LineData(dataSet);

        // Set the LineData to the LineChart
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(Color.LTGRAY); // Set grid line color
        xAxis.setGridLineWidth(1f); // Set grid line width
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12f);
        xAxis.setAxisLineWidth(2f);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setGridColor(Color.LTGRAY); // Set grid line color
        leftAxis.setGridLineWidth(1f);
        leftAxis.setTextSize(12f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setAxisLineWidth(2f);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(12f);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(10f);
        legend.setXEntrySpace(5f);
        legend.setFormToTextSpace(5f);

        lineChart.setTouchEnabled(false);


        lineChart.getAxisRight().setEnabled(false);


//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter());
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1f);
//        xAxis.setLabelCount(12);
        // Customize the LineChart as needed
        Description description = new Description();
        description.setText("Time");
        lineChart.setDescription(description);
        description.setTextColor(Color.BLACK);
        description.setTextSize(12f);

        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        // lineChart.animateXY(1000, 1000);
        lineChart.invalidate(); // Refresh the chart
    }
}