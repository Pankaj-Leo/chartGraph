package com.example.chartgraph;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;

public class MainActivity extends AppCompatActivity {
    private EditText etTotalStudents, etAStudents, etBStudents, etCStudents, etDStudents, etFStudents;
    private Button btnCompute;
    private BarChart chart;
    private TextView tvResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //Input fields
            etTotalStudents = findViewById(R.id.etTotalStudents);
            etAStudents = findViewById(R.id.etAStudents);
            etBStudents = findViewById(R.id.etBStudents);
            etCStudents = findViewById(R.id.etCStudents);
            etDStudents = findViewById(R.id.etDStudents);
            etFStudents = findViewById(R.id.etFStudents);
            btnCompute = findViewById(R.id.btnCompute);

            btnCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateInputs()) {
                    return;
                }
                int totalStudents = Integer.parseInt(etTotalStudents.getText().toString());
                int[] studentCounts = {
                        Integer.parseInt(etAStudents.getText().toString()),
                        Integer.parseInt(etBStudents.getText().toString()),
                        Integer.parseInt(etCStudents.getText().toString()),
                        Integer.parseInt(etDStudents.getText().toString()),
                        Integer.parseInt(etFStudents.getText().toString())
                };
                int sumOfStudents = 0;
                for (int count : studentCounts) {
                    sumOfStudents += count;
                }
                if (sumOfStudents != totalStudents) {
                    showAlert("Error", "Sum of students in all categories must equal total number of students.");
                    return;
                }
                double[] percentages = new double[studentCounts.length]; //Calculate Percentages
                for (int i = 0; i < studentCounts.length; i++) {
                    percentages[i] = (double) studentCounts[i] / totalStudents * 100;
                }
                Intent intent = new Intent(MainActivity.this, BarChartActivity.class); //create intent for Bar Chart
                intent.putExtra("percentages", percentages);
                startActivity(intent);
            }
        });
    } catch (Exception e) {
            Log.e("ErrorTag", "An error occurred: ", e);
        }
    }
    private boolean validateInputs() {
        if (etTotalStudents.getText().toString().isEmpty() ||
                etAStudents.getText().toString().isEmpty() ||
                etBStudents.getText().toString().isEmpty() ||
                etCStudents.getText().toString().isEmpty() ||
                etDStudents.getText().toString().isEmpty() ||
                etFStudents.getText().toString().isEmpty()) {
            showAlert("Error", "All fields must be filled out.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

}

