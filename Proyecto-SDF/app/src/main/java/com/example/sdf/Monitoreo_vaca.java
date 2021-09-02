package com.example.sdf;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.DashPathEffect;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;

import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


public class Monitoreo_vaca extends AppCompatActivity {
    private LineChart lineChart1;
    private LineChart lineChart2;
    private LineDataSet lineDataSet1;
    private LineDataSet lineDataSet2;

    String id_vaca;
    String[] temperatura;
    String[] ph;
    String[] tiempo;
    String id_usuario;
    ImageView back2;

    long referencia;
    long[] tiempo2;
    float[] temperatura2;
    float[] ph2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo_vaca);
        lineChart1 = (LineChart) findViewById(R.id.lineChart1);
        lineChart2 = (LineChart) findViewById(R.id.lineChart2);
        back2 = (ImageView) findViewById(R.id.back2);

        Bundle obtenerData = getIntent().getExtras();
        id_vaca = obtenerData.getString("id_vaca");
        temperatura = obtenerData.getStringArray("temperatura");
        ph = obtenerData.getStringArray("ph");
        tiempo = obtenerData.getStringArray("tiempo");
        id_usuario = obtenerData.getString("id_usuario");

        back2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Menu_Usuario.class);
                intent.putExtra("id_usuario",id_usuario);
                startActivity(intent);
                finish();
            }
        });

        tiempo2 = dateFormat(tiempo);
        temperatura2 = dataFormat(temperatura);
        ph2 = dataFormat(ph);

        lineChart1.setTouchEnabled(true);
        lineChart1.setPinchZoom(true);
        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.activity_my_marker_view);
        mv.setChartView(lineChart1);
        lineChart1.setMarker(mv);
        renderData_temp();

        lineChart2.setTouchEnabled(true);
        lineChart2.setPinchZoom(true);
        MyMarkerView mv2 = new MyMarkerView(getApplicationContext(), R.layout.activity_my_marker_view);
        mv2.setChartView(lineChart2);
        lineChart2.setMarker(mv2);
        renderData_ph();

    }

    public long[] dateFormat(String[] tiempo) {
        long[] times = new long[tiempo.length];
        referencia = 0;
        for (int j = 0; j < tiempo.length; j++) {
            int hora = Integer.parseInt(tiempo[j].substring(0, 2));
            int min = Integer.parseInt(tiempo[j].substring(3, 5));
            int seg = Integer.parseInt(tiempo[j].substring(6, 8));
            Date d = new Date(2017, 8, 7, hora, min, seg);
            if (j == 0) referencia = d.getTime();
            times[j] = d.getTime() - referencia;
        }
        return times;
    }

    public float[] dataFormat(String[] d) {
        float[] data = new float[d.length];
        for (int k = 0; k < d.length; k++) {
            data[k] = Float.parseFloat(d[k]);
        }
        return data;
    }

    public void renderData_temp() {
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        IAxisValueFormatter xAxisFormatter = new MyXAxisValueFormatter(referencia);
        Log.d("ref",String.valueOf(referencia));
        XAxis xAxis = lineChart1.getXAxis();
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setDrawLimitLinesBehindData(true);

        LimitLine ll1 = new LimitLine(42f, "Maximum Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(38f, "Minimum Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = lineChart1.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(55f);
        leftAxis.setAxisMinimum(15f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);

        lineChart1.getAxisRight().setEnabled(false);
        setData_temp();
    }

    private void setData_temp() {

        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < tiempo2.length; i++) {
            values.add(new Entry((float) tiempo2[i], temperatura2[i]));
            Log.d("temperatura",String.valueOf(temperatura2[i]));
            Log.d("tiempo",String.valueOf(tiempo2[i]));
        }

        if (lineChart1.getData() != null &&
                lineChart1.getData().getDataSetCount() > 0) {
            lineDataSet1 = (LineDataSet) lineChart1.getData().getDataSetByIndex(0);
            lineDataSet1.setValues(values);
            lineChart1.getData().notifyDataChanged();
            lineChart1.notifyDataSetChanged();
        } else {
            lineDataSet1 = new LineDataSet(values, "Temperatura");
            lineDataSet1.setDrawIcons(false);
            lineDataSet1.enableDashedLine(10f, 5f, 0f);
            lineDataSet1.enableDashedHighlightLine(10f, 5f, 0f);
            lineDataSet1.setColor(Color.RED);
            lineDataSet1.setCircleColor(Color.RED);
            lineDataSet1.setLineWidth(1f);
            lineDataSet1.setCircleRadius(3f);
            lineDataSet1.setDrawCircleHole(false);
            lineDataSet1.setValueTextSize(9f);
            lineDataSet1.setDrawFilled(true);
            lineDataSet1.setFormLineWidth(1f);
            lineDataSet1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            lineDataSet1.setFormSize(15.f);


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet1);
            LineData data = new LineData(dataSets);
            lineChart1.setData(data);
        }
    }

    public void renderData_ph() {
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);


        IAxisValueFormatter xAxisFormatter2 = new MyXAxisValueFormatter(referencia);
        XAxis xAxis = lineChart2.getXAxis();
        xAxis.setValueFormatter(xAxisFormatter2);
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setDrawLimitLinesBehindData(true);

        LimitLine ll1 = new LimitLine(7f, "Maximum Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(5.6f, "Minimum Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = lineChart2.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(14f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);

        lineChart2.getAxisRight().setEnabled(false);
        setData_ph();
    }

    private void setData_ph() {

        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < tiempo2.length; i++) {
            values.add(new Entry((float) tiempo2[i], ph2[i]));
            Log.d("ph",String.valueOf(ph2[i]));
        }
        if (lineChart2.getData() != null &&
                lineChart2.getData().getDataSetCount() > 0) {
            lineDataSet2 = (LineDataSet) lineChart2.getData().getDataSetByIndex(0);
            lineDataSet2.setValues(values);
            lineChart2.getData().notifyDataChanged();
            lineChart2.notifyDataSetChanged();
        } else {
            lineDataSet2 = new LineDataSet(values, "pH");
            lineDataSet2.setDrawIcons(false);
            lineDataSet2.enableDashedLine(10f, 5f, 0f);
            lineDataSet2.enableDashedHighlightLine(10f, 5f, 0f);
            lineDataSet2.setColor(Color.DKGRAY);
            lineDataSet2.setCircleColor(Color.DKGRAY);
            lineDataSet2.setLineWidth(1f);
            lineDataSet2.setCircleRadius(3f);
            lineDataSet2.setDrawCircleHole(false);
            lineDataSet2.setValueTextSize(9f);
            lineDataSet2.setDrawFilled(true);
            lineDataSet2.setFormLineWidth(1f);
            lineDataSet2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            lineDataSet2.setFormSize(15.f);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet2);
            LineData data = new LineData(dataSets);
            lineChart2.setData(data);
        }
    }

}