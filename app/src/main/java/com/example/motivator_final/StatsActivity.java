package com.example.motivator_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This activity is meant to draw and display the line chart, which contains information
 * about running sessions.
 *
 * @author  Maksim Ilmast
 * @version 1.0
 */
public class StatsActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<String> reversedData;
    public static final String MOVEMENT = "passing movements";

    /**
     * This is the onCreate method, which sets configuration for the layout and executes the chart drawing function.
     * Also it sets the listView and implements set on time listener.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        this.drawChart();

        lv = findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                GymData.getInstance().getReversedList()));  //GymData palautetaan käänteisessä järjestyksessä.

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nextActivity = new Intent(StatsActivity.this,
                        MovementActivity.class);
                nextActivity.putExtra(MOVEMENT, i);
                startActivity(nextActivity);
            }
        });
    }

    /**
     * This is the line chart drawing function.
     * @return Nothing.
     * https://github.com/AnyChart/AnyChart-Android
     */
    private void drawChart() {
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title(getResources().getString(R.string.velocity));
        cartesian.yAxis(0).title(getResources().getString(R.string.velocity_kmh));
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        List<DataEntry> dataSet = this.getDataSet();
        Set set = Set.instantiate();
        set.data(dataSet);
        Mapping dataSetMapping = set.mapAs("{ x: 'x', value: 'value' }");
        Line series1 = cartesian.line(dataSetMapping);
        series1.name("Your result:");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        anyChartView.setChart(cartesian);
    }

    /**
     * This is the function, which returns compiled datasets in order to use them as a basis for the chart.
     * @return Nothing.
     * https://github.com/AnyChart/AnyChart-Android
     */
    private List<DataEntry> getDataSet() {
        RunData runData = RunData.getInstance();
        List<DataEntry> dataSet =  new ArrayList<>();
        dataSet.addAll(runData.getRuns());
        return dataSet;
    }
}
