
package com.example.chartgraph;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Locale;


public class BarChartActivity extends AppCompatActivity {
    private BarChart chart;
    private TextView tvResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        chart = findViewById(R.id.barChart);
        tvResults = findViewById(R.id.tvResults);
        setupBarChart();

        double[] percentages = getIntent().getDoubleArrayExtra("percentages");
        if (percentages != null) {
            displayResults(percentages);
            setChartData(percentages);
        }
    }
    private void setupBarChart() {
        // Setup chart configurations
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // Minimum interval between axis values
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"A", "B", "C", "D", "F"}));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawLabels(false); // no labels on the right side
    }
    private void displayResults(double[] percentages) {
        StringBuilder results = new StringBuilder();
        for (int i = 0; i < percentages.length; i++) {
            results.append(String.format(Locale.getDefault(), "Grade %s: %.2f%%\n", "ABCDE".charAt(i), percentages[i]));
        }
        tvResults.setText(results.toString());
    }
    private void setChartData(double[] percentages) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < percentages.length; i++) {
            entries.add(new BarEntry(i, (float) percentages[i]));
        }
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
        BarDataSet dataSet = new BarDataSet(entries, "Grades of Students");
        dataSet.setColors(colors);
        dataSet.setDrawValues(true);
        BarData barData = new BarData(dataSet);
        chart.setData(barData);
        chart.invalidate(); // refresh
    }
}
